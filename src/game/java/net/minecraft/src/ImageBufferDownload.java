package net.minecraft.src;

import net.lax1dude.eaglercraft.opengl.ImageData;

public class ImageBufferDownload implements ImageBuffer
{
    private int[] imageData;
    private int imageWidth;
    private int imageHeight;

    public ImageData parseUserSkin(ImageData par1ImageData)
    {
        if (par1ImageData == null)
        {
            return null;
        }
        else
        {
            this.imageWidth = 64;
            this.imageHeight = 32;
            ImageData var2 = new ImageData(this.imageWidth, this.imageHeight, true);
            int copyWidth = Math.min(par1ImageData.width, this.imageWidth);
            int copyHeight = Math.min(par1ImageData.height, this.imageHeight);

            for (int y = 0; y < copyHeight; ++y)
            {
                System.arraycopy(par1ImageData.pixels, y * par1ImageData.width, var2.pixels, y * this.imageWidth, copyWidth);
            }

            this.imageData = var2.pixels;
            this.func_78433_b(0, 0, 32, 16);
            this.func_78434_a(32, 0, 64, 32);
            this.func_78433_b(0, 16, 64, 32);
            boolean var4 = false;
            int var5;
            int var6;
            int var7;

            for (var5 = 32; var5 < 64; ++var5)
            {
                for (var6 = 0; var6 < 16; ++var6)
                {
                    var7 = this.imageData[var5 + var6 * 64];

                    if ((var7 >> 24 & 255) < 128)
                    {
                        var4 = true;
                    }
                }
            }

            if (!var4)
            {
                for (var5 = 32; var5 < 64; ++var5)
                {
                    for (var6 = 0; var6 < 16; ++var6)
                    {
                        var7 = this.imageData[var5 + var6 * 64];

                        if ((var7 >> 24 & 255) < 128)
                        {
                            var4 = true;
                        }
                    }
                }
            }

            return var2;
        }
    }

    private void func_78434_a(int par1, int par2, int par3, int par4)
    {
        if (!this.func_78435_c(par1, par2, par3, par4))
        {
            for (int var5 = par1; var5 < par3; ++var5)
            {
                for (int var6 = par2; var6 < par4; ++var6)
                {
                    this.imageData[var5 + var6 * this.imageWidth] &= 16777215;
                }
            }
        }
    }

    private void func_78433_b(int par1, int par2, int par3, int par4)
    {
        for (int var5 = par1; var5 < par3; ++var5)
        {
            for (int var6 = par2; var6 < par4; ++var6)
            {
                this.imageData[var5 + var6 * this.imageWidth] |= -16777216;
            }
        }
    }

    private boolean func_78435_c(int par1, int par2, int par3, int par4)
    {
        for (int var5 = par1; var5 < par3; ++var5)
        {
            for (int var6 = par2; var6 < par4; ++var6)
            {
                int var7 = this.imageData[var5 + var6 * this.imageWidth];

                if ((var7 >> 24 & 255) < 128)
                {
                    return true;
                }
            }
        }

        return false;
    }
}
