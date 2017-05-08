package com.leviathanstudio.craftstudio.client.json;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.leviathanstudio.craftstudio.client.CraftStudioModelNotFound;
import com.leviathanstudio.craftstudio.common.math.Vector3f;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.Charsets;

public class CSJsonReader
{
    JsonObject root;
    String     modid;

    public CSJsonReader(ResourceLocation resourceIn) throws CraftStudioModelNotFound
    {
        JsonParser jsonParser = new JsonParser();
        BufferedReader reader = null;
        IResource iResource = null;
        StringBuilder strBuilder = new StringBuilder();

        try
        {
            iResource = Minecraft.getMinecraft().getResourceManager().getResource(resourceIn);
            reader = new BufferedReader(new InputStreamReader(iResource.getInputStream(), Charsets.UTF_8));
            String s;
            while ((s = reader.readLine()) != null)
                strBuilder.append(s);
            Object object = jsonParser.parse(strBuilder.toString());
            this.root = (JsonObject) object;
            this.modid = iResource.getResourceLocation().getResourceDomain();
        } catch (FileNotFoundException fnfe)
        {
            throw new CraftStudioModelNotFound(resourceIn.toString());
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (reader != null)
                    reader.close();
                if (iResource != null)
                    iResource.close();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public CSReadedModel readModel()
    {

        CSReadedModel model = new CSReadedModel();
        CSReadedModelBlock parent;
        JsonObject jsonBlock;

        // modelReader.readTextureFileSize();

        // model.textureHeight = modelReader.getTextureHeight();
        // model.textureWidth = modelReader.getTextureWidth();

        model.modid = strNormalize(this.modid);
        model.name = strNormalize(this.root.get("title").getAsString());

        JsonArray tree = this.root.getAsJsonArray("tree");
        for (JsonElement element : tree)
        {
            jsonBlock = element.getAsJsonObject();

            parent = new CSReadedModelBlock();
            model.parents.add(parent);

            this.readModelBlock(jsonBlock, parent);

        }
        return model;

    }

    private void readModelBlock(JsonObject jsonBlock, CSReadedModelBlock block)
    {
        this.readModelBlock(jsonBlock, block, null, null);
    }

    private void readModelBlock(JsonObject jsonBlock, CSReadedModelBlock block, CSReadedModelBlock parent,
            Vector3f parentOffset)
    {
        final int[] vertexOrderConvert = new int[] { 3, 2, 1, 0, 6, 7, 4, 5 };
        JsonObject jsonChild;
        CSReadedModelBlock child;

        block.name = strNormalize(jsonBlock.get("name").getAsString());

        JsonArray array = jsonBlock.getAsJsonArray("size"), vertexArray;
        float sizeX = array.get(0).getAsFloat();
        float sizeY = array.get(1).getAsFloat();
        float sizeZ = array.get(2).getAsFloat();

        array = jsonBlock.getAsJsonArray("position");
        float posX = array.get(0).getAsFloat();
        float posY = array.get(1).getAsFloat();
        float posZ = array.get(2).getAsFloat();

        array = jsonBlock.getAsJsonArray("rotation");
        float rotationX = array.get(0).getAsFloat();
        float rotationY = array.get(1).getAsFloat();
        float rotationZ = array.get(2).getAsFloat();

        array = jsonBlock.getAsJsonArray("offsetFromPivot");
        float pivotOffsetX = array.get(0).getAsFloat();
        float pivotOffsetY = array.get(1).getAsFloat();
        float pivotOffsetZ = array.get(2).getAsFloat();

        // It may need improvement

        array = jsonBlock.getAsJsonArray("vertexCoords");
        Vector3f vertex;
        if (array != null)
        {
            block.vertex = new float[8][3];
            for (int i = 0; i < 8; i++)
            {
                vertexArray = array.get(vertexOrderConvert[i]).getAsJsonArray();
                block.vertex[i][0] = vertexArray.get(0).getAsFloat() + pivotOffsetX;
                block.vertex[i][1] = vertexArray.get(1).getAsFloat() - pivotOffsetY;
                block.vertex[i][2] = vertexArray.get(2).getAsFloat() - pivotOffsetZ;
            }
        }
        else
            block.boxSetup = new Vector3f(-sizeX / 2 + pivotOffsetX, -sizeY / 2 - pivotOffsetY,
                    -sizeZ / 2 - pivotOffsetZ);
        if (parent == null)
            block.rotationPoint = new Vector3f(posX, -posY + 24, -posZ);
        else
            block.rotationPoint = new Vector3f(posX + parentOffset.x, -posY + parentOffset.y, -posZ + parentOffset.z);
        block.rotation = new Vector3f(rotationX, -rotationY, -rotationZ);

        block.size = new Vector3f(sizeX, sizeY, sizeZ);

        array = jsonBlock.getAsJsonArray("texOffset");
        block.texOffset[0] = array.get(0).getAsInt();
        block.texOffset[1] = array.get(1).getAsInt();

        array = jsonBlock.getAsJsonArray("children");
        for (JsonElement element : array)
        {
            jsonChild = element.getAsJsonObject();
            child = new CSReadedModelBlock();
            block.childs.add(child);
            this.readModelBlock(jsonChild, child, block, new Vector3f(pivotOffsetX, -pivotOffsetY, -pivotOffsetZ));
        }

    }

    // public CSReadedAnim readAnim() {
    // CSReadedModel model = new CSReadedModel();
    // CSReadedModelBlock parent;
    // JsonObject jsonBlock;
    //
    // // modelReader.readTextureFileSize();
    //
    // // model.textureHeight = modelReader.getTextureHeight();
    // // model.textureWidth = modelReader.getTextureWidth();
    //
    // model.name = strNormalize(this.root.get("title").getAsString());
    //
    // JsonArray tree = root.getAsJsonArray("tree");
    // for (JsonElement element : tree) {
    // jsonBlock = element.getAsJsonObject();
    //
    // parent = new CSReadedModelBlock();
    // model.parents.add(parent);
    //
    // readModelBlock(jsonBlock, parent);
    //
    // }
    // return model;
    // }

    private static String strNormalize(String str)
    {
        return str.replaceAll("[^\\dA-Za-z ]", "_").replaceAll("\\s+", "_").replaceAll("[^\\p{ASCII}]", "_");
    }

}
