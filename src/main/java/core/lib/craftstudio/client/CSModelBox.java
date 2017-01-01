package lib.craftstudio.client;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CSModelBox extends ModelBox
{
    /**
     * The (x,y,z) vertex positions and (u,v) texture coordinates for each of
     * the 8 points on a cube
     */
    private final PositionTextureVertex[] vertexPositions;
    /** An array of 6 TexturedQuads, one for each face of a cube */
    private final TexturedQuad[]          quadList;

    public CSModelBox(ModelRenderer renderer, int textureX, int textureY, float posX, float posY, float posZ, int sizeX, int sizeY, int sizeZ,
            float scaleFactor) {
        this(renderer, textureX, textureY, posX, posY, posZ, sizeX, sizeY, sizeZ, scaleFactor, renderer.mirror);
    }

    public CSModelBox(ModelRenderer renderer, int textureX, int textureY, float posX, float posY, float posZ, int sizeX, int sizeY, int sizeZ,
            float scaleFactor, boolean mirror) {
        super(renderer, textureX, textureY, posX, posY, posZ, sizeX, sizeY, sizeZ, scaleFactor);
        this.vertexPositions = new PositionTextureVertex[8];
        this.quadList = new TexturedQuad[6];
        float endX = posX + sizeX;
        float endY = posY + sizeY;
        float endZ = posZ + sizeZ;
        posX = posX - scaleFactor;
        posY = posY - scaleFactor;
        posZ = posZ - scaleFactor;
        endX = endX + scaleFactor;
        endY = endY + scaleFactor;
        endZ = endZ + scaleFactor;

        if (mirror) {
            final float f3 = endX;
            endX = posX;
            posX = f3;
        }

        final PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(posX, posY, posZ, 0.0F, 0.0F);
        final PositionTextureVertex positiontexturevertex = new PositionTextureVertex(endX, posY, posZ, 0.0F, 8.0F);
        final PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(endX, endY, posZ, 8.0F, 8.0F);
        final PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(posX, endY, posZ, 8.0F, 0.0F);
        final PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(posX, posY, endZ, 0.0F, 0.0F);
        final PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(endX, posY, endZ, 0.0F, 8.0F);
        final PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(endX, endY, endZ, 8.0F, 8.0F);
        final PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(posX, endY, endZ, 8.0F, 0.0F);
        this.vertexPositions[0] = positiontexturevertex7;
        this.vertexPositions[1] = positiontexturevertex;
        this.vertexPositions[2] = positiontexturevertex1;
        this.vertexPositions[3] = positiontexturevertex2;
        this.vertexPositions[4] = positiontexturevertex3;
        this.vertexPositions[5] = positiontexturevertex4;
        this.vertexPositions[6] = positiontexturevertex5;
        this.vertexPositions[7] = positiontexturevertex6;

        this.quadList[0] = new TexturedQuad(
                new PositionTextureVertex[] { positiontexturevertex4, positiontexturevertex, positiontexturevertex1, positiontexturevertex5 },
                textureX + sizeZ + sizeX, textureY + sizeZ, textureX + sizeZ + sizeX + sizeZ, textureY + sizeZ + sizeY, renderer.textureWidth,
                renderer.textureHeight);
        this.quadList[1] = new TexturedQuad(
                new PositionTextureVertex[] { positiontexturevertex7, positiontexturevertex3, positiontexturevertex6, positiontexturevertex2 },
                textureX, textureY + sizeZ, textureX + sizeZ, textureY + sizeZ + sizeY, renderer.textureWidth, renderer.textureHeight);
        this.quadList[2] = new TexturedQuad(
                new PositionTextureVertex[] { positiontexturevertex4, positiontexturevertex3, positiontexturevertex7, positiontexturevertex },
                textureX + sizeZ, textureY, textureX + sizeZ + sizeX, textureY + sizeZ, renderer.textureWidth, renderer.textureHeight);
        this.quadList[3] = new TexturedQuad(
                new PositionTextureVertex[] { positiontexturevertex1, positiontexturevertex2, positiontexturevertex6, positiontexturevertex5 },
                textureX + sizeZ + sizeX, textureY + sizeZ, textureX + sizeZ + sizeX + sizeX, textureY, renderer.textureWidth,
                renderer.textureHeight);
        this.quadList[4] = new TexturedQuad(
                new PositionTextureVertex[] { positiontexturevertex, positiontexturevertex7, positiontexturevertex2, positiontexturevertex1 },
                textureX + sizeZ, textureY + sizeZ, textureX + sizeZ + sizeX, textureY + sizeZ + sizeY, renderer.textureWidth,
                renderer.textureHeight);
        this.quadList[5] = new TexturedQuad(
                new PositionTextureVertex[] { positiontexturevertex3, positiontexturevertex4, positiontexturevertex5, positiontexturevertex6 },
                textureX + sizeZ + sizeX + sizeZ, textureY + sizeZ, textureX + sizeZ + sizeX + sizeZ + sizeX, textureY + sizeZ + sizeY,
                renderer.textureWidth, renderer.textureHeight);

        if (mirror)
            for (final TexturedQuad element : this.quadList)
                element.flipFace();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void render(VertexBuffer renderer, float scale) {
        for (final TexturedQuad element : this.quadList)
            element.draw(renderer, scale);
    }

    @Override
    public CSModelBox setBoxName(String name) {
        this.boxName = name;
        return this;
    }
}