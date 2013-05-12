package net.minecraft.ssHookShotMOD;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.packet.Packet250CustomPayload;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;

public class clienttickhandler implements ITickHandler
{
	Minecraft mc = FMLClientHandler.instance().getClient();

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		if(FMLClientHandler.instance().getClient().thePlayer != null&&!mc.isGamePaused&&mc.inGameHasFocus)
		{
			byte[] keys = new byte[20];

			String[] k = ssTanksMOD.インスタンス.keys;
			
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex(k[0])))
				keys[0] = 1;
			else keys[0] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex(k[1])))
				keys[1] = 1;
			else keys[1] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex(k[2])))
				keys[2] = 1;
			else keys[2] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex(k[3])))
				keys[3] = 1;
			else keys[3] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex(k[4])))
				keys[4] = 1;
			else keys[4] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex(k[5])))
				keys[5] = 1;
			else keys[5] = 0;
			if(Mouse.isButtonDown(0))
				keys[6] = 1;
			else keys[6] = 0;
			if(Mouse.isButtonDown(1))
				keys[7] = 1;
			else keys[7] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex(k[6])))
				keys[8] = 1;
			else keys[8] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex(k[7])))
				keys[9] = 1;
			else keys[9] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex(k[8])))
				keys[10] = 1;
			else keys[10] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex(k[9])))
				keys[11] = 1;
			else keys[11] = 0;
			if(Mouse.isButtonDown(2))
				keys[12] = 1;
			else keys[12] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex(k[10])))
				keys[13] = 1;
			else keys[13] = 0;
			if(Keyboard.isKeyDown(Keyboard.getKeyIndex(k[11])))
				keys[14] = 1;
			else keys[14] = 0;
			
			PacketDispatcher.sendPacketToServer(new Packet250CustomPayload("ssKeyCh",keys));

		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		if(!mc.isGamePaused&&mc.inGameHasFocus&&mc.thePlayer != null&&mc.thePlayer.getCurrentItemOrArmor(2) != null&&mc.thePlayer.getCurrentItemOrArmor(2).getItem().itemID == ssTanksMOD.インスタンス.moveleg.itemID){
			int c = 0;
			c += 255<<24;
			c += ssTanksMOD.インスタンス.uired<<16;
			c += ssTanksMOD.インスタンス.uigreen<<8;
			c += ssTanksMOD.インスタンス.uiblue;
			mc.fontRenderer.drawString("燃料"+ssTanksMOD.インスタンス.クライアント側燃料+"/24000",0,0,c);
		}
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel() { return null; }

}