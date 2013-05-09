package net.minecraft.ssHookShotMOD;

import java.util.HashMap;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "ssTanksMOD", name="フックショットMOD", version="0_0")
@NetworkMod(clientSideRequired = true ,serverSideRequired = false,channels = {"位置合わせ" ,"ssKeyCh"},packetHandler = packethandler.class)
public class ssTanksMOD {

	float クライアント側モーションX = 0;
	float クライアント側モーションY = 0;
	float クライアント側モーションZ = 0;
	boolean クライアント側落ちない = false;
	boolean クライアント側パーティクル出す1 = false;
	boolean クライアント側パーティクル出す2 = false;
	
	Itemkousituken kenn;
	int kennID;
	
	Item 替刃;
	int 替刃ID;
	
	MoveLeggings moveleg;
	int movelegID;

	int EntityHookID;
	int EntitySoutiID;
	
	HashMap<String,byte[]> 入力状態 = new HashMap<String,byte[]>();

	@SidedProxy(clientSide = "net.minecraft.ssHookShotMOD.clientproxy", serverSide = "net.minecraft.ssHookShotMOD.serverproxy")
	public static serverproxy プロキシ;

	@Mod.Instance("ssTanksMOD")
	public static ssTanksMOD インスタンス;

	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try
		{
			cfg.load();
			movelegID = cfg.getItem("itemID", "movelegID", 5020).getInt();
			kennID = cfg.getItem("itemID", "kenn", 5021).getInt();
			替刃ID = cfg.getItem("itemID", "替刃ID", 5022).getInt();
			EntityHookID = cfg.get("EntityID", "EntityHookID", 2704).getInt();
			//EntitySoutiID = cfg.get("EntityID", "EntitySoutiID", 2705).getInt();
		}
		catch (Exception e)
		{
			FMLLog.log(Level.SEVERE, e, "コンフィグでエラー");
		}
		finally
		{
			cfg.save();
		}
	}
	
	@Mod.Init
	public void Init(FMLInitializationEvent event) {

		EntityRegistry.registerGlobalEntityID(EntityHook.class, "EntityHook", this.EntityHookID);
		EntityRegistry.registerModEntity(EntityHook.class, "EntityHook", 0, this, 250, 1,true);

		this.moveleg = (MoveLeggings)new MoveLeggings(this.movelegID,EnumArmorMaterial.DIAMOND,2,2).setUnlocalizedName("sshookshot:moveleg");
		LanguageRegistry.addName(moveleg, "hookshot");
		LanguageRegistry.instance().addNameForObject(moveleg, "ja_JP", "フックショットもどき");
		
		GameRegistry.addRecipe(
				new ItemStack(moveleg, 1), 
                new Object[]{ 
					"AIA",
					"FDF",
					"BSB",
                'I',Item.ingotIron,
                'D',Item.legsIron,
                'F',Item.fishingRod,
                'A',Item.arrow,
                'B',Item.bow,
                'S',Block.lever
                });
		
		this.kenn = (Itemkousituken)new Itemkousituken(this.kennID).setUnlocalizedName("sshookshot:kenn");
		LanguageRegistry.addName(kenn, "kenn");
		LanguageRegistry.instance().addNameForObject(kenn, "ja_JP", "剣");
		
		GameRegistry.addRecipe(
				new ItemStack(kenn, 1,35), 
                new Object[]{ 
					" T ",
					"BB ",
					"BB ",
                'T',Item.ingotIron,
                'B',Item.stick,
                });
		
		this.替刃 = new Item(this.替刃ID).setUnlocalizedName("sshookshot:kaeba").setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(12);
		LanguageRegistry.addName(替刃, "kaeba");
		LanguageRegistry.instance().addNameForObject(替刃, "ja_JP", "替刃");
		
		GameRegistry.addRecipe(
				new ItemStack(替刃, 12), 
                new Object[]{ 
					" T ",
					" T ",
					"   ",
                'T',Item.ingotIron,
                });
		
		プロキシ.登録();
	}
}
