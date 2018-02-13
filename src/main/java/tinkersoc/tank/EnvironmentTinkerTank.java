package tinkersoc.tank;

import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.prefab.AbstractManagedEnvironment;
import slimeknights.tconstruct.smeltery.tileentity.TileTinkerTank;
import li.cil.oc.api.Network;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.machine.Arguments;
import java.util.HashMap;

public class EnvironmentTinkerTank extends AbstractManagedEnvironment {

	protected final TileTinkerTank tank;

	public EnvironmentTinkerTank(TileTinkerTank tank)
	{
		this.tank = tank;

		setNode(Network.newNode(this, Visibility.Network).withComponent("tinker_tank", Visibility.Network).create());

	}


	@Callback(doc = "function():table - Gets the fluids in the tank")
	public Object[] getFluids(final Context context, Arguments arguments)
	{
		return new Object[] { tank.getTank().getFluids().toArray() };
	}

	@Callback(doc = "function():int - Get the total capacity of this tank")
	public Object[] getCapacity(final Context context, Arguments arguments)
	{
		return new Object[] { tank.getTank().getCapacity() };
	}

	@Callback(doc = "function():int - Gets the total amount of all fluids in the tank")
	public Object[] getFillLevel(final Context context, Arguments arguments)
	{
		return new Object[] { tank.getTank().getFluidAmount() };
	}

	@Callback(doc = "function(index:integer):boolean - Moves the fluid at the specified index to the bottom")
	public Object[] moveFluidToBottom(final Context context, Arguments arguments)
	{
		int idx = arguments.checkInteger(0);

		if (idx < 1 || idx > tank.getTank().getFluids().size()) return new Object[] {false, "Invalid index"};

		tank.getTank().moveFluidToBottom(idx - 1);

		return new Object[] {true};

	}

}
