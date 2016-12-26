package fr.zeamateis.test.anim.common;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class BlockTest extends Block implements ITileEntityProvider
{

    public BlockTest() {
        super(Material.ROCK);
        this.setUnlocalizedName("blockTest");
        this.setCreativeTab(CreativeTabs.MISC);
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks
     * for render
     */
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    /**
     * The type of render function called. 3 for standard block models, 2 for
     * TESR's, 1 for liquids, -1 is no render
     */
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing
     * the block.
     */
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBlockTest();
    }
}