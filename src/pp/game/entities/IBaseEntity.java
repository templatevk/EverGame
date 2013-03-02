package pp.game.entities;

import org.andengine.entity.shape.*;

import com.badlogic.gdx.physics.box2d.*;

public interface IBaseEntity {
	EntityType getEntityType();
	Body getBody();
	IAreaShape getShape();
	Object getUserData();
	void setUserData(Object data);
}
