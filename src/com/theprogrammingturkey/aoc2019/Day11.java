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
	private Map<Vector2I, Integer> colors = new HashMap<>();

	public Day11()
	{
		Scanner scanner = new Scanner(System.in);
		String program = FileUtil.loadFile("res/day11-1.txt").get(0);
		IntCodeProgram currentProgram = new IntCodeProgram(program);

		colors.put(new Vector2I(currentLocation), 1);

		int[] panelsPainted = new int[]{0};
		while(!currentProgram.isHalted())
		{

			if(currentProgram.isWaitingForInput())
			{
				int colorAtPanel = colors.computeIfAbsent(new Vector2I(currentLocation), k -> 0);
				if(colorAtPanel > 1)
					colorAtPanel -= 2;
				currentProgram.setInput(colorAtPanel);
			}

			currentProgram.execute((out) ->
			{
				int currentPanelColor = colors.get(currentLocation);
				if(colorOutput)
				{
					colors.put(new Vector2I(currentLocation), (int) out + 2);
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

		List<Vector2I> sortedPositions = new ArrayList<>(colors.keySet());
		sortedPositions.sort((o1, o2) ->
		{
			if(o1.y != o2.y)
				return Integer.compare(o2.y, o1.y);
			else
				return Integer.compare(o1.x, o2.x);
		});

		int smallestX = Integer.MAX_VALUE;
		for(Vector2I vec : sortedPositions)
		{
			if(smallestX > vec.x)
				smallestX = vec.x;
		}

		int currenty = 0;
		for(Vector2I vec : sortedPositions)
		{
			if(currenty != vec.y)
			{
				currenty = vec.y;
				System.out.println();
				int x = vec.x;
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
}
