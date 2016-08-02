package org.nulla.kcrw.entity.entityliving;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityTreeGuard extends KCEntityMob {

	public EntityTreeGuard(World world) {
		super(world);
		this.setSize(0.5F, 0.5F);
		this.experienceValue = 20;
	}
	
	protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
    }
	
	public EntityTreeGuard(World world, double posX, double posY, double posZ) {
		this(world);
		this.setPosition(posX, posY, posZ);
	}
	
	@Override
	protected Item getDropItem() {
        return Item.getItemFromBlock(Blocks.log);
    }
	
	@Override
	public float getEyeHeight() {
        return this.height * 0.8F;
    }

	@Override
	public int getOnDeathDropAuroraPoint() {
		return 10;
	}

	@Override
	public int getOnSpawnNeededAuroraPoint() {
		return 10;
	}

}