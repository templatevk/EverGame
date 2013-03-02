package pp.game.entities;

import org.andengine.entity.primitive.*;
import org.andengine.entity.shape.*;
import org.andengine.entity.sprite.*;
import org.andengine.util.color.*;
import org.andengine.util.math.*;

import pp.game.*;
import pp.game.handlers.entity.*;
import pp.game.physics.*;
import pp.game.utils.geometry.*;

import com.badlogic.gdx.physics.box2d.*;

public class Bullet extends BaseEntity {
	private static final float LINE_BULLET_RECTANGLE_WIDTH = 0.9f;
	private static final float LINE_BULLET_RECTANGLE_HEIGHT = 
			Game.getGameActivity().getEngine().getCamera().getHeight() / 44f;
	private static final float CIRCLE_BULLET_RECTANGLE_WIDTH = 2f;
	private static final float CIRCLE_BULLET_RECTANGLE_HEIGHT = 2f;
	
	private Body body;
	private IAreaShape shape;
	private WeaponType weaponType;
	
	private Bullet() {
		
	}
	
	private Bullet(WeaponType type, IAreaShape shape, Body body) {
		this.weaponType = type;
		this.shape = shape;
		this.body = body;
	}	
	
	static Bullet getBullet(WeaponType type, float xOffset, float yOffset) {
		Player player = Player.getInstance();
		Bullet bullet = new Bullet();
		bullet.weaponType = type;

		Body body = null;
		switch (type) {
		case AK_47:
		case FLAMETHROWER:
		case LASER:
		case M_32:
		case REMINGTON:
		case UZI:
		case PM:
			bullet.shape = new Rectangle(0, 0, 
					LINE_BULLET_RECTANGLE_WIDTH, LINE_BULLET_RECTANGLE_HEIGHT, 
					Game.getGameActivity().getVertexBufferObjectManager());
			break;
		case SHOTGUN:
			bullet.shape = new Rectangle(0, 0, 
					CIRCLE_BULLET_RECTANGLE_WIDTH, CIRCLE_BULLET_RECTANGLE_HEIGHT, 
					Game.getGameActivity().getVertexBufferObjectManager());
			break;
		}
		Sprite sprite = player.getWeapon().getSprite();
		float xCenter = sprite.getX() + sprite.getRotationCenterX();
		float yCenter = sprite.getY() + sprite.getRotationCenterY();
		float[] vertex = { xCenter + xOffset, sprite.getY() + yOffset };
		MathUtils.rotateAroundCenter(vertex, sprite.getRotation(), xCenter, yCenter);
		
		switch (player.getWeapon().getWeaponBonus()) {
		case BULLETS:
			bullet.shape.setColor(new Color(0, 1, 1));
			break;
		default:
			bullet.shape.setColor(new Color(1, 1, 1, 0.6f));
			break;
		}
		
		bullet.shape.setPosition(vertex[0], vertex[1] - LINE_BULLET_RECTANGLE_HEIGHT / 2f);
		bullet.shape.setRotation(player.getWeapon().getSprite().getRotation());
		bullet.shape.registerUpdateHandler(new BulletUpdateHandler(bullet));
		body = PhysicsManager.getInstance().createBody(bullet);		
		bullet.body = body;
		
		return SceneLayoutUtils.adjustBullet(bullet);
	}
	
	public static Bullet getBullet(WeaponType type) {
		return getBullet(type, 0, 0);
	}
	
	public float getDamage() {
		return weaponType.getDamage();
	}
	
	@Override
	public EntityType getEntityType() {
		return EntityType.BULLET;
	}
	
	@Override
	public Body getBody() {
		return body;
	}
	
	@Override
	public IAreaShape getShape() {
		return shape;
	}

	public WeaponType getWeaponType() {
		return weaponType;
	}
}
