package net.minecraft.ssHookShotMOD;

import java.util.WeakHashMap;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Itemkousituken extends ItemSword{

	@SideOnly(Side.CLIENT)
	private Icon[] iconArray;

	public WeakHashMap<EntityPlayer,PlayerXYZ> lastmotion = new WeakHashMap<EntityPlayer,PlayerXYZ>();

	public Itemkousituken(int par1) {
		super(par1,ssTanksMOD.インスタンス.剣);
		this.setMaxStackSize(1);
		this.setMaxDamage(20);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}

	public int getDamageVsEntity(Entity par1Entity)
	{
		return 0;
	}

	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
	{
		if(!par3Entity.worldObj.isRemote)
		{
			if(par3Entity instanceof EntityPlayerMP){
				EntityPlayerMP p = (EntityPlayerMP)par3Entity;

				lastmotion.put(p, new PlayerXYZ(p.posX,p.posY,p.posZ));

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
		this.攻撃(stack,player,entity);
		return false;
	}

	public void 攻撃(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if(stack.getItemDamage() < 20&&!player.worldObj.isRemote){
			double m = 0;
			m += Math.abs(lastmotion.get(player).x - player.posX);
			m += Math.abs(lastmotion.get(player).y - player.posY)*2;
			m += Math.abs(lastmotion.get(player).z - player.posZ);
			m *= 10;
			if(m > 0){
				stack.setItemDamage(stack.getItemDamage()+(int)m);
				entity.attackEntityFrom(DamageSource.causePlayerDamage(player),(int)(m+((m*0.2F)*EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack)))*5);
			}
			else
			{
				stack.setItemDamage(stack.getItemDamage()+2);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.iconArray = new Icon[2];
		this.iconArray[0] = par1IconRegister.registerIcon("sshookshot:kennhanasi");
		this.iconArray[1] = par1IconRegister.registerIcon("sshookshot:kennhaari");
	}

	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
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

	public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
	{
		return true;
	}

	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving)
	{
		return true;
	}

	private class PlayerXYZ
	{
		double x;
		double y;
		double z;

		public PlayerXYZ(double x,double y,double z){
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
}
