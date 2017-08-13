package com.worldofazos.machinefactory.blocks.SolarGenerator;

import com.worldofazos.machinefactory.MachineFactory;
import com.worldofazos.machinefactory.blocks.BlockTileEntity;
import com.worldofazos.machinefactory.blocks.ModBlocks;
import com.worldofazos.machinefactory.handler.ModGuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockSolarGenerator extends BlockTileEntity<SolarGeneratorTE> {

    public BlockSolarGenerator(){
        super(Material.ROCK,"solar_generator");
        this.setCreativeTab(MachineFactory.creativeTab);
        this.setHarvestLevel("Pickaxe", 2);
        this.setHardness(1.0F);
        isBlockContainer = false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            SolarGeneratorTE tile = getTileEntity(world, pos);
            //player.addChatMessage(new TextComponentString("Energy Stored: " + tile.getOxygenStored() + " / " + tile.getMaxOxygenStored() + "RF"));

            player.openGui(MachineFactory.instance, ModGuiHandler.SolarGenerator, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    public boolean isMultiBlock(World world, BlockPos pos){

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        // 3x3 solar panel
        for(int i= -1; i<=1;i++)
            for(int j = -1; j<=1;j++)
                if(getBlock(world, x + 1, y, z + j) == ModBlocks.SolarGenerator)
                    return true;

        return false;
    }

    private Block getBlock(World world, BlockPos pos){
        return world.getBlockState(pos).getBlock();
    }

    private Block getBlock(World world, int x, int y, int z){
        return getBlock(world, new BlockPos(x,y,z));
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        double d3 = (double) pos.getX() + rand.nextDouble();
        double d8 = (double) pos.getY() + rand.nextDouble();
        double d13 = (double) pos.getZ() + rand.nextDouble();
        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d3, d8, d13, 0.0D, 0.0D, 0.0D, new int[0]);
    }

    @Override
    public Class<SolarGeneratorTE> getTileEntityClass() {
        return SolarGeneratorTE.class;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public SolarGeneratorTE createTileEntity(World world, IBlockState state) {
        return new SolarGeneratorTE();
    }

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

}
