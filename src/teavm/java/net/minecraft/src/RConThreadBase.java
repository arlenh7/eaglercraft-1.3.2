package net.minecraft.src;

public abstract class RConThreadBase implements Runnable
{
    protected boolean field_72619_a = false;
    protected IServer field_72617_b;
    protected Thread field_72618_c;

    RConThreadBase(IServer par1IServer)
    {
        this.field_72617_b = par1IServer;
    }

    public synchronized void func_72602_a()
    {
        this.field_72619_a = true;
    }

    public boolean func_72613_c()
    {
        return this.field_72619_a;
    }

    protected void func_72607_a(String par1Str)
    {
        if (this.field_72617_b != null)
        {
            this.field_72617_b.logInfoEvent(par1Str);
        }
    }

    protected void func_72609_b(String par1Str)
    {
        if (this.field_72617_b != null)
        {
            this.field_72617_b.logInfoMessage(par1Str);
        }
    }

    protected void func_72606_c(String par1Str)
    {
        if (this.field_72617_b != null)
        {
            this.field_72617_b.logWarningMessage(par1Str);
        }
    }

    protected void func_72610_d(String par1Str)
    {
        if (this.field_72617_b != null)
        {
            this.field_72617_b.logSevereEvent(par1Str);
        }
    }

    protected int func_72603_d()
    {
        return this.field_72617_b != null ? this.field_72617_b.getPlayerListSize() : 0;
    }

    protected void func_72611_e()
    {
    }

    protected void func_72612_a(boolean par1)
    {
    }
}
