package org.nulla.kcrw.entity.effect;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.nulla.kcrw.entity.EntityHasOwner;
import org.nulla.kcrw.skill.SkillsRw;
import org.nulla.nullacore.api.damage.NullaDamageSource;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityAuroraStorm extends EntityHasOwner {
	
	private float mDPS = 4F;

	public EntityAuroraStorm(World world) {
		super(world);
	}
	
	public EntityAuroraStorm(EntityPlayer owner) {
		super(owner.worldObj);
		
		int exp = SkillsRw.AuroraStorm.getExperience(owner);
		this.mDPS *= 2048 / (2048 - exp);
		
		this.setOwner(owner);
		this.posX = owner.posX;
		this.posY = owner.posY;
		this.posZ = owner.posZ;
	}
	
	@Override
    public void onUpdate() {
		super.onUpdate();

		if (this.worldObj.isRemote) {
			if (this.ticksExisted == 1) {
				for(int i = 0; i < 64; i++) {
					Random ran = new Random();
					float x = ran.nextFloat() * 8 - 4;
					float y = ran.nextFloat() * 4 - 1;
					float z = ran.nextFloat() * 8 - 4;
					EntityParticleFX par = new EntityAuroraStormFX(this.worldObj, this.posX + x, this.posY + y, this.posZ + z, this.getOwner());
					Minecraft.getMinecraft().effectRenderer.addEffect(par);
				}
			}
			return;
		}
		
		if (this.getOwner() == null || !SkillsRw.AuroraStorm.trigSkill(this.getOwner())) {
			this.setDead();
			return;
		}
		
		List list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(
				posX - 4, posY - 1, posZ - 4, posX + 4, posY + 3, posZ + 4));
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			EntityLivingBase entity = (EntityLivingBase)iterator.next();
			if (entity == this.getOwner())
				continue;
			entity.attackEntityFrom(NullaDamageSource.CauseAuroraDamage(this.getOwner()), this.mDPS / 2);	            	
		}
		
		this.posX = this.getOwner().posX;
		this.posY = this.getOwner().posY;
		this.posZ = this.getOwner().posZ;
		
		System.out.println(this.ticksExisted);
		
	}

}
