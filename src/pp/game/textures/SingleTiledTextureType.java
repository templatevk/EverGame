package pp.game.textures;

import org.andengine.util.debug.*;

import pp.game.utils.geometry.*;

public enum SingleTiledTextureType {
	PLAYER_WALK("player/walk.png", 25, 7, 4, 40, 
			new Object[] {
				new int[] { // TILES FOR STOP
					0, 12
				}, new int[] { // TILES FOR WALK
					23, 24, 0, 1, 2, 10, 11, 12, 13, 14
				}
			}),
	PLAYER_DEATH("player/death.png", 56, 7, 8, 100, null);

	private String assetPath;
	private int tilesCount;
	private int rows;
	private int columns;
	private int animationDuration;
	private Object userData;
	
	private SingleTiledTextureType(String assetPath, int tilesCount, 
			int rows, int columns, int animationDuration, Object userData) {
		this.assetPath = SceneLayoutUtils.IS_HD ? 
				new StringBuilder(assetPath).insert(assetPath.lastIndexOf('/') + 1, "hd_").toString()
				: assetPath;
		this.tilesCount = tilesCount;
		this.rows = rows;
		this.columns = columns;
		this.animationDuration = animationDuration;
		this.userData = userData;
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

	public Object getUserData() {
		return userData;
	}
}
