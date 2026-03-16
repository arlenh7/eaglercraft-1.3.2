package net.minecraft.src;

import java.io.IOException;

public class ThreadLanServerFind extends Thread
{
    private final LanServerList field_77500_a;

    public ThreadLanServerFind(LanServerList par1LanServerList) throws IOException
    {
        super("LanServerDetector");
        this.field_77500_a = par1LanServerList;
        this.setDaemon(true);
    }

    public void run()
    {
        // no-op in TeaVM build (LAN discovery not supported)
    }
}
