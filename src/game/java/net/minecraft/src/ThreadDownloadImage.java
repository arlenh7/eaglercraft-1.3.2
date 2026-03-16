package net.minecraft.src;

import net.lax1dude.eaglercraft.internal.PlatformRuntime;
import net.lax1dude.eaglercraft.opengl.ImageData;

class ThreadDownloadImage extends Thread
{
    /** The URL of the image to download. */
    final String location;

    /** The image buffer to use. */
    final ImageBuffer buffer;

    /** The image data. */
    final ThreadDownloadImageData imageData;

    ThreadDownloadImage(ThreadDownloadImageData par1ThreadDownloadImageData, String par2Str, ImageBuffer par3ImageBuffer)
    {
        this.imageData = par1ThreadDownloadImageData;
        this.location = par2Str;
        this.buffer = par3ImageBuffer;
    }

    public void run()
    {
        PlatformRuntime.downloadRemoteURIByteArray(this.location, (data) ->
        {
            if (data == null)
            {
                return;
            }

            ImageData img = ImageData.loadImageFile(data, ImageData.getMimeFromType(this.location));

            if (img != null)
            {
                img = img.swapRB();
                this.imageData.image = this.buffer == null ? img : this.buffer.parseUserSkin(img);
            }
        });
    }
}
