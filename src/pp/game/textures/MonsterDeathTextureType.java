package pp.game.textures;

import pp.game.utils.geometry.*;

public enum MonsterDeathTextureType {
	RUNNER("monsters/runner/death.png", 24, 6, 4, 40),
	ZOMBIE("monsters/zombie/death.png", 12, 3, 4, 80),
	SPIDER("monsters/spider/death.png", 23, 6, 4, 40);
	
	private String assetPath;
	private int tilesCount;
	private int rows;
	private int columns;
	private int animationDuration;
	
	private MonsterDeathTextureType(String assetPath, int tilesCount, int rows,
			int columns, int animationDuration) {
		this.assetPath = SceneLayoutUtils.IS_HD ? 
				new StringBuilder(assetPath).insert(assetPath.lastIndexOf('/') + 1, "hd_").toString()
				: assetPath;
		this.tilesCount = tilesCount;
		this.rows = rows;
		this.columns = columns;
		this.animationDuration = animationDuration;
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
}