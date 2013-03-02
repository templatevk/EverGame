package pp.game.level;

import pp.game.audio.*;
import pp.game.textures.*;

class BasicLevel implements ILevel {
	private static final float DEFAULT_INITIAL_PLAYER_HP = 100;
	private static final float DEFAULT_MAX_PLAYER_HP = 100;
	
	private BackgroundTextureType backgroundTextureType;
	private GameMusicType gameMusicType;
	private float initialPlayerHP;
	private float maxPlayerHP;
	
	public BasicLevel() {
		
	}
	
	public BasicLevel(BackgroundTextureType backgroundTextureType, 
			GameMusicType gameMusicType, float initialPlayerHP, float maxPlayerHP) {
		this.backgroundTextureType = backgroundTextureType;
		this.gameMusicType = gameMusicType;
		this.initialPlayerHP = initialPlayerHP;
		this.maxPlayerHP = maxPlayerHP;
	}	
	
	@Override
	public BackgroundTextureType getBackground() {
		return backgroundTextureType;
	}
	
	@Override
	public GameMusicType getMusic() {
		return gameMusicType;
	}
	
	@Override
	public float getInitialPlayerHP() {
		return initialPlayerHP == 0 ? DEFAULT_INITIAL_PLAYER_HP : initialPlayerHP;
	}

	@Override
	public float getMaxPlayerHP() {
		return maxPlayerHP == 0 ? DEFAULT_MAX_PLAYER_HP : maxPlayerHP;
	}
	
	void setBackgroundTextureType(BackgroundTextureType backgroundTextureType) {
		this.backgroundTextureType = backgroundTextureType;
	}

	void setInitialPlayerHP(float initialPlayerHP) {
		this.initialPlayerHP = initialPlayerHP;
	}

	void setMaxPlayerHP(float maxPlayerHP) {
		this.maxPlayerHP = maxPlayerHP;
	}

	GameMusicType getGameMusicType() {
		return gameMusicType;
	}

	void setGameMusicType(GameMusicType gameMusicType) {
		this.gameMusicType = gameMusicType;
	}
}
