package net.minecraft.src;

class ThreadConnectToServer extends Thread
{
    final GuiConnecting guiConnecting;
    final String server;
    final int port;

    ThreadConnectToServer(GuiConnecting par1GuiConnecting, String par2Str, int par3)
    {
        this.guiConnecting = par1GuiConnecting;
        this.server = par2Str;
        this.port = par3;
        this.setDaemon(true);
    }

    public void run()
    {
        // multiplayer not supported
    }
}
