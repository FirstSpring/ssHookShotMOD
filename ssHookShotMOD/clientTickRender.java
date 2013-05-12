package net.minecraft.ssHookShotMOD;

import java.util.EnumSet;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class clientTickRender implements ITickHandler
{
	Minecraft mc = FMLClientHandler.instance().getClient();
	Random ran = new Random();

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData){
		if(mc.thePlayer != null&&mc.theWorld != null){
			for (String key : ssTanksMOD.インスタンス.クライアント側パーティクル出す1.keySet()) {
				if(ssTanksMOD.インスタンス.クライアント側パーティクル出す1.get(key))
				{
					EntityPlayer player = mc.theWorld.getPlayerEntityByName(key);
					if(player != null&&player.getCurrentItemOrArmor(2) != null&&player.getCurrentItemOrArmor(2).getItem().itemID == ssTanksMOD.インスタンス.moveleg.itemID){
						float fy = 0;
						if(!player.username.equals(mc.thePlayer.username))
							fy = 1.5F;
						for (int l = -8; l < 9; ++l)
						{
							player.worldObj.spawnParticle("crit", player.posX+(ran.nextInt(10)-5)*0.02D, player.posY+fy+(ran.nextInt(10)-5)*0.02D-0.8F, player.posZ+(ran.nextInt(10)-5)*0.02D,0,0,0);
						}
					}
				}
			}
			for (String key : ssTanksMOD.インスタンス.クライアント側パーティクル出す2.keySet()) {
				if(ssTanksMOD.インスタンス.クライアント側パーティクル出す2.get(key))
				{
					EntityPlayer player = mc.theWorld.getPlayerEntityByName(key);
					if(player != null&&player.getCurrentItemOrArmor(2) != null&&player.getCurrentItemOrArmor(2).getItem().itemID == ssTanksMOD.インスタンス.moveleg.itemID){
						float fy = 0;
						if(!player.username.equals(mc.thePlayer.username))
							fy = 1.5F;
						for (int l = -8; l < 9; ++l)
						{
							player.worldObj.spawnParticle("smoke", player.posX+(ran.nextInt(10)-5)*0.02D, player.posY+fy+(ran.nextInt(10)-5)*0.02D-0.8F, player.posZ+(ran.nextInt(10)-5)*0.02D,0,0,0);
						}
					}
				}
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {

	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return null;
	}
}
