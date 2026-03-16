package net.minecraft.src;

class ThreadPollServers extends Thread
{
    private final GuiMultiplayer guiMultiplayer;

    ThreadPollServers(GuiMultiplayer par1GuiMultiplayer)
    {
        this.guiMultiplayer = par1GuiMultiplayer;
        this.setDaemon(true);
    }

    public void run()
    {
        // no-op
    }
}
