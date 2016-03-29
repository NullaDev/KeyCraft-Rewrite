package org.nulla.kcrw.damage;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;

public class KCDamageSource extends DamageSource {
	
	public KCDamageSource(String name) {
		super(name);
	}
	
	public static AuroraDamage CauseAuroraDamage(Entity entity) {
		return new AuroraDamage("aurora", entity);
	}
		
}