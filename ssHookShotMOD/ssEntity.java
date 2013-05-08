package net.minecraft.ssHookShotMOD;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ssEntity extends Entity
{
	protected double newPosX;
	protected double newPosY;
	protected double newPosZ;
	protected double newRotationYaw;
	protected double newRotationPitch;

	protected boolean canTriggerWalking()
	{
		return false;
	}

	public AxisAlignedBB getBoundingBox()
	{
		return null;
	}

	public ssEntity(World par1World)
	{
		super(par1World);
		this.ignoreFrustumCheck = true;
	}

	public boolean canBeCollidedWith()
	{
		return false;
	}

	public boolean canBePushed()
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
	{
		this.newPosX = par1;
		this.newPosY = par3;
		this.newPosZ = par5;
		this.newRotationYaw = (double)par7;
		this.newRotationPitch = (double)par8;
	}

	public void onUpdate()
	{
		super.onUpdate();
		if(this.worldObj.isRemote){
			this.setPosition(this.newPosX, this.newPosY, this.newPosZ);
			this.setRotation((float)this.newRotationYaw, (float)this.newRotationPitch);
		}
	}
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
	{
		return false;
	}
	protected void fall(float par1){}
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound){}
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound){}
}
