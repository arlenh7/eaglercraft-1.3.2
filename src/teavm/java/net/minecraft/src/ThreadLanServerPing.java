package net.minecraft.src;

import java.io.IOException;
import java.util.logging.Logger;

public class ThreadLanServerPing extends Thread
{
    private static Logger field_77530_a = Logger.getLogger("Minecraft");
    private final String motd;
    private boolean isStopping = true;
    private final String address;

    public ThreadLanServerPing(String par1Str, String par2Str) throws IOException
    {
        super("LanServerPinger");
        this.motd = par1Str;
        this.address = par2Str;
        this.setDaemon(true);
    }

    public void run()
    {
        // no-op in TeaVM build (LAN ping not supported)
    }

    public void interrupt()
    {
        super.interrupt();
        this.isStopping = false;
    }

    public static String getPingResponse(String par0Str, String par1Str)
    {
        return "[MOTD]" + par0Str + "[/MOTD][AD]" + par1Str + "[/AD]";
    }

    public static String func_77524_a(String par0Str)
    {
        int var1 = par0Str.indexOf("[MOTD]");

        if (var1 < 0)
        {
            return "missing no";
        }
        else
        {
            int var2 = par0Str.indexOf("[/MOTD]", var1 + "[MOTD]".length());
            return var2 < var1 ? "missing no" : par0Str.substring(var1 + "[MOTD]".length(), var2);
        }
    }

    public static String func_77523_b(String par0Str)
    {
        int var1 = par0Str.indexOf("[/MOTD]");

        if (var1 < 0)
        {
            return null;
        }
        else
        {
            int var2 = par0Str.indexOf("[/MOTD]", var1 + "[/MOTD]".length());

            if (var2 >= 0)
            {
                return null;
            }
            else
            {
                int var3 = par0Str.indexOf("[AD]", var1 + "[/MOTD]".length());

                if (var3 < 0)
                {
                    return null;
                }
                else
                {
                    int var4 = par0Str.indexOf("[/AD]", var3 + "[AD]".length());
                    return var4 < var3 ? null : par0Str.substring(var3 + "[AD]".length(), var4);
                }
            }
        }
    }
}
