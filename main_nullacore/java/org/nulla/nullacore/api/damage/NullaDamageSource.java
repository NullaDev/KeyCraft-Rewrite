package org.nulla.nullacore.api.damage;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;

public class NullaDamageSource extends DamageSource {
	
	public NullaDamageSource(String name) {
		super(name);
	}
	
	public static AuroraDamage CauseAuroraDamage(Entity entity) {
		return new AuroraDamage("aurora", entity);
	}
		
}