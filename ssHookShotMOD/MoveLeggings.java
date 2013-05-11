package net.minecraft.ssHookShotMOD;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.WeakHashMap;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MoveLeggings extends ItemArmor implements ISpecialArmor
{
	Random ran = new Random();

	public WeakHashMap<EntityPlayer,EntityHook> LeftHook = new WeakHashMap<EntityPlayer,EntityHook>();
	public WeakHashMap<EntityPlayer,EntityHook> RightHook = new WeakHashMap<EntityPlayer,EntityHook>();
	public WeakHashMap<EntityPlayer,motionXYZ> PlayermMotion = new WeakHashMap<EntityPlayer,motionXYZ>();
	public WeakHashMap<EntityPlayer,motionXYZ> PlayermMotion2 = new WeakHashMap<EntityPlayer,motionXYZ>();
	public WeakHashMap<EntityPlayer,motionXYZ> PlayermMotion3 = new WeakHashMap<EntityPlayer,motionXYZ>();

	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLiving entityLiving, ItemStack itemStack, int armorSlot)
	{
		return new ModelSouti();
	}

	public MoveLeggings(int par1, EnumArmorMaterial par2EnumArmorMaterial, int par3, int par4) {
		super(par1, par2EnumArmorMaterial, par3, par4);
		this.setMaxDamage(24000);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		super.registerIcons(par1IconRegister);
	}

	public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack)
	{
		if(!player.worldObj.isRemote)
		{
			player.capabilities.allowFlying = true;

			if(!this.PlayermMotion.containsKey(player))
				this.PlayermMotion.put(player,new motionXYZ(0,0,0));
			if(!this.PlayermMotion2.containsKey(player))
				this.PlayermMotion2.put(player,new motionXYZ(0,0,0));
			if(!this.PlayermMotion3.containsKey(player))
				this.PlayermMotion3.put(player,new motionXYZ(0,0,0));

			if(RightHook.containsKey(player)&&(RightHook.get(player).inEntity||RightHook.get(player).inGround)){
				player.fallDistance = 0.0F;
			}
			else if(LeftHook.containsKey(player)&&(LeftHook.get(player).inEntity||LeftHook.get(player).inGround)){
				player.fallDistance = 0.0F;
			}
		}

		if(!player.worldObj.isRemote){

			float[] xyz = this.getxyz(player,itemStack);
			player.moveEntity(xyz[0],xyz[1],xyz[2]);
		}
		else
		{
			if(ssTanksMOD.インスタンス.クライアント側モーションX==0&&ssTanksMOD.インスタンス.クライアント側モーションY==0&&ssTanksMOD.インスタンス.クライアント側モーションZ == 0){}
			else clientproxy.mc.thePlayer.moveEntity(ssTanksMOD.インスタンス.クライアント側モーションX,ssTanksMOD.インスタンス.クライアント側モーションY,ssTanksMOD.インスタンス.クライアント側モーションZ);
			if(ssTanksMOD.インスタンス.クライアント側落ちない)
			{
				clientproxy.mc.thePlayer.motionY = 0.0F;
			}
			if(ssTanksMOD.インスタンス.クライアント側パーティクル出す1){
				for (int l = -8; l < 9; ++l)
				{
					player.worldObj.spawnParticle("crit", player.posX+(ran.nextInt(10)-5)*0.02D, player.posY+(ran.nextInt(10)-5)*0.02D-0.8F, player.posZ+(ran.nextInt(10)-5)*0.02D,0,0,0);
				}
			}
			if(ssTanksMOD.インスタンス.クライアント側パーティクル出す2){
				for (int l = -8; l < 9; ++l)
				{
					player.worldObj.spawnParticle("smoke", player.posX+(ran.nextInt(10)-5)*0.05D, player.posY+(ran.nextInt(10)-5)*0.05D-0.8F, player.posZ+(ran.nextInt(10)-5)*0.05D,0,0,0);
				}
			}
		}

		if(player.worldObj.isRemote)
		{

		}
		else if(ssTanksMOD.インスタンス.入力状態.containsKey(player.username))
		{
			if(ssTanksMOD.インスタンス.入力状態.get(player.username)[12] == 1)
			{
				if(!LeftHook.containsKey(player)){}
				else
				{
					this.LeftHook.get(player).setDead();
					this.LeftHook.remove(player);
				}
				if(!RightHook.containsKey(player)){}
				else
				{
					this.RightHook.get(player).setDead();
					this.RightHook.remove(player);
				}
			}

			if(ssTanksMOD.インスタンス.入力状態.get(player.username)[9] == 1)
			{
				if((24000-itemStack.getItemDamage()) >= 4){
					PlayermMotion3.get(player).x = -MathHelper.sin(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI)*0.5F;
					PlayermMotion3.get(player).z = MathHelper.cos(player.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(player.rotationPitch / 180.0F * (float)Math.PI)*0.5F;
					PlayermMotion3.get(player).y = -MathHelper.sin(player.rotationPitch / 180.0F * (float)Math.PI)*0.8F+0.2F*0.5F;
					itemStack.setItemDamage(itemStack.getItemDamage()+4);
				}
			}
			else{
				PlayermMotion3.get(player).x *= 0.8F;
				if(PlayermMotion3.get(player).y >= 0.1F)
					PlayermMotion3.get(player).y -= 0.05F;
				PlayermMotion3.get(player).z *= 0.8F;
				if(Math.abs(PlayermMotion3.get(player).x)<0.2F)
					PlayermMotion3.get(player).x = 0;
				if(PlayermMotion3.get(player).y<0.1F)
					PlayermMotion3.get(player).y = 0;
				if(Math.abs(PlayermMotion3.get(player).z)<0.2F)
					PlayermMotion3.get(player).z = 0;
			}

			if(ssTanksMOD.インスタンス.入力状態.get(player.username)[5] == 1&&(ssTanksMOD.インスタンス.入力状態.get(player.username)[6] == 1&&
					ssTanksMOD.インスタンス.入力状態.get(player.username)[7] == 1))
			{
				if(!LeftHook.containsKey(player)){}
				else
				{
					this.LeftHook.get(player).setDead();
					this.LeftHook.remove(player);
				}

				EntityHook LeftHook = new EntityHook(player.worldObj,player,6.0F,-15.0F);
				this.LeftHook.put(player,LeftHook);
				player.worldObj.spawnEntityInWorld(LeftHook);

				if(!RightHook.containsKey(player)){}
				else
				{
					this.RightHook.get(player).setDead();
					this.RightHook.remove(player);
				}

				EntityHook RightHook = new EntityHook(player.worldObj,player,6.0F,15.0F);
				this.RightHook.put(player,RightHook);
				player.worldObj.spawnEntityInWorld(RightHook);
			}
			else if(ssTanksMOD.インスタンス.入力状態.get(player.username)[5] == 1&&ssTanksMOD.インスタンス.入力状態.get(player.username)[13] == 1)
			{
				if(!LeftHook.containsKey(player)){}
				else
				{
					this.LeftHook.get(player).setDead();
					this.LeftHook.remove(player);
				}
			}
			else if(ssTanksMOD.インスタンス.入力状態.get(player.username)[5] == 1&&ssTanksMOD.インスタンス.入力状態.get(player.username)[14] == 1)
			{
				if(!RightHook.containsKey(player)){}
				else
				{
					this.RightHook.get(player).setDead();
					this.RightHook.remove(player);
				}
			}
			else if(ssTanksMOD.インスタンス.入力状態.get(player.username)[13] == 1)
			{
				if(!LeftHook.containsKey(player)){}
				else
				{
					this.LeftHook.get(player).setDead();
					this.LeftHook.remove(player);
				}

				EntityHook LeftHook = new EntityHook(player.worldObj,player,6.0F,0);
				this.LeftHook.put(player,LeftHook);
				player.worldObj.spawnEntityInWorld(LeftHook);
			}
			else if(ssTanksMOD.インスタンス.入力状態.get(player.username)[14] == 1)
			{
				if(!RightHook.containsKey(player)){}
				else
				{
					this.RightHook.get(player).setDead();
					this.RightHook.remove(player);
				}

				EntityHook RightHook = new EntityHook(player.worldObj,player,6.0F,0);
				this.RightHook.put(player,RightHook);
				player.worldObj.spawnEntityInWorld(RightHook);
			}
		}
	}

	private float[] getxyz(EntityPlayer player,ItemStack is) {
		int pl = player.worldObj.getBlockId((int)player.posX,(int)(player.posY-0.5F),(int)player.posZ);

		float[] xyz = this.PlayermMotion.get(player).getMotion();
		float[] xyz2 = this.PlayermMotion2.get(player).getMotion();
		float[] xyz3 = this.PlayermMotion3.get(player).getMotion();

		boolean 落ちない = false;

		if(this.LeftHook.containsKey(player))
		{
			if((24000-is.getItemDamage()) >= 1){
				EntityHook LeftHook = this.LeftHook.get(player);
				float lxyz[] = LeftHook.getxyz();
				xyz[0] = lxyz[0];
				xyz[1] = lxyz[1];
				xyz[2] = lxyz[2];
				is.setItemDamage(is.getItemDamage()+1);
				落ちない = LeftHook.inEntity||LeftHook.inGround;
			}
		}
		else{
			xyz[0] *= 0.99F;
			xyz[2] *= 0.99F;
			if(pl>0){
				xyz[0] = 0;
				xyz[1] = 0;
				xyz[2] = 0;
			}
		}

		if(this.RightHook.containsKey(player))
		{
			if((24000-is.getItemDamage()) >= 1){
				EntityHook RightHook =  this.RightHook.get(player);
				float rxyz[] = RightHook.getxyz();
				xyz2[0] = rxyz[0];
				xyz2[1] = rxyz[1];
				xyz2[2] = rxyz[2];
				is.setItemDamage(is.getItemDamage()+1);
				落ちない = RightHook.inEntity||RightHook.inGround;
			}
		}
		else{
			xyz2[0] *= 0.99F;
			xyz2[2] *= 0.99F;
			if(pl>0){
				xyz2[0] = 0;
				xyz2[1] = 0;
				xyz2[2] = 0;
			}
		}

		if(pl > 0&&player.fallDistance > 2.0F&&!Block.blocksList[pl].blockMaterial.isLiquid())
		{
			player.attackEntityFrom(DamageSource.fall,(int)player.fallDistance-2);
			player.fallDistance = 0.0F;
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream(25);
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeFloat(xyz[0]+xyz2[0]+xyz3[0]);
			dos.writeFloat(xyz[1]+xyz2[1]+xyz3[1]);
			dos.writeFloat(xyz[2]+xyz2[2]+xyz3[2]);
			dos.writeBoolean(落ちない);
			dos.writeBoolean(xyz[0]!=0||xyz[1]!=0||xyz[2]!=0||xyz2[0]!=0||xyz2[1]!=0||xyz2[2]!=0);
			dos.writeBoolean(xyz3[0]!=0||xyz3[1]!=0||xyz3[2]!=0);
			dos.writeInt(24000-is.getItemDamage());
		} catch (IOException e) {
			e.printStackTrace();
		}

		PacketDispatcher.sendPacketToPlayer(new Packet250CustomPayload("位置合わせ",bos.toByteArray()),(Player) player);

		this.PlayermMotion.put(player,new motionXYZ(xyz[0],xyz[1],xyz[2]));
		this.PlayermMotion2.put(player,new motionXYZ(xyz2[0],xyz2[1],xyz2[2]));

		xyz[0] = xyz[0] + xyz2[0]+xyz3[0];
		xyz[1] = xyz[1] + xyz2[1]+xyz3[1];
		xyz[2] = xyz[2] + xyz2[2]+xyz3[2];

		return xyz;
	}

	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
	{
		if(slot == 2)
			return "/mods/sshookshot/textures/armor/moveleg.png";
		return null;
	}

	@Override
	public ArmorProperties getProperties(EntityLiving player, ItemStack armor, DamageSource source, double damage,int slot) {
		return new ISpecialArmor.ArmorProperties(0,0,0);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return 0;
	}

	@Override
	public void damageArmor(EntityLiving entity, ItemStack stack, DamageSource source, int damage, int slot) {}
}
