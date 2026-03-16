package net.minecraft.src;

import java.util.concurrent.Callable;

class CallableMemoryInfo implements Callable
{
    /** Gets Memory Information for Crash Report. */
    final CrashReport crashReportMemoryInfo;

    CallableMemoryInfo(CrashReport par1CrashReport)
    {
        this.crashReportMemoryInfo = par1CrashReport;
    }

    public String func_71485_a()
    {
        return "unknown";
    }

    public Object call()
    {
        return this.func_71485_a();
    }
}
