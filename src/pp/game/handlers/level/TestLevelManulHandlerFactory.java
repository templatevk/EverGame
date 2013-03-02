package pp.game.handlers.level;

import pp.game.entities.*;
import pp.game.handlers.*;
import pp.game.level.*;
import pp.game.scene.*;
import pp.game.utils.concurrent.*;

public class TestLevelManulHandlerFactory {
	private TestLevelManulHandlerFactory() {
		
	}
	
	private static class LevelHandler extends DelayedUpdateHandler implements ILevelHandler {
		private Runnable runnable;
		
		@Override
		public void onUpdate() {
			runnable.run();
		}

		public void setRunnable(Runnable runnable) {
			this.runnable = runnable;
		}
	}
	
	public static ILevelHandler createHandler(LevelType type) {
		final LevelHandler handler = new LevelHandler();
		handler.setRequiredDelay(0);
		switch (type) {
		case TEST_HIGH_HP_ONE_MONSTER:
		case TEST_LOW_HP_ONE_MONSTER:
			handler.setRunnable(new Runnable() {
				@Override
				public void run() {
					GameScene.getInstance().attachChild(Monster.getMonster(
							MonsterType.SPIDER).getAliveSprite());
					GameScene.getInstance().unregisterUpdateHandler(handler);
				}
			});
			break;
		case TEST_HIGH_HP_MANY_MONSTERS:
			final int MONSTERS_COUNT = 7;
			final MonsterType MONSTER_TYPE = MonsterType.RUNNER;
			handler.setRunnable(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < MONSTERS_COUNT; i++) {
						GameScene.getInstance().attachChild(Monster.getMonster(
								MONSTER_TYPE).getAliveSprite());
					}
					GameScene.getInstance().unregisterUpdateHandler(handler);
				}
			});
			break;
		case TEST_BONUS_SPAWN:
			final float BONUS_SPAWN_DELAY = 1f;
			handler.setRunnable(new Runnable() {
				@Override
				public void run() {
					BonusManager.getInstance().spawnBonus();
				}
			});
			handler.setRequiredDelay(BONUS_SPAWN_DELAY);
			break;
		}
		return handler;
	}
}
