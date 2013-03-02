package pp.game.level;

import java.io.*;
import java.util.*;

import pp.game.*;
import pp.game.audio.*;
import pp.game.entities.*;
import pp.game.handlers.level.*;
import pp.game.textures.*;
import android.util.*;

class ConfigLevelInitializer implements ILevelInitializer {
private static final String SEPARATOR = ",";
	
	private static final String BACKGROUND = "background";
	private static final String MUSIC = "music";
	private static final String PLAYER_HP_INITIAL = "player.hp.initial";
	private static final String PLAYER_HP_MAX = "player.hp.max";
	private static final String MONSTERS_TYPES = "monsters.types";
	private static final String MONSTERS_SPAWN_INTERVALS = "monsters.spawn.interval";
	private static final String MONSTERS_SPAWN_INTERVAL_DECREMENTS = "monsters.spawn.interval.decrement";
	
	private Properties props;
	
	public ConfigLevelInitializer(ConfigLevelType type) {
		props = new Properties();
		try {
			props.load(Game.getGameActivity().getAssets().open(type.getAssetPath()));
		} catch (IOException e) {
			Log.e("", "Error creating level " + type, e);
		}
	}
	
	private float[] convertToFloat(String property) {
		String value = props.getProperty(property);
		if (value == null) {
			return new float[0];
		}
		
		String[] valuesStr = value.split(SEPARATOR);
		float[] valuesFloat = new float[valuesStr.length];
		for (int i = 0; i < valuesFloat.length; i++) {
			valuesFloat[i] = Float.valueOf(valuesStr[i]);
		}
		return valuesFloat;
	}
	
	private MonsterType[] convertToMonsterType(String property) {
		String value = props.getProperty(property);
		if (value == null) {
			return new MonsterType[0];
		}
		
		String[] valuesStr = value.split(SEPARATOR);
		MonsterType[] valuesMonsterType = new MonsterType[valuesStr.length];
		for (int i = 0; i < valuesMonsterType.length; i++) {
			valuesMonsterType[i] = MonsterType.valueOf(valuesStr[i]);
		}
		return valuesMonsterType;
	}
	
	private GameMusicType getGameMusicType() {
		return GameMusicType.valueOf(props.getProperty(MUSIC));
	}
	
	private float[] getSpawnIntervals() {
		return convertToFloat(MONSTERS_SPAWN_INTERVALS);
	}
	
	private float[] getSpawnIntervalsDecrements() {
		return convertToFloat(MONSTERS_SPAWN_INTERVAL_DECREMENTS);
	}
	
	private MonsterType[] getMonstersTypes() {
		return convertToMonsterType(MONSTERS_TYPES);
	}
	
	private BackgroundTextureType getBackground() {
		return BackgroundTextureType.valueOf(props.getProperty(BACKGROUND));
	}
	
	private float getInitialPlayerHP() {
		String hp = props.getProperty(PLAYER_HP_INITIAL);
		return hp == null ? 0 : Float.valueOf(hp);
	}
	
	private float getMaxPlayerHP() {
		String hp = props.getProperty(PLAYER_HP_MAX);
		return hp == null ? 0 : Float.valueOf(hp);
	}
	
	@Override
	public ILevel getLevel() {
		BasicLevel level = new BasicLevel();
		level.setBackgroundTextureType(getBackground());
		level.setGameMusicType(getGameMusicType());
		level.setInitialPlayerHP(getInitialPlayerHP());
		level.setMaxPlayerHP(getMaxPlayerHP());
		return level;
	}
	
	@Override
	public ILevelHandler getLevelHandler() {
		return new BasicLevelHandler(getMonstersTypes(),
			getSpawnIntervals(), getSpawnIntervalsDecrements());
	}
}
