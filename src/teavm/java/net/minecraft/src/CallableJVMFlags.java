package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableJVMFlags implements Callable
{
    /** Gets additional Java Enviroment info for Crash Report. */
    final CrashReport crashReportJVMFlags;

    CallableJVMFlags(CrashReport par1CrashReport)
    {
        this.crashReportJVMFlags = par1CrashReport;
    }

    public String func_71487_a()
    {
        return "0 total; ";
    }

    public Object call()
    {
        return this.func_71487_a();
    }
}
