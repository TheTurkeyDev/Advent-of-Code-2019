package com.theprogrammingturkey.aoc2019;

import java.util.List;

public class Day12
{
	private Vector3I[] initialPos = new Vector3I[4];

	private Vector3I[] moonsPos = new Vector3I[4];
	private Vector3I[] moonsVel = new Vector3I[4];

	public Day12()
	{
		List<String> input = FileUtil.loadFile("res/day12-1.txt");
		for(int i = 0; i < input.size(); i++)
		{
			String moonPos = input.get(i);
			int x = Integer.parseInt(moonPos.substring(3, moonPos.indexOf(",")));
			int yIndex = moonPos.indexOf("y");
			int y = Integer.parseInt(moonPos.substring(yIndex + 2, moonPos.indexOf(",", yIndex)));
			int zIndex = moonPos.indexOf("z");
			int z = Integer.parseInt(moonPos.substring(zIndex + 2, moonPos.length() - 1));
			moonsPos[i] = new Vector3I(x, y, z);
			moonsVel[i] = new Vector3I(0, 0, 0);
			initialPos[i] = new Vector3I(x, y, z);
		}

		int step;
		for(step = 0; step < 100; step++)
		{
			step();
		}

		int sum = 0;
		for(int i = 0; i < moonsPos.length; i++)
		{
			int pot = Math.abs(moonsPos[i].x) + Math.abs(moonsPos[i].y) + Math.abs(moonsPos[i].z);
			int kin = Math.abs(moonsVel[i].x) + Math.abs(moonsVel[i].y) + Math.abs(moonsVel[i].z);

			sum += pot * kin;
		}
		System.out.println("Part 1: " + sum);

		step = 0;
		for(int i = 0; i < moonsPos.length; i++)
		{
			moonsPos[i] = new Vector3I(initialPos[i]);
			moonsVel[i] = new Vector3I(0, 0, 0);
		}

		int x = 0;
		int y = 0;
		int z = 0;
		do
		{
			step();
			step++;
			if(areEqual(0) && x == 0)
				x = step;
			if(areEqual(1) && y == 0)
				y = step;
			if(areEqual(2) && z == 0)
				z = step;
		}
		while(x == 0 || y == 0 || z == 0);

		System.out.println("Part 2: " + lcm(lcm(x, y), z));
	}

	public long gcd(long a, long b)
	{
		if(a == 0)
			return b;
		return gcd(b % a, a);
	}

	public long lcm(long a, long b)
	{
		return (a * b) / gcd(a, b);
	}

	public boolean areEqual(int id)
	{
		for(int i = 0; i < moonsPos.length; i++)
		{
			if(moonsPos[i].x != initialPos[i].x && id == 0)
				return false;
			else if(moonsPos[i].y != initialPos[i].y && id == 1)
				return false;
			else if(moonsPos[i].z != initialPos[i].z && id == 2)
				return false;
		}

		for(Vector3I vel : moonsVel)
		{
			if(vel.x != 0 && id == 0)
				return false;
			else if(vel.y != 0 && id == 1)
				return false;
			else if(vel.z != 0 && id == 2)
				return false;
		}

		return true;
	}

	public void step()
	{
		for(int i = 0; i < moonsPos.length; i++)
		{
			Vector3I moon1Pos = moonsPos[i];
			Vector3I moon1Vel = moonsVel[i];
			for(int j = i + 1; j < moonsPos.length; j++)
			{
				Vector3I moon2Pos = moonsPos[j];
				Vector3I moon2Vel = moonsVel[j];
				moon1Vel.x += getGravityChange(moon1Pos.x, moon2Pos.x);
				moon1Vel.y += getGravityChange(moon1Pos.y, moon2Pos.y);
				moon1Vel.z += getGravityChange(moon1Pos.z, moon2Pos.z);

				moon2Vel.x += getGravityChange(moon2Pos.x, moon1Pos.x);
				moon2Vel.y += getGravityChange(moon2Pos.y, moon1Pos.y);
				moon2Vel.z += getGravityChange(moon2Pos.z, moon1Pos.z);
			}
		}
		for(int i = 0; i < moonsPos.length; i++)
			moonsPos[i].add(moonsVel[i]);
	}

	public int getGravityChange(int axis1, int axis2)
	{
		if(axis1 < axis2)
			return 1;
		else if(axis1 > axis2)
			return -1;
		return 0;
	}

	public static void main(String[] args)
	{
		new Day12();
	}

	public static class Vector3I
	{
		public int x;
		public int y;
		public int z;

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
			return vec.x == x && vec.y == y && vec.z == z;
		}
	}
}
