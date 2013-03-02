package pp.game.handlers.entity;

import org.andengine.entity.sprite.*;

import pp.game.entities.*;
import pp.game.handlers.*;
import pp.game.textures.*;
import pp.game.utils.*;
import pp.game.utils.geometry.*;
import pp.game.utils.type.*;

public class BasicMonsterUpdateHandler extends DieableUpdateHandler implements IMonsterUpdateHandler {
	private static final float MIN_COORD_DIFF = 20f;
	
	private Player player;
	private Monster monster;
	private AnimatedSprite aliveSprite;
	private MonsterWalkTextureType monsterWalkType;
	private long[] durations;
	
	private class BasicMonsterHandlerCommand implements IHandlerCommand {
		public BasicMonsterHandlerCommand() {
			
		}		
		
		@Override
		public void execute() {				
			if (!monster.isDead()) {				
				Point reverseDirection = GeometryUtils.convertToDirection(
							new Point(aliveSprite.getX(), aliveSprite.getY()), 
							new Point(player.getShape().getX(), player.getShape().getY()));
				Point direction = new Point(-reverseDirection.x, -reverseDirection.y);
				monster.getAliveSprite().setRotation(monster.getAliveSprite().getRotation() + 180);
				animateAndRun(direction);
			}
		}
	}
	
	public BasicMonsterUpdateHandler(Monster monster) {
		setEntities(monster, Player.getInstance());
		setCommands(new BasicMonsterHandlerCommand());
		
		this.monster = monster;
		aliveSprite = monster.getAliveSprite();
		player = Player.getInstance();
		
		monsterWalkType = TypeConverter.getMonsterWalkTextureType(monster.getMonsterType());
		durations = new long[monsterWalkType.getTilesCount()];
		for (int i = 0; i < durations.length; i++) {
			durations[i] = monsterWalkType.getAnimationDuration();
		}
	}
	
	private void animateAndRun(final Point direction) {
		if (!monster.getAliveSprite().isAnimationRunning()) {
			monster.getAliveSprite().animate(durations, true);
		}
		monster.getBody().setLinearVelocity(monster.getMonsterType().getWalkSpeed() 
				* direction.x, monster.getMonsterType().getWalkSpeed() * direction.y);
	}
	
	@Override
	protected void onUpdate() {
		Point diffAbs = new Point();
		Point direction = GeometryUtils.convertToDirection(
				new Point(aliveSprite.getX(), aliveSprite.getY()), 
				new Point(player.getShape().getX(), player.getShape().getY()),
				diffAbs);
		aliveSprite.setRotation(GeometryUtils.getRotation(direction));

		if ((diffAbs.x + diffAbs.y) / 2 > MIN_COORD_DIFF) {
			animateAndRun(direction);
		} else {
			monster.getAliveSprite().stopAnimation();
			monster.getAliveSprite().setCurrentTileIndex(
					CalcUtils.getGreaterOrEqual(monsterWalkType.getStopTiles(), 
					monster.getAliveSprite().getCurrentTileIndex()));
			monster.getBody().setLinearVelocity(0, 0);
		}
	}
}
