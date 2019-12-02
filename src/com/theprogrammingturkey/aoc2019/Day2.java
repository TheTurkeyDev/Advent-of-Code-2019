package com.theprogrammingturkey.aoc2019;

public class Day2
{
	public static void main(String[] args)
	{
		String program = FileUtil.loadFile("res/day2-1.txt").get(0);
		String[] programReset = program.split(",");
		String[] programList = programReset.clone();
		int noun = -1;
		int verb = 0;

		while(!programList[0].equals("19690720"))
		{
			noun++;
			if(noun == 100)
			{
				noun = 0;
				verb++;
			}
			programList = programReset.clone();
			programList[1] = String.valueOf(noun);
			programList[2] = String.valueOf(verb);
			int pc = 0;
			boolean run = true;
			while(run)
			{
				int opcode = Integer.parseInt(programList[pc]);
				int position1;
				int position2;
				int result;
				switch(opcode)
				{
					case 1:
						position1 = Integer.parseInt(programList[pc + 1]);
						position2 = Integer.parseInt(programList[pc + 2]);
						result = Integer.parseInt(programList[position1]) + Integer.parseInt(programList[position2]);
						programList[Integer.parseInt(programList[pc + 3])] = String.valueOf(result);
						break;
					case 2:
						position1 = Integer.parseInt(programList[pc + 1]);
						position2 = Integer.parseInt(programList[pc + 2]);
						result = Integer.parseInt(programList[position1]) * Integer.parseInt(programList[position2]);
						programList[Integer.parseInt(programList[pc + 3])] = String.valueOf(result);
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
