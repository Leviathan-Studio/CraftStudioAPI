package lib.craftstudio.client.json;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.io.Charsets;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lib.craftstudio.common.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

public class CSJsonReader
{
    JsonObject root;
    String     modid;

    public CSJsonReader(ResourceLocation resourceIn) {
        JsonParser jsonParser = new JsonParser();
        BufferedReader reader = null;
        IResource iResource = null;
        StringBuilder strBuilder = new StringBuilder();

        try {
            iResource = Minecraft.getMinecraft().getResourceManager().getResource(resourceIn);
            reader = new BufferedReader(new InputStreamReader(iResource.getInputStream(), Charsets.UTF_8));
            String s;
            while ((s = reader.readLine()) != null)
                strBuilder.append(s);
            Object object = jsonParser.parse(strBuilder.toString());
            this.root = (JsonObject) object;
            this.modid = iResource.getResourceLocation().getResourceDomain();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                iResource.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public CSReadedModel readModel() {

        CSReadedModel model = new CSReadedModel();
        CSReadedModelBlock parent;
        JsonObject jsonBlock;

        // modelReader.readTextureFileSize();

        // model.textureHeight = modelReader.getTextureHeight();
        // model.textureWidth = modelReader.getTextureWidth();

        model.modid = strNormalize(this.modid);
        model.name = strNormalize(this.root.get("title").getAsString());

        JsonArray tree = this.root.getAsJsonArray("tree");
        for (JsonElement element : tree) {
            jsonBlock = element.getAsJsonObject();

            parent = new CSReadedModelBlock();
            model.parents.add(parent);

            this.readModelBlock(jsonBlock, parent);

        }
        return model;

    }

    private void readModelBlock(JsonObject jsonBlock, CSReadedModelBlock block) {
        this.readModelBlock(jsonBlock, block, null, null);
    }

    private void readModelBlock(JsonObject jsonBlock, CSReadedModelBlock block, CSReadedModelBlock parent, Vector3f parentOffset) {
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

        array = jsonBlock.getAsJsonArray("vertexCoords");
        int i = 0;
        Vector3f startVert, endVert;
        if (array != null) {
            vertexArray = array.get(1).getAsJsonArray();
            startVert = new Vector3f(vertexArray.get(0).getAsFloat(), vertexArray.get(1).getAsFloat(), vertexArray.get(2).getAsFloat());
            vertexArray = array.get(6).getAsJsonArray();
            endVert = new Vector3f(vertexArray.get(0).getAsFloat(), vertexArray.get(1).getAsFloat(), vertexArray.get(2).getAsFloat());

            Vector3f vSize = endVert.subtract(startVert);
            block.size = vSize;

            block.faceSize = new int[3];
            block.faceSize[0] = (int) sizeX;
            block.faceSize[1] = (int) -sizeY;
            block.faceSize[2] = (int) -sizeZ;

            block.boxSetup = startVert;
            block.boxSetup.y = -block.boxSetup.y;
            block.boxSetup.z = -block.boxSetup.z;
        }
        else {
            block.size = new Vector3f(sizeX, sizeY, sizeZ);

            block.boxSetup = new Vector3f(-sizeX / 2 + pivotOffsetX, -sizeY / 2 - pivotOffsetY, -sizeZ / 2 - pivotOffsetZ);
        }

        if (parent == null)
            block.rotationPoint = new Vector3f(posX, -posY + 24, -posZ);
        else
            block.rotationPoint = new Vector3f(posX + parentOffset.x, -posY + parentOffset.y, -posZ + parentOffset.z);

        block.rotation = new Vector3f(rotationX, -rotationY, -rotationZ);

        array = jsonBlock.getAsJsonArray("texOffset");
        block.texOffset[0] = array.get(0).getAsInt();
        block.texOffset[1] = array.get(1).getAsInt();

        array = jsonBlock.getAsJsonArray("children");
        for (JsonElement element : array) {
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

    private static String strNormalize(String str) {
        return str.replaceAll("[^\\dA-Za-z ]", "_").replaceAll("\\s+", "_").replaceAll("[^\\p{ASCII}]", "_");
    }

}
