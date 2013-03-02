package pp.game.entities;

import org.andengine.entity.sprite.*;

import com.badlogic.gdx.physics.box2d.*;

public interface IDieableEntity extends IBaseEntity {
	public enum HPState {
		DEAD, LOW, MEDIUM, HIGH;
	}
	
	float getCurrentHP();
	void adjustCurrentHP(float value);	
	float getMaxHP();
	void setMaxHP(float maxHP);
	
	HPState getHPState();
	
	boolean isDead();
	
	Sprite getAliveSprite();
	Sprite getDeadSprite();	
	
	Body getAliveBody();
	Body getDeadBody();
}
