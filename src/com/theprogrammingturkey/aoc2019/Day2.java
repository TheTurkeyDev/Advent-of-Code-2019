package com.theprogrammingturkey.aoc2019;

public class Day2
{
	public static void main(String[] args)
	{
		String program = FileUtil.loadFile("res/day2-1.txt").get(0);
		String[] rawStringList = program.split(",");
		Integer[] programReset = new Integer[rawStringList.length];
		for(int i = 0; i < rawStringList.length; i++)
			programReset[i] = Integer.parseInt(rawStringList[i]);

		Integer[] programList = programReset.clone();
		int noun = -1;
		int verb = 0;

		while(programList[0] != 19690720)
		{
			noun++;
			if(noun == 100)
			{
				noun = 0;
				verb++;
			}
			programList = programReset.clone();
			programList[1] = noun;
			programList[2] = verb;
			int pc = 0;
			boolean run = true;
			while(run)
			{
				int opcode = programList[pc];
				int result;
				switch(opcode)
				{
					case 1:
						result = programList[programList[pc + 1]] + programList[programList[pc + 2]];
						programList[programList[pc + 3]] = result;
						break;
					case 2:
						result = programList[programList[pc + 1]] * programList[programList[pc + 2]];
						programList[programList[pc + 3]] = result;
						break;
					default:
						run = false;
						break;
				}
				pc += 4;
			}
		}

		System.out.println(100 * noun + verb);
	}
}
