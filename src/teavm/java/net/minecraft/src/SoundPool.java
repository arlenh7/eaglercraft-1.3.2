package net.minecraft.src;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SoundPool
{
    /** The RNG used by SoundPool. */
    private Random rand = new Random();

    /**
     * Maps a name (can be sound/newsound/streaming/music/newmusic) to a list of SoundPoolEntry's.
     */
    private Map nameToSoundPoolEntriesMapping = new HashMap();

    /** A list of all SoundPoolEntries that have been loaded. */
    private List allSoundPoolEntries = new ArrayList();

    /**
     * The number of soundPoolEntry's. This value is computed but never used (should be equal to
     * allSoundPoolEntries.size()).
     */
    public int numberOfSoundPoolEntries = 0;
    public boolean isGetRandomSound = true;

    /**
     * Adds a sound to this sound pool.
     */
    public SoundPoolEntry addSound(String par1Str, File par2File)
    {
        String var3 = par1Str;
        par1Str = par1Str.substring(0, par1Str.indexOf("."));

        if (this.isGetRandomSound)
        {
            while (Character.isDigit(par1Str.charAt(par1Str.length() - 1)))
            {
                par1Str = par1Str.substring(0, par1Str.length() - 1);
            }
        }

        par1Str = par1Str.replaceAll("/", ".");

        if (!this.nameToSoundPoolEntriesMapping.containsKey(par1Str))
        {
            this.nameToSoundPoolEntriesMapping.put(par1Str, new ArrayList());
        }

        String var4 = par2File != null ? par2File.getPath() : null;
        SoundPoolEntry var5 = new SoundPoolEntry(var3, var4);
        ((List)this.nameToSoundPoolEntriesMapping.get(par1Str)).add(var5);
        this.allSoundPoolEntries.add(var5);
        ++this.numberOfSoundPoolEntries;
        return var5;
    }

    /**
     * gets a random sound from the specified (by name, can be sound/newsound/streaming/music/newmusic) sound pool.
     */
    public SoundPoolEntry getRandomSoundFromSoundPool(String par1Str)
    {
        List var2 = (List)this.nameToSoundPoolEntriesMapping.get(par1Str);
        return var2 == null ? null : (SoundPoolEntry)var2.get(this.rand.nextInt(var2.size()));
    }

    /**
     * Gets a random SoundPoolEntry.
     */
    public SoundPoolEntry getRandomSound()
    {
        return this.allSoundPoolEntries.isEmpty() ? null : (SoundPoolEntry)this.allSoundPoolEntries.get(this.rand.nextInt(this.allSoundPoolEntries.size()));
    }
}
