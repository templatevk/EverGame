package pp.game.level;

import pp.game.handlers.level.*;
import pp.game.utils.type.*;
import android.util.*;

public class LevelFactory {
	public static ILevelInitializer getLevel(LevelType type) {
		try {
			switch (type) {
			case TEST_HIGH_HP_MANY_MONSTERS:
			case TEST_HIGH_HP_ONE_MONSTER:
			case TEST_LOW_HP_ONE_MONSTER:
			case TEST_BONUS_SPAWN:
				return new TestLevelInitializer(TypeConverter.getConfigLevelType(type), 
						TestLevelManulHandlerFactory.createHandler(type));			
			case TEST_LOW_HP_FREQUENT_SPAWN:
			case TEST_NO_MONSTERS:
			case TEST_HIGH_HP_NORMAL_SPAWN:
				return new TestLevelInitializer(TypeConverter.getConfigLevelType(type)); 
			case SURVIVAL:
				return new ConfigLevelInitializer(TypeConverter.getConfigLevelType(type));
			}
		} catch (Exception e) { 
			RuntimeException re = new RuntimeException("Error initializing level " + type, e);
			Log.e("", "", re);
			throw re;
		}
		return null;
	}
}
