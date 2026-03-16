package net.minecraft.src;

public class TcpConnection implements NetworkManager
{
    public TcpConnection(Object par1Socket, String par2Str, NetHandler par3NetHandler)
    {
        throw new UnsupportedOperationException("TcpConnection is not supported in the TeaVM build");
    }

    public TcpConnection(Object par1Socket, String par2Str, NetHandler par3NetHandler, Object par4PrivateKey)
    {
        throw new UnsupportedOperationException("TcpConnection is not supported in the TeaVM build");
    }

    public void setNetHandler(NetHandler var1) {}

    public void addToSendQueue(Packet var1) {}

    public void wakeThreads() {}

    public void processReadPackets() {}

    public String getSocketAddress()
    {
        return "local";
    }

    public void serverShutdown() {}

    public int packetSize()
    {
        return 0;
    }

    public void networkShutdown(String var1, Object ... var2) {}

    public void closeConnections() {}
}
