package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class GuiConnecting extends GuiScreen
{
    private final GuiScreen parentScreen;

    public GuiConnecting(Minecraft par1Minecraft, ServerData par2ServerData)
    {
        this.mc = par1Minecraft;
        this.parentScreen = new GuiMainMenu();
    }

    public GuiConnecting(Minecraft par1Minecraft, String par2Str, int par3)
    {
        this.mc = par1Minecraft;
        this.parentScreen = new GuiMainMenu();
    }

    protected void keyTyped(char par1, int par2) {}

    public void initGui()
    {
        StringTranslate var1 = StringTranslate.getInstance();
        this.controlList.clear();
        this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, var1.translateKey("gui.back")));
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
