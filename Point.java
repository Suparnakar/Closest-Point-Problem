package kar.suparna;

import java.util.Objects;

public class Point {
	private double x, y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {return x;}
	public void setX(double x) {this.x = x;}
	public double getY() {return y;}
	public void setY(double y) {this.y = y;}
	
	//For finding distance between 2 points
	
	public double findDistance(Point other) {
		return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
	}
	
	// For printing the point
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}

	// For comparing  2 points to see if they are equal
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		return x == other.x && y == other.y;
	}
}