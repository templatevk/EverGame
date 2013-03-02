package pp.game.entities;

import pp.game.utils.geometry.*;

public enum MonsterType {
	RUNNER(100, SceneLayoutUtils.DEFAULT_PLAYER_SPEED * 0.50f, 5, 0.5f,		10), 
	ZOMBIE(250, SceneLayoutUtils.DEFAULT_PLAYER_SPEED * 0.17f, 15, 1,		25), 
	SPIDER(150, SceneLayoutUtils.DEFAULT_PLAYER_SPEED * 0.33f, 10, 0.75f,	15);
	
	private float HP;
	private float walkSpeed;
	private float damage;
	private float attackSpeed;		// in seconds
	private int scorePoints;
	
	private MonsterType(float hP, float walkSpeed, float damage,
			float attackSpeed, int scorePoints) {
		HP = hP;
		this.walkSpeed = walkSpeed;
		this.damage = damage;
		this.attackSpeed = attackSpeed;
		this.scorePoints = scorePoints;
	}

	public float getHP() {
		return HP;
	}

	public float getWalkSpeed() {
		return walkSpeed;
	}

	public float getDamage() {
		return damage;
	}

	public float getAttackSpeed() {
		return attackSpeed;
	}

	public int getScorePoints() {
		return scorePoints;
	}
}
