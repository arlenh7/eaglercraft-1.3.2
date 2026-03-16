package net.minecraft.src;

import java.io.IOException;

public class ServerListenThread extends Thread
{
    public ServerListenThread(NetworkListenThread par1NetworkListenThread, Object par2InetAddress, int par3) throws IOException
    {
        super("ServerListenThread");
        setDaemon(true);
    }

    public void processPendingConnections()
    {
        // no-op in TeaVM build
    }

    public void func_71768_b()
    {
        // no-op in TeaVM build
    }

    public void func_71769_a(Object par1InetAddress)
    {
        // no-op in TeaVM build
    }

    public int func_71765_d()
    {
        return 0;
    }

    public Object getInetAddress()
    {
        return null;
    }
}
