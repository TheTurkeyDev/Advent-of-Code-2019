package com.theprogrammingturkey.aoc2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day17
{
	private static final String ROUTINE_A = "R,4,L,12,R,6,L,12";
	private static final String ROUTINE_B = "R,10,R,6,R,4";
	private static final String ROUTINE_C = "R,4,R,10,R,8,R,4";

	private int[][] map = new int[46][30];

	private Vector2I position = new Vector2I(6, 0);
	private Direction facing = Direction.North;

	private int stage = 0;
	private boolean needNewInput = true;

	public Day17()
	{
		String program = FileUtil.loadFile("res/day17-1.txt").get(0);
		IntCodeProgram currentProgram = new IntCodeProgram(program);

		currentProgram.setMemory(0, 2);

		long[] dust = new long[]{0};

		int[] coords = new int[]{0, 0};
		String input = "";
		while(!currentProgram.isHalted())
		{
			if(currentProgram.isWaitingForInput())
			{
				if(needNewInput)
				{
					if(stage == 0)
					{
						List<String> path = calculatePath();
						String main = Arrays.toString(path.toArray(new String[0]));
						main = main.replace(" ", "");
						main = main.replace(ROUTINE_A, "A");
						main = main.replace(ROUTINE_B, "B");
						main = main.replace(ROUTINE_C, "C");
						main = main.replace("[", "");
						main = main.replace("]", "");
						input = main;

						stage++;
						needNewInput = false;
					}
					else if(stage == 1)
					{
						input = ROUTINE_A;
						stage++;
						needNewInput = false;
					}
					else if(stage == 2)
					{
						input = ROUTINE_B;
						stage++;
						needNewInput = false;
					}
					else if(stage == 3)
					{
						input = ROUTINE_C;
						stage++;
						needNewInput = false;
					}
					else if(stage == 4)
					{
						input = "n";
						stage++;
						needNewInput = false;
					}
				}
				if(input.length() > 0)
				{
					currentProgram.setInput(input.charAt(0));
					System.out.print(input.charAt(0));
					input = input.substring(1);
				}
				else
				{
					currentProgram.setInput(10);
					System.out.println();
					needNewInput = true;
				}
			}


			currentProgram.execute((out) ->
			{
				if(coords[1] == 46)
				{
					if(stage == 5)
						dust[0] = out;
					else
						System.out.print((char) out);
					return;
				}

				if(out == 10)
				{
					coords[0] = 0;
					coords[1]++;
				}
				else
				{
					map[coords[1]][coords[0]] = (int) out;
					coords[0]++;
				}

				System.out.print((char) out);
			});
		}

		int alignmentParam = 0;
		for(int row = 1; row < map.length - 1; row++)
			for(int col = 1; col < map[row].length - 1; col++)
				if(map[row][col] == 35 && map[row - 1][col] == 35 && map[row + 1][col] == 35 && map[row][col - 1] == 35 && map[row][col + 1] == 35)
					alignmentParam += col * row;

		System.out.println("Part 1: " + alignmentParam);
		System.out.println("Part 2: " + dust[0]);
	}

	public List<String> calculatePath()
	{
		List<String> instructions = new ArrayList<>();
		Vector2I nextLoc;
		int moves = 0;
		boolean run = true;

		while(run)
		{
			nextLoc = position.move(facing);
			if(nextLoc.y < 0 || nextLoc.y > 45 || nextLoc.x < 0 || nextLoc.x > 29 || map[nextLoc.y][nextLoc.x] != 35)
			{
				if(moves != 0)
					instructions.add(String.valueOf(moves));

				moves = 0;
				String turnDir = turn(position);
				if(turnDir.equals("R"))
				{
					facing = facing.turnRight();
					instructions.add(turnDir);
				}
				else if(turnDir.equals("L"))
				{
					facing = facing.turnLeft();
					instructions.add(turnDir);
				}
				else
				{
					run = false;
				}
			}
			else
			{
				position = nextLoc;
				moves++;
			}
		}

		return instructions;
	}

	public String turn(Vector2I pos)
	{
		Vector2I turned = pos.move(facing.turnLeft());
		if(turned.y < 0 || turned.y > 45 || turned.x < 0 || turned.x > 29 || map[turned.y][turned.x] != 35)
		{
			turned = pos.move(facing.turnRight());
			if(turned.y < 0 || turned.y > 45 || turned.x < 0 || turned.x > 29 || map[turned.y][turned.x] != 35)
				return "N";
			else
				return "R";
		}
		else
		{
			return "L";
		}
	}

	public static void main(String[] args)
	{
		new Day17();
	}
}