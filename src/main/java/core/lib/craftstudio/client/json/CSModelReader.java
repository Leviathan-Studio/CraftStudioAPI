package lib.craftstudio.client.json;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.lang.model.element.Modifier;

import org.apache.commons.lang3.ClassUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lib.craftstudio.common.math.Vector3f;

public class CSModelReader
{
    private JsonObject       jsonObject;

    public ArrayList<String> parent;
    public ArrayList<String> child;

    private int              textureWidth, textureHeight;

    public CSModelReader(File fileInputModel) {

        this.parent = new ArrayList<>();
        this.child = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(fileInputModel.getAbsolutePath());
            Object object = jsonParser.parse(fileReader);
            this.jsonObject = (JsonObject) object;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public JsonArray getTreeArray() {
        return this.jsonObject.get("tree").getAsJsonArray();
    }

    public void getChildArray(JsonArray tree, CSModelReader modelReader, CSReadedModel model) {
        Iterator<?> iteratorTree = tree.iterator();
        CSReadedModelBlock child;

        while (iteratorTree.hasNext()) {
            JsonObject treeObj = (JsonObject) iteratorTree.next();

            if (treeObj.getAsJsonArray("children") != null) {
                this.getChildArray(treeObj.getAsJsonArray("children"), modelReader, model);

                for (JsonElement j : treeObj.getAsJsonArray("children").getAsJsonArray()) {

                    this.parent.add(treeObj.get("name").getAsString());
                    this.child.add(j.getAsJsonObject().get("name").getAsString());

                    float sizeX = j.getAsJsonObject().get("size").getAsJsonArray().get(0).getAsFloat();
                    float sizeY = j.getAsJsonObject().get("size").getAsJsonArray().get(1).getAsFloat();
                    float sizeZ = j.getAsJsonObject().get("size").getAsJsonArray().get(2).getAsFloat();

                    float posX = j.getAsJsonObject().get("position").getAsJsonArray().get(0).getAsFloat();
                    float posY = j.getAsJsonObject().get("position").getAsJsonArray().get(1).getAsFloat();
                    float posZ = j.getAsJsonObject().get("position").getAsJsonArray().get(2).getAsFloat();

                    float rotationX = j.getAsJsonObject().get("rotation").getAsJsonArray().get(0).getAsFloat();
                    float rotationY = j.getAsJsonObject().get("rotation").getAsJsonArray().get(1).getAsFloat();
                    float rotationZ = j.getAsJsonObject().get("rotation").getAsJsonArray().get(2).getAsFloat();

                    float pivotOffsetX = j.getAsJsonObject().get("offsetFromPivot").getAsJsonArray().get(0).getAsFloat();
                    float pivotOffsetY = j.getAsJsonObject().get("offsetFromPivot").getAsJsonArray().get(1).getAsFloat();
                    float pivotOffsetZ = j.getAsJsonObject().get("offsetFromPivot").getAsJsonArray().get(2).getAsFloat();

                    JsonArray parentPivot = treeObj.getAsJsonObject().get("offsetFromPivot").getAsJsonArray();

                    Vector3f vBoxSetup = new Vector3f(-sizeX / 2 + pivotOffsetX, -sizeY / 2 - pivotOffsetY, -sizeZ / 2 - pivotOffsetZ);

                    Vector3f vRotationPoint = new Vector3f(posX + parentPivot.get(0).getAsFloat(), -posY - parentPivot.get(1).getAsFloat(),
                            -posZ - parentPivot.get(2).getAsFloat());

                    Vector3f vRotationAngle = new Vector3f(rotationX, -rotationY, -rotationZ);

                    classConstructor.addStatement("this.$N = new $T(this, \"$N\", $L, $L)", this.getChildPartName(j), ClassUtils.CS_MODEL_RENDERER,
                            this.getChildPartName(j), j.getAsJsonObject().get("texOffset").getAsJsonArray().get(0),
                            j.getAsJsonObject().get("texOffset").getAsJsonArray().get(1));

                    if (j.getAsJsonObject().has("vertexCoords"))
                        classConstructor.addStatement("this.$N.addBox($LF, $LF, $LF, $L, $L, $L, true)", this.getChildPartName(j), vBoxSetup.x,
                                vBoxSetup.y, vBoxSetup.z, (int) sizeX, (int) sizeY, (int) sizeZ);
                    else
                        classConstructor.addStatement("this.$N.addBox($LF, $LF, $LF, $L, $L, $L)", this.getChildPartName(j), vBoxSetup.x, vBoxSetup.y,
                                vBoxSetup.z, (int) sizeX, (int) sizeY, (int) sizeZ);

                    classConstructor.addStatement("this.$N.setDefaultRotationPoint($LF, $LF, $LF)", this.getChildPartName(j), vRotationPoint.x,
                            vRotationPoint.y, vRotationPoint.z)

                            .addStatement("this.$N.setInitialRotationMatrix($LF, $LF, $LF)", this.getChildPartName(j), vRotationAngle.x,
                                    vRotationAngle.y, vRotationAngle.z)

                            .addStatement("this.parts.put(this.$N.boxName, this.$N)", this.getChildPartName(j), this.getChildPartName(j));

                    FieldSpec modelsPartField = FieldSpec.builder(ClassUtils.CS_MODEL_RENDERER, this.getChildPartName(j), Modifier.PRIVATE).build();

                    modelClass.addField(modelsPartField);

                }
            }
        }
    }

    public String getModelName() {
        return this.jsonObject.get("title").getAsString().replaceAll("[^\\dA-Za-z ]", "_").replaceAll("\\s+", "_").replaceAll("[^\\p{ASCII}]", "_");
    }

    public String getPartName(int partNumber) {
        return this.getTreeArray().get(partNumber).getAsJsonObject().get("name").getAsString().replaceAll("[^\\dA-Za-z ]", "_")
                .replaceAll("\\s+", "_").replaceAll("[^\\p{ASCII}]", "_");
    }

    public String getChildPartName(JsonElement j) {
        return j.getAsJsonObject().get("name").getAsString().replaceAll("[^\\dA-Za-z ]", "_").replaceAll("\\s+", "_").replaceAll("[^\\p{ASCII}]",
                "_");
    }

    public JsonArray getPosition(int partNumber) {
        return this.getTreeArray().get(partNumber).getAsJsonObject().get("position").getAsJsonArray();
    }

    public JsonArray getOffsetFromPivot(int partNumber) {
        return this.getTreeArray().get(partNumber).getAsJsonObject().get("offsetFromPivot").getAsJsonArray();
    }

    public JsonArray getSize(int partNumber) {
        return this.getTreeArray().get(partNumber).getAsJsonObject().get("size").getAsJsonArray();
    }

    public JsonArray getRotation(int partNumber) {
        return this.getTreeArray().get(partNumber).getAsJsonObject().get("rotation").getAsJsonArray();
    }

    public JsonArray getTextOffset(int partNumber) {
        return this.getTreeArray().get(partNumber).getAsJsonObject().get("texOffset").getAsJsonArray();
    }

    public int getTextureHeight() {
        return this.textureHeight;
    }

    public void setTextureHeight(int textureHeight) {
        this.textureHeight = textureHeight;
    }

    public int getTextureWidth() {
        return this.textureWidth;
    }

    public void setTextureWidth(int textureWidth) {
        this.textureWidth = textureWidth;
    }

    // public void readTextureFileSize() {
    // try {
    // BufferedImage bimg = ImageIO.read(MainFrame.fileInputTexture);
    // this.setTextureWidth(bimg.getWidth());
    // this.setTextureHeight(bimg.getHeight());
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
}