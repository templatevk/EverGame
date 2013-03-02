package pp.game.entities;

import org.andengine.entity.sprite.*;

import com.badlogic.gdx.physics.box2d.*;

abstract class DieableEntity extends BaseEntity implements IDieableEntity {
	private static final float FULL_HP_COEF = 0.8f;
	private static final float MEDIUM_HP_COEF = 0.5f;
	
	private volatile boolean isDead = false;
	private float currentHP;
	private float maxHP;
	private volatile HPState hpState;
	private Sprite aliveSprite;
	private Sprite deadSprite;
	private Body aliveBody;
	private Body deadBody;
	
	public DieableEntity() {
		
	}
	
	protected abstract void die();
	
	private void adjustHPState() {
		float playerHPCoef = getCurrentHP() / getMaxHP();	
		if (playerHPCoef >= FULL_HP_COEF) {
			hpState = HPState.HIGH;
		} else if (playerHPCoef >= MEDIUM_HP_COEF) {
			hpState = HPState.MEDIUM;
		} else if (getCurrentHP() != 0) {
			hpState = HPState.LOW;
		} else {
			hpState = HPState.DEAD;
		}
	}
	
	@Override
	public float getCurrentHP() {
		return currentHP;
	}

	@Override
	public void adjustCurrentHP(float value) {
		this.currentHP += value;
		if (currentHP <= 0) {
			if (!isDead()) {
				die();
			}
			isDead = true;
			currentHP = 0;
		} else if (currentHP > maxHP) {
			currentHP = maxHP;
		}
		adjustHPState();
	}

	@Override
	public float getMaxHP() {
		return maxHP;
	}

	@Override
	public void setMaxHP(float maxHP) {
		this.maxHP = maxHP;
		adjustHPState();
	}

	@Override
	public boolean isDead() {
		return isDead;
	}
	
	@Override
	public Sprite getAliveSprite() {
		return aliveSprite;
	}

	@Override
	public Sprite getDeadSprite() {
		return deadSprite;
	}
	
	@Override
	public Body getBody() {
		return isDead() ? getDeadBody() : getAliveBody();
	}
	
	@Override
	public Sprite getShape() {
		return isDead() ? getDeadSprite() : getAliveSprite();
	}
	
	@Override
	public HPState getHPState() {
		return hpState;
	}
	
	protected void setIdDead(boolean isDead) {
		this.isDead = isDead;
	}
	
	protected void setCurrentHP(float currentHP) {
		this.currentHP = currentHP;
		adjustHPState();
	}
	
	protected void setAliveSprite(Sprite aliveSprite) {
		this.aliveSprite = aliveSprite;
	}

	protected void setDeadSprite(Sprite deadSprite) {
		this.deadSprite = deadSprite;
	}

	@Override
	public Body getAliveBody() {
		return aliveBody;
	}

	protected void setAliveBody(Body aliveBody) {
		this.aliveBody = aliveBody;
	}

	@Override
	public Body getDeadBody() {
		return deadBody;
	}

	protected void setDeadBody(Body deadBody) {
		this.deadBody = deadBody;
	}
}
