package pp.game.level;

import pp.game.audio.*;
import pp.game.textures.*;

public interface ILevel {
	BackgroundTextureType getBackground();
	GameMusicType getMusic();
	float getInitialPlayerHP();
	float getMaxPlayerHP();
}
