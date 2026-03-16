package net.minecraft.src;

public class RConThreadClient extends RConThreadBase
{
    RConThreadClient(IServer par1IServer, Object par2Socket)
    {
        super(par1IServer);
    }

    public void run()
    {
        // no-op in TeaVM build
    }
}
