package tinkersoc;

import li.cil.oc.api.Network;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.AbstractManagedEnvironment;
import li.cil.oc.api.driver.NamedBlock;
import net.minecraft.tileentity.TileEntity;


public class AbstractTinkersEnvironment<T extends TileEntity> extends  AbstractManagedEnvironment implements NamedBlock {

	protected T tile;
	protected String name;

	public AbstractTinkersEnvironment(String name, T tile)
	{
		this.name = name;
		this.tile = tile;

		setNode(Network.newNode(this, Visibility.Network).withComponent(name, Visibility.Network).create());
	}

	@Override
	public String preferredName() {
		return name;
	}

	@Override
	public int priority() {
		return 5;
	}
}
