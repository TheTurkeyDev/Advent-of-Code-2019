package com.theprogrammingturkey.aoc2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day18
{
	public Day18()
	{
		List<String> input = FileUtil.loadFile("res/day18-1.txt");
		char[][] map = new char[input.size()][input.get(0).length()];
		Vector2I currentPos = null;

		for(int row = 0; row < input.size(); row++)
		{
			String line = input.get(row);
			for(int col = 0; col < line.length(); col++)
			{
				map[row][col] = line.charAt(col);

				if(line.charAt(col) == '@')
					currentPos = new Vector2I(col, row);
			}
		}


		long time = System.currentTimeMillis();
		int steps = findShortestSteps(map, currentPos, '@');
		System.out.println("Part 1: " + steps);
		System.out.println("time: " + (System.currentTimeMillis() - time));
	}

	private static List<Node> cache = new ArrayList<>();

	public int findShortestSteps(char[][] map, Vector2I currentPos, char parent)
	{
		List<Node> nodes = getNodes(currentPos, map);
		int steps = 0;

		for(Node n : cache)
		{
			if(n.c == parent)
			{
				List<Character> copy = new ArrayList<>();
				for(Node n2 : nodes)
					copy.add(n2.c);

				boolean cached = true;
				for(Node n2 : n.gated)
				{
					if(copy.contains(n2.c))
						copy.remove((Character) n2.c);
					else
						cached = false;
				}

				if(cached && copy.size() == 0)
					return n.dist;
			}
		}

		for(Node n : nodes)
		{
//			if(parent == '@')
//				System.out.println("Here " + nodes.size());
			char[][] mapCopy = new char[map.length][map[0].length];

			for(int row = 0; row < mapCopy.length; row++)
			{
				mapCopy[row] = map[row].clone();
				for(int col = 0; col < mapCopy[row].length; col++)
					if(mapCopy[row][col] == Character.toUpperCase(n.c))
						mapCopy[row][col] = '.';
			}

			mapCopy[n.vec.y][n.vec.x] = '.';

			int pathSteps = findShortestSteps(mapCopy, new Vector2I(n.vec.x, n.vec.y), n.c);
			if(steps == 0 || n.dist + pathSteps < steps)
				steps = n.dist + pathSteps;
		}

		Node cacheAdd = new Node(parent, steps, new Vector3I(0, 0, 0));
		cacheAdd.gated.addAll(nodes);
		cache.add(cacheAdd);

		return steps;
	}

	public List<Node> getNodes(Vector2I pos, char[][] map)
	{
		List<Node> nodes = new ArrayList<>();
		List<Node> toReturn = new ArrayList<>();
		List<Vector3I> pointsCache = new ArrayList<>();
		List<Vector3I> visitedPoints = new ArrayList<>();
		Vector3I start = new Vector3I(pos.x, pos.y, 0);
		start.behind = '@';
		pointsCache.add(start);

		while(pointsCache.size() > 0)
		{
			Vector3I currentPos = pointsCache.remove(0);
			if(visitedPoints.contains(currentPos))
				continue;
			visitedPoints.add(currentPos);


			if(map[currentPos.y][currentPos.x] != '.' && map[currentPos.y][currentPos.x] != '@')
			{
				Node node = new Node(map[currentPos.y][currentPos.x], currentPos.z, currentPos);
				nodes.add(node);
				if(currentPos.behind == '@')
				{
					if(Character.isLowerCase(node.c))
						toReturn.add(node);
				}
				else
				{
					for(Node node2 : nodes)
						if(node2.c == currentPos.behind)
							node2.gated.add(node);
				}

				if(Character.isUpperCase(node.c))
					currentPos.behind = node.c;
			}

			for(Direction d : Direction.values())
			{
				if(d != Direction.None)
				{
					Vector3I movePos = currentPos.move(d);
					if(movePos.y >= 0 && movePos.y < map.length && movePos.x >= 0 && movePos.x < map[0].length && map[movePos.y][movePos.x] != '#')
						pointsCache.add(movePos);
				}
			}
		}

		return toReturn;
	}

	public static void main(String[] args)
	{
		new Day18();
	}

	public static class Node
	{
		Vector3I vec;
		char c;
		int dist;
		List<Node> gated = new ArrayList<>();

		public Node(char c, int dist, Vector3I vec)
		{
			this.c = c;
			this.dist = dist;
			this.vec = vec;
		}

		public String toString()
		{
			return c + "&" + Arrays.toString(gated.toArray(new Node[0]));
		}
	}

	public class Vector3I
	{
		public int x;
		public int y;
		public int z;
		public char behind;

		public Vector3I(int x, int y, int z)
		{
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public Vector3I(Vector3I vec)
		{
			this.x = vec.x;
			this.y = vec.y;
			this.z = vec.z;
		}

		public void add(Vector3I vec)
		{
			this.x += vec.x;
			this.y += vec.y;
			this.z += vec.z;
		}

		public Vector3I move(Direction dir)
		{
			Vector3I vec;
			switch(dir)
			{
				case North:
					vec = new Vector3I(x, y - 1, z + 1);
					vec.behind = behind;
					return vec;
				case South:
					vec = new Vector3I(x, y + 1, z + 1);
					vec.behind = behind;
					return vec;
				case East:
					vec = new Vector3I(x + 1, y, z + 1);
					vec.behind = behind;
					return vec;
				case West:
					vec = new Vector3I(x - 1, y, z + 1);
					vec.behind = behind;
					return vec;
			}
			return new Vector3I(this);
		}

		@Override
		public String toString()
		{
			return "(" + x + "," + y + "," + z + ")";
		}

		@Override
		public boolean equals(Object obj)
		{
			if(!(obj instanceof Vector3I))
				return false;

			Vector3I vec = (Vector3I) obj;
			return vec.x == x && vec.y == y;
		}
	}
}
