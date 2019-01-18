package com.leviathanstudio.test.common.block;

import com.leviathanstudio.test.common.tileEntity.TileEntityTest;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class BlockTest extends Block implements ITileEntityProvider
{

    public BlockTest() {
        super(Material.ROCK);
        this.setRegistryName("block_test").setUnlocalizedName("block_test");
        this.setCreativeTab(CreativeTabs.MISC);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public TileEntityTest createNewTileEntity(World worldIn, int meta) {
        return new TileEntityTest(worldIn);
    }

}