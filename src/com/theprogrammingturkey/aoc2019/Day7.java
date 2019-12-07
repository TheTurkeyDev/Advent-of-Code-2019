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
		String[] rawStringList = program.split(",");
		Integer[] programReset = new Integer[rawStringList.length];
		for(int i = 0; i < rawStringList.length; i++)
			programReset[i] = Integer.parseInt(rawStringList[i]);


		int largestTotal = 0;

		for(Integer[] phases : phaseSettings)
		{
			IntCodeProgram[] programs = new IntCodeProgram[5];
			boolean[] phaseInputs = new boolean[]{true, true, true, true, true};
			for(int i = 0; i < programs.length; i++)
				programs[i] = new IntCodeProgram(programReset);
			int j = 0;
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
						int lastProgram = j - 1;
						if(lastProgram == -1)
							lastProgram = 4;
						currentProgram.setInput(programs[lastProgram].lastOutput);
					}
				}

				currentProgram.execute();

				j++;
				if(j == 5)
					j = 0;
			}

			if(programs[4].lastOutput > largestTotal)
				largestTotal = programs[4].lastOutput;
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
