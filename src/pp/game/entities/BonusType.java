package pp.game.entities;

public enum BonusType {
	NONE(0),
	
	AK_47			(0.3f), 
	FLAMETHROWER	(0.3f), 
	LASER			(0.3f), 
	SHOTGUN			(0.3f), 
	UZI				(0.3f),
	
	HP_SMALL		(0.4f),
	HP_MEDIUM		(0.3f),
	HP_LARGE		(0.2f),
	FREEZE			(0.4f),
	SPEED			(0.4f),
	BULLETS			(0.4f);
	
	private float chance;

	private BonusType(float chance) {
		this.chance = chance;
	}

	public float getChance() {
		return chance;
	}
}
