package pp.game.physics;

import com.badlogic.gdx.physics.box2d.*;

class EntityTypeInfo {
	float shapeSizeCoef;
	FixtureDef fixtureDef;
	
	public EntityTypeInfo() {
		
	}
	
	public  EntityTypeInfo(float shapeSizeCoef, FixtureDef fixtureDef) {
		this.shapeSizeCoef = shapeSizeCoef;
		this.fixtureDef = fixtureDef;
	}
}
