package lib.craftstudio.client;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CSModelBox
{
    /** An array of 6 TexturedQuads, one for each face of a cube */
    private final TexturedQuad[] quadList;
    public String boxName;

    public CSModelBox(ModelRenderer renderer, int texU, int texV, float x, float y, float z, float dx, float dy, float dz)
    {
        this(renderer, texU, texV, x, y, z, dx, dy, dz, renderer.mirror);
    }
    
    public CSModelBox(ModelRenderer renderer, int texU, int texV, float x, float y, float z, float dx, float dy, float dz, boolean mirror){
    	this(renderer, getVertexFromCoord(x, y, z, dx, dy, dz, mirror), getTextureUVsFromCoord(texU, texV, x, y, z, dx, dy, dz), mirror);
    }
    
    public CSModelBox(ModelRenderer renderer, PositionTextureVertex positionTextureVertex[], int[][] textUVs, boolean mirror){
    	this(positionTextureVertex);
    	this.setTexture(renderer, textUVs);
    	if (mirror)
        {
            for (TexturedQuad texturedquad : this.quadList)
            {
                texturedquad.flipFace();
            }
        }
    }
    
    public CSModelBox(PositionTextureVertex positionTextureVertex[]){
    	this.quadList = new TexturedQuad[6];
    	this.setVertex(positionTextureVertex);
    }
    
    /**
     * Set les vertices du bloc
     * ! Un setTexture est nécessaire apprait ceci
     * 
     * Ordre des vertices:
     *     vertices[0] = (0, 0, 0) (origine du bloc)
     *     vertices[1] = (x, 0, 0)
     *     vertices[2] = (x, y, 0)
     *     vertices[3] = (0, y, 0)
     *     vertices[4] = (0, 0, z)
     *     vertices[5] = (x, 0, z)
     *     vertices[6] = (x, y, z) (fin du bloc)
     *     vertices[7] = (0, y, z)
     * 
     * @param positionTextureVertex Liste des vertices
     */
    public void setVertex(PositionTextureVertex positionTextureVertex[]){
    	if (positionTextureVertex.length == 8){
    		this.quadList[0] = new TexturedQuad(new PositionTextureVertex[] {positionTextureVertex[5], positionTextureVertex[1], positionTextureVertex[2], positionTextureVertex[6]});
            this.quadList[1] = new TexturedQuad(new PositionTextureVertex[] {positionTextureVertex[0], positionTextureVertex[4], positionTextureVertex[7], positionTextureVertex[3]});
            this.quadList[2] = new TexturedQuad(new PositionTextureVertex[] {positionTextureVertex[5], positionTextureVertex[4], positionTextureVertex[0], positionTextureVertex[1]});
            this.quadList[3] = new TexturedQuad(new PositionTextureVertex[] {positionTextureVertex[2], positionTextureVertex[3], positionTextureVertex[7], positionTextureVertex[6]});
            this.quadList[4] = new TexturedQuad(new PositionTextureVertex[] {positionTextureVertex[1], positionTextureVertex[0], positionTextureVertex[3], positionTextureVertex[2]});
            this.quadList[5] = new TexturedQuad(new PositionTextureVertex[] {positionTextureVertex[4], positionTextureVertex[5], positionTextureVertex[6], positionTextureVertex[7]});
    	}
    }
    
    /**
     * Set les UV des textures
     * 
     * Ordre des faces:
     *     faces[0] = X1
     *     faces[1] = X0
     *     faces[2] = Y0
     *     faces[3] = Y1
     *     faces[4] = Z0
     *     faces[5] = Z1
     * Ordre des coordonnées :
     *     coord[0] = U0 (x0)
     *     coord[1] = V0 (y0) (Haut/Gauche)
     *     coord[2] = U1 (x1)
     *     coord[3] = V1 (y1) (Bas/Droite)
     * 
     * @param renderer Le ModelRenderer à utilisé
     * @param textUVs Les coordonnées des textures
     */
    public void setTexture(ModelRenderer renderer, int[][] textUVs){
    	int[] textUV;
    	if (textUVs.length == 6){
    		for (int i = 0; i < 6; i++){
    			textUV = textUVs[i];
    			if (textUV.length == 4){
    				this.quadList[i] = new TexturedQuad(this.quadList[i].vertexPositions, textUV[0], textUV[1], textUV[2], textUV[3], renderer.textureWidth, renderer.textureHeight);
    			}
    		}
    	}
    }
    
    private static PositionTextureVertex[] getVertexFromCoord(float x, float y, float z, float dx, float dy, float dz, boolean mirror){
    	PositionTextureVertex[] positionTextureVertex = new PositionTextureVertex[8];
    	float endX = x + dx;
        float endY = y + dy;
        float endZ = z + dz;

        if (mirror)
        {
            float buffer = endX;
            endX = x;
            x = buffer;
        }

        positionTextureVertex[0] = new PositionTextureVertex(x, y, z, 0.0F, 0.0F);
        positionTextureVertex[1] = new PositionTextureVertex(endX, y, z, 0.0F, 0.0F);
        positionTextureVertex[2] = new PositionTextureVertex(endX, endY, z, 0.0F, 0.0F);
        positionTextureVertex[3] = new PositionTextureVertex(x, endY, z, 0.0F, 0.0F);
        positionTextureVertex[4] = new PositionTextureVertex(x, y, endZ, 0.0F, 0.0F);
        positionTextureVertex[5] = new PositionTextureVertex(endX, y, endZ, 0.0F, 0.0F);
        positionTextureVertex[6] = new PositionTextureVertex(endX, endY, endZ, 0.0F, 0.0F);
        positionTextureVertex[7] = new PositionTextureVertex(x, endY, endZ, 0.0F, 0.0F);
        
        return positionTextureVertex;
    }
    
    private static int[][] getTextureUVsFromCoord(int texU, int texV, float x, float y, float z, float dx, float dy, float dz){
    	int[][] tab = new int[][] {
    		{(int) (texU + dz + dx), (int) (texV + dz), (int) (texU + dz + dx + dz), (int) (texV + dz + dy)},
    		{texU, (int) (texV + dz), (int) (texU + dz), (int) (texV + dz + dy)},
    		{(int) (texU + dz), texV, (int) (texU + dz + dx), (int) (texV + dz)},
    		{(int) (texU + dz + dx), (int) (texV + dz), (int) (texU + dz + dx + dx), texV},
    		{(int) (texU + dz), (int) (texV + dz), (int) (texU + dz + dx), (int) (texV + dz + dy)},
    		{(int) (texU + dz + dx + dz), (int) (texV + dz), (int) (texU + dz + dx + dz + dx), (int) (texV + dz + dy)}
    	};
    	for (int [] is: tab){
    		for (int i: is)
    			System.out.print(i + " ");
    		System.out.println("\n");
    	}
    	return tab;
    }

    @SideOnly(Side.CLIENT)
    public void render(VertexBuffer renderer, float scale)
    {
        for (TexturedQuad texturedquad : this.quadList)
        {
            texturedquad.draw(renderer, scale);
        }
    }

    public CSModelBox setBoxName(String name)
    {
        this.boxName = name;
        return this;
    }
}