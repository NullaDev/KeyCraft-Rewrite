package org.nulla.kcrw.common;

public class KCDelayedEffect extends KCSpecialEffect {
	
	public int rest_ticks = 0;
	protected ICallback Callback = null;
	
	public interface ICallback {
		void onTrigger();
	}
	
	public KCDelayedEffect(int ticks) {
		this(ticks, true);
	}
	
	public KCDelayedEffect(int ticks, boolean add) {
		this.rest_ticks = ticks;
		if (add)
			KCEffectHandler.AllEffects.add(this);
	}
	
	public KCDelayedEffect setCallback(ICallback callback) {
		Callback = callback;
		return this;
	}
	
	public void onTrigger() {
		Callback.onTrigger();
	}
	
	public KCDelayedEffect clone() {
		KCDelayedEffect newEffect = new KCDelayedEffect(this.rest_ticks, false);
		newEffect.setCallback(this.Callback);
		return this;
	}

}
