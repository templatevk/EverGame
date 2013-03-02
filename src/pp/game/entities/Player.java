package pp.game.entities;

import java.util.*;

import org.andengine.entity.sprite.*;
import org.andengine.opengl.vbo.*;

import pp.game.*;
import pp.game.audio.*;
import pp.game.handlers.*;
import pp.game.handlers.entity.*;
import pp.game.level.*;
import pp.game.observers.*;
import pp.game.physics.*;
import pp.game.scene.*;
import pp.game.textures.*;
import pp.game.utils.geometry.*;

public class Player extends DieableEntity implements IPreparable, IResetable, IDieableObservable {	
	private static final Player INTANCE = new Player();
	
	private AnimatedSprite legsSprite;
	private IGameScene scene;
	private Weapon weapon;
	private float playerSpeed;
	private Set<IObserver<IDieableEntity>> observers = new HashSet<IObserver<IDieableEntity>>();
	
	private class PlayerUpdateHandler extends DieableUpdateHandler {		
		public PlayerUpdateHandler() {
			setEntities(Player.this);
			setCommands(new IHandlerCommand() {
				@Override
				public void execute() {
					GameScene.getInstance().unregisterUpdateHandler(
							PlayerUpdateHandler.this);
				}
			});
		}
		
		@Override
		protected void onUpdate() {
			weapon.getSprite().setPosition(getAliveSprite());
			legsSprite.setPosition(getAliveSprite());
		}
	}
	
	private Player() {	
		Game.getGameInstance().addPreparable(this);
		
		TextureHolder holder = TextureHolder.getInstance();
		VertexBufferObjectManager vertexManager = Game.getGameActivity()
				.getVertexBufferObjectManager();
		
		setAliveSprite(new Sprite(0, 0, holder.getTexture(SingleTextureType.STUB), 
				vertexManager));
		getAliveSprite().setAlpha(0);
		setDeadSprite(new AnimatedSprite(0, 0, holder.getTiledTexture(
				SingleTiledTextureType.PLAYER_DEATH), vertexManager));
		
		legsSprite = new AnimatedSprite(0, 0, holder.getTiledTexture(
				SingleTiledTextureType.PLAYER_WALK), vertexManager);
		legsSprite.setCurrentTileIndex(((int[])((Object[])
				SingleTiledTextureType.PLAYER_WALK.getUserData())[0])[0]);
	}	
	
	public static Player getInstance() {
		return INTANCE;
	}
	
	private void notifyObservers() {
		for (IObserver<IDieableEntity> observer : observers) {
			observer.onChanged(this);
		}
	}
	
	@Override
	public AnimatedSprite getDeadSprite() {
		return (AnimatedSprite)super.getDeadSprite();
	}
	
	@Override
	public void prepare(ILevel level) {		
		PhysicsManager physicsManager = PhysicsManager.getInstance();
		float x = SceneLayoutUtils.BACKGROUND_MAX_X / 2f;
		float y = SceneLayoutUtils.BACKGROUND_MAX_Y / 2f;
		scene = GameScene.getInstance();	
		
		getAliveSprite().setPosition(x, y);
		getAliveSprite().setRotation(0);
		SceneLayoutUtils.adjustPlayer(this);	
		
		super.setCurrentHP(level.getInitialPlayerHP());
		setMaxHP(level.getMaxPlayerHP());
		setIdDead(false);
		setPlayerSpeed(SceneLayoutUtils.DEFAULT_PLAYER_SPEED);
		
		setAliveBody(physicsManager.createBody(this));
		
		setWeapon(Weapon.getWeapon(WeaponType.PM));
		
		Game.getGameActivity().getEngine().getCamera().setChaseEntity(getAliveSprite());
			
		scene.attachChild(getAliveSprite());
		scene.attachChild(legsSprite);
		scene.registerUpdateHandler(new PlayerUpdateHandler());
	}
	
	@Override
	public void reset() {
		if (!isDead()) {
			PhysicsManager.getInstance().removeBody(getAliveBody());
		}
	}
	
	@Override
	public Priority getPriority() {
		return Priority.MEDIUM;
	}
	
	@Override
	public EntityType getEntityType() {
		return EntityType.PLAYER;
	}

	@Override
	public void adjustCurrentHP(float value) {
		super.adjustCurrentHP(value);
		notifyObservers();
	}
	
	@Override
	protected void setCurrentHP(float currentHP) {
		super.setCurrentHP(currentHP);
		notifyObservers();
	}
	
	@Override
	public void setMaxHP(float maxHP) {
		super.setMaxHP(maxHP);
		notifyObservers();
	}
	
	@Override
	public void die() {
		PhysicsManager.getInstance().removeBody(getAliveBody());
		scene.detachChild(weapon.getSprite(), false);
		scene.detachChild(legsSprite);
		
		scene.unregisterUpdateHandlers(new EndGameHandlersMatcher());
		scene.attachChild(getDeadSprite());
		getDeadSprite().setPosition(getAliveSprite());
		
		Game.getGameActivity().getEngine().getCamera().setChaseEntity(getDeadSprite());
		getDeadSprite().animate(SingleTiledTextureType.PLAYER_DEATH.getAnimationDuration(), false);
		
		AudioHolder.getInstance().playSound(SoundType.PLAYER_DEATH);
		scene.setCurrentMusic(GameMusicType.END_GAME);
	}	
	
	@Override
	public void addObservable(IObserver<IDieableEntity> observer) {
		observers.add(observer);
	}

	@Override
	public void removeObservable(IObserver<IDieableEntity> observer) {
		observers.remove(observer);
	}

	public void setWeapon(Weapon newWeapon) {
		if (this.weapon != null) {
			scene.detachChild(this.weapon.getSprite());
			newWeapon.setWeaponBonus(weapon.getWeaponBonus());
		}
		this.weapon = newWeapon;
		scene.attachChild(SceneLayoutUtils.adjustWeapon(newWeapon).getSprite());
	}

	public Weapon getWeapon() {
		return weapon;
	}
	
	public float getPlayerSpeed() {
		return playerSpeed;
	}

	public void setPlayerSpeed(float playerSpeed) {
		this.playerSpeed = playerSpeed;
	}

	public AnimatedSprite getLegsSprite() {
		return legsSprite;
	}
}
