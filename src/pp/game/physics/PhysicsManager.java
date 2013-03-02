package pp.game.physics;

import java.util.*;

import org.andengine.engine.handler.*;
import org.andengine.entity.primitive.*;
import org.andengine.entity.shape.*;
import org.andengine.extension.physics.box2d.*;
import org.andengine.opengl.vbo.*;

import pp.game.*;
import pp.game.entities.*;
import pp.game.level.*;
import pp.game.scene.*;
import pp.game.utils.geometry.*;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PhysicsManager implements IPreparable, IResetable, IDestroyable {	
	private static final int VELOCITY_ITERATIONS = 1;
	private static final int POSITION_ITERATIONS = 1;
	
	private static final float BULLET_SHAPE_SIZE_COEF 		= 0.50f;
	private static final float MONSTER_SHAPE_SIZE_COEF 		= 0.50f;
	private static final float PLAYER_SHAPE_SIZE_COEF 		= 0.50f;
	private static final float BONUS_SHAPE_SIZE_COEF 		= 1.00f;
	
	private static final short BULLET_CATEGORY_BIT 			= 1;
	private static final short MONSTER_CATEGORY_BIT 		= 2;
	private static final short PLAYER_CATEGORY_BIT 			= 4;
	private static final short BONUS_CATEGORY_BIT 			= 8;
	private static final short WALL_CATEGORY_BIT			= 16;
	
	private static final PhysicsManager INSTANCE = new PhysicsManager();
	
	private PhysicsWorld world;
	private Map<Body, PhysicsConnector> bodyConnectors;
	private Map<EntityType, EntityTypeInfo> entityTypeInfo;
	
	private PhysicsManager() {
		IGame game = Game.getGameInstance();
		game.addPreparable(this);
		game.addResetable(this);
		game.addDestroyable(this);
		
		entityTypeInfo = new HashMap<EntityType, EntityTypeInfo>();
		entityTypeInfo.put(EntityType.MONSTER, new EntityTypeInfo() {{
			this.shapeSizeCoef = MONSTER_SHAPE_SIZE_COEF;
			this.fixtureDef = PhysicsFactory.createFixtureDef(0, 0, 0, false, MONSTER_CATEGORY_BIT, 
					(short)(MONSTER_CATEGORY_BIT | BULLET_CATEGORY_BIT | PLAYER_CATEGORY_BIT),  
					(short)0);
		}});
		entityTypeInfo.put(EntityType.PLAYER, new EntityTypeInfo() {{
			this.shapeSizeCoef = PLAYER_SHAPE_SIZE_COEF;
			this.fixtureDef = PhysicsFactory.createFixtureDef(0, 0, 0, false, PLAYER_CATEGORY_BIT, 
					(short)(BONUS_CATEGORY_BIT | MONSTER_CATEGORY_BIT | WALL_CATEGORY_BIT), (short)0);
		}});
		entityTypeInfo.put(EntityType.BULLET, new EntityTypeInfo() {{
			this.shapeSizeCoef = BULLET_SHAPE_SIZE_COEF;
			this.fixtureDef = PhysicsFactory.createFixtureDef(0, 0, 0, false, BULLET_CATEGORY_BIT, 
					MONSTER_CATEGORY_BIT, (short)0);
		}});
		entityTypeInfo.put(EntityType.BONUS, new EntityTypeInfo() {{
			this.shapeSizeCoef = BONUS_SHAPE_SIZE_COEF;
			this.fixtureDef = PhysicsFactory.createFixtureDef(0, 0, 0,
					false, BONUS_CATEGORY_BIT, (short)(PLAYER_CATEGORY_BIT), (short)0);
		}});
	}
	
	public static PhysicsManager getInstance() {
		return INSTANCE;
	}
	
	@Override
	public void prepare(ILevel level) {
		world = new PhysicsWorld(new Vector2(0, 0), false, 
				VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		world.setContactListener(new SceneContactListener());
		
		bodyConnectors = new HashMap<Body, PhysicsConnector>();
		
		FixtureDef wallFixture = PhysicsFactory.createFixtureDef(0, 0, 0, false,
				WALL_CATEGORY_BIT, PLAYER_CATEGORY_BIT, (short)0);
		VertexBufferObjectManager manager = Game.getGameActivity().getVertexBufferObjectManager();
		float x = SceneLayoutUtils.BACKGROUND_MAX_X;
		float y = SceneLayoutUtils.BACKGROUND_MAX_Y;
		PhysicsFactory.createLineBody(world, new Line(0f, 0f, x, 0f, manager), wallFixture);
		PhysicsFactory.createLineBody(world, new Line(0f, 0f, 0f, y, manager), wallFixture);
		PhysicsFactory.createLineBody(world, new Line(x, 0f, x, y, manager), wallFixture);
		PhysicsFactory.createLineBody(world, new Line(0f, y, x, y, manager), wallFixture);
	}
	
	@Override
	public void reset() {
		world.dispose();
	}
	
	@Override
	public void destroy() {
	
	}
	
	@Override
	public Priority getPriority() {
		return Priority.FIRST;
	}
	
	public IUpdateHandler getUpdateHandler() {
		return world;
	}
	
	public void registerJoint(JointDef def) {
		world.createJoint(def);
	}
	
	public Body createBody(IBaseEntity entity) {
		Body body = null;
		IAreaShape shape = entity.getShape();
		EntityTypeInfo info = entityTypeInfo.get(entity.getEntityType());
		
		switch (entity.getEntityType()) {
		case BULLET:
			switch (((Bullet)entity).getWeaponType()) {
			case AK_47:
			case FLAMETHROWER:
			case LASER:
			case M_32:
			case PM:
			case REMINGTON:
			case SHOTGUN:
			case UZI:
				body = PhysicsFactory.createBoxBody(world, entity.getShape(), BodyType.DynamicBody, 
						entityTypeInfo.get(entity.getEntityType()).fixtureDef);
				break;
			}
			body.setBullet(true);
			break;
			
		case MONSTER:
		case PLAYER:
			body = PhysicsFactory.createBoxBody(world, shape.getX() + shape.getWidth() / 2f,
					shape.getY() + shape.getHeightScaled() / 2f, 
					shape.getWidthScaled() * info.shapeSizeCoef, 
					shape.getHeightScaled() * info.shapeSizeCoef,
					BodyType.DynamicBody, info.fixtureDef);
			break;
		case BONUS:
			body = PhysicsFactory.createBoxBody(world, shape.getX() + shape.getWidth() / 2f,
					shape.getY() + shape.getHeightScaled() / 2f, 
					shape.getWidthScaled() * info.shapeSizeCoef, 
					shape.getHeightScaled() * info.shapeSizeCoef,
					BodyType.StaticBody, info.fixtureDef);
			break;
		}
		
		PhysicsConnector connector = new PhysicsConnector(entity.getShape(), body, true, false);
		body.setUserData(entity);
		bodyConnectors.put(body, connector);
		world.registerPhysicsConnector(connector);
		return body;
	}
	
	public void removeBody(final Body body) {
		Game.getGameActivity().runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				PhysicsConnector connector = bodyConnectors.get(body);
				if (connector != null) {
					GameScene.getInstance().detachChild(connector.getShape());
					world.unregisterPhysicsConnector(connector);
				}
				body.setActive(false);
				bodyConnectors.remove(body);
			}
		});
	}
}
