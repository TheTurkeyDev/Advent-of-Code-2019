package com.theprogrammingturkey.aoc2019;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10
{
	public Comparator<Vector2I> angleCompare = (o1, o2) ->
	{
		double o1Angle = Math.atan2(o1.y, o1.x);
		if(o1Angle < -Math.PI / 2)
			o1Angle += Math.PI * 2;
		double o2Angle = Math.atan2(o2.y, o2.x);
		if(o2Angle < -Math.PI / 2)
			o2Angle += Math.PI * 2;
		return Double.compare(o1Angle, o2Angle);
	};

	public Day10()
	{
		List<String> lines = FileUtil.loadFile("res/day10-1.txt");

		List<Vector2I> meteors = new ArrayList<>();

		for(int y = 0; y < lines.size(); y++)
		{
			String line = lines.get(y);
			char[] chars = line.toCharArray();
			for(int x = 0; x < chars.length; x++)
			{
				if(chars[x] == '#')
				{
					meteors.add(new Vector2I(x, y));
				}
			}
		}

		Vector2I currentLargest = new Vector2I(0, 0);
		int mostMeteorsVisible = -1;

		for(Vector2I origin : meteors)
		{
			int meteorsVisible = 0;
			for(Vector2I meteor : meteors)
			{
				if(origin.equals(meteor))
					continue;

				Vector2I ray = new Vector2I(meteor.x - origin.x, meteor.y - origin.y);
				int gcf = findGCF(Math.abs(ray.x), Math.abs(ray.y));

				ray.x = ray.x / gcf;
				ray.y = ray.y / gcf;


				Vector2I location = new Vector2I(ray.x + origin.x, ray.y + origin.y);
				int i = 1;
				boolean visible = true;

				while(!meteor.equals(location))
				{
					if(meteors.contains(location))
						visible = false;
					location = new Vector2I((ray.x * i) + origin.x, (ray.y * i) + origin.y);
					i++;
				}


				if(visible)
					meteorsVisible++;
			}

			if(meteorsVisible > mostMeteorsVisible)
			{
				mostMeteorsVisible = meteorsVisible;
				currentLargest = origin;
			}
		}

		System.out.println("Part 1: " + currentLargest + ": " + mostMeteorsVisible);

		Map<Vector2I, List<Vector2I>> asteriodLines = new HashMap<>();

		for(Vector2I meteor : meteors)
		{
			if(currentLargest.equals(meteor))
				continue;

			Vector2I ray = new Vector2I(meteor.x - currentLargest.x, meteor.y - currentLargest.y);
			int gcf = findGCF(Math.abs(ray.x), Math.abs(ray.y));

			ray.x = ray.x / gcf;
			ray.y = ray.y / gcf;

			List<Vector2I> asteriods = asteriodLines.computeIfAbsent(ray, k -> new ArrayList<>());

			int i = 0;
			while(i < asteriods.size())
			{
				if(asteriods.get(i).distanceTo(currentLargest) > meteor.distanceTo(currentLargest))
					break;
				i++;
			}

			asteriods.add(i, meteor);
		}

		List<Vector2I> sortedVectors = new ArrayList<>(asteriodLines.keySet());
		sortedVectors.sort(angleCompare);
		Vector2I lastDestroyed = null;
		int destroyed = 0;
		for(Vector2I vector : sortedVectors)
		{
			List<Vector2I> asteriods = asteriodLines.get(vector);
			if(asteriods.size() != 0)
			{
				lastDestroyed = asteriods.remove(0);
				destroyed++;
				if(destroyed == 200)
					break;
			}
		}

		System.out.println("Part 2: " + lastDestroyed.hashCode());
	}

	public int findGCF(int x, int y)
	{
		if(y == 0)
			return x;
		else
			return findGCF(y, x % y);
	}


	public static void main(String[] args)
	{
		new Day10();
	}

	public static class Vector2I
	{
		public int x;
		public int y;

		public Vector2I(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

		public double distanceTo(Vector2I vec)
		{
			return Math.pow(vec.x - x, 2) + Math.pow(vec.y - y, 2);
		}

		@Override
		public String toString()
		{
			return "(" + x + "," + y + ")";
		}

		@Override
		public boolean equals(Object obj)
		{
			if(!(obj instanceof Vector2I))
				return false;

			Vector2I vec = (Vector2I) obj;
			return vec.x == x && vec.y == y;
		}

		@Override
		public int hashCode()
		{
			return x * 100 + y;
		}
	}
}
