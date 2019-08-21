package com.leviathanstudio.craftstudio.client.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.client.exception.CSMalformedJsonException;
import com.leviathanstudio.craftstudio.client.exception.CSResourceNotFoundException;
import com.leviathanstudio.craftstudio.client.util.EnumFrameType;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.io.Charsets;

import javax.vecmath.Vector3f;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map.Entry;

/**
 * Class used to read json and extract a {@link CSReadedModel} or a
 * {@link CSReadedAnim}.
 *
 * @author Timmypote
 * @author ZeAmateis
 * @author Phenix246
 * @since 0.3.0
 */
@OnlyIn(Dist.CLIENT)
public class CSJsonReader {
    /**
     * The JsonObject that is the root of the file
     */
    private JsonObject root;
    /**
     * The resource location
     */
    private String ress;

    /**
     * Create a {@link CSJsonReader} link to the resource.
     *
     * @param resourceIn Location of the <i>model.csjsmodel</i> or the
     *                   <i>anim.csjsmodelanim</i>.
     * @throws CSResourceNotFoundException If the files doesn't exist.
     * @see #readModel()
     * @see #readAnim()
     */
    public CSJsonReader(ResourceLocation resourceIn) throws CSResourceNotFoundException {
        JsonParser jsonParser = new JsonParser();
        BufferedReader reader = null;
        IResource iResource = null;
        StringBuilder strBuilder = new StringBuilder();
        this.ress = resourceIn.toString();

        try {
            System.out.println(Minecraft.getInstance().getResourceManager().getResource(resourceIn).getLocation());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            iResource = Minecraft.getInstance().getResourceManager().getResource(resourceIn);
            reader = new BufferedReader(new InputStreamReader(iResource.getInputStream(), Charsets.UTF_8));
            String s;
            while ((s = reader.readLine()) != null)
                strBuilder.append(s);
            Object object = jsonParser.parse(strBuilder.toString());
            this.root = (JsonObject) object;
        } catch (FileNotFoundException fnfe) {
            throw new CSResourceNotFoundException(this.ress);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (iResource != null)
                    iResource.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Extract a block (and all its children) from a {@link JsonObject} and
     * place it in the {@link CSReadedModelBlock}.
     *
     * @param jsonBlock The object to read the information.
     * @param block     The block to place the information.
     */
    private static void readModelBlock(JsonObject jsonBlock, CSReadedModelBlock block) {
        readModelBlock(jsonBlock, block, null);
    }

    /**
     * Extract a child block from a {@link JsonObject} and place it in the
     * {@link CSReadedModelBlock}.
     *
     * @param jsonBlock    The object to read the information.
     * @param block        The block to place the information.
     * @param parentOffset The offset from pivot of the parent block.
     */
    private static void readModelBlock(JsonObject jsonBlock, CSReadedModelBlock block, Vector3f parentOffset) {
        final int[] vertexOrderConvert = new int[]{3, 2, 1, 0, 6, 7, 4, 5};
        JsonObject jsonChild;
        CSReadedModelBlock child;

        block.setName(strNormalize(jsonBlock.get("name").getAsString()));

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
        if (array != null) {
            block.setVertex(new float[8][3]);
            for (int i = 0; i < 8; i++) {
                vertexArray = array.get(vertexOrderConvert[i]).getAsJsonArray();
                block.getVertex()[i][0] = vertexArray.get(0).getAsFloat();
                block.getVertex()[i][1] = -vertexArray.get(1).getAsFloat();
                block.getVertex()[i][2] = -vertexArray.get(2).getAsFloat();
            }
            float stretchx = sizeX != 0.0F ? Math.abs(block.getVertex()[1][0] - block.getVertex()[0][0]) / sizeX : 1;
            float stretchy = sizeY != 0.0F ? Math.abs(block.getVertex()[3][1] - block.getVertex()[0][1]) / sizeY : 1;
            float stretchz = sizeZ != 0.0F ? Math.abs(block.getVertex()[4][2] - block.getVertex()[0][2]) / sizeZ : 1;

            for (int i = 0; i < 8; i++) {
                float vertex[] = block.getVertex()[i];
                vertex[0] = vertex[0] / stretchx;
                vertex[1] = vertex[1] / stretchy;
                vertex[2] = vertex[2] / stretchz;
            }

            block.setStretch(new Vector3f(stretchx, stretchy, stretchz));
        } else
            block.setStretch(new Vector3f(1, 1, 1));

        if (parentOffset == null)
            block.setRotationPoint(new Vector3f(posX, -posY + 24, -posZ));
        else
            block.setRotationPoint(new Vector3f(posX, -posY, -posZ));
        block.setRotation(new Vector3f(rotationX, -rotationY, -rotationZ));
        block.setOffset(new Vector3f(pivotOffsetX, -pivotOffsetY, -pivotOffsetZ));
        block.setSize(new Vector3f(sizeX, -sizeY, -sizeZ));

        array = jsonBlock.getAsJsonArray("texOffset");
        block.getTexOffset()[0] = array.get(0).getAsInt();
        block.getTexOffset()[1] = array.get(1).getAsInt();

        array = jsonBlock.getAsJsonArray("children");
        for (JsonElement element : array) {
            jsonChild = element.getAsJsonObject();
            child = new CSReadedModelBlock();
            block.getChilds().add(child);
            readModelBlock(jsonChild, child, new Vector3f(pivotOffsetX, -pivotOffsetY, -pivotOffsetZ));
        }

    }

    /**
     * Extract a block's informations and place them in a
     * {@link CSReadedAnimBlock}.
     *
     * @param entry The entry containing the informations.
     * @param block The block to store the informations.
     */
    private static void readAnimBlock(Entry<String, JsonElement> entry, CSReadedAnimBlock block) {
        block.setName(strNormalize(entry.getKey()));
        JsonObject objBlock = entry.getValue().getAsJsonObject(), objField;

        objField = objBlock.get("position").getAsJsonObject();
        addKFElement(objField, block, EnumFrameType.POSITION);
        objField = objBlock.get("offsetFromPivot").getAsJsonObject();
        addKFElement(objField, block, EnumFrameType.OFFSET);
        objField = objBlock.get("size").getAsJsonObject();
        addKFElement(objField, block, EnumFrameType.SIZE);
        objField = objBlock.get("rotation").getAsJsonObject();
        addKFElement(objField, block, EnumFrameType.ROTATION);
        objField = objBlock.get("stretch").getAsJsonObject();
        addKFElement(objField, block, EnumFrameType.STRETCH);
    }

    /**
     * Extract the element asked of all the keyframes.
     *
     * @param obj   The object with the keyframes.
     * @param block The block to store the keyframes.
     * @param type  type of element to add. See {@link CSReadedAnimBlock}.
     */
    private static void addKFElement(JsonObject obj, CSReadedAnimBlock block, EnumFrameType type) {
        int keyFrame;
        Vector3f value;
        JsonArray array;

        for (Entry<String, JsonElement> entry : obj.entrySet()) {
            keyFrame = Integer.parseInt(entry.getKey());
            array = entry.getValue().getAsJsonArray();
            switch (type) {
                case STRETCH:
                case SIZE:
                    value = new Vector3f(array.get(0).getAsFloat(), array.get(1).getAsFloat(), array.get(2).getAsFloat());
                    break;
                default:
                    value = new Vector3f(array.get(0).getAsFloat(), -array.get(1).getAsFloat(), -array.get(2).getAsFloat());
            }
            block.addKFElement(keyFrame, type, value);
        }
    }

    /**
     * Normalize a String.
     *
     * @param str The String to normalize.
     * @return The normalized String.
     */
    private static String strNormalize(String str) {
        return str.replaceAll("[^\\dA-Za-z ]", "_").replaceAll("\\s+", "_").replaceAll("[^\\p{ASCII}]", "_");
    }

    /**
     * Extract a {@link CSReadedModel} from a .csjsmodel file.
     *
     * @return A new {@link CSReadedModel} containing the informations of the
     * file.
     * @throws CSMalformedJsonException If the json does match the model structure
     */
    public CSReadedModel readModel() throws CSMalformedJsonException {

        CSReadedModel model = new CSReadedModel();
        CSReadedModelBlock parent;
        JsonObject jsonBlock;
        JsonElement jsEl;

        jsEl = this.root.get("title");
        if (jsEl == null)
            throw new CSMalformedJsonException("title", "String", this.ress);
        model.setName(strNormalize(jsEl.getAsString()));

        JsonArray tree = this.root.getAsJsonArray("tree");
        if (tree == null)
            throw new CSMalformedJsonException("tree", "Array", this.ress);
        for (JsonElement element : tree)
            if (element.isJsonObject()) {
                jsonBlock = element.getAsJsonObject();

                parent = new CSReadedModelBlock();
                model.getParents().add(parent);

                try {
                    readModelBlock(jsonBlock, parent);
                } catch (NullPointerException | ClassCastException | IllegalStateException e) {
                    // e.printStackTrace();
                    throw new CSMalformedJsonException(parent.getName() != null ? parent.getName() : "a parent block without name", this.ress);
                }
            }
        return model;
    }

    /**
     * Extract a {@link CSReadedAnim} from a .csjsmodelanim file.
     *
     * @return A new {@link CSReadedAnim} containing the informations of the
     * file.
     * @throws CSMalformedJsonException If the json does match the animation structure
     */
    public CSReadedAnim readAnim() throws CSMalformedJsonException {

        CSReadedAnim anim = new CSReadedAnim();
        CSReadedAnimBlock block;
        JsonElement jsEl;

        jsEl = this.root.get("title");
        if (jsEl == null)
            throw new CSMalformedJsonException("title", "String", this.ress);
        anim.setName(strNormalize(jsEl.getAsString()));
        jsEl = this.root.get("duration");
        if (jsEl == null)
            throw new CSMalformedJsonException("duration", "Integer", this.ress);
        anim.setDuration(jsEl.getAsInt());
        jsEl = this.root.get("holdLastKeyframe");
        if (jsEl == null)
            throw new CSMalformedJsonException("holdLastKeyframe", "Boolean", this.ress);
        anim.setHoldLastK(jsEl.getAsBoolean());

        jsEl = this.root.get("nodeAnimations");
        if (jsEl == null)
            throw new CSMalformedJsonException("nodeAnimations", "Object", this.ress);
        JsonObject nodeAnims = jsEl.getAsJsonObject();
        for (Entry<String, JsonElement> entry : nodeAnims.entrySet()) {
            block = new CSReadedAnimBlock();
            anim.getBlocks().add(block);
            try {
                readAnimBlock(entry, block);
            } catch (Exception e) {
                CraftStudioApi.getLogger().error(e.getMessage());
                throw new CSMalformedJsonException(block.getName() != null ? block.getName() : "a block without name", this.ress);
            }
        }
        return anim;
    }

}
