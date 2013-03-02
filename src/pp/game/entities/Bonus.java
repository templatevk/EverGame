package pp.game.entities;

import org.andengine.entity.*;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.*;
import org.andengine.entity.sprite.*;
import org.andengine.util.modifier.*;

import pp.game.*;
import pp.game.physics.*;
import pp.game.textures.*;
import pp.game.utils.geometry.*;
import pp.game.utils.type.*;

import com.badlogic.gdx.physics.box2d.*;

public class Bonus extends BaseEntity {
	private static final float SCALE_MODIFIER_DURATION = 1;
	private static final int LOOP_MIDIFIER_LOOPS_COUNT = 4;
	
	private Body body;
	private Sprite sprite;
	private BonusType type;
	
	private Bonus(BonusType type) {
		this.type = type;
	}
	
	public static Bonus getBonus(BonusType type, IEntity entity) {
		return getBonus(type, entity.getX(), entity.getY());
	}
	
	public static Bonus getBonus(BonusType type, float x, float y) {
		final Bonus bonus = new Bonus(type);
		bonus.sprite = new Sprite(0, 0, TextureHolder.getInstance().getTexture(
				TypeConverter.getBonusTextureType(type)), 
				Game.getGameActivity().getVertexBufferObjectManager());
		bonus.sprite.setPosition(x, y);
		SceneLayoutUtils.adjustBonus(bonus);		
		bonus.body = PhysicsManager.getInstance().createBody(bonus);
		
		final float scale = bonus.getShape().getScaleX();
		SequenceEntityModifier modifier = new SequenceEntityModifier(new IEntityModifierListener() {
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						PhysicsManager.getInstance().removeBody(bonus.body);					
					}
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				
					}
				},
				new LoopEntityModifier(new SequenceEntityModifier(
						new ScaleModifier(SCALE_MODIFIER_DURATION, scale * 0.8f, scale * 1.2f),
						new ScaleModifier(SCALE_MODIFIER_DURATION, scale * 1.2f, scale * 0.8f)
					), LOOP_MIDIFIER_LOOPS_COUNT), 
				new ScaleModifier(SCALE_MODIFIER_DURATION, scale * 0.8f, scale * 0.6f),
				new ScaleModifier(SCALE_MODIFIER_DURATION, scale * 0.6f, scale * 0.4f),
				new ScaleModifier(SCALE_MODIFIER_DURATION, scale * 0.4f, scale * 0.2f),
				new ScaleModifier(SCALE_MODIFIER_DURATION, scale * 0.2f, scale * 0.0f));
		bonus.sprite.registerEntityModifier(modifier);
		
		return bonus;
	}
	
	@Override
	public EntityType getEntityType() {
		return EntityType.BONUS;
	}
	
	@Override
	public Body getBody() {
		return body;
	}
	
	@Override
	public Sprite getShape() {
		return sprite;
	}

	public BonusType getBonusType() {
		return type;
	}
}
