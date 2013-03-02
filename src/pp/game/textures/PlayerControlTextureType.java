package pp.game.textures;

public enum PlayerControlTextureType {
	MOVE_BASE("textures/controls/onscreen_control_base.png"),
	MOVE_KNOB("textures/controls/onscreen_control_knob.png"),
	SHOOT_BASE("textures/controls/onscreen_control_base.png"),
	SHOOT_KNOB("textures/controls/onscreen_control_knob.png");
	
	private String assetPath;
	
	private PlayerControlTextureType(String assetPath) {
		this.assetPath = assetPath;
	}
	
	public String getAssetPath() {
		return assetPath;
	}
}
