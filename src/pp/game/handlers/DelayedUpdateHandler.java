package pp.game.handlers;

public abstract class DelayedUpdateHandler extends UpdateHandler {
	private float delay;
	private float requiredDelay;
	
	protected DelayedUpdateHandler() {
		
	}
	
	protected DelayedUpdateHandler(float requiredDelay) {
		this.requiredDelay = requiredDelay;
	}
	
	@Override
	final public void onUpdate(float secondsElasped) {
		delay += secondsElasped;
		if (delay < requiredDelay) {
			return;
		}
		onUpdate();
		delay = 0;
	}
	
	public void setRequiredDelay(float delay) {
		this.requiredDelay = delay;
	}
	
	protected float getRequiredDelay() {
		return requiredDelay;
	}
	
	protected abstract void onUpdate();
}
