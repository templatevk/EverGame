package pp.game.physics;

import pp.game.entities.*;
import pp.game.handlers.contact.*;
import pp.game.scene.*;

import com.badlogic.gdx.physics.box2d.*;

class SceneContactListener implements ContactListener {		
	public SceneContactListener() {

	}
	
	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		Body bodyA = fixtureA.getBody();
		Body bodyB = fixtureB.getBody();
		IBaseEntity entityA = (IBaseEntity)bodyA.getUserData();
		IBaseEntity entityB = (IBaseEntity)bodyB.getUserData();
		
		if (entityA == null || entityB == null) {
			return;
		}
		
		IContactHandler handler = null;
		EntityType typeA = entityA.getEntityType();
		EntityType typeB = entityB.getEntityType();
		
		// PLAYER && MONSTER CONTACT
		if (typeA == EntityType.PLAYER && typeB == EntityType.MONSTER) {
			handler = ContactResolver.getPlayerMonsterContactHandler((Monster)entityB);
		} else if (typeB == EntityType.PLAYER && typeA == EntityType.MONSTER) {
			handler = ContactResolver.getPlayerMonsterContactHandler((Monster)entityA);
		}
		
		// BULLET && MONSTER CONTACT
		else if (typeA == EntityType.BULLET && typeB == EntityType.MONSTER) {
			ContactResolver.handleBulletMonsterContact((Bullet)entityA, (Monster)entityB);
		} else if (typeB == EntityType.BULLET && typeA == EntityType.MONSTER) {
			ContactResolver.handleBulletMonsterContact((Bullet)entityB, (Monster)entityA);
		}
		
		// BONUS && PLAYER CONTACT
		else if (typeA == EntityType.BONUS && typeB == EntityType.PLAYER) {
			ContactResolver.handleBonusPlayerContact((Bonus)entityA);
		} else if (typeB == EntityType.BONUS && typeA == EntityType.PLAYER) {
			ContactResolver.handleBonusPlayerContact((Bonus)entityB);
		}

		if (handler != null) {
			GameScene.getInstance().registerUpdateHandler(handler);
		}
	}
	
	@Override
	public void endContact(Contact contact) {
			
	}
	
	@Override
	public void postSolve(Contact contact, ContactImpulse contactImpulse) {
		
	}
	
	@Override
	public void preSolve(Contact contact, Manifold manifold) {
		
	}
}