package net.minecraft.ssHookShotMOD;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class clientTickRender implements ITickHandler
{
	Minecraft mc = FMLClientHandler.instance().getClient();

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if(!mc.isGamePaused&&mc.inGameHasFocus)
			mc.fontRenderer.drawString("燃料"+ssTanksMOD.インスタンス.クライアント側燃料+"/24000",0,0,0);
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.RENDER);
	}

	@Override
	public String getLabel() {
		return null;
	}
}
