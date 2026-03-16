package net.minecraft.src;

import java.io.IOException;
import net.minecraft.server.MinecraftServer;

public class NetLoginHandler extends NetHandler
{
    public NetworkManager myTCPConnection;
    public boolean connectionComplete = true;

    private final MinecraftServer mcServer;
    private String clientUsername = null;

    public NetLoginHandler(MinecraftServer par1MinecraftServer, Object par2Socket, String par3Str) throws IOException
    {
        this.mcServer = par1MinecraftServer;
        this.myTCPConnection = null;
    }

    public void tryLogin() {}

    public void raiseErrorAndDisconnect(String par1Str) {}

    public void handleClientProtocol(Packet2ClientProtocol par1Packet2ClientProtocol)
    {
        this.clientUsername = par1Packet2ClientProtocol.getUsername();
    }

    public void handleSharedKey(Packet252SharedKey par1Packet252SharedKey) {}

    public void handleClientCommand(Packet205ClientCommand par1Packet205ClientCommand) {}

    public void handleLogin(Packet1Login par1Packet1Login) {}

    public void initializePlayerConnection() {}

    public void handleErrorMessage(String par1Str, Object[] par2ArrayOfObj) {}

    public void handleServerPing(Packet254ServerPing par1Packet254ServerPing) {}

    public void registerPacket(Packet par1Packet) {}

    public String getUsernameAndAddress()
    {
        return this.clientUsername != null ? this.clientUsername : "local";
    }

    public boolean isServerHandler()
    {
        return true;
    }

    static String func_72526_a(NetLoginHandler par0NetLoginHandler)
    {
        return "";
    }

    static MinecraftServer func_72530_b(NetLoginHandler par0NetLoginHandler)
    {
        return par0NetLoginHandler.mcServer;
    }

    static Object func_72525_c(NetLoginHandler par0NetLoginHandler)
    {
        return null;
    }

    static String func_72533_d(NetLoginHandler par0NetLoginHandler)
    {
        return par0NetLoginHandler.clientUsername;
    }

    static boolean func_72531_a(NetLoginHandler par0NetLoginHandler, boolean par1)
    {
        return false;
    }
}
