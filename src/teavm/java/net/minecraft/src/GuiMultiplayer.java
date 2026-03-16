package net.minecraft.src;

public class GuiMultiplayer extends GuiScreen
{
    private final GuiScreen parentScreen;

    public GuiMultiplayer(GuiScreen par1GuiScreen)
    {
        this.parentScreen = par1GuiScreen;
    }

    public void initGui()
    {
        this.controlList.clear();
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, StatCollector.translateToLocal("gui.back")));
    }

    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 0)
        {
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }

    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "i didnt make multiplayer yet", this.width / 2, this.height / 2 - 20, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
