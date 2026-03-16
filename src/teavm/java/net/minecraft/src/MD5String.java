package net.minecraft.src;

import java.math.BigInteger;
import net.lax1dude.eaglercraft.crypto.MD5Digest;

public class MD5String
{
    private String theString;

    public MD5String(String par1Str)
    {
        this.theString = par1Str;
    }

    /**
     * Gets the MD5 string
     */
    public String getMD5String(String par1Str)
    {
        String var2 = this.theString + par1Str;
        byte[] var3 = var2.getBytes();
        MD5Digest var4 = new MD5Digest();
        var4.update(var3, 0, var2.length());
        byte[] var5 = new byte[16];
        var4.doFinal(var5, 0);
        return (new BigInteger(1, var5)).toString(16);
    }
}
