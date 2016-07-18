package org.nulla.kcrw.entity.entityliving;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityBlackDog extends EntityMob {

	public EntityBlackDog(World world) {
		super(world);
		this.setSize(1F, 1F);
		
		this.experienceValue = 20;
	}
	
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(12.0D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(3.0D);
    }
	
	public EntityBlackDog(World world, double posX, double posY, double posZ) {
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

}
