package org.nulla.kcrw.entity.entityliving;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityTreeProducer extends KCEntityAnimal {

	int timeUntilNextProduce;
	
	public EntityTreeProducer(World p_i1681_1_) {
		super(p_i1681_1_);
		this.setSize(1f, 1f);
		this.timeUntilNextProduce=this.rand.nextInt(2000) + 2000;
		
		this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWander(this, 0.1D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
	}
	
	@Override
	public boolean isAIEnabled() {
        return true;
    }
	
	protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0005D);
    }

	public void onLivingUpdate(){
		super.onLivingUpdate();
		if (!this.worldObj.isRemote && !this.isChild() && --this.timeUntilNextProduce <= 0) {
            this.dropItem(Item.getItemFromBlock(Blocks.log), this.rand.nextInt(3));
            this.timeUntilNextProduce = this.rand.nextInt(2000) + 2000;
        }
	}
	
	@Override
	public int getOnDeathDropAuroraPoint() {
		return this.getAge();
	}

	@Override
	public int getOnSpawnNeededAuroraPoint() {
		return 0;
	}

	@Override
	public float getEyeHeight() {
        return this.height * 0.7F;
    }
	
	@Override
	public boolean isChild(){
		return true;
	}
	
	@Override
	public EntityAgeable createChild(EntityAgeable p_90011_1_) {
		return new EntityTreeProducer(this.worldObj);
	}
	
	@Override
	protected Item getDropItem() {
        return Item.getItemFromBlock(Blocks.log);
    }
}
