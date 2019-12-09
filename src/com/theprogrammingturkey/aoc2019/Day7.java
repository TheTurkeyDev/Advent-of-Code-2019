package com.theprogrammingturkey.aoc2019;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day7
{
	List<Integer[]> phaseSettings = new ArrayList<>();

	public Day7()
	{
		generateCombintaions();
		Scanner scanner = new Scanner(System.in);
		String program = FileUtil.loadFile("res/day7-1.txt").get(0);


		long largestTotal = 0;

		for(Integer[] phases : phaseSettings)
		{
			IntCodeProgram[] programs = new IntCodeProgram[5];
			boolean[] phaseInputs = new boolean[]{true, true, true, true, true};
			for(int i = 0; i < programs.length; i++)
				programs[i] = new IntCodeProgram(program);
			int j = 0;
			long[] lastOutput = new long[1];
			while(!programs[4].isHalted())
			{
				IntCodeProgram currentProgram = programs[j];

				if(currentProgram.isWaitingForInput())
				{
					if(phaseInputs[j])
					{
						currentProgram.setInput(phases[j]);
						phaseInputs[j] = false;
					}
					else
					{
						currentProgram.setInput(lastOutput[0]);
					}
				}

				currentProgram.execute((out) -> lastOutput[0] = out);

				j++;
				if(j == 5)
					j = 0;
			}

			if(lastOutput[0] > largestTotal)
				largestTotal = lastOutput[0];
		}

		System.out.println(largestTotal);

		scanner.close();
	}

	public void generateCombintaions()
	{
		for(int i = 5; i < 10; i++)
		{
			for(int j = 5; j < 10; j++)
			{
				if(i == j)
					continue;
				for(int k = 5; k < 10; k++)
				{
					if(i == k || j == k)
						continue;
					for(int l = 5; l < 10; l++)
					{
						if(i == l || j == l || k == l)
							continue;
						for(int m = 5; m < 10; m++)
						{
							if(i == m || j == m || k == m || l == m)
								continue;
							phaseSettings.add(new Integer[]{i, j, k, l, m});
						}
					}
				}
			}
		}

	}

	public static void main(String[] args)
	{
		new Day7();
	}
}
