package net.minecraft.ssHookShotMOD;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntitySouti extends ssEntity{

	EntityPlayer p;

	public EntitySouti(World par1World) {
		super(par1World);
	}

	public EntitySouti(World par1World,EntityPlayer p) {
		super(par1World);
		this.p = p;
	}

	public void onUpdate()
	{
		super.onUpdate();

		if(!this.worldObj.isRemote)
		{
			if(this.p == null||this.p.isDead)
			{
				this.setDead();
				return;
			}

			if(this.p.getCurrentItemOrArmor(2) != null){
				if(this.p.getCurrentItemOrArmor(2).getItem().itemID == ssTanksMOD.インスタンス.moveleg.itemID)
					this.dataWatcher.updateObject(20,1);
				else this.dataWatcher.updateObject(20,0);
			}
		}
	}
	
	public boolean get見えてるか()
	{
		return this.dataWatcher.getWatchableObjectInt(20) == 1;
	}

	protected void entityInit()
	{
		this.dataWatcher.addObject(20,0);//movelegつけてるか
		this.dataWatcher.addObject(22,"");//名前
	}
}
