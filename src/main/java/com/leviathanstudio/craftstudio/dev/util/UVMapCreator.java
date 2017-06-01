package com.leviathanstudio.craftstudio.dev.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;

import com.leviathanstudio.craftstudio.client.json.CSReadedModel;
import com.leviathanstudio.craftstudio.client.json.CSReadedModelBlock;
import com.leviathanstudio.craftstudio.client.model.CSModelBox;
import com.leviathanstudio.craftstudio.client.util.math.Vector3f;
import com.leviathanstudio.craftstudio.common.exception.CSResourceNotRegisteredException;
import com.leviathanstudio.craftstudio.dev.CraftStudioApiDev;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class UVMapCreator {
	
	private CSReadedModel rModel;
	private BufferedImage bi;
	private List<UVs> uvsList = new ArrayList();
			//revertUvsList = new ArrayList();
	
	public UVMapCreator(ResourceLocation modelIn){
		this.rModel = GameRegistry.findRegistry(CSReadedModel.class).getValue(modelIn);
		if (this.rModel == null)
			throw new CSResourceNotRegisteredException(modelIn.toString());
		//this.revertUvsList.add(new UVs(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE));
	}
	
	public void createUVMap(){
		CraftStudioApiDev.getLogger().info("Start creation of UV Map ...");
		this.createUV();
		CraftStudioApiDev.getLogger().info("Generate UV Map ...");
		this.generateMap();
		CraftStudioApiDev.getLogger().info("Write UV Map ...");
		this.writeMap();
		CraftStudioApiDev.getLogger().info("Creation of UV Map Complete");
	}
	
	private void createUV(){
		for (CSReadedModelBlock block : this.rModel.getParents()){
			this.createUVForBlock(block);
		}
		//this.revertUvsList = null;
	}
	
	private void createUVForBlock(CSReadedModelBlock block){
		CraftStudioApiDev.getLogger().info(block.getName());
		Vector3f size = block.getAbsSize();
		UVs uvs = this.getBlockUV(block);
		uvs.name = block.getName();
		this.uvsList.add(uvs);
		
		for (CSReadedModelBlock child : block.getChilds()){
			this.createUVForBlock(child);
		}
	}
	
	private UVs getBlockUV(CSReadedModelBlock block){
		Vector3f size = block.getSize();
		int[][] uvs = CSModelBox.getTextureUVsForRect(block.getTexOffset()[0], block.getTexOffset()[1], size.x, -size.y, -size.z);
		
		int u1 = uvs[0][0], v1 = uvs[0][1], u2 = uvs[0][0], v2 = uvs[0][1];
		
		for (int[] ti : uvs){
			if (ti[0] < u1)
				u1 = ti[0];
			if (ti[2] < u1)
				u1 = ti[2];
			if (ti[0] > u2)
				u2 = ti[0];
			if (ti[2] > u2)
				u2 = ti[2];
			if (ti[1] < v1)
				v1 = ti[1];
			if (ti[3] < v1)
				v1 = ti[3];
			if (ti[1] > v2)
				v2 = ti[1];
			if (ti[3] > v2)
				v2 = ti[3];
		}
		if (u1 < 0){
			u2 = u2 - u1;
			u1 = 0;
		}
		if (v1 < 0){
			v2 = v2 - v1;
			v1 = 0;
		}
		return new UVs(u1, v1, u2, v2);
	}
	
//	private void addUVs(UVs uvs){
//		List<UVs> toRemove = new ArrayList<>();
//		UVs[] nUvs = new UVs[4];
//		this.uvsList.add(uvs);
//		for (int i = 0 ; i < this.revertUvsList.size(); i++){
//			UVs revertUvs = this.revertUvsList.get(i);
//			if (revertUvs.contains(uvs)){
//				toRemove.add(revertUvs);
//				nUvs[0] = new UVs(revertUvs.u1, revertUvs.v1, uvs.u2, uvs.v1);
//				nUvs[1] = new UVs(revertUvs.u1, uvs.v1, uvs.u1, revertUvs.v2);
//				nUvs[2] = new UVs(uvs.u1, uvs.v2, revertUvs.u2, revertUvs.v2);
//				nUvs[3] = new UVs(uvs.u2, revertUvs.v1, revertUvs.u2, uvs.v2);
//				for (UVs aUvs : nUvs)
//					if (!aUvs.isNullOrNegative())
//						this.revertUvsList.add(aUvs);
//			}
//		}
//		this.revertUvsList.remove(toRemove);
//		//this.revertUvsList.sort(new UVsComparator());
//	}
//	
//	private UVs getBestUVs(int uSize, int vSize){
//		UVs testUvs = null;
//		boolean flag;
//		for (UVs uvs : this.revertUvsList){
//			testUvs = new UVs(uvs.u1, uvs.v1, uvs.u1 + uSize, uvs.v1 + vSize);
//			flag = true;
//			for (UVs takenUvs : this.uvsList)
//				if (takenUvs.contains(testUvs)){
//					flag = false;
//					break;
//				}
//			if (flag)
//				break;
//		}
//		this.addUVs(testUvs);
//		return testUvs;
//	}
	
	private void generateMap(){
		int[] size = this.getTextSize();
		this.bi = new BufferedImage(size[0], size[1], BufferedImage.TYPE_INT_ARGB);
		Graphics2D ig = this.bi.createGraphics();
		Font font = new Font("Consolas", 0, 8);
		ig.setFont(font);
		
		for (UVs uvs : this.uvsList)
			this.drawUVs(uvs, ig);
		this.uvsList = null;
	}
	
	private int[] getTextSize(){
		int maxu = 0, maxv = 0;
		for (UVs uvs : this.uvsList){
			if (uvs.u2 > maxu)
				maxu = uvs.u2;
			if (uvs.v2 > maxv)
				maxv = uvs.v2;
		}
		return new int[]{maxu, maxv};
	}
	
	private void drawUVs(UVs uvs, Graphics2D ig){
		CSReadedModelBlock block = this.rModel.getBlockFromName(uvs.name);
		Vector3f size = block.getSize();
		int[][] textUvs = CSModelBox.getTextureUVsForRect(block.getTexOffset()[0], block.getTexOffset()[1], size.x, -size.y, -size.z);
		ig.setPaint(Color.MAGENTA);
		drawRect(textUvs[0], ig);
		ig.setPaint(Color.RED);
		drawRect(textUvs[1], ig);
		ig.setPaint(Color.GREEN);
		drawRect(textUvs[2], ig);
		ig.setPaint(Color.YELLOW);
		drawRect(textUvs[3], ig);
		ig.setPaint(Color.BLUE);
		drawRect(textUvs[4], ig);
		ig.setPaint(Color.CYAN);
		drawRect(textUvs[5], ig);
		
//		ig.setPaint(Color.CYAN);
//		ig.fillRect(uvs.u1, uvs.v1, uvs.u2 - uvs.u1, uvs.v2 - uvs.v1);
		
		FontMetrics fm = ig.getFontMetrics();
		int strWidth = fm.stringWidth(block.getName());
		int strHeight = fm.getAscent();
		ig.setPaint(Color.BLACK);
//		if (uvs.u2 - uvs.u1 >= strWidth && uvs.v2 - uvs.v1 >= strHeight)
//			ig.drawString(block.getName(), uvs.u1 + (uvs.u2 - uvs.u1 - strWidth)/2, uvs.v1 + (uvs.v2 - uvs.v1 + strHeight)/2);
	}
	
	private static void drawRect(int[] coord, Graphics2D ig){
		int a = coord[0] < coord[2]? coord[0] : coord[2],
			b = coord[1] < coord[3]? coord[1] : coord[3],
			A = coord[0] < coord[2]? coord[2] - a : coord[0] -a,
			B = coord[0] < coord[2]? coord[3] - b : coord[1] -b;
		
		ig.fillRect(a, b, A, B);
	}
	
	private void writeMap(){
		try {
			ImageIO.write(bi, "PNG", new File(this.rModel.getName() + ".png"));
		} catch (IOException e) {
			CraftStudioApiDev.getLogger().error("Unable to save the file " + this.rModel.getName() + ".png");
			e.printStackTrace();
		}
	}
	
	
	
	private static class UVs{
		int u1;
		int v1;
		int u2;
		int v2;
		String name;
		
		UVs(int u1, int v1, int u2, int v2){
			this.u1 = u1;
			this.v1 = v1;
			this.u2 = u2;
			this.v2 = v2;
		}
		
		boolean contains(UVs uvs){
			return this.contains(uvs, false);
		}
		
		private boolean contains(UVs uvs, boolean innerCheck){
			if (this.containPoint(uvs.u1, uvs.v1)|| this.containPoint(uvs.u2, uvs.v1)
					|| this.containPoint(uvs.u1, uvs.v2)|| this.containPoint(uvs.u2, uvs.v2)
					|| this.equals(uvs) || (!innerCheck && uvs.contains(this, true)))
				return true;
			return false;
		}
		
		boolean containPoint(int u, int v){
			if (Math.signum((this.u1-u))*Math.signum((this.u2-u))<0 && Math.signum((this.v1-v))*Math.signum((this.v2-v))<0)
				return true;
			return false;
		}
		
		boolean isNullOrNegative(){
			if (this.u2 - this.u1 <=0 || this.v2 - this.v1 <= 0)
				return true;
			return false;
		}
		
		public boolean equals(UVs uvs){
			if (this.u1 == uvs.u1 && this.u2 == uvs.u2 && this.v1 == uvs.v1 && this.v2 == uvs.v2)
				return true;
			return false;
		}
		
		int getSurface(){
			return (this.u2-this.u1)*(this.v2 - this.v1);
		}
	}
	
	private static class UVsComparator implements Comparator<UVs>{
		@Override
		public int compare(UVs o1, UVs o2) {
			return o1.getSurface() - o2.getSurface();
		}
	}
}
