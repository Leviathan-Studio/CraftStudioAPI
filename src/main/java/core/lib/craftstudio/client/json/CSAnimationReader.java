package lib.craftstudio.client.json;

import java.io.File;
import java.io.FileReader;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CSAnimationReader
{
    private JsonObject jsonObject;

    public CSAnimationReader(File fileInputAnimation) {
        JsonParser jsonParser = new JsonParser();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(fileInputAnimation.getAbsolutePath());
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

    public JsonObject getAnimationNodeList() {
        return this.jsonObject.getAsJsonObject("nodeAnimations");
    }

    public String getAnimationName() {
        return this.jsonObject.get("title").getAsString().replaceAll("[^\\dA-Za-z ]", "_").replaceAll("\\s+", "_").replaceAll("[^\\p{ASCII}]", "_");
    }

    public String getPartName(Entry<String, JsonElement> entrySet) {
        return entrySet.getKey().replaceAll("[^\\dA-Za-z ]", "_").replaceAll("\\s+", "_").replaceAll("[^\\p{ASCII}]", "_");
    }

    public JsonElement getPosition(Entry<String, JsonElement> entrySet) {
        return entrySet.getValue().getAsJsonObject().get("position");
    }

    public JsonElement getOffsetFromPivot(Entry<String, JsonElement> entrySet) {
        return entrySet.getValue().getAsJsonObject().get("offsetFromPivot");
    }

    public JsonElement getSize(Entry<String, JsonElement> entrySet) {
        return entrySet.getValue().getAsJsonObject().get("size");
    }

    public JsonElement getRotation(Entry<String, JsonElement> entrySet) {
        return entrySet.getValue().getAsJsonObject().get("rotation");
    }

    public JsonElement getStretch(Entry<String, JsonElement> entrySet) {
        return entrySet.getValue().getAsJsonObject().get("stretch");
    }

    public int getAnimationDuration() {
        return this.jsonObject.get("duration").getAsInt() / 2;
    }
}
