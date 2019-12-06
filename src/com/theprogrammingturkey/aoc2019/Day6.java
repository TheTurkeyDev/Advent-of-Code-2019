package com.theprogrammingturkey.aoc2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day6
{
	public Day6()
	{
		List<String> input = FileUtil.loadFile("res/day6-1.txt");

		Map<String, List<String>> orbits = new HashMap<>();

		for(String orbit : input)
		{
			String[] orbitData = orbit.split("\\)");
			List<String> orbiting = orbits.computeIfAbsent(orbitData[0], k -> new ArrayList<>());
			orbiting.add(orbitData[1]);
		}

		System.out.println("PART 1: " + getTotalOrbits("COM", orbits, 0));

		List<String> youPath = getPathToObject("YOU", orbits);
		List<String> sanPath = getPathToObject("SAN", orbits);
		while(youPath.get(0).equals(sanPath.get(0)))
		{
			youPath.remove(0);
			sanPath.remove(0);
		}
		youPath.remove(youPath.size() - 1);
		sanPath.remove(sanPath.size() - 1);

		System.out.println("PART 2: " + (youPath.size() + sanPath.size()));
	}

	public int getTotalOrbits(String currentMass, Map<String, List<String>> orbits, int depth)
	{
		int sumOrbits = depth;

		if(orbits.containsKey(currentMass))
			for(String orbiting : orbits.get(currentMass))
				sumOrbits += getTotalOrbits(orbiting, orbits, depth + 1);

		return sumOrbits;
	}

	public List<String> getPathToObject(String toObject, Map<String, List<String>> orbits)
	{
		List<String> path = new ArrayList<>();
		if(toObject.equals("COM"))
		{
			path.add("COM");
		}
		else
		{
			for(String orbitCenter : orbits.keySet())
			{
				if(orbits.get(orbitCenter).contains(toObject))
				{
					path = getPathToObject(orbitCenter, orbits);
					path.add(toObject);
				}
			}
		}

		return path;
	}


	public static void main(String[] args)
	{
		new Day6();
	}
}
