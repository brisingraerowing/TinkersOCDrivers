package tinkersoc.smeltery;

import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.prefab.AbstractManagedEnvironment;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;
import li.cil.oc.api.Network;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.machine.Arguments;
import java.util.HashMap;


public class EnvironmentSmeltery extends AbstractManagedEnvironment {

	protected final TileSmeltery smeltery;

	public EnvironmentSmeltery(TileSmeltery smeltery) {
		this.smeltery = smeltery;

		setNode(Network.newNode(this, Visibility.Network).withComponent("smeltery", Visibility.Network).create());
	}

	@Callback(doc = "function():table - Get the fluids contained in the smeltery")
	public Object[] getContainedFluids(final Context context, Arguments arguments)
	{
		return smeltery.getTank().getFluids().toArray();
	}

	@Callback(doc = "function():table - Get information on the smeltery's current fuel")
	public Object[] getFuelInfo(final Context context, Arguments arguments)
	{
		TileSmeltery.FuelInfo info = smeltery.getFuelDisplay();
		return new Object[]{new HashMap<String, Object>() {
			{
				put("fluid", info.fluid);
				put("heat", info.heat);
				put("maxCap", info.maxCap);
			}

		}
		};
	}

	@Callback(doc = "function():int - Gets the amount of fuel in the smeltery")
	public Object[] getFuelLevel(final Context context, Arguments arguments) { return new Object[] {smeltery.getFuelDisplay().fluid.amount}; }

	@Callback(doc = "function([index:int]):int - Gets the smeltery temperature or the temperature of an item being smelted")
	public Object[] getTemperature(final Context context, Arguments arguments)
	{
		if (arguments.count() == 0) { return new Object[] {smeltery.getTemperature() }; }

		return new Object[] {smeltery.getTemperature(arguments.checkInteger(0))};
	}

	@Callback(doc = "function(index:int) - Returns whether the item in the specified slot can be heated")
	public Object[] canHeat(final Context context, Arguments arguments)
	{
		return new Object[] {smeltery.canHeat(arguments.checkInteger(0))};
	}

	@Callback(doc = "function():int - Gets the number of smelting slots the smeltery has")
	public Object[] getInventorySize(final Context context, Arguments arguments)
	{
		return new Object[] {smeltery.getSizeInventory()};
	}

	@Callback(doc = "function(index:int):int - Gets the required temperature for the item in the specified slot")
	public Object[] getTempRequired(final Context context, Arguments arguments)
	{
		return new Object[] {smeltery.getTempRequired(arguments.checkInteger(0))};
	}

	@Callback(doc = "function():boolean - Gets whether the smeltery has fuel")
	public Object[] hasFuel(final Context context, Arguments arguments)
	{
		return new Object[] {smeltery.hasFuel()};
	}

	@Callback(doc = "function():boolean - Gets whether the smeltery is empty (has no fluids)")
	public Object[] isEmpty(final Context context, Arguments arguments)
	{
		return new Object[] {smeltery.getTank().getFluidAmount() == 0};
	}

	@Callback(doc = "function():int - Gets the total fluid capacity of the smeltery")
	public Object[] getCapacity(final Context context, Arguments arguments)
	{
		return new Object[] { smeltery.getTank().getCapacity() };
	}

	@Callback(doc = "function():integer - Gets the total amount of all fluids in the smeltery")
	public Object[] getFillLevel(final Context context, Arguments arguments)
	{
		return new Object[] { smeltery.getTank().getFluidAmount() };
	}

	@Callback(doc = "function(index:integer):boolean - Moves the fluid at the specified index to the bottom")
	public Object[] moveFluidToBottom(final Context context, Arguments arguments)
	{
		int idx = arguments.checkInteger(0);

		if (idx < 1 || idx > smeltery.getTank().getFluids().size()) return new Object[] {false, "Invalid index"};

		smeltery.getTank().moveFluidToBottom(idx - 1);

		return new Object[] {true};

	}

}
