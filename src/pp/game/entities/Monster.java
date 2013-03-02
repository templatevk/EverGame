package pp.game.entities;

import java.util.*;

import org.andengine.engine.handler.*;
import org.andengine.entity.sprite.*;
import org.andengine.opengl.vbo.*;

import pp.game.*;
import pp.game.handlers.*;
import pp.game.physics.*;
import pp.game.scene.*;
import pp.game.textures.*;
import pp.game.utils.concurrent.*;
import pp.game.utils.geometry.*;
import pp.game.utils.type.*;

public class Monster extends DieableEntity {
	private static final float DEAD_SPRITE_REMOVE_DELAY = 10f;
	
	private MonsterType type;
	private MonsterDeathTextureType deathTextureType;
	private IUpdateHandler handler;
	private long[] deathAnimationDurations;
	
	private Monster(MonsterType type) {
		this.type = type;
		deathTextureType = TypeConverter.getMonsterDeathTextureType(type);
		deathAnimationDurations = new long[deathTextureType.getTilesCount()];
		Arrays.fill(deathAnimationDurations, deathTextureType.getAnimationDuration());        
	}
	
	public static Monster getMonster(MonsterType type) {		
		VertexBufferObjectManager vertexManager = Game.getGameActivity()
				.getVertexBufferObjectManager();
		TextureHolder holder = TextureHolder.getInstance();
		Monster monster = new Monster(type);
		
		monster.setAliveSprite(new AnimatedSprite(0, 0, holder.getTiledTexture(
				TypeConverter.getMonsterWalkTextureType(type)), vertexManager));
		monster.setDeadSprite(new AnimatedSprite(0, 0, holder.getTiledTexture(
				TypeConverter.getMonsterDeathTextureType(type)), vertexManager));
		SceneLayoutUtils.adjustMonster(monster);
		
		monster.setAliveBody(PhysicsManager.getInstance().createBody(monster));
		monster.initHandler();		
		monster.setMaxHP(type.getHP());
		monster.setCurrentHP(type.getHP());
		
		return monster;
	}
	
	private void initHandler() {
		handler = HandlerFactory.getMonsterHandler(this);
		getAliveSprite().registerUpdateHandler(handler);
	}

	public MonsterType getMonsterType() {
		return type;
	}
	
	@Override
	public AnimatedSprite getAliveSprite() {
		return (AnimatedSprite)super.getAliveSprite();
	}
	
	@Override
	public AnimatedSprite getDeadSprite() {
		return (AnimatedSprite)super.getDeadSprite();
	}
	
	@Override
	public EntityType getEntityType() {
		return EntityType.MONSTER;
	}

	@Override
	public void adjustCurrentHP(float value) {
		if (value < 0) {
			Sprite bloodSprite = new Sprite(getDeadSprite().getX(), getDeadSprite().getY(),
					TextureHolder.getInstance().getTexture(SingleTextureType.getRandomBlood()),
					Game.getGameActivity().getVertexBufferObjectManager());
			SceneLayoutUtils.adjustBloodSprite(bloodSprite);
			bloodSprite.setPosition(getAliveSprite());
			GameScene.getInstance().attachChild(bloodSprite);
		}
		super.adjustCurrentHP(value);
	}
	
	@Override
	public void die() {
		Game.getGameInstance().addScore(type.getScorePoints());
		BonusManager.getInstance().onMonsterDeath(this);
		
		PhysicsManager manager = PhysicsManager.getInstance();
		final IGameScene scene = GameScene.getInstance();
		
		getDeadSprite().setRotation(getAliveSprite().getRotation());
		getDeadSprite().setPosition(getAliveSprite());
		getDeadSprite().animate(deathAnimationDurations, false);
		
		manager.removeBody(getAliveBody());
		ConcurrentUtils.removeHandlerFromEntity(getAliveSprite(), handler);
		
		scene.attachChild(getDeadSprite());
		
		getDeadSprite().registerUpdateHandler(new DelayedUpdateHandler(DEAD_SPRITE_REMOVE_DELAY) {			
			@Override
			protected void onUpdate() {
				getDeadSprite().unregisterUpdateHandler(this);
				scene.detachChild(getDeadSprite());
			}
		});
	}
}
