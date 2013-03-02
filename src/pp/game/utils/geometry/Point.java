package pp.game.utils.geometry;

public class Point {
	public float x;
	public float y;
	
	public Point() {
		
	}
	
	public Point(Point point) {
		this.x = point.x;
		this.y = point.y;
	}
	
	public Point(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
}
