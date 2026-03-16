package net.minecraft.src;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtil
{
    public static String buildPostString(Map par0Map)
    {
        StringBuilder var1 = new StringBuilder();
        Iterator var2 = par0Map.entrySet().iterator();

        while (var2.hasNext())
        {
            Entry var3 = (Entry)var2.next();

            if (var1.length() > 0)
            {
                var1.append('&');
            }

            var1.append(var3.getKey());

            if (var3.getValue() != null)
            {
                var1.append('=');
                var1.append(var3.getValue().toString());
            }
        }

        return var1.toString();
    }

    public static String sendPost(Object par0URL, Map par1Map, boolean par2)
    {
        return "";
    }

    public static String sendPost(Object par0URL, String par1Str, boolean par2)
    {
        return "";
    }

    public static void downloadTexturePack(File par0File, String par1Str, IDownloadSuccess par2IDownloadSuccess, Map par3Map, int par4, IProgressUpdate par5IProgressUpdate)
    {
        // no-op in TeaVM build
    }

    public static int func_76181_a() throws IOException
    {
        return 25564;
    }
}
