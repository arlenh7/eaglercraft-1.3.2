package net.minecraft.src;

import java.io.File;
import java.util.Map;

final class HttpUtilRunnable implements Runnable
{
    final IProgressUpdate feedbackHook;
    final String sourceURL;
    final Map field_76177_c;
    final File destinationFile;
    final IDownloadSuccess field_76175_e;
    final int field_76173_f;

    HttpUtilRunnable(IProgressUpdate par1IProgressUpdate, String par2Str, Map par3Map, File par4File, IDownloadSuccess par5IDownloadSuccess, int par6)
    {
        this.feedbackHook = par1IProgressUpdate;
        this.sourceURL = par2Str;
        this.field_76177_c = par3Map;
        this.destinationFile = par4File;
        this.field_76175_e = par5IDownloadSuccess;
        this.field_76173_f = par6;
    }

    public void run()
    {
        if (this.feedbackHook != null)
        {
            this.feedbackHook.onNoMoreProgress();
        }
    }
}
