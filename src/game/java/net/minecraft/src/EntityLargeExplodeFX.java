package net.minecraft.src;

import org.lwjgl.opengl.GL11;

import net.lax1dude.eaglercraft.opengl.Tessellator;

public class EntityLargeExplodeFX extends EntityFX
{
    private int field_70581_a = 0;
    private int field_70584_aq = 0;
    private RenderEngine field_70583_ar;
    private float field_70582_as;

    public EntityLargeExplodeFX(RenderEngine par1RenderEngine, World par2World, double par3, double par5, double par7, double par9, double par11, double par13)
    {
        super(par2World, par3, par5, par7, 0.0D, 0.0D, 0.0D);
        this.field_70583_ar = par1RenderEngine;
        this.field_70584_aq = 6 + this.rand.nextInt(4);
        this.particleRed = this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.6F + 0.4F;
        this.field_70582_as = 1.0F - (float)par9 * 0.5F;
    }

    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        int var8 = (int)(((float)this.field_70581_a + par2) * 15.0F / (float)this.field_70584_aq);

        if (var8 <= 15)
        {
            this.field_70583_ar.bindTexture(this.field_70583_ar.getTexture("/misc/explosion.png"));
            float var9 = (float)(var8 % 4) / 4.0F;
            float var10 = var9 + 0.24975F;
            float var11 = (float)(var8 / 4) / 4.0F;
            float var12 = var11 + 0.24975F;
            float var13 = 2.0F * this.field_70582_as;
            float var14 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)par2 - interpPosX);
            float var15 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)par2 - interpPosY);
            float var16 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)par2 - interpPosZ);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            RenderHelper.disableStandardItemLighting();
            par1Tessellator.draw();
            GL11.glPolygonOffset(0.0F, 0.0F);
            GL11.glEnable(GL11.GL_LIGHTING);
        }
    }

    public int getBrightnessForRender(float par1)
    {
        return 61680;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        ++this.field_70581_a;

        if (this.field_70581_a == this.field_70584_aq)
        {
            this.setDead();
        }
    }

    public int getFXLayer()
    {
        return 3;
    }
}
