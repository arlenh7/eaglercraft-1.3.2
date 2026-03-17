package net.minecraft.src;

import net.lax1dude.eaglercraft.EagRuntime;
import net.lax1dude.eaglercraft.internal.EnumPlatformType;
import net.minecraft.server.MinecraftServer;

public class IntegratedPlayerList extends ServerConfigurationManager
{
    private NBTTagCompound tagsForLastWrittenPlayer = null;

    public IntegratedPlayerList(IntegratedServer par1IntegratedServer)
    {
        super(par1IntegratedServer);

        if (EagRuntime.getPlatformType() == EnumPlatformType.DESKTOP)
        {
            this.viewDistance = 8;
        }
        else
        {
            // Smaller server-side watch radius significantly reduces low-end chunk IO and tick cost.
            this.viewDistance = 6;
        }
    }

    /**
     * also stores the NBTTags if this is an intergratedPlayerList
     */
    protected void writePlayerData(EntityPlayerMP par1EntityPlayerMP)
    {
        if (par1EntityPlayerMP.getCommandSenderName().equals(this.getIntegratedServer().getServerOwner()))
        {
            this.tagsForLastWrittenPlayer = new NBTTagCompound();
            par1EntityPlayerMP.writeToNBT(this.tagsForLastWrittenPlayer);
        }

        super.writePlayerData(par1EntityPlayerMP);
    }

    /**
     * get the associated Integrated Server
     */
    public IntegratedServer getIntegratedServer()
    {
        return (IntegratedServer)super.getServerInstance();
    }

    /**
     * gets the tags created in the last writePlayerData call
     */
    public NBTTagCompound getTagsFromLastWrite()
    {
        return this.tagsForLastWrittenPlayer;
    }

    public MinecraftServer getServerInstance()
    {
        return this.getIntegratedServer();
    }
}
