package pp.game.entities;

import java.util.*;

import pp.game.*;
import pp.game.handlers.*;
import pp.game.scene.*;
import pp.game.utils.geometry.*;

public class BonusManager {
	public static final float BONUS_HP_LARGE_VALUE 					= 50f;
	public static final float BONUS_HP_MEDIUM_VALUE					= 35f;
	public static final float BONUS_HP_SMALL_VALUE					= 20f;
	
	private static final float SPEED_BONUS_COEF 						= 1.5f;
	private static final float FRREZE_BONUS_DURATION					= 8f;
	private static final float SPEED_BONUS_DURATION 					= 8f;
	private static final float BULLETS_BONUS_DURATION 				= 8f;
	
	private static final float MONSTER_BONUS_CHANCE 					= 0.23f;
	private static final float MONSTER_BONUS_CHANCE_PLAYER_HAS_PM 	= 0.35f;
	private static final float WEAPON_BONUS_CHANCE_PLAYER_HAS_PM 		= 0.8f;
	private static final float WEAPON_BONUS_CHANCE 					= 0.3f;
	
	private static final BonusManager INSTANCE = new BonusManager();
	
	private Random rand = new Random();
	private Player player = Player.getInstance();
	private IGameScene scene = GameScene.getInstance();
	private List<BonusType> weaponBonusTypes;
	private List<BonusType> otherBonusTypes;
	private Set<PickedBonus> pickedBonuses;
	
	private class PickedBonus {
		int id;
		BonusType type;
		
		@Override
		public boolean equals(Object o) {
			PickedBonus bonus = (PickedBonus)o;
			return id == bonus.id;
		}
	}
	
	private BonusManager() {
		weaponBonusTypes = new ArrayList<BonusType>();
		weaponBonusTypes.add(BonusType.AK_47);
		weaponBonusTypes.add(BonusType.UZI);
		weaponBonusTypes.add(BonusType.SHOTGUN);
		
		otherBonusTypes = new ArrayList<BonusType>();
		otherBonusTypes.add(BonusType.HP_LARGE);
		otherBonusTypes.add(BonusType.HP_MEDIUM);
		otherBonusTypes.add(BonusType.HP_SMALL);
		otherBonusTypes.add(BonusType.SPEED);
		otherBonusTypes.add(BonusType.BULLETS);
	}
	
	public static BonusManager getInstance() {
		return INSTANCE;
	}
	
	private boolean proc(float chance) {
		return rand.nextFloat() + chance >= 1;
	}
	
	private BonusType getBonusType() {
		if (player.getWeapon().getWeaponType() == WeaponType.PM) {
			if (!proc(MONSTER_BONUS_CHANCE_PLAYER_HAS_PM)) return BonusType.NONE;
		} else {
			if (!proc(MONSTER_BONUS_CHANCE)) return BonusType.NONE;
		}
				
		boolean isWeaponBonus = player.getWeapon().getWeaponType() == WeaponType.PM
				? proc(WEAPON_BONUS_CHANCE_PLAYER_HAS_PM) : proc(WEAPON_BONUS_CHANCE);
		BonusType resultBonus = BonusType.NONE;
		float maxChance = 0;
		float currentChance;
		List<BonusType> resultBonusTypes = isWeaponBonus ? weaponBonusTypes : otherBonusTypes; 
		for (BonusType type : resultBonusTypes) {
			currentChance = type.getChance() + rand.nextFloat();
			if (currentChance > maxChance) {
				maxChance = currentChance;
				resultBonus = type;
			}
		}
		return resultBonus;
	}
	
	public void spawnBonus() {
		final BonusType type = getBonusType();
		
		if (type == BonusType.NONE) {
			return;
		}
		
		Game.getGameActivity().runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				Bonus bonus = Bonus.getBonus(type, 
						rand.nextInt((int)SceneLayoutUtils.BACKGROUND_MAX_X),
						rand.nextInt((int)SceneLayoutUtils.BACKGROUND_MAX_Y));
				scene.attachChild(bonus.getShape());
			}
		});
	}
	
	public void onMonsterDeath(final Monster monster) {
		final BonusType type = getBonusType();
		
		if (type == BonusType.NONE) {
			return;
		}
		
		Game.getGameActivity().runOnUpdateThread(new Runnable() {
			@Override
			public void run() {
				Bonus bonus = Bonus.getBonus(type, monster.getShape());
				scene.attachChild(bonus.getShape());
			}
		});
	}
	
	public void onBonusPick(BonusType type) {
		switch (type) {
		case AK_47:
		case FLAMETHROWER:
		case LASER:
		case SHOTGUN:
		case UZI:
			player.setWeapon(Weapon.getWeapon(WeaponType.valueOf(
					bonus.getBonusType().toString())));
			break;
		case HP_LARGE:
			player.adjustCurrentHP(BonusManager.BONUS_HP_LARGE_VALUE);
			break;
		case HP_MEDIUM:
			player.adjustCurrentHP(BonusManager.BONUS_HP_MEDIUM_VALUE);
			break;
		case HP_SMALL:
			player.adjustCurrentHP(BonusManager.BONUS_HP_SMALL_VALUE);
			break;
		case SPEED:
			player.setPlayerSpeed(SceneLayoutUtils.DEFAULT_PLAYER_SPEED * SPEED_BONUS_COEF);
			GameScene.getInstance().registerUpdateHandler(
					new DelayedUpdateHandler(SPEED_BONUS_DURATION) {
				@Override
				protected void onUpdate() {
					GameScene.getInstance().unregisterUpdateHandler(this);
					player.setPlayerSpeed(SceneLayoutUtils.DEFAULT_PLAYER_SPEED);
				}
			});
			break;
		case BULLETS:
			player.getWeapon().setWeaponBonus(BonusType.BULLETS);
			GameScene.getInstance().registerUpdateHandler(
					new DelayedUpdateHandler(BULLETS_BONUS_DURATION) {
				@Override
				protected void onUpdate() {
					GameScene.getInstance().unregisterUpdateHandler(this);
					player.getWeapon().setWeaponBonus(BonusType.NONE);
				}
			});
			break;
		}
	}
}
