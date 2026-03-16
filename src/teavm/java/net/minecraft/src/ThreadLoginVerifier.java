package net.minecraft.src;

class ThreadLoginVerifier extends Thread
{
    final NetLoginHandler field_72590_a;

    ThreadLoginVerifier(NetLoginHandler par1NetLoginHandler)
    {
        this.field_72590_a = par1NetLoginHandler;
    }

    public void run()
    {
        // Multiplayer login verification is not supported in TeaVM build
    }
}
