package com.theprogrammingturkey.aoc2019;

import java.util.Scanner;

public class Day5
{
	public Day5()
	{
		Scanner scanner = new Scanner(System.in);
		String program = FileUtil.loadFile("res/day5-1.txt").get(0);
		String[] rawStringList = program.split(",");
		Integer[] programReset = new Integer[rawStringList.length];
		for(int i = 0; i < rawStringList.length; i++)
			programReset[i] = Integer.parseInt(rawStringList[i]);

		Integer[] programList = programReset.clone();

		int pc = 0;
		boolean run = true;
		int[] params = new int[3];
		boolean[] paramModes = new boolean[3];
		while(run)
		{
			String instruction = String.valueOf(programList[pc]);
			for(int i = instruction.length(); i < 5; i++)
				instruction = "0" + instruction;

			int opcode = Integer.parseInt(instruction.substring(instruction.length() - 2));
			paramModes[0] = instruction.charAt(2) == '0';
			paramModes[1] = instruction.charAt(1) == '0';
			paramModes[2] = instruction.charAt(0) == '0';

			int result;
			switch(opcode)
			{
				case 1:
					params[0] = getValueforParamMode(paramModes[0], programList, pc + 1);
					params[1] = getValueforParamMode(paramModes[1], programList, pc + 2);
					params[2] = programList[pc + 3];

					result = params[0] + params[1];
					programList[params[2]] = result;
					pc += 4;
					break;
				case 2:
					params[0] = getValueforParamMode(paramModes[0], programList, pc + 1);
					params[1] = getValueforParamMode(paramModes[1], programList, pc + 2);
					params[2] = programList[pc + 3];

					result = params[0] * params[1];
					programList[params[2]] = result;
					pc += 4;
					break;
				case 3:
					System.out.print("Give input: ");
					int input = scanner.nextInt();
					params[0] = programList[pc + 1];
					programList[params[0]] = input;
					pc += 2;
					break;
				case 4:
					params[0] = getValueforParamMode(paramModes[0], programList, pc + 1);
					System.out.println(params[0]);
					pc += 2;
					break;
				case 5:
					params[0] = getValueforParamMode(paramModes[0], programList, pc + 1);
					params[1] = getValueforParamMode(paramModes[1], programList, pc + 2);
					if(params[0] != 0)
						pc = params[1];
					else
						pc += 3;
					break;
				case 6:
					params[0] = getValueforParamMode(paramModes[0], programList, pc + 1);
					params[1] = getValueforParamMode(paramModes[1], programList, pc + 2);
					if(params[0] == 0)
						pc = params[1];
					else
						pc += 3;
					break;
				case 7:
					params[0] = getValueforParamMode(paramModes[0], programList, pc + 1);
					params[1] = getValueforParamMode(paramModes[1], programList, pc + 2);
					params[2] = programList[pc + 3];
					programList[params[2]] = params[0] < params[1] ? 1 : 0;
					pc += 4;
					break;
				case 8:
					params[0] = getValueforParamMode(paramModes[0], programList, pc + 1);
					params[1] = getValueforParamMode(paramModes[1], programList, pc + 2);
					params[2] = programList[pc + 3];
					programList[params[2]] = params[0] == params[1] ? 1 : 0;
					pc += 4;
					break;
				default:
					run = false;
					break;
			}
		}

		scanner.close();
	}

	public int getValueforParamMode(boolean posmode, Integer[] program, int pc)
	{
		if(posmode)
			return program[program[pc]];
		else
			return program[pc];
	}

	public static void main(String[] args)
	{
		new Day5();
	}
}
