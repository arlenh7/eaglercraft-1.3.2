package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet253ServerAuthData extends Packet
{
    private String serverId;
    private byte[] publicKey = new byte[0];
    private byte[] verifyToken = new byte[0];

    public Packet253ServerAuthData() {}

    public Packet253ServerAuthData(String par1Str, Object par2PublicKey, byte[] par3ArrayOfByte)
    {
        this.serverId = par1Str;
        this.publicKey = new byte[0];
        this.verifyToken = par3ArrayOfByte == null ? new byte[0] : par3ArrayOfByte;
    }

    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        this.serverId = readString(par1DataInputStream, 20);
        this.publicKey = readBytesFromStream(par1DataInputStream);
        this.verifyToken = readBytesFromStream(par1DataInputStream);
    }

    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeString(this.serverId, par1DataOutputStream);
        writeByteArray(par1DataOutputStream, this.publicKey);
        writeByteArray(par1DataOutputStream, this.verifyToken);
    }

    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleServerAuthData(this);
    }

    public int getPacketSize()
    {
        return 2 + this.serverId.length() * 2 + 2 + this.publicKey.length + 2 + this.verifyToken.length;
    }

    public String getServerId()
    {
        return this.serverId;
    }

    public byte[] getPublicKey()
    {
        return this.publicKey;
    }

    public byte[] getVerifyToken()
    {
        return this.verifyToken;
    }
}
