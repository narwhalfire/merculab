package net.scottnotfound.merculab.proxy;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
@Mod.EventBusSubscriber(Side.SERVER)
public class ServerProxy extends Proxy
{

}
