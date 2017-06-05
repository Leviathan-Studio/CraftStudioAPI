package com.leviathanstudio.craftstudio.client.model;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import com.leviathanstudio.craftstudio.client.util.CraftStudioHelper;
import com.leviathanstudio.craftstudio.client.util.math.Matrix4f;
import com.leviathanstudio.craftstudio.client.util.math.Quat4fHelper;
import com.leviathanstudio.craftstudio.client.util.math.Quaternion;
import com.leviathanstudio.craftstudio.client.util.math.Vector3f;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CSModelRenderer extends ModelRenderer
{
    /** Custom version, as parent variable is PRIVATE */
    private int             textureOffsetX;

    /** Custom version, as parent variable is PRIVATE */
    private int             textureOffsetY;

    /** Custom version, as parent variable is PRIVATE */
    private boolean         compiled;

    /** Custom version, as parent variable is PRIVATE */
    private int             displayList;

    public List<CSModelBox> cubeCSList            = new ArrayList<>();

    private final Matrix4f  rotationMatrix        = new Matrix4f();
    /** Previous value of the matrix */
    private Matrix4f        prevRotationMatrix    = new Matrix4f();

    /** Default informations for un-animated models */
    private float           defaultRotationPointX;
    private float           defaultRotationPointY;
    private float           defaultRotationPointZ;
    private Matrix4f        defaultRotationMatrix = new Matrix4f();
    private Quaternion      defaultRotationAsQuaternion;

    public CSModelRenderer(ModelBase modelbase, String partName, int xTextureOffset, int yTextureOffset) {
        super(modelbase, partName);
        this.setTextureSize(modelbase.textureWidth, modelbase.textureHeight);
        this.setTextureOffset(xTextureOffset, yTextureOffset);
    }

    @Override
    public ModelRenderer setTextureOffset(int x, int y) {
        this.textureOffsetX = x;
        this.textureOffsetY = y;
        this.cubeList.size();
        return this;
    }

    public ModelRenderer addBox(String name, CSModelBox modelBox) {
        name = this.boxName + "." + name;
        this.cubeCSList.add(modelBox.setBoxName(name));
        return this;
    }

    public ModelRenderer addBox(String name, float par2, float par3, float par4, float par5, float par6, float par7) {
        name = this.boxName + "." + name;
        this.cubeCSList.add(new CSModelBox(this, this.textureOffsetX, this.textureOffsetY, par2, par3, par4, par5, par6, par7).setBoxName(name));
        return this;
    }

    public ModelRenderer addBox(float posX, float posY, float posZ, float sizeX, float sizeY, float sizeZ) {
        this.cubeCSList.add(new CSModelBox(this, this.textureOffsetX, this.textureOffsetY, posX, posY, posZ, sizeX, sizeY, sizeZ));
        return this;
    }

    public ModelRenderer addBox(float posX, float posY, float posZ, float sizeX, float sizeY, float sizeZ, boolean mirror) {
        this.cubeCSList.add(new CSModelBox(this, this.textureOffsetX, this.textureOffsetY, posX, posY, posZ, sizeX, sizeY, sizeZ, mirror));
        return this;
    }

    public ModelRenderer addBox(PositionTextureVertex positionTextureVertex[], int[][] textUVs) {
        this.cubeCSList.add(new CSModelBox(this, positionTextureVertex, textUVs));
        return this;
    }

    public ModelRenderer addBox(PositionTextureVertex positionTextureVertex[], int[][] textUVs, boolean mirror) {
        this.cubeCSList.add(new CSModelBox(this, positionTextureVertex, textUVs, mirror));
        return this;
    }

    public ModelRenderer addBox(CSModelBox model) {
        this.cubeCSList.add(model);
        return this;
    }

    private static CSModelRenderer getModelRendererFromNameAndBlock(String name, CSModelRenderer block) {
        CSModelRenderer childModel, result;

        if (block.boxName.equals(name))
            return block;

        for (ModelRenderer child : block.childModels)
            if (child instanceof CSModelRenderer) {
                childModel = (CSModelRenderer) child;
                result = getModelRendererFromNameAndBlock(name, childModel);
                if (result != null)
                    return result;
            }

        return null;
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

                GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);
                int i;

                if (this.rotationMatrix.isEmptyRotationMatrix()) {
                    if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
                        GlStateManager.callList(this.displayList);

                        if (this.childModels != null)
                            for (i = 0; i < this.childModels.size(); ++i)
                                this.childModels.get(i).render(scale);
                    }
                    else {
                        GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                        GlStateManager.callList(this.displayList);

                        if (this.childModels != null)
                            for (i = 0; i < this.childModels.size(); ++i)
                                this.childModels.get(i).render(scale);

                        GlStateManager.translate(-this.rotationPointX * scale, -this.rotationPointY * scale, -this.rotationPointZ * scale);
                    }
                }
                else {
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                    final FloatBuffer buf = CraftStudioHelper.makeFloatBuffer(this.rotationMatrix.intoArray());
                    GlStateManager.multMatrix(buf);

                    GlStateManager.callList(this.displayList);

                    if (this.childModels != null)
                        for (i = 0; i < this.childModels.size(); ++i)
                            this.childModels.get(i).render(scale);

                    GlStateManager.popMatrix();
                }

                GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);

                this.prevRotationMatrix = this.rotationMatrix;
            }
    }

    /**
     * Allows the changing of Angles after a box has been rendered
     */
    @Override
    public void postRender(float scale) {
        if (!this.isHidden)
            if (this.showModel) {
                if (!this.compiled)
                    this.compileDisplayList(scale);

                if (this.rotationMatrix.equals(this.prevRotationMatrix)) {
                    if (this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F)
                        GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
                }
                else {
                    GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);

                    GlStateManager.multMatrix(FloatBuffer.wrap(this.rotationMatrix.intoArray()));
                }
            }
    }

    @Override
    public void renderWithRotation(float scale) {}

    /**
     * Set default rotation point (model with no animations) and set the current
     * rotation point.
     */
    public void setDefaultRotationPoint(float par1, float par2, float par3) {
        this.defaultRotationPointX = par1;
        this.defaultRotationPointY = par2;
        this.defaultRotationPointZ = par3;
        this.setRotationPoint(par1, par2, par3);
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

    /** Set the rotation point */
    @Override
    public void setRotationPoint(float par1, float par2, float par3) {
        this.rotationPointX = par1;
        this.rotationPointY = par2;
        this.rotationPointZ = par3;
    }

    /** Reset the rotation point to the default values. */
    public void resetRotationPoint() {
        this.rotationPointX = this.defaultRotationPointX;
        this.rotationPointY = this.defaultRotationPointY;
        this.rotationPointZ = this.defaultRotationPointZ;
    }

    public Vector3f getPositionAsVector() {
        return new Vector3f(this.rotationPointX, this.rotationPointY, this.rotationPointZ);
    }

    @Deprecated
    public Quaternion getRotationAsQuaternion() {
        // Rotation from position ?!?
        return new Quaternion(Quat4fHelper.quaternionFromEulerAnglesInDegrees(this.getPositionAsVector().getX(), this.getPositionAsVector().getY(),
                this.getPositionAsVector().getZ()));
    }

    /**
     * Set rotation matrix setting also an initial default value (model with no
     * animations).
     */
    public void setInitialRotationMatrix(Matrix4f matrix) {
        this.defaultRotationMatrix = matrix;
        this.setRotationMatrix(matrix);
        this.defaultRotationAsQuaternion = CraftStudioHelper.getQuaternionFromMatrix(this.rotationMatrix);
    }

    /**
     * Set rotation matrix setting also an initial default value (model with no
     * animations).
     */
    public void setInitialRotationMatrix(float x, float y, float z) {
        this.setInitialRotationMatrix(new Matrix4f().set(new Quaternion(x, y, z)).transpose());
    }

    /** Set the rotation matrix values based on the given matrix. */
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

    /** Reset the rotation matrix to the default one. */
    public void resetRotationMatrix() {
        this.setRotationMatrix(this.defaultRotationMatrix);
    }

    public Matrix4f getRotationMatrix() {
        return this.rotationMatrix;
    }

    public Quaternion getDefaultRotationAsQuaternion() {
        return this.defaultRotationAsQuaternion.clone();
    }

    /**
     * Compiles a GL display list for this model.
     */
    public void compileDisplayList(float scale) {
        this.displayList = GLAllocation.generateDisplayLists(1);
        GlStateManager.glNewList(this.displayList, 4864);
        final VertexBuffer vertexbuffer = Tessellator.getInstance().getBuffer();

        for (int i = 0; i < this.cubeCSList.size(); ++i)
            this.cubeCSList.get(i).render(vertexbuffer, scale);

        GlStateManager.glEndList();
        this.compiled = true;
    }

    /** Getter */
    public List<CSModelBox> getCubeCSList() {
        return cubeCSList;
    }

}