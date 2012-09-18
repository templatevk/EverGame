package pp.game.gameplay;

import java.util.*;

import android.util.*;

/*
 * Base class that represents abstract factory for producing
 * textures, monters, bonuses, etc
 */
public abstract class AbstractLevelFactory {
	private static HashMap<String, Class<? extends AbstractLevelFactory>> levels;
	
	static {
		levels = new HashMap<String, Class<? extends AbstractLevelFactory>>();
		levels.put("Survival", SurvivalLevelFactory.class);
	}
	
	protected AbstractLevelFactory() {
		
	}
	
	public static AbstractLevelFactory getLevel(String name) {
		AbstractLevelFactory level = null;
		try {
			level = levels.get(name).newInstance();
		} catch (Exception e) {
			Log.e("AbstractLevelFactory", "Error instantiating level " + name, e);
		}
		return level;
	}
	
	public abstract String getBackgroundPicture();
}
