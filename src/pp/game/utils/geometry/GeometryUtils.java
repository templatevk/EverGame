package pp.game.utils.geometry;

import org.andengine.util.math.*;

import pp.game.utils.*;

public class GeometryUtils {
	public static float getRotation(Point direction) {
		float x = direction.x;
		float y = direction.y;
		x *= CalcUtils.COORD_ADJUST_COEF;
		y *= CalcUtils.COORD_ADJUST_COEF;
		double b = Math.abs(x);
		double c = MathUtils.distance(0, 0, x, y);
		float cornerL = (float)Math.toDegrees(Math.asin(Math.abs(b / c)));
		if (x >= 0 && y >= 0) {
			cornerL = 180 - cornerL;
		} else if (x < 0 && y >= 0) {
			cornerL += 180;
		} else if (x < 0 && y < 0) {
			cornerL = 360 - cornerL;
		}
		return cornerL;
	}
	
	public static Point convertToDirection(Point to) {
		return convertToDirection(new Point(0, 0), to, null);
	}
	
	public static Point convertToDirection(Point from, Point to) {
		return convertToDirection(from, to, null);
	}
	
	public static Point convertToDirection(Point from, Point to, Point diffAbsDst) {
		float dX = to.x - from.x;
		float dY = to.y - from.y;
		float xAbs = Math.abs(dX);
		float yAbs = Math.abs(dY);
		
		if (diffAbsDst != null) {
			diffAbsDst.x = xAbs;
			diffAbsDst.y = yAbs;
		}
		
		if (xAbs > yAbs) {
			dY /= xAbs;
			dX = Math.signum(dX);
		} else {
			dX /= yAbs;
			dY = Math.signum(dY);
		}
		
		return new Point(dX, dY);
	}
}
