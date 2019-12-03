package com.theprogrammingturkey.aoc2019;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day3
{
	private Point currentPoint;
	private Map<Point, Integer> visitedPoints = new HashMap<>();

	public Day3()
	{
		List<String> input = FileUtil.loadFile("res/day3-1.txt");

		String[] wire1 = input.get(0).split(",");
		currentPoint = new Point(0, 0);
		int steps = 1;
		for(String instruction : wire1)
		{
			int distance = Integer.parseInt(instruction.substring(1));
			offsetPoint(instruction.charAt(0), distance, steps);
			steps += distance;
		}

		int currentShortestDist = Integer.MAX_VALUE;
		int currentShortestSteps = Integer.MAX_VALUE;
		String[] wire2 = input.get(1).split(",");
		currentPoint = new Point(0, 0);
		steps = 1;
		for(String instruction : wire2)
		{
			Point currentPointCache = new Point(currentPoint);
			int distance = Integer.parseInt(instruction.substring(1));
			int manhattanDist = this.offsetPointWDistCheck(instruction.charAt(0), distance);
			if(manhattanDist != -1 && manhattanDist < currentShortestDist)
				currentShortestDist = manhattanDist;

			currentPoint = new Point(currentPointCache);
			Map<Point, Integer> stepIntersections = this.offsetPointWStepCheck(instruction.charAt(0), distance, steps);
			steps += distance;

			for(Point point : stepIntersections.keySet())
			{
				int stepsSum = stepIntersections.get(point) + visitedPoints.get(point);
				if(stepsSum < currentShortestSteps)
					currentShortestSteps = stepsSum;
			}
		}

		System.out.println("Closest Manhattan Distance: " + currentShortestDist);
		System.out.println("Least Steps:" + currentShortestSteps);
	}

	public void offsetPoint(char direction, int distance, int steps)
	{
		for(int i = 0; i < distance; i++)
		{
			if(direction == 'U')
				currentPoint.y++;
			else if(direction == 'D')
				currentPoint.y--;
			else if(direction == 'L')
				currentPoint.x--;
			else if(direction == 'R')
				currentPoint.x++;

			visitedPoints.put(new Point(currentPoint), steps + i);
		}
	}

	public int offsetPointWDistCheck(char direction, int distance)
	{
		int closestIntersection = -1;
		for(int i = 0; i < distance; i++)
		{
			if(direction == 'U')
				currentPoint.y++;
			else if(direction == 'D')
				currentPoint.y--;
			else if(direction == 'L')
				currentPoint.x--;
			else if(direction == 'R')
				currentPoint.x++;
			if(visitedPoints.containsKey(currentPoint))
			{
				int manhattanDist = Math.abs(currentPoint.x) + Math.abs(currentPoint.y);
				if(closestIntersection == -1 || manhattanDist < closestIntersection)
					closestIntersection = manhattanDist;
			}
		}

		return closestIntersection;
	}

	public Map<Point, Integer> offsetPointWStepCheck(char direction, int distance, int steps)
	{
		Map<Point, Integer> stepIntesections = new HashMap<>();
		for(int i = 0; i < distance; i++)
		{
			if(direction == 'U')
				currentPoint.y++;
			else if(direction == 'D')
				currentPoint.y--;
			else if(direction == 'L')
				currentPoint.x--;
			else if(direction == 'R')
				currentPoint.x++;
			if(visitedPoints.containsKey(currentPoint))
				stepIntesections.put(new Point(currentPoint), steps + i);
		}

		return stepIntesections;
	}

	public static void main(String[] args)
	{
		new Day3();
	}

	static class Point
	{
		int x;
		int y;

		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

		public Point(Point point)
		{
			this.x = point.x;
			this.y = point.y;
		}

		@Override
		public boolean equals(Object obj)
		{
			if(!(obj instanceof Point))
				return false;

			Point point = (Point) obj;

			return point.x == x && point.y == y;
		}

		@Override
		public int hashCode()
		{
			return x * 10000 + y;
		}

		@Override
		public String toString()
		{
			return "(" + this.x + "," + this.y + ")";
		}
	}
}
