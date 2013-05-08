package net.minecraft.ssHookShotMOD;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Itemkousituken extends Item{

	@SideOnly(Side.CLIENT)
	private Icon[] iconArray;

	public Itemkousituken(int par1) {
		super(par1);
		this.setMaxStackSize(1);
		this.setMaxDamage(20);
	}

	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.bow;
	}

	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
	{
		if(!par3Entity.worldObj.isRemote)
		{
			if(par3Entity instanceof EntityPlayerMP){
				EntityPlayerMP p = (EntityPlayerMP)par3Entity;
				if(ssTanksMOD.インスタンス.入力状態.containsKey(p.username)&&par1ItemStack == p.getHeldItem())
				{

					if(ssTanksMOD.インスタンス.入力状態.get(p.username)[7] == 1&&this.getItemDamageFromStack(par1ItemStack)>0)
					{
						if (p.inventory.hasItem(ssTanksMOD.インスタンス.替刃.itemID))
						{
							p.swingItem();
							p.inventory.consumeInventoryItem(ssTanksMOD.インスタンス.替刃.itemID);
							par1ItemStack.setItemDamage(0);
						}
					}
				}
			}
		}
	}

	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if(stack.getItemDamage() < 37){
			float m = 0;
			if(ssTanksMOD.インスタンス.moveleg.PlayermMotion.containsKey(player)){
				m += Math.abs(ssTanksMOD.インスタンス.moveleg.PlayermMotion.get(player).x);
				m += Math.abs(ssTanksMOD.インスタンス.moveleg.PlayermMotion.get(player).y);
				m += Math.abs(ssTanksMOD.インスタンス.moveleg.PlayermMotion.get(player).z);
			}
			if(ssTanksMOD.インスタンス.moveleg.PlayermMotion2.containsKey(player)){
				m += Math.abs(ssTanksMOD.インスタンス.moveleg.PlayermMotion2.get(player).x);
				m += Math.abs(ssTanksMOD.インスタンス.moveleg.PlayermMotion2.get(player).y);
				m += Math.abs(ssTanksMOD.インスタンス.moveleg.PlayermMotion2.get(player).z);
			}
			if(ssTanksMOD.インスタンス.moveleg.PlayermMotion3.containsKey(player)){
				m += Math.abs(ssTanksMOD.インスタンス.moveleg.PlayermMotion3.get(player).x);
				m += Math.abs(ssTanksMOD.インスタンス.moveleg.PlayermMotion3.get(player).y);
				m += Math.abs(ssTanksMOD.インスタンス.moveleg.PlayermMotion3.get(player).z);
			}
			m*=10;
			stack.setItemDamage(stack.getItemDamage()+(int)m);
			entity.attackEntityFrom(DamageSource.causePlayerDamage(player),((int)m-1)*8);
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.iconArray = new Icon[2];
		this.iconArray[0] = par1IconRegister.registerIcon("sshookshot:kennhatuki");
		this.iconArray[1] = par1IconRegister.registerIcon("sshookshot:kennhanasi");
	}

	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1)
	{
		if(par1 >= 20)
		{
			return this.iconArray[0];
		}
		return this.iconArray[1];
	}
}
