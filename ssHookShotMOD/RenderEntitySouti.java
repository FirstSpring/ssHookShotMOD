package net.minecraft.ssHookShotMOD;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class RenderEntitySouti extends Render{

	ModelSouti model;

	public RenderEntitySouti(){
		model = new ModelSouti();
	}

	@Override
	public void doRender(Entity entity, double par2, double par4, double par6, float par8, float par9){
		EntitySouti e = (EntitySouti)entity;
		if(e.get見えてるか()){
			GL11.glPushMatrix();
			GL11.glTranslatef((float)par2, (float)par4, (float)par6);
			GL11.glRotatef(-par8+180.0F, 0.0F, 1.0F, 0.0F);
			this.loadTexture("/item/boat.png");
			GL11.glScalef(0.6F,0.6F,0.6F);
			GL11.glScalef(-1.0F, -1.0F, 1.0F);
			this.model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
		}
	}
}
