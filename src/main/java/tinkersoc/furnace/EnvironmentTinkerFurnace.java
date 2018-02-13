package tinkersoc.furnace;

import li.cil.oc.api.API;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.prefab.AbstractManagedEnvironment;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;
import slimeknights.tconstruct.smeltery.tileentity.TileSearedFurnace;
import li.cil.oc.api.Network;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.machine.Arguments;
import java.util.HashMap;

public class EnvironmentTinkerFurnace extends AbstractManagedEnvironment {

	protected final TileSearedFurnace furnace;

	public EnvironmentTinkerFurnace(TileSearedFurnace furnace)
	{
		this.furnace = furnace;

		setNode(Network.newNode(this, Visibility.Network).withComponent("seared_furnace", Visibility.Network).create());

	}

	@Callback(doc = "function():table - Get information on the furnace's current fuel")
	public Object[] getFuelInfo(final Context context, Arguments arguments)
	{
		TileSmeltery.FuelInfo info = furnace.getFuelDisplay();
		return new Object[]{new HashMap<String, Object>() {
			{
				put("fluid", info.fluid);
				put("heat", info.heat);
				put("maxCap", info.maxCap);
			}

		}
		};
	}

	@Callback(doc = "function():int - Gets the amount of fuel in the furnace")
	public Object[] getFuelLevel(final Context context, Arguments arguments)
	{
		return new Object[] {furnace.getFuelDisplay().fluid.amount};
	}


	@Callback(doc = "function([index:int]):int - Gets the furnace temperature or the temperature of an item being smelted")
	public Object[] getTemperature(final Context context, Arguments arguments)
	{
		if (arguments.count() == 0) { return new Object[] { furnace.getTemperature() }; }

		return new Object[] {furnace.getTemperature(arguments.checkInteger(0))};
	}

	@Callback(doc = "function(index:int) - Returns whether the item in the specified slot can be heated")
	public Object[] canHeat(final Context context, Arguments arguments)
	{
		return new Object[] {furnace.canHeat(arguments.checkInteger(0))};
	}

	@Callback(doc = "function():int - Gets the number of smelting slots the furnace has")
	public Object[] getInventorySize(final Context context, Arguments arguments)
	{
		return new Object[] {furnace.getSizeInventory()};
	}

	@Callback(doc = "function(index:int):int - Gets the required temperature for the item in the specified slot")
	public Object[] getTempRequired(final Context context, Arguments arguments)
	{
		return new Object[] {furnace.getTempRequired(arguments.checkInteger(0))};
	}

	@Callback(doc = "function():boolean - Gets whether the furnace has fuel")
	public Object[] hasFuel(final Context context, Arguments arguments)
	{
		return new Object[] {furnace.hasFuel()};
	}

	@Callback(doc = "function(index:int):table - Gets the stack in the specified slot")
	public Object[] getStackInSlot(final Context context, Arguments arguments)
	{

		if (!API.config.getBoolean("misc.allowItemStackInspection")) { return new Object[] {null, "ItemStack inspection disabled in OC config"}; }

		int slot = arguments.checkInteger(0);
		if (slot < 1 || slot > furnace.getSizeInventory()) { return new Object[] {null, "Invalid slot"}; }

		slot--;

		ItemStack stack = furnace.getItemHandler().getStackInSlot(slot);

		if(stack.isEmpty()) { return new Object[] {null, "No item"}; }

		return new Object[] {stack};

	}

	@Callback(doc = "function(index:int):number - Gets the heating progress of the item in the specified slot")
	public Object[] getHeatingProgress(final Context context, Arguments arguments)
	{
		return new Object[] { furnace.getHeatingProgress(arguments.checkInteger(0)) };
	}


}
