package pp.game.handlers.level;

import pp.game.entities.*;
import pp.game.handlers.*;
import pp.game.scene.*;

public class BasicLevelHandler extends DelayedUpdateHandler implements ILevelHandler {
	private static final float MIN_SPAWN_INTERVAL = 0.3f;
	
	private MonsterType[] monsterTypes;
	private float[] spawnIntervals;
	private float[] currentSpawnIntervals;
	private float[] spawnIntervalsDecrements;
	
	public BasicLevelHandler(MonsterType[] monsterTypes, float[] spawnIntervals,
			float[] spawnIntervalsDecrements) {
		float minRequiredDelay = Integer.MAX_VALUE;
		currentSpawnIntervals = new float[spawnIntervals.length];
		for (int i = 0; i < spawnIntervals.length; i++) {
			if (spawnIntervals[i] < minRequiredDelay) {
				minRequiredDelay = spawnIntervals[i];
			}
			currentSpawnIntervals[i] = spawnIntervals[i];
		}
		setRequiredDelay(minRequiredDelay);
		
		this.monsterTypes = monsterTypes;
		this.spawnIntervals = spawnIntervals;
		this.spawnIntervalsDecrements = spawnIntervalsDecrements;
	}
	
	@Override
	protected void onUpdate() {
		for (int i = 0; i < spawnIntervals.length; i++) {
			currentSpawnIntervals[i] -= getRequiredDelay();
		}
		
		for (int i = 0; i < spawnIntervals.length; i++) {		
			final int fI = i;
			if (spawnIntervals[i] - spawnIntervalsDecrements[i] > MIN_SPAWN_INTERVAL) {
				spawnIntervals[i] -= spawnIntervalsDecrements[i];
				if (spawnIntervals[i] < getRequiredDelay()) {
					setRequiredDelay(spawnIntervals[i]);
				}
			}
			
			if (currentSpawnIntervals[i] <= 0) {
				Monster monster = Monster.getMonster(monsterTypes[fI]);
				GameScene.getInstance().attachChild(monster.getAliveSprite());
				currentSpawnIntervals[i] = spawnIntervals[i];
			}
		}
	}

	public MonsterType[] getMonsterTypes() {
		return monsterTypes;
	}

	public void setMonsterTypes(MonsterType[] monsterTypes) {
		this.monsterTypes = monsterTypes;
	}

	public float[] getSpawnIntervals() {
		return spawnIntervals;
	}

	public void setSpawnIntervals(float[] spawnIntervals) {
		this.spawnIntervals = spawnIntervals;
	}

	public float[] getCurrentSpawnIntervals() {
		return currentSpawnIntervals;
	}

	public void setCurrentSpawnIntervals(float[] currentSpawnIntervals) {
		this.currentSpawnIntervals = currentSpawnIntervals;
	}

	public float[] getSpawnIntervalsDecrements() {
		return spawnIntervalsDecrements;
	}

	public void setSpawnIntervalsDecrements(float[] spawnIntervalsDecrements) {
		this.spawnIntervalsDecrements = spawnIntervalsDecrements;
	}
}
