package net.minecraft.src;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

public class PlayerUsageSnooper
{
    /** String map for report data */
    private Map dataMap = new HashMap();
    private final String uniqueID = UUID.randomUUID().toString();

    /** URL of the server to send the report to (string in TeaVM) */
    private final String serverUrl;
    private final IPlayerUsage playerStatsCollector;

    private final Object syncLock = new Object();
    private boolean isRunning = false;

    /** incremented on every getSelfCounterFor */
    private int selfCounter = 0;

    public PlayerUsageSnooper(String par1Str, IPlayerUsage par2IPlayerUsage)
    {
        this.serverUrl = "http://snoop.minecraft.net/" + par1Str + "?version=" + 1;
        this.playerStatsCollector = par2IPlayerUsage;
    }

    /**
     * issuing start multiple times is not an error.
     */
    public void startSnooper()
    {
        if (!this.isRunning)
        {
            this.isRunning = true;
            this.addBaseDataToSnooper();
        }
    }

    private void addBaseDataToSnooper()
    {
        this.addData("snooper_token", this.uniqueID);
        this.addData("os_name", System.getProperty("os.name"));
        this.addData("os_version", System.getProperty("os.version"));
        this.addData("os_architecture", System.getProperty("os.arch"));
        this.addData("java_version", System.getProperty("java.version"));
        this.addData("version", "1.3.2");
        this.playerStatsCollector.addServerTypeToSnooper(this);
    }

    public void addMemoryStatsToSnooper()
    {
        this.addData("memory_total", Long.valueOf(0L));
        this.addData("memory_max", Long.valueOf(0L));
        this.addData("memory_free", Long.valueOf(0L));
        this.addData("cpu_cores", Integer.valueOf(1));
        this.playerStatsCollector.addServerStatsToSnooper(this);
    }

    /**
     * Adds information to the report
     */
    public void addData(String par1Str, Object par2Obj)
    {
        synchronized (this.syncLock)
        {
            this.dataMap.put(par1Str, par2Obj);
        }
    }

    public Map getCurrentStats()
    {
        LinkedHashMap var1 = new LinkedHashMap();

        synchronized (this.syncLock)
        {
            this.addMemoryStatsToSnooper();
            Iterator var3 = this.dataMap.entrySet().iterator();

            while (var3.hasNext())
            {
                Entry var4 = (Entry)var3.next();
                var1.put(var4.getKey(), var4.getValue().toString());
            }

            return var1;
        }
    }

    public boolean isSnooperRunning()
    {
        return this.isRunning;
    }

    public void stopSnooper()
    {
        this.isRunning = false;
    }

    public String func_80006_f()
    {
        return this.uniqueID;
    }

    static IPlayerUsage getStatsCollectorFor(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.playerStatsCollector;
    }

    static Object getSyncLockFor(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.syncLock;
    }

    static Map getDataMapFor(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.dataMap;
    }

    /**
     * returns a value indicating how many times this function has been run on the snooper
     */
    static int getSelfCounterFor(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.selfCounter++;
    }

    static Object getServerUrlFor(PlayerUsageSnooper par0PlayerUsageSnooper)
    {
        return par0PlayerUsageSnooper.serverUrl;
    }
}
