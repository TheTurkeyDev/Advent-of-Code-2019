package com.theprogrammingturkey.aoc2019;

public class Day13
{
	private long score = 0;
	private int[][] board = new int[26][45];
	private int[] outputState = new int[]{0};

	private boolean settingScore = false;
	private int ballX = 0;
	private int paddleX = 0;

	public Day13()
	{

		String program = FileUtil.loadFile("res/day13-1.txt").get(0);
		IntCodeProgram currentProgram = new IntCodeProgram(program);
		currentProgram.setMemory(0, 2);

		int[] currentValues = new int[]{0, 0};
		while(!currentProgram.isHalted())
		{
			if(currentProgram.isWaitingForInput())
			{
				displayGame();
				currentProgram.setInput(Integer.compare(ballX, paddleX));
			}

			currentProgram.execute((out) ->
			{
				if(outputState[0] == 0)
				{
					if(out == -1)
						settingScore = true;
					else
						currentValues[0] = (int) out;
				}
				else if(outputState[0] == 1)
				{
					currentValues[1] = (int) out;
				}
				else
				{
					if(settingScore)
					{
						score = out;
						settingScore = false;
					}
					else
					{
						if(out == 3)
							paddleX = currentValues[0];
						else if(out == 4)
							ballX = currentValues[0];
						board[currentValues[1]][currentValues[0]] = (int) out;
					}
				}

				outputState[0] = (outputState[0] + 1) % 3;
			});
		}

		int count = 0;
		for(int[] row : board)
			for(int col : row)
				if(col == 2)
					count++;

		System.out.println("PART 1: " + count);
		System.out.println("PART 2: " + score);
	}

	public void displayGame()
	{
		System.out.println("SCORE: " + score);
		for(int[] row : board)
		{
			for(int col : row)
			{
				if(col == 0)
					System.out.print(" ");
				if(col == 1)
					System.out.print("▐");
				if(col == 2)
					System.out.print("▄");
				if(col == 3)
					System.out.print("_");
				if(col == 4)
					System.out.print(".");
			}
			System.out.println();
		}
	}

	public static void main(String[] args)
	{
		new Day13();
	}
}
