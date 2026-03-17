package net.minecraft.src;

/**
 * Lightweight handoff screen used while wiring the client to an integrated server.
 * Keeps packets flowing and prevents fallback to the main menu during startup.
 */
public class GuiJoiningWorld extends GuiScreen
{
    private final NetClientHandler netHandler;
    private int keepAliveCounter = 0;

    public GuiJoiningWorld(NetClientHandler par1NetClientHandler)
    {
        this.netHandler = par1NetClientHandler;
    }

    protected void keyTyped(char par1, int par2) {}

    public void initGui()
    {
        this.controlList.clear();
    }

    public void updateScreen()
    {
        ++this.keepAliveCounter;

        if (this.keepAliveCounter % 20 == 0)
        {
            this.netHandler.addToSendQueue(new Packet0KeepAlive());
        }

        this.netHandler.processReadPackets();
    }

    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawBackground(0);
        this.drawCenteredString(this.fontRenderer, "Joining World...", this.width / 2, this.height / 2 - 50, 16777215);
        super.drawScreen(par1, par2, par3);
    }

    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
