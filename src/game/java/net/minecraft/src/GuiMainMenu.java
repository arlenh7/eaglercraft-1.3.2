package net.minecraft.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import net.lax1dude.eaglercraft.EagRuntime;
import net.lax1dude.eaglercraft.minecraft.EaglerFontRenderer;
import net.lax1dude.eaglercraft.profile.GuiScreenEditProfile;
import net.lax1dude.eaglercraft.opengl.EaglercraftGPU;
import net.lax1dude.eaglercraft.opengl.ImageData;
import net.minecraft.client.Minecraft;
import net.peyton.eagler.minecraft.FontRenderer;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.glu.GLU;

public class GuiMainMenu extends GuiScreen
{
    /** The RNG used by the Main Menu Screen. */
    private static final Random rand = new Random();

    /** Counts the number of screen updates. */
    private float updateCounter = 0.0F;

    /** The splash message. */
    private String splashText = "missingno";
    private GuiButton field_73973_d;

    /** Timer used to rotate the panorama, increases every tick. */
    private int panoramaTimer = 0;

    /**
     * Texture allocated for the current viewport of the main menu's panorama background.
     */
    private int viewportTexture;
    private static final String[] field_73978_o = new String[] {"/title/bg/panorama0.png", "/title/bg/panorama1.png", "/title/bg/panorama2.png", "/title/bg/panorama3.png", "/title/bg/panorama4.png", "/title/bg/panorama5.png"};

    private int scrollPosition = 0;
    private static final int visibleLines = 21;
    private int dragstart = -1;
    private int dragstartI = -1;
    private ArrayList<String> readmeLines = new ArrayList();
    public boolean showReadme = false;
    private int mousex = 0;
    private int mousey = 0;
    private GuiButton readmeButton;

    public GuiMainMenu()
    {
        BufferedReader var1 = null;

        try
        {
            ArrayList var2 = new ArrayList();
            var1 = new BufferedReader(new InputStreamReader(GuiMainMenu.class.getResourceAsStream("/title/splashes.txt"), Charset.forName("UTF-8")));
            String var3;

            while ((var3 = var1.readLine()) != null)
            {
                var3 = var3.trim();

                if (var3.length() > 0)
                {
                    var2.add(var3);
                }
            }

            do
            {
                this.splashText = (String)var2.get(rand.nextInt(var2.size()));
            }
            while (this.splashText.hashCode() == 125780783);
        }
        catch (IOException var12)
        {
            ;
        }
        finally
        {
            if (var1 != null)
            {
                try
                {
                    var1.close();
                }
                catch (IOException var11)
                {
                    ;
                }
            }
        }

        this.updateCounter = rand.nextFloat();
    }

    public static Random getRand()
    {
        return rand;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        ++this.panoramaTimer;

        if (this.showReadme)
        {
            if (Mouse.isButtonDown(0) && this.dragstart > 0)
            {
                int trackHeight = 193;
                int lines = this.readmeLines.size();
                if (lines < 1)
                {
                    lines = 1;
                }
                this.scrollPosition = (this.mousey - this.dragstart) * lines / trackHeight + this.dragstartI;
                if (this.scrollPosition < 0)
                {
                    this.scrollPosition = 0;
                }
                if (lines <= visibleLines)
                {
                    this.scrollPosition = 0;
                }
                else if (this.scrollPosition + visibleLines > lines)
                {
                    this.scrollPosition = lines - visibleLines;
                }
            }
            else
            {
                this.dragstart = -1;
            }
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        if (this.showReadme && par2 == 1)
        {
            this.hideReadme();
        }
    }

    public void handleMouseInput()
    {
        super.handleMouseInput();
        if (this.showReadme)
        {
            int var1 = Mouse.getEventDWheel();
            if (var1 < 0)
            {
                this.scrollPosition += 3;
            }
            if (var1 > 0)
            {
                this.scrollPosition -= 3;
            }
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.viewportTexture = this.mc.renderEngine.allocateAndSetupTexture(new ImageData(256, 256, true));
        Calendar var1 = Calendar.getInstance();
        var1.setTime(new Date());

        if (var1.get(2) + 1 == 11 && var1.get(5) == 9)
        {
            this.splashText = "Happy birthday, ez!";
        }
        else if (var1.get(2) + 1 == 6 && var1.get(5) == 1)
        {
            this.splashText = "Happy birthday, Notch!";
        }
        else if (var1.get(2) + 1 == 12 && var1.get(5) == 24)
        {
            this.splashText = "Merry X-mas!";
        }
        else if (var1.get(2) + 1 == 1 && var1.get(5) == 1)
        {
            this.splashText = "Happy new year!";
        }

        StringTranslate var2 = StringTranslate.getInstance();
        int var4 = this.height / 4 + 48;

        if (this.mc.isDemo())
        {
            this.func_73972_b(var4, 24, var2);
        }
        else
        {
            this.func_73969_a(var4, 24, var2);
        }

        this.controlList.add(new GuiButton(3, this.width / 2 - 100, var4 + 48, "Fork on Github"));

        this.controlList.add(new GuiButton(0, this.width / 2 - 100, var4 + 72 + 12, 98, 20, var2.translateKey("menu.options")));
        this.controlList.add(new GuiButton(4, this.width / 2 + 2, var4 + 72 + 12, 98, 20, "Edit Profile"));

        this.controlList.add(new GuiButtonLanguage(5, this.width / 2 - 124, var4 + 72 + 12));
        this.readmeButton = new GuiButton(6, this.width - 102, 2, 100, 12, "Made by arlen");
        this.controlList.add(this.readmeButton);

        if (this.readmeLines.isEmpty())
        {
            int width = 315;
            byte[] file = EagRuntime.getResourceBytes("/assets/eagler/readme.txt");
            if (file == null)
            {
                for (int i = 0; i < 30; ++i)
                {
                    this.readmeLines.add(" -- file not found -- ");
                }
            }
            else
            {
                String text = new String(file, Charset.forName("UTF-8"));
                String[] lines = text.split("\n");
                for (String s : lines)
                {
                    String s2 = s.trim();
                    if (s2.length() == 0)
                    {
                        this.readmeLines.add("");
                    }
                    else
                    {
                        String[] words = s2.split(" ");
                        String currentLine = "   ";
                        for (String s3 : words)
                        {
                            String cCurrentLine = currentLine + s3 + " ";
                            if (this.fontRenderer.getStringWidth(cCurrentLine) < width)
                            {
                                currentLine = cCurrentLine;
                            }
                            else
                            {
                                this.readmeLines.add(currentLine);
                                currentLine = s3 + " ";
                            }
                        }
                        this.readmeLines.add(currentLine);
                    }
                }
            }
        }

        if (EagRuntime.getStorage("profileSeen") == null)
        {
            this.mc.displayGuiScreen(new GuiScreenEditProfile(this));
        }
    }

    private void func_73969_a(int par1, int par2, StringTranslate par3StringTranslate)
    {
        this.controlList.add(new GuiButton(1, this.width / 2 - 100, par1, par3StringTranslate.translateKey("menu.singleplayer")));
        this.controlList.add(new GuiButton(2, this.width / 2 - 100, par1 + par2 * 1, par3StringTranslate.translateKey("menu.multiplayer")));
    }

    private void func_73972_b(int par1, int par2, StringTranslate par3StringTranslate)
    {
        this.controlList.add(new GuiButton(11, this.width / 2 - 100, par1, par3StringTranslate.translateKey("menu.playdemo")));
        this.controlList.add(this.field_73973_d = new GuiButton(12, this.width / 2 - 100, par1 + par2 * 1, par3StringTranslate.translateKey("menu.resetdemo")));
        ISaveFormat var4 = this.mc.getSaveLoader();
        WorldInfo var5 = var4.getWorldInfo("Demo_World");

        if (var5 == null)
        {
            this.field_73973_d.enabled = false;
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 0)
        {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (par1GuiButton.id == 5)
        {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings));
        }

        if (par1GuiButton.id == 1)
        {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }

        if (par1GuiButton.id == 2)
        {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (par1GuiButton.id == 3)
        {
            EagRuntime.openLink("https://github.com/arlenh7/eaglercraft-1.3.2");
        }

        if (par1GuiButton.id == 4)
        {
            this.mc.displayGuiScreen(new GuiScreenEditProfile(this));
        }

        if (par1GuiButton.id == 11)
        {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }

        if (par1GuiButton.id == 12)
        {
            ISaveFormat var2 = this.mc.getSaveLoader();
            WorldInfo var3 = var2.getWorldInfo("Demo_World");

            if (var3 != null)
            {
                GuiYesNo var4 = GuiSelectWorld.func_74061_a(this, var3.getWorldName(), 12);
                this.mc.displayGuiScreen(var4);
            }
        }

        if (par1GuiButton.id == 6)
        {
            this.showReadme = true;
        }
    }

    private void hideReadme()
    {
        this.showReadme = false;
    }

    public void confirmClicked(boolean par1, int par2)
    {
        if (par1 && par2 == 12)
        {
            ISaveFormat var3 = this.mc.getSaveLoader();
            var3.flushCache();
            var3.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        }
    }

    /**
     * Draws the main menu panorama
     */
    private void drawPanorama(int par1, int par2, float par3)
    {
        Tessellator var4 = Tessellator.instance;
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GLU.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        byte var5 = 8;

        for (int var6 = 0; var6 < var5 * var5; ++var6)
        {
            GL11.glPushMatrix();
            float var7 = ((float)(var6 % var5) / (float)var5 - 0.5F) / 64.0F;
            float var8 = ((float)(var6 / var5) / (float)var5 - 0.5F) / 64.0F;
            float var9 = 0.0F;
            GL11.glTranslatef(var7, var8, var9);
            GL11.glRotatef(MathHelper.sin(((float)this.panoramaTimer + par3) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-((float)this.panoramaTimer + par3) * 0.1F, 0.0F, 1.0F, 0.0F);

            for (int var10 = 0; var10 < 6; ++var10)
            {
                GL11.glPushMatrix();

                if (var10 == 1)
                {
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (var10 == 2)
                {
                    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                }

                if (var10 == 3)
                {
                    GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (var10 == 4)
                {
                    GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (var10 == 5)
                {
                    GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                }

                GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture(field_73978_o[var10]));
                var4.startDrawingQuads();
                var4.setColorRGBA_I(16777215, 255 / (var6 + 1));
                float var11 = 0.0F;
                var4.addVertexWithUV(-1.0D, -1.0D, 1.0D, (double)(0.0F + var11), (double)(0.0F + var11));
                var4.addVertexWithUV(1.0D, -1.0D, 1.0D, (double)(1.0F - var11), (double)(0.0F + var11));
                var4.addVertexWithUV(1.0D, 1.0D, 1.0D, (double)(1.0F - var11), (double)(1.0F - var11));
                var4.addVertexWithUV(-1.0D, 1.0D, 1.0D, (double)(0.0F + var11), (double)(1.0F - var11));
                var4.draw();
                GL11.glPopMatrix();
            }

            GL11.glPopMatrix();
            GL11.glColorMask(true, true, true, false);
        }

        var4.setTranslation(0.0D, 0.0D, 0.0D);
        GL11.glColorMask(true, true, true, true);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    /**
     * Rotate and blurs the skybox view in the main menu
     */
    private void rotateAndBlurSkybox(float par1)
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.viewportTexture);
        GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColorMask(true, true, true, false);
        Tessellator var2 = Tessellator.instance;
        var2.startDrawingQuads();
        byte var3 = 3;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            var2.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F / (float)(var4 + 1));
            int var5 = this.width;
            int var6 = this.height;
            float var7 = (float)(var4 - var3 / 2) / 256.0F;
            var2.addVertexWithUV((double)var5, (double)var6, (double)this.zLevel, (double)(0.0F + var7), 0.0D);
            var2.addVertexWithUV((double)var5, 0.0D, (double)this.zLevel, (double)(1.0F + var7), 0.0D);
            var2.addVertexWithUV(0.0D, 0.0D, (double)this.zLevel, (double)(1.0F + var7), 1.0D);
            var2.addVertexWithUV(0.0D, (double)var6, (double)this.zLevel, (double)(0.0F + var7), 1.0D);
        }

        var2.draw();
        GL11.glColorMask(true, true, true, true);
    }

    /**
     * Renders the skybox in the main menu
     */
    private void renderSkybox(int par1, int par2, float par3)
    {
        GL11.glViewport(0, 0, 256, 256);
        this.drawPanorama(par1, par2, par3);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        Tessellator var4 = Tessellator.instance;
        var4.startDrawingQuads();
        float var5 = this.width > this.height ? 120.0F / (float)this.width : 120.0F / (float)this.height;
        float var6 = (float)this.height * var5 / 256.0F;
        float var7 = (float)this.width * var5 / 256.0F;
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        var4.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
        int var8 = this.width;
        int var9 = this.height;
        var4.addVertexWithUV(0.0D, (double)var9, (double)this.zLevel, (double)(0.5F - var6), (double)(0.5F + var7));
        var4.addVertexWithUV((double)var8, (double)var9, (double)this.zLevel, (double)(0.5F - var6), (double)(0.5F - var7));
        var4.addVertexWithUV((double)var8, 0.0D, (double)this.zLevel, (double)(0.5F + var6), (double)(0.5F - var7));
        var4.addVertexWithUV(0.0D, 0.0D, (double)this.zLevel, (double)(0.5F + var6), (double)(0.5F + var7));
        var4.draw();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.mousex = par1;
        this.mousey = par2;
        this.renderSkybox(par1, par2, par3);
        Tessellator var4 = Tessellator.instance;
        short var5 = 274;
        int var6 = this.width / 2 - var5 / 2;
        byte var7 = 30;
        this.drawGradientRect(0, 0, this.width, this.height, -2130706433, 16777215);
        this.drawGradientRect(0, 0, this.width, this.height, 0, Integer.MIN_VALUE);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/title/mclogo.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if ((double)this.updateCounter < 1.0E-4D)
        {
            this.drawTexturedModalRect(var6 + 0, var7 + 0, 0, 0, 99, 44);
            this.drawTexturedModalRect(var6 + 99, var7 + 0, 129, 0, 27, 44);
            this.drawTexturedModalRect(var6 + 99 + 26, var7 + 0, 126, 0, 3, 44);
            this.drawTexturedModalRect(var6 + 99 + 26 + 3, var7 + 0, 99, 0, 26, 44);
            this.drawTexturedModalRect(var6 + 155, var7 + 0, 0, 45, 155, 44);
        }
        else
        {
            this.drawTexturedModalRect(var6 + 0, var7 + 0, 0, 0, 155, 44);
            this.drawTexturedModalRect(var6 + 155, var7 + 0, 0, 45, 155, 44);
        }

        var4.setColorOpaque_I(16777215);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.width / 2 + 90), 70.0F, 0.0F);
        GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
        float var8 = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * (float)Math.PI * 2.0F) * 0.1F);
        var8 = var8 * 100.0F / (float)(this.fontRenderer.getStringWidth(this.splashText) + 32);
        GL11.glScalef(var8, var8, var8);
        this.drawCenteredString(this.fontRenderer, this.splashText, 0, -8, 16776960);
        GL11.glPopMatrix();
        String var9 = "Minecraft 1.3.2";

        if (this.mc.isDemo())
        {
            var9 = var9 + " Demo";
        }

        this.drawString(this.fontRenderer, var9, 2, this.height - 20, 16777215);
        this.drawString(this.fontRenderer, "Eaglercraft 1.3.2-u1 [" + EagRuntime.getPlatformType() + "]", 2, this.height - 10, 16777215);
        String var10 = "Copyright Mojang AB. Do not distribute!";
        this.drawString(this.fontRenderer, var10, this.width - this.fontRenderer.getStringWidth(var10) - 2, this.height - 10, 16777215);

        if (this.showReadme)
        {
            super.drawScreen(0, 0, par3);
            this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
            int x = (this.width - 345) / 2;
            int y = (this.height - 230) / 2;
            drawRect(x, y, x + 345, y + 230, 0xFFCCCCCC);
            drawRect(x + 2, y + 2, x + 343, y + 228, 0xFFEEEEEE);
            drawRect(x + 323, y + 7, x + 336, y + 20, 0xFFAA4444);
            this.drawCenteredString(this.fontRenderer, "x", x + 329, y + 9, 0xFFFFFFFF);

            int lines = this.readmeLines.size();
            if (this.scrollPosition < 0)
            {
                this.scrollPosition = 0;
            }
            if (lines <= visibleLines)
            {
                this.scrollPosition = 0;
            }
            else if (this.scrollPosition + visibleLines > lines)
            {
                this.scrollPosition = lines - visibleLines;
            }

            for (int i = 0; i < visibleLines && (this.scrollPosition + i) < lines; ++i)
            {
                this.fontRenderer.drawString(this.readmeLines.get(this.scrollPosition + i), x + 10, y + 10 + (i * 10), 0x404060);
            }

            int trackHeight = 193;
            int offset = lines > 0 ? trackHeight * this.scrollPosition / lines : 0;
            int handleHeight = lines > 0 ? (visibleLines * trackHeight / lines) : trackHeight;
            if (handleHeight < 8)
            {
                handleHeight = 8;
            }
            drawRect(x + 326, y + 27, x + 334, y + 220, 0x33000020);
            drawRect(x + 326, y + 27 + offset, x + 334, y + 27 + offset + handleHeight + 1, 0x66000000);
        }
        else
        {
            super.drawScreen(par1, par2, par3);
        }
    }

    protected void mouseClicked(int par1, int par2, int par3)
    {
        if (this.showReadme)
        {
            if (par3 == 0)
            {
                int x = (this.width - 345) / 2;
                int y = (this.height - 230) / 2;
                if (par1 >= (x + 323) && par1 <= (x + 336) && par2 >= (y + 7) && par2 <= (y + 20))
                {
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    this.hideReadme();
                }
                int trackHeight = 193;
                int lines = this.readmeLines.size();
                if (lines < 1)
                {
                    lines = 1;
                }
                int offset = trackHeight * this.scrollPosition / lines;
                int handleHeight = (visibleLines * trackHeight / lines);
                if (handleHeight < 8)
                {
                    handleHeight = 8;
                }
                if (par1 >= (x + 326) && par1 <= (x + 334) && par2 >= (y + 27 + offset)
                        && par2 <= (y + 27 + offset + handleHeight + 1))
                {
                    this.dragstart = par2;
                    this.dragstartI = this.scrollPosition;
                }
            }
        }
        else
        {
            super.mouseClicked(par1, par2, par3);
        }

    }
}
