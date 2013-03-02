package pp.game.handlers.contact;

import pp.game.audio.*;
import pp.game.entities.*;
import pp.game.handlers.*;
import pp.game.scene.*;

public class PlayerMonsterContactUpdateHandler extends DelayedUpdateHandler implements IContactHandler {
	private Monster monster;
	private Player player = Player.getInstance();
	
	public PlayerMonsterContactUpdateHandler(Monster monster) {
		this.monster = monster;
		setRequiredDelay(monster.getMonsterType().getAttackSpeed());
		hitPlayer();
	}
			
	private void hitPlayer() {
		if (!player.isDead() && !monster.isDead()) {
			if (player.getAliveSprite().collidesWith(monster.getAliveSprite())) {
				player.adjustCurrentHP(-monster.getMonsterType().getDamage());
				AudioHolder.getInstance().playEntityHitSound(
						EntityHitSoundType.getRandomPlayerSound());
			} else {
				GameScene.getInstance().unregisterUpdateHandler(this);
			}
		}
	}
	
	@Override
	protected void onUpdate() {
		hitPlayer();
	}
}
