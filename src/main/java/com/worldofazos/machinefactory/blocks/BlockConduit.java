package com.worldofazos.machinefactory.blocks;

import com.worldofazos.machinefactory.blocks.EnergyConduit.conduitTE;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.*;

public class BlockConduit extends BlockTileEntity<conduitTE>{

    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool DOWN = PropertyBool.create("down");
    public static final PropertyBool UP = PropertyBool.create("up");

    protected static AxisAlignedBB createAABB(int fromX, int fromY, int fromZ, int toX, int toY, int toZ) {
        return new AxisAlignedBB((float) fromX / 16F, (float) fromY / 16F, (float) fromZ / 16F, (float) toX / 16F, (float) toY / 16F, (float) toZ / 16F);
    }

    protected static AxisAlignedBB CENTER = createAABB(4, 4, 4, 10, 10, 10);
    protected static AxisAlignedBB CENTER_NORTH = createAABB(4, 4, 0, 10, 10, 4);
    protected static AxisAlignedBB CENTER_EAST = createAABB(10, 4, 4, 16, 10, 10);
    protected static AxisAlignedBB CENTER_SOUTH = createAABB(4, 4, 10, 10, 10, 16);
    protected static AxisAlignedBB CENTER_WEST = createAABB(0, 4, 4, 4, 10, 10);
    protected static AxisAlignedBB CENTER_UP = createAABB(4, 10, 4, 10, 16, 10);
    protected static AxisAlignedBB CENTER_DOWN = createAABB(4, 0, 4, 10, 4, 10);

    public BlockConduit(String name) {
        super(Material.ROCK, name);
        setLightOpacity(255);
        useNeighborBrightness = true;
        setHardness(3.0F);
        setHarvestLevel("pickaxe", 0);
        setResistance(5.0F);

        this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)).withProperty(DOWN, Boolean.valueOf(false)).withProperty(UP, Boolean.valueOf(false)));
    }

    public boolean canConnectTo(IBlockAccess world, BlockPos pos)
    {
        TileEntity te = world.getTileEntity(pos);
        if(te != null && te.hasCapability(CapabilityEnergy.ENERGY, null)) return true;
        else if(te != null && te.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, null)) return true;
        else if(te != null && te.hasCapability(TeslaCapabilities.CAPABILITY_PRODUCER, null)) return true;
        else if (te != null && te.hasCapability(TeslaCapabilities.CAPABILITY_HOLDER, null)) return true;
        //return world.getBlockState(pos).getBlock() instanceof BlockConduit;
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced){
        tooltip.add("Transfer 100 000 RF/T");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!world.isRemote) {
            if(player.isSneaking()) {
                conduitTE tile = this.getTileEntity(world, pos);
                player.addChatMessage(new TextComponentString(tile.toDebug()));
            }

        }
        return super.onBlockActivated(world, pos, state, player, hand, heldItem, side, hitX, hitY, hitZ);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return state.withProperty(NORTH, Boolean.valueOf(this.canConnectTo(worldIn, pos.north()))).withProperty(EAST, Boolean.valueOf(this.canConnectTo(worldIn, pos.east()))).withProperty(SOUTH, Boolean.valueOf(this.canConnectTo(worldIn, pos.south()))).withProperty(WEST, Boolean.valueOf(this.canConnectTo(worldIn, pos.west()))).withProperty(DOWN, Boolean.valueOf(this.canConnectTo(worldIn, pos.down()))).withProperty(UP, Boolean.valueOf(this.canConnectTo(worldIn, pos.up())));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {NORTH, EAST, WEST, SOUTH, DOWN, UP});
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess w, BlockPos p)
    {
        Map<EnumFacing, Boolean> conns = new HashMap<EnumFacing, Boolean>();

        for(EnumFacing f : EnumFacing.VALUES)
        {
            conns.put(f, false);

        }

        double nx = 5D / 16D, ny = 5D / 16D, nz = 5D / 16D, xx = 11D / 16D, xy = 11D / 16D, xz = 11D / 16D;

        if(conns.get(EnumFacing.SOUTH)) xz = 1D;
        if(conns.get(EnumFacing.NORTH)) nz = 0D;
        if(conns.get(EnumFacing.EAST)) xx = 1D;
        if(conns.get(EnumFacing.WEST)) nx = 0D;
        if(conns.get(EnumFacing.UP)) xy = 1D;
        if(conns.get(EnumFacing.DOWN)) ny = 0D;

        return new AxisAlignedBB(nx, ny, nz, xx, xy, xz);
    }

    @Override
    public void addCollisionBoxToList(IBlockState s, World w, BlockPos p, AxisAlignedBB box, List<AxisAlignedBB> l, Entity ent)
    {
        addCollisionBoxToList(p, box, l, CENTER);

        Map<EnumFacing, Boolean> conns = new HashMap<EnumFacing, Boolean>();

        for(EnumFacing f : EnumFacing.VALUES)
        {
            conns.put(f, false);

        }

        double nx = 5D / 16D, ny = 5D / 16D, nz = 5D / 16D, xx = 11D / 16D, xy = 11D / 16D, xz = 11D / 16D;

        if(conns.get(EnumFacing.SOUTH)) addCollisionBoxToList(p, box, l, CENTER_SOUTH);
        if(conns.get(EnumFacing.NORTH)) addCollisionBoxToList(p, box, l, CENTER_NORTH);
        if(conns.get(EnumFacing.EAST)) addCollisionBoxToList(p, box, l, CENTER_EAST);
        if(conns.get(EnumFacing.WEST)) addCollisionBoxToList(p, box, l, CENTER_WEST);
        if(conns.get(EnumFacing.UP)) addCollisionBoxToList(p, box, l, CENTER_UP);
        if(conns.get(EnumFacing.DOWN)) addCollisionBoxToList(p, box, l, CENTER_DOWN);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState p_isOpaqueCube_1_)
    {
        return false;
    }

    @Override
    public boolean isVisuallyOpaque()
    {
        return false;
    }

    @Override
    public boolean isFullyOpaque(IBlockState p_isFullyOpaque_1_)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState p_isFullCube_1_)
    {
        return false;
    }

    @Override
    public int getLightOpacity(IBlockState p_getLightOpacity_1_)
    {
        return 0;
    }

    @Override
    public int getLightOpacity(IBlockState p_getLightOpacity_1_, IBlockAccess p_getLightOpacity_2_, BlockPos p_getLightOpacity_3_)
    {
        return 0;
    }

    @Override
    public Class<conduitTE> getTileEntityClass() {
        return conduitTE.class;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public conduitTE createTileEntity(World world, IBlockState state) {
        return new conduitTE();
    }

}