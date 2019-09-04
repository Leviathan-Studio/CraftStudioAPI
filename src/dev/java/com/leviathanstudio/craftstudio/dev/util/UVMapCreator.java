package com.leviathanstudio.craftstudio.dev.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.leviathanstudio.craftstudio.client.exception.CSResourceNotRegisteredException;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;
import com.leviathanstudio.craftstudio.client.json.CSReadedModelBlock;
import com.leviathanstudio.craftstudio.client.model.CSModelBox;
import com.leviathanstudio.craftstudio.client.registry.RegistryHandler;
import com.leviathanstudio.craftstudio.dev.CraftStudioApiDev;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Object to help generate a UV Map for a model.
 * 
 * @since 0.3.0
 * 
 * @author Timmypote
 */

@OnlyIn(Dist.CLIENT)
public class UVMapCreator
{

    private CSReadedModel rModel;
    private BufferedImage bi;
    private int           maxu = 0, maxv = 0;

    public UVMapCreator(ResourceLocation modelIn) {
        this.rModel = RegistryHandler.modelRegistry.getValue(modelIn)
        		.orElseThrow(() -> new CSResourceNotRegisteredException(modelIn.toString())); 
    }

    public boolean createUVMap() {
        CraftStudioApiDev.getLogger().info("Getting the size of the UV Map ...");
        this.getUVSize();
        CraftStudioApiDev.getLogger().info("Generate UV Map ...");
        this.generateMap();
        CraftStudioApiDev.getLogger().info("Write UV Map ...");
        boolean w = this.writeMap();
        if (w)
            CraftStudioApiDev.getLogger().info("Creation of UV Map Complete");
        else
            CraftStudioApiDev.getLogger().error("Unable to save the file " + this.rModel.getName() + ".png");
        return w;
    }

    private void getUVSize() {
        for (CSReadedModelBlock block : this.rModel.getParents())
            this.getUVSizeByBlock(block);
    }

    private void getUVSizeByBlock(CSReadedModelBlock block) {
        int[][] uvs = CSModelBox.getTextureUVsForRect(block.getTexOffset()[0], block.getTexOffset()[1], block.getSize().x, block.getSize().y,
                block.getSize().z);

        for (int[] ti : uvs) {
            if (ti[0] > this.maxu)
                this.maxu = ti[0];
            if (ti[1] > this.maxv)
                this.maxv = ti[1];
            if (ti[2] > this.maxu)
                this.maxu = ti[2];
            if (ti[3] > this.maxv)
                this.maxv = ti[3];
        }

        for (CSReadedModelBlock child : block.getChilds())
            this.getUVSizeByBlock(child);
    }

    private void generateMap() {
        this.bi = new BufferedImage(this.maxu, this.maxv, BufferedImage.TYPE_INT_ARGB);
        Graphics2D ig = this.bi.createGraphics();

        for (CSReadedModelBlock block : this.rModel.getParents())
            this.drawUVForBlock(block, ig);
    }

    private void drawUVForBlock(CSReadedModelBlock block, Graphics2D ig) {
        int[][] textUvs = CSModelBox.getTextureUVsForRect(block.getTexOffset()[0], block.getTexOffset()[1], block.getSize().x, block.getSize().y,
                block.getSize().z);
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

        for (CSReadedModelBlock child : block.getChilds())
            this.drawUVForBlock(child, ig);
    }

    private static void drawRect(int[] coord, Graphics2D ig) {
        int a = coord[0] < coord[2] ? coord[0] : coord[2], b = coord[1] < coord[3] ? coord[1] : coord[3],
                A = coord[0] < coord[2] ? coord[2] - a : coord[0] - a, B = coord[0] < coord[2] ? coord[3] - b : coord[1] - b;

        ig.fillRect(a, b, A, B);
    }

    private boolean writeMap() {
        try {
            ImageIO.write(this.bi, "PNG", new File(this.rModel.getName() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
