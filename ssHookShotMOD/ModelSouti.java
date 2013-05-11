package net.minecraft.ssHookShotMOD;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class ModelSouti extends ModelBiped
{
	ModelRenderer kikai1;
	ModelRenderer kikai2;
	ModelRenderer rittai;

	public ModelSouti()
	{
		textureWidth = 64;
		textureHeight = 64;

		kikai1 = new ModelRenderer(this, 0, 20);
		kikai1.addBox(5F, 5F, -4F, 4, 4, 12);
		kikai1.setRotationPoint(0F, 0F, 0F);
		kikai1.setTextureSize(64, 64);
		kikai1.mirror = true;
		setRotation(kikai1, -0.3490659F, 0F, 0F);
		kikai2 = new ModelRenderer(this, 0, 0);
		kikai2.addBox(-9F, 5F, -4F, 4, 4, 12);
		kikai2.setRotationPoint(0F, 0F, 0F);
		kikai2.setTextureSize(64, 64);
		kikai2.mirror = true;
		setRotation(kikai2, -0.3490659F, 0F, 0F);
		rittai = new ModelRenderer(this, 0, 40);
		rittai.addBox(-3F, 0F, 3F, 6, 4, 3);
		rittai.setRotationPoint(0F, 0F, 0F);
		rittai.setTextureSize(64, 64);
		rittai.mirror = true;
		setRotation(rittai, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		GL11.glTranslatef(0.0F,0.4F,0.0F);
		GL11.glScalef(0.8F,0.8F,0.8F);
		if(entity.isSneaking())
		{
			GL11.glTranslatef(0.0F,0.0F,0.4F);
		}
		setRotationAngles(f, f1, f2, f3, f4, f5,entity);
		kikai1.render(f5);
		kikai2.render(f5);
		rittai.render(f5);
		if(entity.isSneaking())
		{
			GL11.glTranslatef(0.0F,0.0F,-0.4F);
		}
		GL11.glTranslatef(0.0F,-0.4F,0.0F);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
