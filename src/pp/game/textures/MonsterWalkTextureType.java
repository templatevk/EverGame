package pp.game.textures;

import pp.game.entities.*;
import pp.game.utils.*;
import pp.game.utils.geometry.*;

public enum MonsterWalkTextureType {
	RUNNER("monsters/runner/walk.png", 24, 6, 4, 0.5f,
			CalcUtils.getMonsterAnimationDuration(MonsterType.RUNNER), 
			new int[] { 7, 15 }),
	ZOMBIE("monsters/zombie/walk.png", 24, 6, 4, 
			CalcUtils.getMonsterAnimationDuration(MonsterType.ZOMBIE), 
			new int[] { 8, 20 }),
	SPIDER("monsters/spider/walk.png", 13, 4, 4, 
			CalcUtils.getMonsterAnimationDuration(MonsterType.SPIDER), 
			new int[] { 0, 4, 8 });

	public static final int MONSTER_TEXTURE_HEIGHT = 128;
	public static final int MONSTER_TEXTURE_WIDTH = 128;
	
	private String assetPath;
	private int tilesCount;
	private int rows;
	private int columns;
	private int animationDuration;
	private int[] stopTiles;
	
	private MonsterWalkTextureType(String assetPath, int tilesCount, int rows,
			int columns, int animationDuration, int[] stopTiles) {
		this.assetPath = SceneLayoutUtils.IS_HD ? 
				new StringBuilder(assetPath).insert(assetPath.lastIndexOf('/') + 1, "hd_").toString()
				: assetPath;
		this.tilesCount = tilesCount;
		this.rows = rows;
		this.columns = columns;
		this.animationDuration = animationDuration;
		this.stopTiles = stopTiles;
	}
	
	private MonsterWalkTextureType(String assetPath, int tilesCount, int rows,
			int columns, float animationDurationAdjustCoef, int animationDuration,
			int[] stopTiles) {
		this(assetPath, tilesCount, rows, columns, (int)(animationDuration 
				/ animationDurationAdjustCoef), stopTiles);
	}

	public String getAssetPath() {
		return assetPath;
	}

	public int getTilesCount() {
		return tilesCount;
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public int getAnimationDuration() {
		return animationDuration;
	}

	public int[] getStopTiles() {
		return stopTiles;
	}
}