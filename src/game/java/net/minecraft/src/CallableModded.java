package net.minecraft.src;

import java.util.concurrent.Callable;
import net.lax1dude.eaglercraft.EagRuntime;
import net.lax1dude.eaglercraft.internal.EnumPlatformType;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;

public class CallableModded implements Callable
{
    /** Gets if Minecraft is Modded. */
    final Minecraft minecraftModded;

    public CallableModded(Minecraft par1Minecraft)
    {
        this.minecraftModded = par1Minecraft;
    }

    public String func_74415_a()
    {
        String var1 = ClientBrandRetriever.getClientModName();
        if (!var1.equals("vanilla"))
        {
            return "Definitely; \'" + var1 + "\'";
        }

        if (EagRuntime.getPlatformType() == EnumPlatformType.WASM_GC)
        {
            return "Very likely";
        }

        try
        {
            Object[] signers = (Object[])Minecraft.class.getMethod("getSigners").invoke(Minecraft.class);
            return signers == null ? "Very likely" : "Probably not";
        }
        catch (Throwable t)
        {
            return "Very likely";
        }
    }

    public Object call()
    {
        return this.func_74415_a();
    }
}
