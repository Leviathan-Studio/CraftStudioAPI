package lib.craftstudio.client.json;

import java.io.IOException;
import java.util.Map.Entry;

import javax.lang.model.element.Modifier;

import org.apache.commons.lang3.ClassUtils;

import com.google.gson.JsonElement;

import scala.swing.MainFrame;

public class ChannelAnimClassGen
{

    public void generate(CSAnimationReader animationReader) throws IOException {

        MethodSpec.Builder classConstructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).addParameter(String.class, "name")
                .addParameter(float.class, "fps");

        if (MainFrame.loopCheckbox.isSelected())
            classConstructor.addStatement("super(name, fps, " + animationReader.getAnimationDuration() + ", Channel.EnumAnimationMode.LOOP)");
        else
            classConstructor.addStatement("super(name, fps, " + animationReader.getAnimationDuration() + ", Channel.EnumAnimationMode.LINEAR)");

        MethodSpec.Builder initializeAllFrames = MethodSpec.methodBuilder("initializeAllFrames").addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED);

        int keyFrameCount = 0;
        KeyFrameComparator keyFrameComparator = new KeyFrameComparator();

        keyFrameComparator.preInit(animationReader, initializeAllFrames);
        this.writeRotation(animationReader, initializeAllFrames, keyFrameCount);
        this.writePosition(animationReader, initializeAllFrames, keyFrameCount);
        keyFrameComparator.postInit(animationReader, initializeAllFrames);

        TypeSpec.Builder modelClass = TypeSpec.classBuilder("Channel" + animationReader.getAnimationName() + "Animation")
                .addModifiers(Modifier.PUBLIC).superclass(ClassUtils.CHANNEL).addMethod(classConstructor.build())
                .addMethod(initializeAllFrames.build());

        JavaFile javaFile = JavaFile.builder(ClassUtils.PACKAGE_NAME, modelClass.build()).build();

        javaFile.writeTo(MainFrame.selectedDirectory);
    }

    private void writePosition(CSAnimationReader animationReader, Builder initializeAllFrames, int keyFrameCount) {

        float positionX = 0.0F, positionY = 0.0F, positionZ = 0.0F;

        for (Entry<String, JsonElement> entrySet : animationReader.getAnimationNodeList().entrySet())
            for (Entry<String, JsonElement> entrySetRotation : animationReader.getPosition(entrySet).getAsJsonObject().entrySet()) {

                for (int j = 0; j < animationReader.getPosition(entrySet).getAsJsonObject().get(entrySetRotation.getKey()).getAsJsonArray()
                        .size(); j++) {

                    keyFrameCount = Integer.parseInt(entrySetRotation.getKey());

                    positionX = animationReader.getPosition(entrySet).getAsJsonObject().get(entrySetRotation.getKey()).getAsJsonArray().get(0)
                            .getAsFloat();

                    positionY = animationReader.getPosition(entrySet).getAsJsonObject().get(entrySetRotation.getKey()).getAsJsonArray().get(1)
                            .getAsFloat();

                    positionZ = animationReader.getPosition(entrySet).getAsJsonObject().get(entrySetRotation.getKey()).getAsJsonArray().get(2)
                            .getAsFloat();

                }
                try {

                    initializeAllFrames.addStatement(
                            "keyframe$L.modelRenderersTranslations.put(\"$L\", new $T($LF, $LF, $LF))", keyFrameCount / 2, animationReader
                                    .getPartName(entrySet).replaceAll("[^\\dA-Za-z ]", "_").replaceAll("\\s+", "_").replaceAll("[^\\p{ASCII}]", "_"),
                            ClassUtils.VECTOR_3F, positionX, -positionY, -positionZ);

                } catch (Exception e) {}
            }
    }

    private void writeRotation(CSAnimationReader animationReader, Builder initializeAllFrames, int keyFrameCount) {
        float rotationX = 0.0F, rotationY = 0.0F, rotationZ = 0.0F;

        for (Entry<String, JsonElement> entrySet : animationReader.getAnimationNodeList().entrySet())
            for (Entry<String, JsonElement> entrySetRotation : animationReader.getRotation(entrySet).getAsJsonObject().entrySet()) {

                for (int j = 0; j < animationReader.getRotation(entrySet).getAsJsonObject().get(entrySetRotation.getKey()).getAsJsonArray()
                        .size(); j++) {

                    keyFrameCount = Integer.parseInt(entrySetRotation.getKey());

                    rotationX = animationReader.getRotation(entrySet).getAsJsonObject().get(entrySetRotation.getKey()).getAsJsonArray().get(0)
                            .getAsFloat();

                    rotationY = animationReader.getRotation(entrySet).getAsJsonObject().get(entrySetRotation.getKey()).getAsJsonArray().get(1)
                            .getAsFloat();

                    rotationZ = animationReader.getRotation(entrySet).getAsJsonObject().get(entrySetRotation.getKey()).getAsJsonArray().get(2)
                            .getAsFloat();
                }
                try {
                    initializeAllFrames.addStatement(
                            "keyframe$L.modelRenderersRotations.put(\"$L\", new $T($LF, $LF, $LF))", keyFrameCount / 2, animationReader
                                    .getPartName(entrySet).replaceAll("[^\\dA-Za-z ]", "_").replaceAll("\\s+", "_").replaceAll("[^\\p{ASCII}]", "_"),
                            ClassUtils.QUATERNION, rotationX, -rotationY, -rotationZ);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }

}