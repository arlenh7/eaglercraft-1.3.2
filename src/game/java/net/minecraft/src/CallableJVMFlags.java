package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import net.lax1dude.eaglercraft.EagRuntime;
import net.lax1dude.eaglercraft.internal.EnumPlatformType;

class CallableJVMFlags implements Callable
{
    /** Gets additional Java Enviroment info for Crash Report. */
    final CrashReport crashReportJVMFlags;

    CallableJVMFlags(CrashReport par1CrashReport)
    {
        this.crashReportJVMFlags = par1CrashReport;
    }

    public String func_71487_a()
    {
        if (EagRuntime.getPlatformType() == EnumPlatformType.WASM_GC)
        {
            return "0 total; (not available)";
        }

        try
        {
            Class<?> mf = Class.forName("java.lang.management.ManagementFactory");
            Object runtime = mf.getMethod("getRuntimeMXBean").invoke(null);
            List var2 = (List)runtime.getClass().getMethod("getInputArguments").invoke(runtime);
            int var3 = 0;
            StringBuilder var4 = new StringBuilder();
            Iterator var5 = var2.iterator();

            while (var5.hasNext())
            {
                String var6 = (String)var5.next();

                if (var6.startsWith("-X"))
                {
                    if (var3++ > 0)
                    {
                        var4.append(" ");
                    }

                    var4.append(var6);
                }
            }

            return String.format("%d total; %s", new Object[] {Integer.valueOf(var3), var4.toString()});
        }
        catch (Throwable t)
        {
            return "0 total; (not available)";
        }
    }

    public Object call()
    {
        return this.func_71487_a();
    }
}
