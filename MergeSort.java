package kar.suparna;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergeSort {
//	private static Point[] pointValues = {new Point(20, 30), new Point(120, 300), new Point(400, 500),
//			new Point(500, 10), new Point(120, 100), new Point(30, 40)};			// TESTING
	
//	public static void main(String... args) {
//		List<Point> points = new ArrayList<>();
//		points.addAll(Arrays.asList(pointValues));
//		System.out.println("Points created : " + points.toString());
//		points = sortPoints(points, Comparator.comparing(Point::getX));
//		System.out.println("Points sorted : " + points.toString());
//	}

	public static List<Point> sortPoints(List<Point> points, Comparator<Point> c) {
		Point[] a = points.toArray(new Point[0]);
		Point[] b = points.toArray(new Point[0]);
		mergeSortJava(a, b, 0, a.length, c);
        return Arrays.asList(b);
	}
	
	private static void mergeSortJava(Point[] pointsSource, Point[] pointsDest, int l, int h, Comparator<Point> c) {
		int len = h - l;
		if (len < 2)					// return because single element cannot be divided further
			return;

		// Recursive method to put halves of pointsDest into pointsSource
		int destL = l;
		int destHigh = h;
		int mid = (l + h) >>> 1;
		mergeSortJava(pointsDest, pointsSource, l, mid, c);
		mergeSortJava(pointsDest, pointsSource, mid, h, c);

		// Merge sorted portions into pointsDest
		for (int i = destL, x = l, y = mid; i < destHigh; i++) {
			if (y >= h || x < mid && c.compare(pointsSource[x], pointsSource[y]) <= 0)
				pointsDest[i] = pointsSource[x++];
			else
				pointsDest[i] = pointsSource[y++];
		}
	}
}