package tinkersoc.furnace;

import li.cil.oc.api.driver.DriverBlock;
import li.cil.oc.api.network.ManagedEnvironment;
import net.minecraft.tileentity.TileEntity;
import slimeknights.tconstruct.smeltery.tileentity.TileSearedFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class DriverTinkerFurnace implements DriverBlock{
	@Override
	public boolean worksWith(World world, BlockPos blockPos, EnumFacing enumFacing) {

		TileEntity tile = world.getTileEntity(blockPos);

		return tile != null && tile instanceof TileSearedFurnace;

	}

	@Override
	public ManagedEnvironment createEnvironment(World world, BlockPos blockPos, EnumFacing enumFacing) {

		TileSearedFurnace tile = (TileSearedFurnace)world.getTileEntity(blockPos);

		return new EnvironmentTinkerFurnace(tile);

	}
}
