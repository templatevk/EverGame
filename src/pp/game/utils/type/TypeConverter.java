package pp.game.utils.type;

import pp.game.audio.*;
import pp.game.entities.*;
import pp.game.level.*;
import pp.game.textures.*;

public class TypeConverter {
	private TypeConverter() {
		
	}
	
	public static MonsterWalkTextureType getMonsterWalkTextureType(MonsterType type) {
		return MonsterWalkTextureType.valueOf(type.toString());
	}
	
	public static MonsterDeathTextureType getMonsterDeathTextureType(MonsterType type) {
		return MonsterDeathTextureType.valueOf(type.toString());
	}
	
	public static WeaponTextureType getWeaponTextureType(WeaponType type) {
		return WeaponTextureType.valueOf(type.toString());
	}
	
	public static EntityHitSoundType getEntityHitSoundType(MonsterType type) {
		return EntityHitSoundType.valueOf(type.toString());
	}
	
	public static WeaponReloadSoundType getWeaponReloadSoundType(WeaponType type) {
		return WeaponReloadSoundType.valueOf(type.toString());
	}
	
	public static WeaponShotSoundType getWeaponShotSoundType(WeaponType type) {
		return WeaponShotSoundType.valueOf(type.toString());
	}
	
	public static BulletTextureType getBulletTextureType(WeaponType type) {
		return BulletTextureType.valueOf(type.toString());
	}	
	
	public static BonusTextureType getBonusTextureType(BonusType type) {
		return BonusTextureType.valueOf(type.toString());
	}
	
	public static ConfigLevelType getConfigLevelType(LevelType type) {
		switch (type) {
		case TEST_LOW_HP_ONE_MONSTER:
			return ConfigLevelType.TEST_LOW_HP;
		case TEST_HIGH_HP_ONE_MONSTER:
		case TEST_HIGH_HP_MANY_MONSTERS:
		case TEST_BONUS_SPAWN:
			return ConfigLevelType.TEST_NO_MONSTERS;
		}
		return ConfigLevelType.valueOf(type.toString());
	}
}
