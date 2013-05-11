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
			if(p.getCurrentItemOrArmor(2) != null){
				if((24000-p.getCurrentItemOrArmor(2).getItemDamage()) < 24000){
					p.getCurrentItemOrArmor(2).setItemDamage(p.getCurrentItemOrArmor(2).getItemDamage()-600);
					is.stackSize--;
				}
			}
		}
		return is;
	}

}
