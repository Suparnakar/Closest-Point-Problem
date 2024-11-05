package kar.suparna;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Comparator;



public class Main_file {

	private static Point[] pointValues;												// For taking the input
	
	private static double smallestDistance = Double.MAX_VALUE;						// initialize to max, for output
	private static List<Point> points = new ArrayList<>();							// For calculation
	private static List<Point> closestPoints = new ArrayList<>();					// For the output
	
	public static void main(String... args) {
		// take user inputs
		Scanner sc = new Scanner(System.in);
		System.out.print("How many points are there on the 2D plane? ");
		int numberOfPoints = sc.nextInt();
		pointValues = new Point[numberOfPoints];
		for (int i=1; i<=numberOfPoints; i++) {
			System.out.println("Enter the coordinates of Point " + i);
			System.out.print("X" + i + " : ");
			double x = sc.nextDouble();
			System.out.print("Y" + i + " : ");
			double y = sc.nextDouble();
			pointValues[i-1] = new Point(x, y);
		}
		sc.close();
		
		Main_file main = new Main_file();
		points.addAll(Arrays.asList(pointValues)); // adding array elements to list
		System.out.println("Points created : " + points.toString());
		main.findClosestPair();
		System.out.println("The closest pair of points are : " + closestPoints.get(0).toString()
				+ " and " + closestPoints.get(1).toString());
		System.out.println("The distance between them is : " + new DecimalFormat("0.00").format(smallestDistance) + " units");
	}
	
	private void findClosestPair() {
		List<Point> pointsX = Arrays.asList(pointValues);
		// Collections.sort(pointsX, (p1, p2) -> Double.compare(p1.getX(), p2.getX()));
		// Collections.sort(pointsX, Comparator.comparing((Point p) -> p.getX()));
		// Collections.sort(pointsX, Comparator.comparing(Point::getX));
		points = MergeSort.sortPoints(points, Comparator.comparing(Point::getX)); // own mergesort impl

		System.out.println("Points after sorting by X : " + pointsX.toString());
		List<Point> pointsY = Arrays.asList(pointValues);
		// Collections.sort(pointsY, Comparator.comparing(Point::getY));

		System.out.println("Points after sorting by Y : " + pointsY.toString());
		points = MergeSort.sortPoints(points, Comparator.comparing(Point::getY)); // own mergesort impl
		smallestDistance = findClosestPairRecursive(pointsX, pointsY, pointsX.size());
		}
	
	// The main logic to find smallest distance
	private double findClosestPairRecursive(List<Point> pointsX, List<Point> pointsY, int n) {
		double smallest = smallestDistance;
		if (n <= 3) {
			smallest = findSmallestDistanceByCalculation(pointsX);
			return smallest;
		}
		
		// If x coordinate of the point is less than mid point then it will go the left List. But if x coordinate is 
		//equal to mid point's x coordinate then check if point's y coordinate is less than mid point's y coordinate 
		//then add to leftList. Now if this Left list's population become bigger than median then add to Right list.
		int median = n/2;
		Point midPoint = pointsX.get(median); //Finding the Mid point
		List<Point> pointsYL = new ArrayList<Point>();
		List<Point>	pointsYR = new ArrayList<Point>();
		int iteration = 0;
		for (Point point : pointsY) {
			if (!(iteration++ < n))
				break;
			if ((point.getX() < midPoint.getX() || (point.getX() == midPoint.getX()
					&& point.getY() < midPoint.getY())) && pointsYL.size() < median)
				pointsYL.add(point);
			else
				pointsYR.add(point);
		}
		
		//Finding smallest distance in left half and right half
		double distanceL = findClosestPairRecursive(pointsX, pointsYL, median);
		double distanceR = findClosestPairRecursive(pointsX.subList(median, pointsX.size()), pointsYR, n-median);
		double distance = Math.min(distanceL, distanceR);		// get minimum distance between left half and right half
		
		// Build a strip which contains only points closer than distance to the median line
		List<Point> strip = new ArrayList<>(); // Create a List which will contain only those points in strip
		for (int i = 0; i < n; i++) {
			Point point = pointsY.get(i);
			if (Math.abs(point.getX() - midPoint.getX()) < distance)
				strip.add(point);
		}
		
		// closest points in strip. Return the minimum of distance and closest distance in strip according to Y coordinate
		smallest = Math.min(distance, smallestDistance);
		for (int i = 0; i < strip.size(); ++i)
			for (int j = i+1; j < strip.size() && (strip.get(j).getY() - strip.get(i).getY()) < smallest; ++j) {
				double dist = strip.get(i).findDistance(strip.get(j));
				if (dist < smallest) {
					smallest = dist;
					closestPoints.clear(); 
					closestPoints.add(points.get(i)); closestPoints.add(points.get(j));
				}
			}
		return smallest;
	}

	private double findSmallestDistanceByCalculation(List<Point> points) {
		for (int i = 0; i < points.size(); i++)
			for (int j = i+1; j < points.size(); ++j) // if distance between point i & j is less than smallest distance then update 
				if (points.get(i).findDistance(points.get(j)) < smallestDistance) {
					smallestDistance = points.get(i).findDistance(points.get(j));
					closestPoints.clear(); // if these are closest points then clear previous points & add these points. 
					closestPoints.add(points.get(i)); 
					closestPoints.add(points.get(j));
//					System.out.println("Local minimum distance found = " + smallestDistance);
				}
		return smallestDistance;
	}
}