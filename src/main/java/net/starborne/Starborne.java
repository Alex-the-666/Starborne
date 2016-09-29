package net.starborne;

import net.ilexiconn.llibrary.server.network.NetworkWrapper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.starborne.server.ServerProxy;
import net.starborne.server.core.StarbornePlugin;
import net.starborne.server.message.BreakBlockEntityMessage;
import net.starborne.server.message.EntityChunkMessage;
import net.starborne.server.message.InteractBlockEntityMessage;
import net.starborne.server.message.PlayEventEntityMessage;
import net.starborne.server.message.SetEntityBlockMessage;

@Mod(modid = Starborne.MODID, name = "Starborne", version = Starborne.VERSION, dependencies = "required-after:llibrary@[" + Starborne.LLIBRARY_VERSION + ",)")
public class Starborne {
    public static final String MODID = "starborne";
    public static final String VERSION = "0.1.0";
    public static final String LLIBRARY_VERSION = "1.7.0";

    @Mod.Instance(Starborne.MODID)
    public static Starborne INSTANCE;

    @SidedProxy(clientSide = "net.starborne.client.ClientProxy", serverSide = "net.starborne.server.ServerProxy")
    public static ServerProxy PROXY;

    @NetworkWrapper({ EntityChunkMessage.class, SetEntityBlockMessage.class, InteractBlockEntityMessage.class, BreakBlockEntityMessage.class, PlayEventEntityMessage.class })
    public static SimpleNetworkWrapper networkWrapper;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        if (!StarbornePlugin.loaded) {
            System.err.println("Failed to load Starborne! Missing coremod parameters! (-Dfml.coreMods.load=net.starborne.server.core.StarbornePlugin)");
            FMLCommonHandler.instance().exitJava(1, false);
        }

        Starborne.PROXY.onPreInit();
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        Starborne.PROXY.onInit();
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        Starborne.PROXY.onPostInit();
    }
}
