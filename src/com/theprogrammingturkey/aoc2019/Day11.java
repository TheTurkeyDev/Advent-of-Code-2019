package com.theprogrammingturkey.aoc2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Day11
{
	private boolean colorOutput = true;
	private Direction currentDirection = Direction.NORTH;
	private Vector2I currentLocation = new Vector2I(0, 0);
	private Map<String, Integer> colors = new HashMap<>();

	public Day11()
	{
		Scanner scanner = new Scanner(System.in);
		String program = FileUtil.loadFile("res/day11-1.txt").get(0);
		IntCodeProgram currentProgram = new IntCodeProgram(program);

		colors.put(currentLocation.toString(), 1);

		int[] panelsPainted = new int[]{0};
		while(!currentProgram.isHalted())
		{

			if(currentProgram.isWaitingForInput())
			{
				int colorAtPanel = colors.computeIfAbsent(currentLocation.toString(), k -> 0);
				if(colorAtPanel > 1)
					colorAtPanel -= 2;
				currentProgram.setInput(colorAtPanel);
			}

			currentProgram.execute((out) ->
			{
				int currentPanelColor = colors.get(currentLocation.toString());
				if(colorOutput)
				{
					colors.put(currentLocation.toString(), (int) out + 2);
					if(currentPanelColor < 2 && currentPanelColor != out)
						panelsPainted[0]++;
				}
				else
				{
					currentDirection = currentDirection.turnForDirection(out == 1, currentLocation);
				}
				colorOutput = !colorOutput;
			});
		}

		//System.out.println("Part 1: " + panelsPainted[0]);

		List<String> sortedPositions = new ArrayList<>(colors.keySet());
		sortedPositions.sort((o1, o2) ->
		{
			int o1x = Integer.parseInt(o1.substring(1, o1.indexOf(",")));
			int o2x = Integer.parseInt(o2.substring(1, o2.indexOf(",")));
			int o1y = Integer.parseInt(o1.substring(o1.indexOf(",") + 1, o1.indexOf(")")));
			int o2y = Integer.parseInt(o2.substring(o2.indexOf(",") + 1, o2.indexOf(")")));
			if(o1y != o2y)
				return Integer.compare(o2y, o1y);
			else
				return Integer.compare(o1x, o2x);
		});

		int smallestX = Integer.MAX_VALUE;
		for(String vec : sortedPositions)
		{
			int x = Integer.parseInt(vec.substring(1, vec.indexOf(",")));
			if(smallestX > x)
				smallestX = x;
		}

		int currenty = 0;
		for(String vec : sortedPositions)
		{
			int x = Integer.parseInt(vec.substring(1, vec.indexOf(",")));
			int y = Integer.parseInt(vec.substring(vec.indexOf(",") + 1, vec.indexOf(")")));
			if(currenty != y)
			{
				currenty = y;
				System.out.println();
				while(x > smallestX)
				{
					System.out.print(" ");
					x--;
				}
			}
			if(colors.get(vec) % 2 == 1)
				System.out.print("█");
			else
				System.out.print(" ");
		}

		scanner.close();
	}

	public static void main(String[] args)
	{
		new Day11();
	}

	public enum Direction
	{
		NORTH,
		SOUTH,
		EAST,
		WEST;

		public Direction turnForDirection(boolean turnRight, Vector2I currentPos)
		{
			switch(this)
			{
				case NORTH:
					if(turnRight)
					{
						currentPos.x++;
						return EAST;
					}
					else
					{
						currentPos.x--;
						return WEST;
					}
				case EAST:
					if(turnRight)
					{
						currentPos.y--;
						return SOUTH;
					}
					else
					{
						currentPos.y++;
						return NORTH;
					}
				case SOUTH:
					if(turnRight)
					{
						currentPos.x--;
						return WEST;
					}
					else
					{
						currentPos.x++;
						return EAST;
					}
				case WEST:
					if(turnRight)
					{
						currentPos.y++;
						return NORTH;
					}
					else
					{
						currentPos.y--;
						return SOUTH;
					}
				default:
					return this;
			}
		}
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

		@Override
		public String toString()
		{
			return "(" + x + "," + y + ")";
		}

		@Override
		public boolean equals(Object obj)
		{
			if(!(obj instanceof Day10.Vector2I))
				return false;

			Day10.Vector2I vec = (Day10.Vector2I) obj;
			return vec.x == x && vec.y == y;
		}

		@Override
		public int hashCode()
		{
			return (x + "" + y).hashCode();
		}
	}
}
