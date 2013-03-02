package pp.game.level;

public enum ConfigLevelType {
	SURVIVAL("levels/survival.lvl"),
	
	TEST_NO_MONSTERS("levels/test/no_monsters.lvl"),
	TEST_LOW_HP("levels/test/low_hp.lvl"),
	TEST_LOW_HP_FREQUENT_SPAWN("levels/test/low_hp_frequent_spawn.lvl"),
	TEST_HIGH_HP_NORMAL_SPAWN("levels/test/high_hp_normal_spawn.lvl");
	
	private String assetPath;
	
	private ConfigLevelType(String assetPath) {
		this.assetPath = assetPath;
	}

	public String getAssetPath() {
		return assetPath;
	}
}
