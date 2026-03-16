package net.minecraft.src;

public class EntityMobs extends EntityCreature implements IMobs {
	protected int attackStrength = 2;

	public EntityMobs(World var1) {
		super(var1);
		this.health = 20;
	}

	public void onLivingUpdate() {

		super.onLivingUpdate();
	}

	public void onUpdate() {
		super.onUpdate();
		if(this.worldObj.difficultySetting == 0) {
			this.setDead();
		}

	}

	protected Entity findPlayerToAttack() {
		EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, 16.0D);
		return var1 != null && this.canEntityBeSeen(var1) ? var1 : null;
	}

	public boolean attackEntityFrom(Entity var1, int var2) {
		if(super.attackEntityFrom(DamageSource.generic, var2)) {
			if(this.riddenByEntity != var1 && this.ridingEntity != var1) {
				if(var1 != this) {
					this.entityToAttack = var1;
				}

				return true;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	protected void attackEntity(Entity var1, float var2) {
		if((double)var2 < 2.5D && var1.boundingBox.maxY > this.boundingBox.minY && var1.boundingBox.minY < this.boundingBox.maxY) {
			this.attackTime = 20;
			var1.attackEntityFrom(DamageSource.generic, this.attackStrength);
		}

	}

	public float getBlockPathWeight(int var1, int var2, int var3) {
		return 0.5F - this.worldObj.getLightBrightness(var1, var2, var3);
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		super.writeEntityToNBT(var1);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		super.readEntityFromNBT(var1);
	}

	public boolean getCanSpawnHere() {
		int var1 = MathHelper.floor_double(this.posX);
		int var2 = MathHelper.floor_double(this.boundingBox.minY);
		int var3 = MathHelper.floor_double(this.posZ);
		if(this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, var1, var2, var3) > this.rand.nextInt(32)) {
			return false;
		} else {
			int var4 = this.worldObj.getBlockLightValue(var1, var2, var3);
			return var4 <= this.rand.nextInt(8) && super.getCanSpawnHere();
		}
	}

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getMaxHealth'");
	}
}
