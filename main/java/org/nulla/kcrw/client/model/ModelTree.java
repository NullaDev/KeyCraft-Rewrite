package org.nulla.kcrw.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;

@SideOnly(Side.CLIENT)
public class ModelTree extends ModelBase {

	public ModelRenderer treeHead;
	public ModelRenderer treeBody;
	public ModelRenderer treeRightArm;
	public ModelRenderer treeLeftArm;
	public ModelRenderer treeLeftLeg;
	public ModelRenderer treeRightLeg;

	public ModelTree() {
		this(0.0F);
	}

	public ModelTree(float p_i1161_1_) {
		this(p_i1161_1_, -7.0F);
	}

	public ModelTree(float scale, float p_i1162_2_) {
    	short short1 = 128;
    	short short2 = 128;
    	this.treeHead = (new ModelRenderer(this)).setTextureSize(short1, short2);
    	this.treeHead.setRotationPoint(0.0F, 0.0F + p_i1162_2_, -2.0F);
    	this.treeHead.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -5.5F, 8, 10, 8, scale);
    	this.treeHead.setTextureOffset(24, 0).addBox(-1.0F, -5.0F, -7.5F, 2, 4, 2, scale);
    	this.treeBody = (new ModelRenderer(this)).setTextureSize(short1, short2);
    	this.treeBody.setRotationPoint(0.0F, 0.0F + p_i1162_2_, 0.0F);
    	this.treeBody.setTextureOffset(0, 40).addBox(-9.0F, -2.0F, -6.0F, 18, 12, 11, scale);
    	this.treeBody.setTextureOffset(0, 70).addBox(-4.5F, 10.0F, -3.0F, 9, 5, 6, scale + 0.5F);
    	this.treeRightArm = (new ModelRenderer(this)).setTextureSize(short1, short2);
    	this.treeRightArm.setRotationPoint(0.0F, -7.0F, 0.0F);
    	this.treeRightArm.setTextureOffset(60, 21).addBox(-13.0F, -2.5F, -3.0F, 4, 30, 6, scale);
    	this.treeLeftArm = (new ModelRenderer(this)).setTextureSize(short1, short2);
    	this.treeLeftArm.setRotationPoint(0.0F, -7.0F, 0.0F);
    	this.treeLeftArm.setTextureOffset(60, 58).addBox(9.0F, -2.5F, -3.0F, 4, 30, 6, scale);
    	this.treeLeftLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(short1, short2);
    	this.treeLeftLeg.setRotationPoint(-4.0F, 18.0F + p_i1162_2_, 0.0F);
    	this.treeLeftLeg.setTextureOffset(37, 0).addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, scale);
    	this.treeRightLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(short1, short2);
    	this.treeRightLeg.mirror = true;
    	this.treeRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0F, 18.0F + p_i1162_2_, 0.0F);
    	this.treeRightLeg.addBox(-3.5F, -3.0F, -3.0F, 6, 16, 5, scale);
	}

    /**
     * Sets the models various rotation angles then renders the model.
     */
    @Override
    public void render(Entity entity, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, entity);
        this.treeHead.render(p_78088_7_);
        this.treeBody.render(p_78088_7_);
        this.treeLeftLeg.render(p_78088_7_);
        this.treeRightLeg.render(p_78088_7_);
        this.treeRightArm.render(p_78088_7_);
        this.treeLeftArm.render(p_78088_7_);
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float headY, float headX, float p_78087_6_, Entity entity) {
        this.treeHead.rotateAngleY = headY * (float)Math.PI / 180;
        this.treeHead.rotateAngleX = headX * (float)Math.PI / 180;
        this.treeLeftLeg.rotateAngleX = -1.5F * this.func_78172_a(p_78087_1_, 13.0F) * p_78087_2_;
        this.treeRightLeg.rotateAngleX = 1.5F * this.func_78172_a(p_78087_1_, 13.0F) * p_78087_2_;
        this.treeLeftLeg.rotateAngleY = 0.0F;
        this.treeRightLeg.rotateAngleY = 0.0F;
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    @Override
    public void setLivingAnimations(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_) {
    	this.treeRightArm.rotateAngleX = (-0.2F + 1.5F * this.func_78172_a(p_78086_2_, 13.0F)) * p_78086_3_;
    	this.treeLeftArm.rotateAngleX = (-0.2F - 1.5F * this.func_78172_a(p_78086_2_, 13.0F)) * p_78086_3_;
    }

    private float func_78172_a(float p_78172_1_, float p_78172_2_)
    {
        return (Math.abs(p_78172_1_ % p_78172_2_ - p_78172_2_ * 0.5F) - p_78172_2_ * 0.25F) / (p_78172_2_ * 0.25F);
    }
}