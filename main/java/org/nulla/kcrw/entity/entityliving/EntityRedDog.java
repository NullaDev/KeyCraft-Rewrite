package org.nulla.kcrw.entity.entityliving;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityRedDog extends KCEntityMob {

	public EntityRedDog(World world) {
		super(world);
		this.setSize(1F, 1F);
		this.experienceValue = 40;
	}

	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(25.0D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(4.0D);
    }
	
	public EntityRedDog(World world, double posX, double posY, double posZ) {
		this(world);
		this.setPosition(posX, posY, posZ);
	}
	
	@Override
	protected Item getDropItem() {
        return Items.chicken;
    }
	
	@Override
	public float getEyeHeight() {
        return this.height * 0.8F;
    }

	@Override
	public int getOnDeathDropAuroraPoint() {
		return 15;
	}

	@Override
	public int getOnSpawnNeededAuroraPoint() {
		return 15;
	}

}
