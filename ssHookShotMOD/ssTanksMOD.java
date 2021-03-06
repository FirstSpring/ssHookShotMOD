package net.minecraft.ssHookShotMOD;

import java.util.HashMap;
import java.util.logging.Level;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "ssHookShotMOD", name="フックショットMOD", version="0_0")
@NetworkMod(clientSideRequired = true ,serverSideRequired = false,channels = {"パーティクル同期","位置合わせ" ,"ssKeyCh"},packetHandler = packethandler.class)
public class ssTanksMOD {

	float クライアント側モーションX = 0;
	float クライアント側モーションY = 0;
	float クライアント側モーションZ = 0;
	int クライアント側燃料 = 0;
	boolean クライアント側落ちない = false;
	
	public HashMap<String,Boolean> クライアント側パーティクル出す1 = new HashMap<String,Boolean>();
	public HashMap<String,Boolean> クライアント側パーティクル出す2 = new HashMap<String,Boolean>();
	
	String keys[] = new String[20];
	
	int uired;
	int uigreen;
	int uiblue;
	
	EnumToolMaterial 剣 = EnumHelper.addToolMaterial("剣",1,0,20,0,14);
	
	Itemkousituken kenn;
	int kennID;
	
	Item 燃料;
	int 燃料ID;
	
	Item 替刃;
	int 替刃ID;
	
	MoveLeggings moveleg;
	int movelegID;

	int EntityHookID;
	int EntitySoutiID;
	
	HashMap<String,byte[]> 入力状態 = new HashMap<String,byte[]>();

	@SidedProxy(clientSide = "net.minecraft.ssHookShotMOD.clientproxy", serverSide = "net.minecraft.ssHookShotMOD.serverproxy")
	public static serverproxy プロキシ;

	@Mod.Instance("ssHookShotMOD")
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
			燃料ID = cfg.getItem("itemID", "燃料ID", 5022).getInt();
			EntityHookID = cfg.get("EntityID", "EntityHookID", 2704).getInt();
			keys[0] = cfg.get("キー入力","W","W").getString();
			keys[1] = cfg.get("キー入力","A","A").getString();
			keys[2] = cfg.get("キー入力","S","S").getString();
			keys[3] = cfg.get("キー入力","D","D").getString();
			keys[4] = cfg.get("キー入力","SPACE","SPACE").getString();
			keys[5] = cfg.get("キー入力","LSHIFT","LSHIFT").getString();
			keys[6] = cfg.get("キー入力","TAB","TAB").getString();
			keys[7] = cfg.get("キー入力","LCONTROL","LCONTROL").getString();
			keys[8] = cfg.get("キー入力","R","R").getString();
			keys[9] = cfg.get("キー入力","F","F").getString();
			keys[10] = cfg.get("キー入力","V","V").getString();
			keys[11] = cfg.get("キー入力","B","B").getString();
			uired = cfg.get("表示色","赤",255).getInt();
			uigreen = cfg.get("表示色","緑",255).getInt();
			uiblue = cfg.get("表示色","青",255).getInt();
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

		this.moveleg = (MoveLeggings)new MoveLeggings(this.movelegID,EnumArmorMaterial.CHAIN,2,2).setUnlocalizedName("sshookshot:moveleg");
		LanguageRegistry.addName(moveleg, "hookshot");
		LanguageRegistry.instance().addNameForObject(moveleg, "ja_JP", "フックショットもどき");
		
		GameRegistry.addRecipe(
				new ItemStack(moveleg,1,24000), 
                new Object[]{ 
					"A A",
					"BCB",
					"F F",
                'C',Item.legsChain,
                'F',Item.fishingRod,
                'A',Item.arrow,
                'B',Item.bow,
                });
		
		this.kenn = (Itemkousituken)new Itemkousituken(this.kennID).setUnlocalizedName("sshookshot:kenn");
		LanguageRegistry.addName(kenn, "kenn");
		LanguageRegistry.instance().addNameForObject(kenn, "ja_JP", "剣");
		
		GameRegistry.addRecipe(
				new ItemStack(kenn, 1,20), 
                new Object[]{ 
					" S ",
					"BB ",
					"BB ",
                'S',Item.ingotIron,
                'B',Item.stick,
                });
		
		this.替刃 = new Item(this.替刃ID).setUnlocalizedName("sshookshot:kaeba").setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(12);
		LanguageRegistry.addName(替刃, "kaeba");
		LanguageRegistry.instance().addNameForObject(替刃, "ja_JP", "替刃");
		
		GameRegistry.addRecipe(
				new ItemStack(替刃, 8), 
                new Object[]{ 
					" T ",
					" T ",
					"S S",
                'T',Item.ingotIron,
                'S',Item.stick
                });
		
		this.燃料 = new Itemnennryou(this.燃料ID).setUnlocalizedName("sshookshot:nennryou").setCreativeTab(CreativeTabs.tabCombat).setMaxStackSize(64).setMaxDamage(600);
		LanguageRegistry.addName(燃料, "nennryou");
		LanguageRegistry.instance().addNameForObject(燃料, "ja_JP", "燃料");
		
		GameRegistry.addRecipe(
				new ItemStack(燃料, 32), 
                new Object[]{ 
					" M ",
					" S ",
					"   ",
                'M',Item.coal,
                'S',Item.firework,
                });
		
		プロキシ.登録();
	}
}
