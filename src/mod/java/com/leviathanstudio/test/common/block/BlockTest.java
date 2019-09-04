package com.leviathanstudio.test.common.block;

import com.leviathanstudio.test.common.tileEntity.TileEntityTest;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockReader;

public class BlockTest extends Block
{

    public BlockTest() {
        super(Material.ROCK);
        this.setRegistryName("block_test").setUnlocalizedName("block_test");
        this.setCreativeTab(CreativeTabs.MISC);
    }

    @Override
    public boolean isOpaqueCube(BlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    
    @Override
    public TileEntityTest createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityTest(world);
    }

}