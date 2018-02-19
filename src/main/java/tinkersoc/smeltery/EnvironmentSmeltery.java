package tinkersoc.smeltery;

import com.sun.corba.se.spi.ior.ObjectKey;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.prefab.AbstractManagedEnvironment;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;
import li.cil.oc.api.Network;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.machine.Arguments;
import java.util.HashMap;
import net.minecraft.item.ItemStack;
import li.cil.oc.api.API;
import tinkersoc.AbstractTinkersEnvironment;


public class EnvironmentSmeltery extends AbstractTinkersEnvironment<TileSmeltery> {

	public EnvironmentSmeltery(TileSmeltery smeltery) {
		super("smeltery", smeltery);
	}

	@Callback(doc = "function():table - Get the fluids contained in the smeltery")
	public Object[] getContainedFluids(final Context context, Arguments arguments)
	{
		return tile.getTank().getFluids().toArray();
	}

	@Callback(doc = "function():table - Get information on the smeltery's current fuel")
	public Object[] getFuelInfo(final Context context, Arguments arguments)
	{
		TileSmeltery.FuelInfo info = tile.getFuelDisplay();
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
	public Object[] getFuelLevel(final Context context, Arguments arguments) { return new Object[] {tile.getFuelDisplay().fluid.amount}; }

	@Callback(doc = "function([index:int]):int - Gets the smeltery temperature or the temperature of an item being smelted")
	public Object[] getTemperature(final Context context, Arguments arguments)
	{
		if (arguments.count() == 0) { return new Object[] {tile.getTemperature() }; }

		return new Object[] {tile.getTemperature(arguments.checkInteger(0))};
	}

	@Callback(doc = "function(index:int) - Returns whether the item in the specified slot can be heated")
	public Object[] canHeat(final Context context, Arguments arguments)
	{
		return new Object[] {tile.canHeat(arguments.checkInteger(0))};
	}

	@Callback(doc = "function():int - Gets the number of smelting slots the smeltery has")
	public Object[] getInventorySize(final Context context, Arguments arguments)
	{
		return new Object[] {tile.getSizeInventory()};
	}

	@Callback(doc = "function(index:int):int - Gets the required temperature for the item in the specified slot")
	public Object[] getTempRequired(final Context context, Arguments arguments)
	{
		return new Object[] {tile.getTempRequired(arguments.checkInteger(0))};
	}

	@Callback(doc = "function():boolean - Gets whether the smeltery has fuel")
	public Object[] hasFuel(final Context context, Arguments arguments)
	{
		return new Object[] {tile.hasFuel()};
	}

	@Callback(doc = "function():boolean - Gets whether the smeltery is empty (has no fluids)")
	public Object[] isEmpty(final Context context, Arguments arguments)
	{
		return new Object[] {tile.isEmpty()};
	}

	@Callback(doc = "function():int - Gets the total fluid capacity of the smeltery")
	public Object[] getCapacity(final Context context, Arguments arguments)
	{
		return new Object[] { tile.getTank().getCapacity() };
	}

	@Callback(doc = "function():int - Gets the total amount of all fluids in the smeltery")
	public Object[] getFillLevel(final Context context, Arguments arguments)
	{
		return new Object[] { tile.getTank().getFluidAmount() };
	}

	@Callback(doc = "function(index:integer):boolean - Moves the fluid at the specified index to the bottom")
	public Object[] moveFluidToBottom(final Context context, Arguments arguments)
	{
		int idx = arguments.checkInteger(0);

		if (idx < 1 || idx > tile.getTank().getFluids().size()) return new Object[] {false, "Invalid index"};

		tile.getTank().moveFluidToBottom(idx - 1);

		return new Object[] {true};

	}

	@Callback(doc = "function(index:int):table - Gets the stack in the specified slot")
	public Object[] getStackInSlot(final Context context, Arguments arguments)
	{

		if (!API.config.getBoolean("misc.allowItemStackInspection")) { return new Object[] {null, "ItemStack inspection disabled in OC config"}; }

		int slot = arguments.checkInteger(0);
		if (slot < 1 || slot > tile.getSizeInventory()) { return new Object[] {null, "Invalid slot"}; }

		slot--;

		ItemStack stack = tile.getItemHandler().getStackInSlot(slot);

		if(stack.isEmpty()) { return new Object[] {null, "No item"}; }

		return new Object[] {stack};

	}

	@Callback(doc = "function(index:int):number - Gets the heating progress of the item in the specified slot")
	public Object[] getHeatingProgress(final Context context, Arguments arguments)
	{
		return new Object[] { tile.getHeatingProgress(arguments.checkInteger(0)) };
	}

}
