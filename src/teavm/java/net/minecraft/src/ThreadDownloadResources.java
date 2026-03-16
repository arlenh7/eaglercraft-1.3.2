package net.minecraft.src;

import java.io.File;
import net.minecraft.client.Minecraft;

public class ThreadDownloadResources extends Thread
{
    public File resourcesFolder;
    private final Minecraft mc;
    private boolean closing = false;

    public ThreadDownloadResources(File par1File, Minecraft par2Minecraft)
    {
        this.mc = par2Minecraft;
        this.setName("Resource download thread");
        this.setDaemon(true);
        this.resourcesFolder = new File(par1File, "resources/");
    }

    public void run()
    {
        // no-op in TeaVM build
    }

    public void reloadResources()
    {
        // no-op in TeaVM build
    }

    public void closeMinecraft()
    {
        this.closing = true;
    }
}
