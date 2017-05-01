package lib.craftstudio.client.json;

import java.io.IOException;

import lib.craftstudio.common.math.Vector3f;

public class ModelClassGen
{

    public void generate(CSModelReader modelReader) throws IOException {

        CSReadedModel model = new CSReadedModel();
        CSReadedModelBlock parent;

        // modelReader.readTextureFileSize();
        //
        // model.textureHeight = modelReader.getTextureHeight();
        // model.textureWidth = modelReader.getTextureWidth();

        model.name = modelReader.getModelName();

        for (int i = 0; i < modelReader.getTreeArray().size(); i++) {

            parent = new CSReadedModelBlock();
            model.parents.add(parent);

            float sizeX = modelReader.getSize(i).get(0).getAsFloat();
            float sizeY = modelReader.getSize(i).get(1).getAsFloat();
            float sizeZ = modelReader.getSize(i).get(2).getAsFloat();

            float posX = modelReader.getPosition(i).get(0).getAsFloat();
            float posY = modelReader.getPosition(i).get(1).getAsFloat();
            float posZ = modelReader.getPosition(i).get(2).getAsFloat();

            float rotationX = modelReader.getRotation(i).get(0).getAsFloat();
            float rotationY = modelReader.getRotation(i).get(1).getAsFloat();
            float rotationZ = modelReader.getRotation(i).get(2).getAsFloat();

            float pivotOffsetX = modelReader.getOffsetFromPivot(i).get(0).getAsFloat();
            float pivotOffsetY = modelReader.getOffsetFromPivot(i).get(1).getAsFloat();
            float pivotOffsetZ = modelReader.getOffsetFromPivot(i).get(2).getAsFloat();

            parent.size = new Vector3f(sizeX, sizeY, sizeZ);
            // Taille

            parent.boxSetup = new Vector3f(-sizeX / 2 + pivotOffsetX, -sizeY / 2 - pivotOffsetY, -sizeZ / 2 - pivotOffsetZ);
            // Origine du block

            parent.rotationPoint = new Vector3f(posX, -posY + 24, -posZ);
            // Translation appliquée

            parent.rotation = new Vector3f(rotationX, -rotationY, -rotationZ);
            // Rotation initiale

            // classConstructor.addStatement("this.$N = new $T(this, \"$N\", $L,
            // $L)", modelReader.getPartName(i), ClassUtils.CS_MODEL_RENDERER,
            // modelReader.getPartName(i), modelReader.getTextOffset(0).get(0),
            // modelReader.getTextOffset(0).get(1))
            //
            // .addStatement("this.$N.addBox($LF, $LF, $LF, $L, $L, $L)",
            // modelReader.getPartName(i), vBoxSetup.x, vBoxSetup.y,
            // vBoxSetup.z,
            // (int) sizeX, (int) sizeY, (int) sizeZ)
            //
            // .addStatement("this.$N.setDefaultRotationPoint($LF, $LF, $LF)",
            // modelReader.getPartName(i), vRotationPoint.x, vRotationPoint.y,
            // vRotationPoint.z)
            //
            // .addStatement("this.$N.setInitialRotationMatrix($LF, $LF, $LF)",
            // modelReader.getPartName(i), vRotationAngle.x, vRotationAngle.y,
            // vRotationAngle.z)
            //
            // .addStatement("this.parts.put(this.$N.boxName, this.$N)",
            // modelReader.getPartName(i), modelReader.getPartName(i));

        }

        // modelClass
        // .addField(FieldSpec
        // .builder(ParameterizedTypeName.get(ClassName.get(HashMap.class),
        // TypeName.get(String.class), ClassUtils.CS_MODEL_RENDERER),
        // "parts", Modifier.PRIVATE)
        // .initializer("new $T<$T, $T>()", HashMap.class, String.class,
        // ClassUtils.CS_MODEL_RENDERER).build());
        //
        // modelReader.getChildArray(modelReader.getTreeArray(), modelReader,
        // model);
        //
        // for (int i = 0; i < modelReader.parent.size(); i++)
        // classConstructor.addStatement("this.$L.addChild($L)",
        // modelReader.parent.get(i).replaceAll("[^\\dA-Za-z ]",
        // "_").replaceAll("\\s+", "_").replaceAll("[^\\p{ASCII}]", "_"),
        // modelReader.child.get(i).replaceAll("[^\\dA-Za-z ]",
        // "_").replaceAll("\\s+", "_").replaceAll("[^\\p{ASCII}]", "_"));
        //
        // MethodSpec.Builder renderMethod;
        // if (MainFrame.renderBlockAnimatedCheckbox.isSelected())
        // renderMethod =
        // MethodSpec.methodBuilder("render").addParameter(ClassUtils.TILE_ENTITY,
        // "tileEntityIn")
        // .addStatement("$T modelScale = $NF", float.class, "0.0625")
        // .addStatement("$T.performAnimationInModel(parts, ($T) $N)",
        // ClassUtils.ANIMATION_HANDLER, ClassUtils.IANIMATED, "tileEntityIn");
        // else
        // renderMethod =
        // MethodSpec.methodBuilder("render").addAnnotation(Override.class).addParameter(ClassUtils.ENTITY,
        // "entityIn")
        // .addParameter(float.class, "limbSwing").addParameter(float.class,
        // "limbSwingAmount").addParameter(float.class, "ageInTicks")
        // .addParameter(float.class, "netHeadYaw").addParameter(float.class,
        // "headPitch").addParameter(float.class, "scale")
        // .addModifiers(Modifier.PUBLIC)
        // .addStatement("$T.performAnimationInModel(parts, ($T)$N)",
        // ClassUtils.ANIMATION_HANDLER, ClassUtils.IANIMATED, "entityIn");
        //
        // MethodSpec modelRenderNameMethod =
        // MethodSpec.methodBuilder("getModelRendererFromName").returns(ClassUtils.CS_MODEL_RENDERER)
        // .addParameter(String.class,
        // "name").addModifiers(Modifier.PUBLIC).addStatement("return
        // parts.get(name)").build();
        //
        // for (int i = 0; i < modelReader.getTreeArray().size(); i++)
        // if (MainFrame.renderBlockAnimatedCheckbox.isSelected())
        // renderMethod.addStatement("this.$N.render($N)",
        // modelReader.getPartName(i), "modelScale");
        // else
        // renderMethod.addStatement("this.$N.render($N)",
        // modelReader.getPartName(i), "scale");
        //
        // modelClass.addModifiers(Modifier.PUBLIC).superclass(ClassUtils.MODEL_BASE).addMethod(classConstructor.build()).addMethod(renderMethod.build())
        // .addMethod(modelRenderNameMethod);
        //
        // for (int i = 0; i < modelReader.getTreeArray().size(); i++) {
        // FieldSpec modelsPartField =
        // FieldSpec.builder(ClassUtils.CS_MODEL_RENDERER,
        // modelReader.getPartName(i), Modifier.PRIVATE).build();
        //
        // modelClass.addField(modelsPartField);
        //
        // }
        //
        // JavaFile javaFile = JavaFile.builder(ClassUtils.PACKAGE_NAME,
        // modelClass.build()).build();
        //
        // javaFile.writeTo(MainFrame.selectedDirectory);
    }

}