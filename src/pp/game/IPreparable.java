package pp.game;

import pp.game.level.*;

public interface IPreparable extends IPrioritized {
	void prepare(ILevel level);
}
