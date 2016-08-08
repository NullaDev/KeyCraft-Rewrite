package org.nulla.kcrw.entity.entityliving;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.BlockLog;
import net.minecraft.entity.Entity;
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
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityTreeProducer extends KCEntityAnimal {

	int timeUntilNextProduce;
	
	public EntityTreeProducer(World p_i1681_1_) {
		super(p_i1681_1_);
		this.setSize(1f, 1f);
		this.timeUntilNextProduce=this.rand.nextInt(1000) + 1000;
		
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
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.05D);
    }
	
	public boolean getCanSpawnHere()
    {
        int x = MathHelper.floor_double(this.posX);
        int y = MathHelper.floor_double(this.boundingBox.minY);
        int z = MathHelper.floor_double(this.posZ);
        boolean isTreeBranchHere=false,isTreeLeafHere=false;
        for(int i=x-5;i<=x+5;i++)
        {
        	for(int j=y-2;j<=y+7;j++)
            {
        		for(int k=z-5;k<=z+5;k++)
                {
                	if(worldObj.getBlock(i, j, k)instanceof BlockLeavesBase)isTreeLeafHere=true;
                	if(worldObj.getBlock(i, j, k)instanceof BlockLog && worldObj.getBlock(i, j+1, k)instanceof BlockLog && worldObj.getBlock(i, j+2, k)instanceof BlockLog)isTreeBranchHere=true;
                }
            }
        }
        return isTreeLeafHere && isTreeBranchHere && super.getCanSpawnHere();
    }

	public void onLivingUpdate(){
		super.onLivingUpdate();
		if (!this.worldObj.isRemote && !this.isChild() && --this.timeUntilNextProduce <= 0) {
			this.dropItem(Items.apple, this.rand.nextInt(1));
			this.dropItem(Items.bread, this.rand.nextInt(1));
            this.dropItem(Item.getItemFromBlock(Blocks.log), this.rand.nextInt(3));
            this.timeUntilNextProduce = this.rand.nextInt(1000) + 1000;
        }
	}
	
	protected void attackEntity(Entity par1, float par2)
	{
		super.attackEntity(par1, par2);
		this.hasAttacked=false;
		timeUntilNextProduce=2000+timeUntilNextProduce;
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
		return false;
	}
	
	@Override
	public EntityAgeable createChild(EntityAgeable p_90011_1_) {
		return null;
	}
	
	@Override
	protected Item getDropItem() {
        return Item.getItemFromBlock(Blocks.log);
    }
}
