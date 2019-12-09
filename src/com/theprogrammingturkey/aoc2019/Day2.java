package com.theprogrammingturkey.aoc2019;

public class Day2
{
	public static void main(String[] args)
	{
		String program = FileUtil.loadFile("res/day2-1.txt").get(0);
		String[] rawStringList = program.split(",");
		long[] programReset = new long[rawStringList.length];
		for(int i = 0; i < rawStringList.length; i++)
			programReset[i] = Long.parseLong(rawStringList[i]);

		int noun = -1;
		int verb = 0;

		long memZero = 0;
		while(memZero != 19690720)
		{
			noun++;
			if(noun == 100)
			{
				noun = 0;
				verb++;
			}
			long[] programList = programReset.clone();
			programList[1] = noun;
			programList[2] = verb;
			IntCodeProgram currentProgram = new IntCodeProgram(programList);
			currentProgram.execute(System.out::println);
			memZero = currentProgram.getValueforParamMode(1, 0);
		}

		System.out.println(100 * noun + verb);
	}
}
