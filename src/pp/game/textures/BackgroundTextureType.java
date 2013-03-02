package pp.game.textures;

public enum BackgroundTextureType {
	BASE_0("textures/background/base_0.png", 256, 256),
	BASE_1("textures/background/base_1.png", 256, 256),
	BASE_2("textures/background/base_2.png", 256, 256),
	BASE_3("textures/background/base_3.png", 256, 256);
	
	private String assetPath;
	private float width;
	private float height;
	
	private BackgroundTextureType(String assetPath, float width, float height) {
		this.assetPath = assetPath;
		this.width = width;
		this.height = height;
	}

	public String getAssetPath() {
		return assetPath;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
}
