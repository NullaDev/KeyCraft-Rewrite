package org.nulla.kcrw.entity.effect;

import java.util.Random;

import org.nulla.kcrw.KCUtils;
import org.nulla.kcrw.skill.SkillsRw;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityAuroraStormFX extends EntityParticleFX {
	
	private final double ran = new Random().nextDouble();
	private final double spe = Math.PI / 60 + new Random().nextDouble() * Math.PI / 60;
	private final double R;
	private double a;
	private final EntityPlayer mOwner;

	public EntityAuroraStormFX(World world, double posX, double posY, double posZ, EntityPlayer owner) {
		super(world, posX, posY, posZ, KCUtils.zero);
		this.particleMaxAge = Integer.MAX_VALUE;
		this.mOwner = owner;

		R = Math.sqrt((posX - owner.posX) * (posX - owner.posX) + (posZ - owner.posZ) * (posZ - owner.posZ));
		a = Math.asin((posX - owner.posX) / R);
	}
	
	@Override
	public void onUpdate() {
		
		if (!SkillsRw.AuroraStorm.getIsOn(mOwner)) {
			this.setDead();
			return;
		}
		
		float b = (float) Math.sin(2 * Math.PI * ran + this.ticksExisted * Math.PI / 10) * 0.5F + 0.5F;
		this.setRBGColorF(0.5F, 1F, b);
		
		a += spe;
		this.motionX = this.mOwner.posX + R * Math.sin(a) - this.posX;
		this.motionY = 0;
		this.motionZ = this.mOwner.posZ + R * Math.sin(a) - this.posZ;
	}

}
