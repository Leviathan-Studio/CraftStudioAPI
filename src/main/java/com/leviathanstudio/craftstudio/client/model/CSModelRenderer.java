package com.leviathanstudio.craftstudio.client.model;

import com.leviathanstudio.craftstudio.client.util.MathHelper;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.PositionTextureVertex;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to render CSModelBoxs.
 *
 * @author Timmypote
 * @since 0.3.0
 */
@OnlyIn(Dist.CLIENT)
public class CSModelRenderer extends RendererModel {
    private final Matrix4f rotationMatrix = new Matrix4f();
    public List<CSModelBox> cubeCSList = new ArrayList<>();
    /**
     * Custom version, as parent variable is PRIVATE
     */
    private int textureOffsetX;
    /**
     * Custom version, as parent variable is PRIVATE
     */
    private int textureOffsetY;
    /**
     * Custom version, as parent variable is PRIVATE
     */
    private boolean compiled;
    /**
     * Custom version, as parent variable is PRIVATE
     */
    private int displayList;
    /**
     * Previous value of the matrix
     */
    private Matrix4f prevRotationMatrix = new Matrix4f();

    private Vector3f stretch = new Vector3f(1, 1, 1);

    /**
     * Default informations for un-animated models
     */
    private float defaultRotationPointX;
    private float defaultRotationPointY;
    private float defaultRotationPointZ;
    private Matrix4f defaultRotationMatrix = new Matrix4f();
    private Quat4f defaultRotationAsQuaternion;
    private float defaultOffsetX = 0;
    private float defaultOffsetY = 0;
    private float defaultOffsetZ = 0;
    private Vector3f defaultStretch = new Vector3f(1, 1, 1);

    public CSModelRenderer(Model modelbase, String partName, int xTextureOffset, int yTextureOffset) {
        super(modelbase, partName);
        this.setTextureSize(modelbase.textureWidth, modelbase.textureHeight);
        this.setTextureOffset(xTextureOffset, yTextureOffset);
    }

    private static CSModelRenderer getRendererModelFromNameAndBlock(String name, CSModelRenderer block) {
        CSModelRenderer childModel, result;

        if (block.boxName.equals(name))
            return block;

        for (RendererModel child : block.childModels)
            if (child instanceof CSModelRenderer) {
                childModel = (CSModelRenderer) child;
                result = getRendererModelFromNameAndBlock(name, childModel);
                if (result != null)
                    return result;
            }

        return null;
    }

    @Override
    public RendererModel setTextureOffset(int x, int y) {
        this.textureOffsetX = x;
        this.textureOffsetY = y;
        this.cubeList.size();
        return this;
    }

    public RendererModel addBox(String name, CSModelBox modelBox) {
        name = this.boxName + "." + name;
        this.cubeCSList.add(modelBox.setBoxName(name));
        return this;
    }

    public RendererModel addBox(String name, float par2, float par3, float par4, float par5, float par6, float par7) {
        name = this.boxName + "." + name;
        this.cubeCSList.add(new CSModelBox(this, this.textureOffsetX, this.textureOffsetY, par2, par3, par4, par5, par6, par7).setBoxName(name));
        return this;
    }

    public RendererModel addBox(float posX, float posY, float posZ, float sizeX, float sizeY, float sizeZ) {
        this.cubeCSList.add(new CSModelBox(this, this.textureOffsetX, this.textureOffsetY, posX, posY, posZ, sizeX, sizeY, sizeZ));
        return this;
    }

    public RendererModel addBox(float posX, float posY, float posZ, float sizeX, float sizeY, float sizeZ, boolean mirror) {
        this.cubeCSList.add(new CSModelBox(this, this.textureOffsetX, this.textureOffsetY, posX, posY, posZ, sizeX, sizeY, sizeZ, mirror));
        return this;
    }

    public RendererModel addBox(PositionTextureVertex positionTextureVertex[], int[][] textUVs) {
        this.cubeCSList.add(new CSModelBox(this, positionTextureVertex, textUVs));
        return this;
    }

    public RendererModel addBox(PositionTextureVertex positionTextureVertex[], int[][] textUVs, boolean mirror) {
        this.cubeCSList.add(new CSModelBox(this, positionTextureVertex, textUVs, mirror));
        return this;
    }

    public RendererModel addBox(CSModelBox model) {
        this.cubeCSList.add(model);
        return this;
    }

    /**
     * Render model parts
     */
    @Override
    public void render(float scale) {
        if (!this.isHidden)
            if (this.showModel) {
                if (!this.compiled)
                    this.compileDisplayList(scale);

                GlStateManager.pushMatrix();

                GlStateManager.translated(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                FloatBuffer buf = MathHelper.makeFloatBuffer(this.rotationMatrix);
                GlStateManager.multMatrix(buf);
                GlStateManager.translated(this.offsetX * scale, this.offsetY * scale, this.offsetZ * scale);

                GlStateManager.pushMatrix();
                GlStateManager.scaled(this.stretch.x, this.stretch.y, this.stretch.z);
                GlStateManager.callList(this.displayList);
                GlStateManager.popMatrix();

                if (this.childModels != null)
                    for (int i = 0; i < this.childModels.size(); ++i)
                        this.childModels.get(i).render(scale);

                GlStateManager.popMatrix();

                this.prevRotationMatrix = this.rotationMatrix;
            }
    }

    /**
     * Allows the changing of Angles after a box has been rendered
     */
    @Override
    public void postRender(float scale) {
    }

    @Override
    public void renderWithRotation(float scale) {
    }

    /**
     * Set default rotation point (model with no animations) and set the current
     * rotation point.
     */
    public void setDefaultRotationPoint(float x, float y, float z) {
        this.defaultRotationPointX = x;
        this.defaultRotationPointY = y;
        this.defaultRotationPointZ = z;
        this.setRotationPoint(x, y, z);
    }

    public float getDefaultRotationPointX() {
        return this.defaultRotationPointX;
    }

    public float getDefaultRotationPointY() {
        return this.defaultRotationPointY;
    }

    public float getDefaultRotationPointZ() {
        return this.defaultRotationPointZ;
    }

    /**
     * Set the rotation point
     */
    @Override
    public void setRotationPoint(float x, float y, float z) {
        this.rotationPointX = x;
        this.rotationPointY = y;
        this.rotationPointZ = z;
    }

    /**
     * Reset the rotation point to the default values.
     */
    public void resetRotationPoint() {
        this.rotationPointX = this.defaultRotationPointX;
        this.rotationPointY = this.defaultRotationPointY;
        this.rotationPointZ = this.defaultRotationPointZ;
    }

    public Vector3f getPositionAsVector() {
        return new Vector3f(this.rotationPointX, this.rotationPointY, this.rotationPointZ);
    }

    public void setDefaultOffset(float x, float y, float z) {
        this.defaultOffsetX = x;
        this.defaultOffsetY = y;
        this.defaultOffsetZ = z;
        this.setOffset(x, y, z);
    }

    public void setOffset(float x, float y, float z) {
        this.offsetX = x;
        this.offsetY = y;
        this.offsetZ = z;
    }

    public void resetOffset() {
        this.offsetX = this.defaultOffsetX;
        this.offsetY = this.defaultOffsetY;
        this.offsetZ = this.defaultOffsetZ;
    }

    public Vector3f getOffsetAsVector() {
        return new Vector3f(this.offsetX, this.offsetY, this.offsetZ);
    }

    public void setDefaultStretch(float x, float y, float z) {
        this.defaultStretch = new Vector3f(x, y, z);
        this.setStretch(x, y, z);
    }

    public void setStretch(float x, float y, float z) {
        this.stretch = new Vector3f(x, y, z);
    }

    public void resetStretch() {
        this.stretch = this.defaultStretch;
    }

    public Vector3f getStretchAsVector() {
        return new Vector3f(this.stretch);
    }

    /**
     * Set rotation matrix setting also an initial default value (model with no
     * animations).
     */
    public void setInitialRotationMatrix(Matrix4f matrix) {
        this.defaultRotationMatrix = matrix;
        this.setRotationMatrix(matrix);
        Matrix4f mat = (Matrix4f) this.rotationMatrix.clone();
        mat.transpose();
        if (this.defaultRotationAsQuaternion == null)
            this.defaultRotationAsQuaternion = new Quat4f();
        this.defaultRotationAsQuaternion.set(mat);
    }

    /**
     * Set rotation matrix setting also an initial default value (model with no
     * animations).
     */
    public void setInitialRotationMatrix(float x, float y, float z) {
        Matrix4f mat = new Matrix4f();
        mat.set(MathHelper.quatFromEuler(x, y, z));
        mat.transpose();
        this.setInitialRotationMatrix(mat);
    }

    /**
     * Reset the rotation matrix to the default one.
     */
    public void resetRotationMatrix() {
        this.setRotationMatrix(this.defaultRotationMatrix);
    }

    public Matrix4f getRotationMatrix() {
        return this.rotationMatrix;
    }

    /**
     * Set the rotation matrix values based on the given matrix.
     */
    public void setRotationMatrix(Matrix4f matrix) {
        this.rotationMatrix.m00 = matrix.m00;
        this.rotationMatrix.m01 = matrix.m01;
        this.rotationMatrix.m02 = matrix.m02;
        this.rotationMatrix.m03 = matrix.m03;
        this.rotationMatrix.m10 = matrix.m10;
        this.rotationMatrix.m11 = matrix.m11;
        this.rotationMatrix.m12 = matrix.m12;
        this.rotationMatrix.m13 = matrix.m13;
        this.rotationMatrix.m20 = matrix.m20;
        this.rotationMatrix.m21 = matrix.m21;
        this.rotationMatrix.m22 = matrix.m22;
        this.rotationMatrix.m23 = matrix.m23;
        this.rotationMatrix.m30 = matrix.m30;
        this.rotationMatrix.m31 = matrix.m31;
        this.rotationMatrix.m32 = matrix.m32;
        this.rotationMatrix.m33 = matrix.m33;
    }

    public Quat4f getDefaultRotationAsQuaternion() {
        return new Quat4f(this.defaultRotationAsQuaternion);
    }

    /**
     * Compiles a GL display list for this model.
     */
    public void compileDisplayList(float scale) {
        this.displayList = GLAllocation.generateDisplayLists(1);
        GlStateManager.newList(this.displayList, GL11.GL_COMPILE);
        BufferBuilder vertexbuffer = Tessellator.getInstance().getBuffer();

        for (int i = 0; i < this.cubeCSList.size(); ++i)
            this.cubeCSList.get(i).render(vertexbuffer, scale);

        GlStateManager.endList();
        this.compiled = true;
    }

    /**
     * Getter
     */
    public List<CSModelBox> getCubeCSList() {
        return this.cubeCSList;
    }

}