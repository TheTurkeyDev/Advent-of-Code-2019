package com.theprogrammingturkey.aoc2019;

public class IntCodeProgram
{
	private Integer[] programList;
	private int pc = 0;
	private boolean isHalted = false;
	private boolean run = true;
	private int[] params = new int[3];
	private boolean[] paramModes = new boolean[3];

	private boolean waitingForInput = false;
	public int lastOutput = 0;


	public IntCodeProgram(Integer[] programList)
	{
		this.programList = programList;
	}

	public void execute()
	{
		while(run && !isHalted)
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
					run = false;
					waitingForInput = true;
					break;
				case 4:
					params[0] = getValueforParamMode(paramModes[0], programList, pc + 1);
					lastOutput = params[0];
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
					isHalted = true;
					break;
			}
		}
	}

	public boolean isWaitingForInput()
	{
		return waitingForInput;
	}

	public void setInput(int input)
	{
		if(waitingForInput)
		{
			params[0] = programList[pc + 1];
			programList[params[0]] = input;
			pc += 2;
			waitingForInput = false;
			run = true;
		}
	}

	public boolean isHalted()
	{
		return isHalted;
	}

	public int getValueforParamMode(boolean posmode, Integer[] program, int pc)
	{
		if(posmode)
			return program[program[pc]];
		else
			return program[pc];
	}
}
