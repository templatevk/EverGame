package pp.game.utils;

import java.util.*;

import org.andengine.util.math.*;

import pp.game.entities.*;
import pp.game.level.*;
import pp.game.textures.*;
import pp.game.utils.geometry.*;

public class CalcUtils {
	public static final float COORD_ADJUST_COEF = 1000;
	
	public static <T extends Comparable<T>> T getGreaterOrEqual(Collection<T> collection, T element) {
		for (T t : collection) {
			if (t.compareTo(element) == 0) {
				return t;
			}
		}
		return collection.iterator().next();
	}
	
	public static <T extends Comparable<T>> T getGreaterOrEqual(T[] array, T element) {
		for (T t : array) {
			if (t.compareTo(element) == 0) {
				return t;
			}
		}
		return array[0];
	}
	
	public static int getGreaterOrEqual(int[] array, int element) {
		for (int t : array) {
			if (t == element) {
				return t;
			}
		}
		return array[0];
	}
	
	public static int getMonsterAnimationDuration(MonsterType type) {
		return (int)(SingleTiledTextureType.PLAYER_WALK.getAnimationDuration()
				* (SceneLayoutUtils.DEFAULT_PLAYER_SPEED / type.getWalkSpeed()));
	}
}
