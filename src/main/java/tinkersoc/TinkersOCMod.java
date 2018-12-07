package tinkersoc;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import li.cil.oc.api.Driver;
import tinkersoc.smeltery.DriverSmeltery;
import tinkersoc.furnace.DriverTinkerFurnace;
import tinkersoc.tank.DriverTinkerTank;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.init.Items;

@Mod(modid = TinkersOCMod.MODID, name = TinkersOCMod.NAME, version = TinkersOCMod.VERSION, dependencies = "required-after:opencomputers@[1.7.0,);required-after:tconstruct@[1.12.2-2.9.0,)")
public class TinkersOCMod
{

	public static final String MODID = "tinkersoc";
	public static final String NAME = "Tinkers OpenComputers Driver";
	public static final String VERSION = "0.5";


	private static Logger logger;

	public static Logger getLogger() { return logger; }

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		Driver.add(new DriverSmeltery());
		Driver.add(new DriverTinkerTank());
		Driver.add(new DriverTinkerFurnace());
	}
}
