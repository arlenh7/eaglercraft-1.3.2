package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet252SharedKey extends Packet
{
    private byte[] field_73307_a = new byte[0];
    private byte[] field_73305_b = new byte[0];

    public Packet252SharedKey() {}

    public Packet252SharedKey(Object par1SecretKey, Object par2PublicKey, byte[] par3ArrayOfByte)
    {
        this.field_73307_a = new byte[0];
        this.field_73305_b = par3ArrayOfByte == null ? new byte[0] : par3ArrayOfByte;
    }

    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        this.field_73307_a = readBytesFromStream(par1DataInputStream);
        this.field_73305_b = readBytesFromStream(par1DataInputStream);
    }

    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeByteArray(par1DataOutputStream, this.field_73307_a);
        writeByteArray(par1DataOutputStream, this.field_73305_b);
    }

    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleSharedKey(this);
    }

    public int getPacketSize()
    {
        return 2 + this.field_73307_a.length + 2 + this.field_73305_b.length;
    }
}
