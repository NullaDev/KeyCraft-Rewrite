package org.nulla.kcrw.entity.effect;

import java.util.Random;

import org.nulla.kcrw.KCUtils;
import org.nulla.kcrw.skill.SkillsRw;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityAuroraStormFX extends EntityParticleFX {
	
	private final double randomDouble = new Random().nextDouble();
	private final double omegle = Math.PI / 60 + new Random().nextDouble() * Math.PI / 60;
	private final double y;
	private final double R;
	private double rad;
	private final EntityPlayer mOwner;

	public EntityAuroraStormFX(World world, double posX, double posY, double posZ, EntityPlayer owner) {
		super(world, posX, posY, posZ, KCUtils.zeroVec3);
		this.particleMaxAge = Integer.MAX_VALUE;
		this.mOwner = owner;

		y = this.posY - owner.posY;
		R = Math.sqrt((posX - owner.posX) * (posX - owner.posX) + (posZ - owner.posZ) * (posZ - owner.posZ));
		rad = Math.atan2(this.posY - owner.posY, this.posX - owner.posX);
	}
	
	@Override
	public void onUpdate() {
		if (!SkillsRw.AuroraStorm.getIsOn(mOwner)) {
			this.setDead();
			return;
		}
		
		if (!SkillsRw.AuroraStorm.isInSlot(mOwner)) {
			this.setDead();
			return;
		}
		
		float b = (float) Math.sin(2 * Math.PI * randomDouble + this.ticksExisted * Math.PI / 10) * 0.5F + 0.5F;
		this.setRBGColorF(0.5F, 1F, b);
		
		rad += omegle;
		this.motionY = this.mOwner.posY - this.posY + y;
		this.motionX = this.mOwner.posX + R * Math.cos(rad) - this.posX;
		this.motionZ = this.mOwner.posZ + R * Math.sin(rad) - this.posZ;
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		
		this.ticksExisted++;
	}

}
