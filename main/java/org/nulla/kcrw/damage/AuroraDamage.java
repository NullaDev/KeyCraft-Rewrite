package org.nulla.kcrw.damage;

import net.minecraft.entity.Entity;

public class AuroraDamage extends KCDamageSource {

	protected Entity mAttacker = null;
	
	public AuroraDamage(String name, Entity attacker) {
		super(name);
		this.mAttacker = attacker;
		this.setDamageBypassesArmor();
		this.setMagicDamage();
	}
	
	@Override
	public Entity getEntity() {
        return mAttacker;
    }

}
