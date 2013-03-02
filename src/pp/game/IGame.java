package pp.game;

import java.util.*;

import pp.game.level.*;
import pp.game.observers.*;

public interface IGame extends IObservable<Integer> {
	void start(ILevelInitializer level);
	void addScore(int score);
	int getCurrentScore();
	void pause();
	void resume();
	void stop();
	void exit();
	void addPreparable(IPreparable preparable);
	void removePreparable(IPreparable preparable);
	void addDestroyable(IDestroyable destroyable);
	void removeDestroyable(IDestroyable destroyable);
	void addPauseable(IPausable pausable);
	void removePauseable(IPausable pausable);
	void addResetable(IResetable resetable);
	void removeResetable(IResetable resetable);
	List<Integer> getHighScores();
}
