package net.minecraft.src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LanServerList
{
    private final ArrayList field_77555_b = new ArrayList();
    boolean field_77556_a;

    public synchronized boolean func_77553_a()
    {
        return this.field_77556_a;
    }

    public synchronized void func_77552_b()
    {
        this.field_77556_a = false;
    }

    public synchronized List func_77554_c()
    {
        return Collections.unmodifiableList(this.field_77555_b);
    }

    public synchronized void func_77551_a(String par1Str, Object par2InetAddress)
    {
        // no LAN discovery in TeaVM build
    }
}
