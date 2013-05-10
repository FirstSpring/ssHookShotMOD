package net.minecraft.ssHookShotMOD;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Itemnennryou extends Item{

	public Itemnennryou(int par1) {
		super(par1);
	}

	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer p)
	{
		if(!w.isRemote)
		{
			if(ssTanksMOD.インスタンス.moveleg.燃料.containsKey(p)){
				if(ssTanksMOD.インスタンス.moveleg.燃料.get(p) < 24000){
					ssTanksMOD.インスタンス.moveleg.燃料.put(p,ssTanksMOD.インスタンス.moveleg.燃料.get(p)+600);
					is.stackSize--;
				}
			}
		}
		return is;
	}

}
