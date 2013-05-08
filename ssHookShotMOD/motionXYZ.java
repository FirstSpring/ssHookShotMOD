package net.minecraft.ssHookShotMOD;

public class motionXYZ{
	public float x;
	public float y;
	public float z;
	
	public motionXYZ(float x,float y,float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float[] getMotion()
	{
		float[] xyz = new float[3];
		xyz[0] = x;
		xyz[1] = y;
		xyz[2] = z;
		return xyz;
	}
}
