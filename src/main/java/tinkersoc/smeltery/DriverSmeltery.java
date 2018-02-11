package tinkersoc.smeltery;

import li.cil.oc.api.driver.DriverBlock;
import li.cil.oc.api.network.ManagedEnvironment;
import net.minecraft.tileentity.TileEntity;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tinkersoc.TinkersOCMod;

public class DriverSmeltery implements DriverBlock{


	@Override
	public boolean worksWith(World world, BlockPos blockPos, EnumFacing enumFacing) {

		TileEntity tile = world.getTileEntity(blockPos);

		return tile != null && tile instanceof TileSmeltery;

	}

	@Override
	public ManagedEnvironment createEnvironment(World world, BlockPos blockPos, EnumFacing enumFacing) {

		TileSmeltery tile = (TileSmeltery)world.getTileEntity(blockPos);

		return new EnvironmentSmeltery(tile);

	}
}
