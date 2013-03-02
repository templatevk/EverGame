package pp.game.level;

import pp.game.handlers.*;
import pp.game.handlers.level.*;

public interface ILevelInitializer {
	ILevel getLevel();
	ILevelHandler getLevelHandler();
}
