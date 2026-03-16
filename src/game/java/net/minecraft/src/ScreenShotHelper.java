package net.minecraft.src;

import java.io.File;
import net.lax1dude.eaglercraft.EagRuntime;

public class ScreenShotHelper
{
    /**
     * Takes a screenshot and saves it to the screenshots directory. Returns the filename of the screenshot.
     */
    public static String saveScreenshot(File par0File, int par1, int par2)
    {
        return formatScreenshotResult(EagRuntime.saveScreenshot());
    }

    public static String func_74292_a(File par0File, String par1Str, int par2, int par3)
    {
        return formatScreenshotResult(EagRuntime.saveScreenshot());
    }

    private static String formatScreenshotResult(String name)
    {
        if (name == null || "nothing".equals(name))
        {
            return "Failed to save";
        }

        return "Saved screenshot as " + name;
    }
}
