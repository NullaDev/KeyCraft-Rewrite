package org.nulla.nullacore.api.damage;

import net.minecraft.entity.Entity;

public class AuroraDamage extends NullaDamageSource {

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
