package net.minecraft.src;

import java.util.concurrent.Callable;
import net.minecraft.client.ClientBrandRetriever;

class CallableIsModded implements Callable
{
    /** Gets if Client Is Modded. */
    final IntegratedServer integratedServerIsModded;

    CallableIsModded(IntegratedServer par1IntegratedServer)
    {
        this.integratedServerIsModded = par1IntegratedServer;
    }

    public String func_76971_a()
    {
        String var1 = ClientBrandRetriever.getClientModName();

        if (!var1.equals("vanilla"))
        {
            return "Definitely; '" + var1 + "'";
        }
        else
        {
            var1 = this.integratedServerIsModded.getServerModName();
            return !var1.equals("vanilla") ? "Definitely; '" + var1 + "'" : "Probably not";
        }
    }

    public Object call()
    {
        return this.func_76971_a();
    }
}
