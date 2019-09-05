package com.leviathanstudio.test.common.block;

import com.leviathanstudio.test.common.tileEntity.TileEntityTest;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.world.IBlockReader;

public class BlockTest extends Block
{

    public BlockTest(Block.Properties props) {
        super(props);
    }


    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    
    @Override
    public TileEntityTest createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityTest();
    }

}