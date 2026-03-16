package net.minecraft.src;

import net.lax1dude.eaglercraft.opengl.GlStateManager;
import org.lwjgl.opengl.GL11;

public class OpenGlHelper
{
    /**
     * An OpenGL constant corresponding to GL_TEXTURE0, used when setting data pertaining to auxiliary OpenGL texture
     * units.
     */
    public static int defaultTexUnit;

    /**
     * An OpenGL constant corresponding to GL_TEXTURE1, used when setting data pertaining to auxiliary OpenGL texture
     * units.
     */
    public static int lightmapTexUnit;

    /** Last lightmap texture coords set via setLightmapTextureCoords */
    public static float lastLightmapX = 0.0F;
    public static float lastLightmapY = 0.0F;

    /**
     * True if the renderer supports multitextures and the OpenGL version != 1.3
     */
    private static boolean useMultitextureARB = false;

    /**
     * Initializes the texture constants to be used when rendering lightmap values
     */
    public static void initializeTextures()
    {
        useMultitextureARB = false;
        defaultTexUnit = GL11.GL_TEXTURE0;
        lightmapTexUnit = GL11.GL_TEXTURE1;
    }

    /**
     * Sets the current lightmap texture to the specified OpenGL constant
     */
    public static void setActiveTexture(int par0)
    {
        GlStateManager.setActiveTexture(par0);
    }

    /**
     * Sets the current lightmap texture to the specified OpenGL constant
     */
    public static void setClientActiveTexture(int par0)
    {
        GlStateManager.setActiveTexture(par0);
    }

    /**
     * Sets the current coordinates of the given lightmap texture
     */
    public static void setLightmapTextureCoords(int par0, float par1, float par2)
    {
        GlStateManager.setActiveTexture(par0);
        GlStateManager.texCoords2D(par1, par2);
        if (par0 == lightmapTexUnit)
        {
            lastLightmapX = par1;
            lastLightmapY = par2;
        }
    }
}
