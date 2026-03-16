package net.minecraft.src;

class RConThreadQueryAuth
{
    private long field_72598_b;
    private int field_72599_c;
    private byte[] field_72596_d;
    private byte[] field_72597_e;

    final RConThreadQuery field_72600_a;

    public RConThreadQueryAuth(RConThreadQuery par1RConThreadQuery, Object par2DatagramPacket)
    {
        this.field_72600_a = par1RConThreadQuery;
        this.field_72598_b = System.currentTimeMillis();
        this.field_72599_c = 0;
        this.field_72596_d = new byte[0];
        this.field_72597_e = new byte[0];
    }

    public Boolean func_72593_a(long par1)
    {
        return Boolean.valueOf(this.field_72598_b < par1);
    }

    public int func_72592_a()
    {
        return this.field_72599_c;
    }

    public byte[] func_72594_b()
    {
        return this.field_72597_e;
    }

    public byte[] func_72591_c()
    {
        return this.field_72596_d;
    }
}
