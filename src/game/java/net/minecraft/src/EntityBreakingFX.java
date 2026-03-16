package net.minecraft.src;

import net.lax1dude.eaglercraft.opengl.Tessellator;

public class EntityBreakingFX extends EntityFX
{
    public EntityBreakingFX(World par1World, double par2, double par4, double par6, Item par8Item)
    {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
        this.setParticleTextureIndex(par8Item.getIconFromDamage(0));
        this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
        this.particleGravity = Block.blockSnow.blockParticleGravity;
        this.particleScale /= 2.0F;
    }

    public EntityBreakingFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, Item par14Item)
    {
        this(par1World, par2, par4, par6, par14Item);
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;
        this.motionX += par8;
        this.motionY += par10;
        this.motionZ += par12;
    }

    public int getFXLayer()
    {
        return 2;
    }

    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        float var8 = ((float)(this.getParticleTextureIndex() % 16) + this.particleTextureJitterX / 4.0F) / 16.0F;
        float var9 = var8 + 0.015609375F;
        float var10 = ((float)(this.getParticleTextureIndex() / 16) + this.particleTextureJitterY / 4.0F) / 16.0F;
        float var11 = var10 + 0.015609375F;
        float var12 = 0.1F * this.particleScale;
        float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)par2 - interpPosX);
        float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)par2 - interpPosY);
        float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)par2 - interpPosZ);
        float var16 = 1.0F;
    }
}
