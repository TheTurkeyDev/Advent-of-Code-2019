package com.theprogrammingturkey.aoc2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15
{
	private Map<Vector2I, Integer> locations = new HashMap<>();
	private Vector2I currentLoc = new Vector2I(0, 0);

	private List<Vector2I> directions = new ArrayList<>();

	public Day15()
	{
		String program = FileUtil.loadFile("res/day15-1.txt").get(0);
		IntCodeProgram currentProgram = new IntCodeProgram(program);

		Vector2I insert = new Vector2I(0, 0);
		locations.put(insert, 1);

		int[] currentDir = new int[]{4};

		while(!currentProgram.isHalted())
		{
			if(currentProgram.isWaitingForInput())
			{
				currentProgram.setInput(currentDir[0]);
			}

			currentProgram.execute((out) ->
			{
				Vector2I markLoc = new Vector2I(currentLoc);
				move(currentDir[0], markLoc);
				if(out == 1)
				{
					move(currentDir[0], currentLoc);
				}
				else if(out == 2)
				{
					move(currentDir[0], currentLoc);
				}

				if(!locations.containsKey(markLoc))
					locations.put(markLoc, (int) out);

				if(directions.size() > 0)
				{
					Vector2I toGo = directions.remove(0);
					if(currentLoc.y > toGo.y)
						currentDir[0] = 2;
					else if(currentLoc.y < toGo.y)
						currentDir[0] = 1;
					else if(currentLoc.x > toGo.x)
						currentDir[0] = 3;
					else
						currentDir[0] = 4;
				}
				else
				{
					Vector2I north = new Vector2I(currentLoc.x, currentLoc.y + 1);
					Vector2I south = new Vector2I(currentLoc.x, currentLoc.y - 1);
					Vector2I west = new Vector2I(currentLoc.x - 1, currentLoc.y);
					Vector2I east = new Vector2I(currentLoc.x + 1, currentLoc.y);
					if(!locations.containsKey(north))
					{
						currentDir[0] = 1;
					}
					else if(!locations.containsKey(south))
					{
						currentDir[0] = 2;
					}
					else if(!locations.containsKey(west))
					{
						currentDir[0] = 3;
					}
					else if(!locations.containsKey(east))
					{
						currentDir[0] = 4;
					}
					else
					{
						findNotVistied(directions, new Vector2I(currentLoc));
						directions.remove(0);
						if(directions.size() == 0)
							currentProgram.haltProgram();
					}
				}
			});
		}

		List<Vector2I> path = new ArrayList<>();
		goToEnd(path, new Vector2I(0, 0));
		outputMap(path);
		System.out.println("Part 1: " + (path.size() - 1));

		for(Vector2I key : locations.keySet())
			if(locations.get(key) == 2)
				locations.put(key, 3);

		int i = 0;
		boolean full = false;
		while(!full)
		{
			i++;
			full = true;
			for(Vector2I key : locations.keySet())
			{
				if(locations.get(key) == 3)
				{
					Vector2I north = new Vector2I(key.x, key.y + 1);
					if(locations.containsKey(north) && locations.get(north) != 0 && locations.get(north) != 3)
						locations.put(north, 4);
					Vector2I south = new Vector2I(key.x, key.y - 1);
					if(locations.containsKey(south) && locations.get(south) != 0 && locations.get(south) != 3)
						locations.put(south, 4);
					Vector2I west = new Vector2I(key.x - 1, key.y);
					if(locations.containsKey(west) && locations.get(west) != 0 && locations.get(west) != 3)
						locations.put(west, 4);
					Vector2I east = new Vector2I(key.x + 1, key.y);
					if(locations.containsKey(east) && locations.get(east) != 0 && locations.get(east) != 3)
						locations.put(east, 4);
				}
				else if(locations.get(key) == 1)
				{
					full = false;
				}
			}

			for(Vector2I key : locations.keySet())
				if(locations.get(key) == 4)
					locations.put(key, 3);
		}

		System.out.println("Part 2: " + i);
	}

	public boolean findNotVistied(List<Vector2I> path, Vector2I loc)
	{
		path.add(loc);
		Vector2I north = new Vector2I(loc.x, loc.y + 1);
		Vector2I south = new Vector2I(loc.x, loc.y - 1);
		Vector2I west = new Vector2I(loc.x - 1, loc.y);
		Vector2I east = new Vector2I(loc.x + 1, loc.y);
		if(!locations.containsKey(north))
		{
			path.add(north);
			return true;
		}
		else if(!locations.containsKey(south))
		{
			path.add(south);
			return true;
		}
		else if(!locations.containsKey(west))
		{
			path.add(west);
			return true;
		}
		else if(!locations.containsKey(east))
		{
			path.add(east);
			return true;
		}
		else
		{
			if(locations.get(north) != 0 && !path.contains(north))
			{
				if(findNotVistied(path, north))
					return true;
				else
					path.remove(path.size() - 1);
			}
			if(locations.get(south) != 0 && !path.contains(south))
			{
				if(findNotVistied(path, south))
					return true;
				else
					path.remove(path.size() - 1);
			}
			if(locations.get(east) != 0 && !path.contains(east))
			{
				if(findNotVistied(path, east))
					return true;
				else
					path.remove(path.size() - 1);
			}
			if(locations.get(west) != 0 && !path.contains(west))
			{
				if(findNotVistied(path, west))
					return true;
				else
					path.remove(path.size() - 1);
			}

			return false;
		}
	}

	public boolean goToEnd(List<Vector2I> path, Vector2I loc)
	{
		path.add(loc);
		Vector2I north = new Vector2I(loc.x, loc.y + 1);
		Vector2I south = new Vector2I(loc.x, loc.y - 1);
		Vector2I west = new Vector2I(loc.x - 1, loc.y);
		Vector2I east = new Vector2I(loc.x + 1, loc.y);
		if(locations.get(north) == 2)
		{
			path.add(north);
			return true;
		}
		else if(locations.get(south) == 2)
		{
			path.add(south);
			return true;
		}
		else if(locations.get(west) == 2)
		{
			path.add(west);
			return true;
		}
		else if(locations.get(east) == 2)
		{
			path.add(east);
			return true;
		}
		else
		{
			List<Vector2I> shortestPath = new ArrayList<>();
			List<Vector2I> subPath = new ArrayList<>(path);
			if(locations.get(north) != 0 && !path.contains(north))
				if(goToEnd(subPath, north))
					shortestPath = subPath;

			subPath = new ArrayList<>(path);
			if(locations.get(south) != 0 && !path.contains(south))
				if(goToEnd(subPath, south))
					if(subPath.size() < shortestPath.size() || shortestPath.size() == 0)
						shortestPath = subPath;

			subPath = new ArrayList<>(path);
			if(locations.get(east) != 0 && !path.contains(east))
				if(goToEnd(subPath, east))
					if(subPath.size() < shortestPath.size() || shortestPath.size() == 0)
						shortestPath = subPath;

			if(locations.get(west) != 0 && !path.contains(west))
				if(goToEnd(subPath, west))
					if(subPath.size() < shortestPath.size() || shortestPath.size() == 0)
						shortestPath = subPath;

			if(shortestPath.size() == 0)
			{
				path.remove(path.size() - 1);
				return false;
			}
			else
			{
				path.clear();
				path.addAll(shortestPath);
				return true;
			}
		}
	}

	public void outputMap(List<Vector2I> path)
	{
		List<Vector2I> keys = new ArrayList<>(locations.keySet());

		int lowX = Integer.MAX_VALUE;
		int highX = Integer.MIN_VALUE;
		int lowY = Integer.MAX_VALUE;
		int highY = Integer.MIN_VALUE;
		for(Vector2I vec : keys)
		{
			if(vec.x < lowX)
				lowX = vec.x;
			if(vec.x > highX)
				highX = vec.x;

			if(vec.y < lowY)
				lowY = vec.y;
			if(vec.y > highY)
				highY = vec.y;
		}

		for(int y = highY; y >= lowY; y--)
		{
			for(int x = lowX; x <= highX; x++)
			{
				Vector2I vec = new Vector2I(x, y);
				if(currentLoc.equals(vec))
				{
					System.out.print("o");
				}
				else if(vec.equals(new Vector2I(0, 0)))
				{
					System.out.print("S");
				}
				else if(path.contains(vec))
				{
					System.out.print("+");
				}
				else if(locations.containsKey(vec))
				{
					int tileId = locations.get(vec);
					if(tileId == 0)
						System.out.print("█");
					else if(tileId == 1)
						System.out.print(" ");
					else if(tileId == 2)
						System.out.print("x");
					else if(tileId >= 3)
						System.out.print(" ");
				}
				else
				{
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}

	public void move(int direction, Vector2I vec)
	{
		if(direction == 1)
			vec.y++;
		else if(direction == 2)
			vec.y--;
		else if(direction == 3)
			vec.x--;
		else if(direction == 4)
			vec.x++;
	}


	public static void main(String[] args)
	{
		new Day15();
	}
}
