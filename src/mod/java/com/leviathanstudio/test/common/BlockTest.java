package com.leviathanstudio.test.common;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockTest extends Block implements ITileEntityProvider
{

    public BlockTest() {
        super(Material.ROCK);
        this.setRegistryName("block_test").setUnlocalizedName("block_test");
        this.setCreativeTab(CreativeTabs.MISC);
        ItemBlock itemBlock = new ItemBlock(this);
        itemBlock.setRegistryName("item_block_test");
        GameRegistry.register(itemBlock);
        GameRegistry.register(this);
    }
    
    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param){
        TileEntity tile = worldIn.getTileEntity(pos);
        return tile.receiveClientEvent(id, param);
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