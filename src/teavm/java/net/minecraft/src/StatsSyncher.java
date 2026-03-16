package net.minecraft.src;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import net.lax1dude.eaglercraft.EagRuntime;

public class StatsSyncher
{
    private volatile boolean isBusy = false;
    private volatile Map field_77430_b = null;
    private volatile Map field_77431_c = null;

    /**
     * The StatFileWriter object, presumably used to write to the statistics files
     */
    private StatFileWriter statFileWriter;

    /** Unused in TeaVM */
    private File unsentDataFile;
    private File dataFile;
    private File unsentTempFile;
    private File tempFile;
    private File unsentOldFile;
    private File oldFile;

    /** The Session object */
    private Session theSession;
    private int field_77433_l = 0;
    private int field_77434_m = 0;

    private final String storageKey;
    private final String usernameSafe;

    public StatsSyncher(Session par1Session, StatFileWriter par2StatFileWriter, File par3File)
    {
        this.statFileWriter = par2StatFileWriter;
        this.theSession = par1Session;
        String user = "player";
        if (par1Session != null && par1Session.username != null && par1Session.username.length() > 0)
        {
            user = par1Session.username;
        }
        this.usernameSafe = user;
        this.storageKey = "stats." + user.toLowerCase();

        Map loaded = this.loadFromStorage();
        if (loaded != null)
        {
            par2StatFileWriter.writeStats(loaded);
        }
    }

    private Map loadFromStorage()
    {
        try
        {
            byte[] data = EagRuntime.getStorage(this.storageKey);
            if (data != null)
            {
                String str = new String(data, StandardCharsets.UTF_8);
                return StatFileWriter.func_77453_b(str);
            }
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }

        return null;
    }

    private void saveToStorage(Map par1Map)
    {
        if (par1Map == null)
        {
            return;
        }

        try
        {
            String str = StatFileWriter.func_77441_a(this.usernameSafe, "local", par1Map);
            EagRuntime.setStorage(this.storageKey, str.getBytes(StandardCharsets.UTF_8));
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

    /**
     * Attempts to begin receiving stats from the server. Will throw an IllegalStateException if the syncher is already
     * busy.
     */
    public void beginReceiveStats()
    {
        if (this.isBusy)
        {
            throw new IllegalStateException("Can\'t get stats from server while StatsSyncher is busy!");
        }
        else
        {
            this.field_77433_l = 100;
            this.isBusy = true;
            try
            {
                Map loaded = this.loadFromStorage();
                if (loaded != null)
                {
                    this.field_77430_b = loaded;
                }
            }
            finally
            {
                this.isBusy = false;
            }
        }
    }

    /**
     * Attempts to begin sending stats to the server. Will throw an IllegalStateException if the syncher is already
     * busy.
     */
    public void beginSendStats(Map par1Map)
    {
        if (this.isBusy)
        {
            throw new IllegalStateException("Can\'t save stats while StatsSyncher is busy!");
        }
        else
        {
            this.field_77433_l = 100;
            this.isBusy = true;
            try
            {
                this.saveToStorage(par1Map);
            }
            finally
            {
                this.isBusy = false;
            }
        }
    }

    public void syncStatsFileWithMap(Map par1Map)
    {
        if (this.isBusy)
        {
            return;
        }

        this.isBusy = true;

        try
        {
            this.saveToStorage(par1Map);
        }
        finally
        {
            this.isBusy = false;
        }
    }

    public boolean func_77425_c()
    {
        return this.field_77433_l <= 0 && !this.isBusy && this.field_77431_c == null;
    }

    public void func_77422_e()
    {
        if (this.field_77433_l > 0)
        {
            --this.field_77433_l;
        }

        if (this.field_77434_m > 0)
        {
            --this.field_77434_m;
        }

        if (this.field_77431_c != null)
        {
            this.statFileWriter.func_77448_c(this.field_77431_c);
            this.field_77431_c = null;
        }

        if (this.field_77430_b != null)
        {
            this.statFileWriter.func_77452_b(this.field_77430_b);
            this.field_77430_b = null;
        }
    }

    static Map func_77419_a(StatsSyncher par0StatsSyncher)
    {
        return par0StatsSyncher.field_77430_b;
    }

    static File func_77408_b(StatsSyncher par0StatsSyncher)
    {
        return par0StatsSyncher.dataFile;
    }

    static File func_77407_c(StatsSyncher par0StatsSyncher)
    {
        return par0StatsSyncher.tempFile;
    }

    static File func_77411_d(StatsSyncher par0StatsSyncher)
    {
        return par0StatsSyncher.oldFile;
    }

    static void func_77414_a(StatsSyncher par0StatsSyncher, Map par1Map, File par2File, File par3File, File par4File)
    {
        par0StatsSyncher.saveToStorage(par1Map);
    }

    static Map func_77416_a(StatsSyncher par0StatsSyncher, Map par1Map)
    {
        return par0StatsSyncher.field_77430_b = par1Map;
    }

    static Map func_77410_a(StatsSyncher par0StatsSyncher, File par1File, File par2File, File par3File)
    {
        return par0StatsSyncher.loadFromStorage();
    }

    static boolean setBusy(StatsSyncher par0StatsSyncher, boolean par1)
    {
        return par0StatsSyncher.isBusy = par1;
    }

    static File getUnsentDataFile(StatsSyncher par0StatsSyncher)
    {
        return par0StatsSyncher.unsentDataFile;
    }

    static File getUnsentTempFile(StatsSyncher par0StatsSyncher)
    {
        return par0StatsSyncher.unsentTempFile;
    }

    static File getUnsentOldFile(StatsSyncher par0StatsSyncher)
    {
        return par0StatsSyncher.unsentOldFile;
    }
}
