package net.minecraft.src;

import java.io.File;
import java.io.IOException;
import net.minecraft.server.MinecraftServer;

public class DedicatedServer extends MinecraftServer implements IServer
{
    public DedicatedServer(File par1File)
    {
        super(par1File);
    }

    /**
     * Initialises the server and starts it.
     */
    protected boolean startServer() throws IOException
    {
        throw new UnsupportedOperationException("Dedicated server is not supported in the TeaVM build");
    }

    public boolean canStructuresSpawn()
    {
        return false;
    }

    public EnumGameType getGameType()
    {
        return EnumGameType.SURVIVAL;
    }

    /**
     * defaults to "1" for the dedicated server
     */
    public int getDifficulty()
    {
        return 1;
    }

    /**
     * defaults to false
     */
    public boolean isHardcore()
    {
        return false;
    }

    public boolean isDedicatedServer()
    {
        return true;
    }

    public NetworkListenThread getNetworkThread()
    {
        return null;
    }

    public String shareToLAN(EnumGameType var1, boolean var2)
    {
        return null;
    }

    public int getOrSetIntProperty(String var1, int var2)
    {
        return var2;
    }

    public String getOrSetProperty(String var1, String var2)
    {
        return var2;
    }

    public void setArbitraryProperty(String var1, Object var2) {}

    public void saveSettingsToFile() {}

    public String getSettingsFilePath()
    {
        return "";
    }

    public String getHostName()
    {
        return "";
    }

    /**
     * never used. Can not be called "getServerPort" is already taken
     */
    public int getMyServerPort()
    {
        return 0;
    }

    /**
     * minecraftServer.getMOTD is used in 2 places instead (it is a non-virtual function which returns the same thing)
     */
    public String getServerMOTD()
    {
        return this.getMOTD();
    }

    public String getMinecraftVersion()
    {
        return "1.3.2";
    }

    public int getPlayerListSize()
    {
        return 0;
    }

    public int getMaxPlayers()
    {
        return 0;
    }

    public String[] getAllUsernames()
    {
        return new String[0];
    }

    public String getFolderName()
    {
        return "";
    }

    /**
     * rename this when a patch comes out which uses it
     */
    public String returnAnEmptyString()
    {
        return "";
    }

    public String executeCommand(String var1)
    {
        return "";
    }

    public boolean doLogInfoEvent()
    {
        return false;
    }

    public void logInfoMessage(String var1) {}

    public void logWarningMessage(String var1) {}

    public void logSevereEvent(String var1) {}

    public void logInfoEvent(String var1) {}
}
