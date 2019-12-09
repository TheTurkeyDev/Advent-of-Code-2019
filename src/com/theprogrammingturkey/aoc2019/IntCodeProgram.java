package com.theprogrammingturkey.aoc2019;

import java.util.Arrays;

public class IntCodeProgram
{
	private long[] memory;
	private int pc = 0;

	private boolean isHalted = false;
	private boolean run = true;
	private long[] params = new long[3];
	private int[] paramModes = new int[3];
	private int relativeBase = 0;

	private boolean waitingForInput = false;


	public IntCodeProgram(long[] programList)
	{
		memory = Arrays.copyOf(programList, 10000);
	}

	public void execute(Output out)
	{
		while(run && !isHalted)
		{
			String instruction = String.valueOf(memory[pc]);
			for(int i = instruction.length(); i < 5; i++)
				instruction = "0" + instruction;

			int opcode = Integer.parseInt(instruction.substring(instruction.length() - 2));
			paramModes[0] = instruction.charAt(2) - 48;
			paramModes[1] = instruction.charAt(1) - 48;
			paramModes[2] = instruction.charAt(0) - 48;

			long result;
			switch(opcode)
			{
				case 1:
					params[0] = getValueforParamMode(paramModes[0], pc + 1);
					params[1] = getValueforParamMode(paramModes[1], pc + 2);

					result = params[0] + params[1];
					setValueforParamMode(paramModes[2], pc + 3, result);
					pc += 4;
					break;
				case 2:
					params[0] = getValueforParamMode(paramModes[0], pc + 1);
					params[1] = getValueforParamMode(paramModes[1], pc + 2);

					result = params[0] * params[1];
					setValueforParamMode(paramModes[2], pc + 3, result);
					pc += 4;
					break;
				case 3:
					run = false;
					waitingForInput = true;
					break;
				case 4:
					params[0] = getValueforParamMode(paramModes[0], pc + 1);
					out.write(params[0]);
					pc += 2;
					break;
				case 5:
					params[0] = getValueforParamMode(paramModes[0], pc + 1);
					params[1] = getValueforParamMode(paramModes[1], pc + 2);
					if(params[0] != 0)
						pc = (int) params[1];
					else
						pc += 3;
					break;
				case 6:
					params[0] = getValueforParamMode(paramModes[0], pc + 1);
					params[1] = getValueforParamMode(paramModes[1], pc + 2);
					if(params[0] == 0)
						pc = (int) params[1];
					else
						pc += 3;
					break;
				case 7:
					params[0] = getValueforParamMode(paramModes[0], pc + 1);
					params[1] = getValueforParamMode(paramModes[1], pc + 2);
					setValueforParamMode(paramModes[2], pc + 3, params[0] < params[1] ? 1 : 0);
					pc += 4;
					break;
				case 8:
					params[0] = getValueforParamMode(paramModes[0], pc + 1);
					params[1] = getValueforParamMode(paramModes[1], pc + 2);
					setValueforParamMode(paramModes[2], pc + 3, params[0] == params[1] ? 1 : 0);
					pc += 4;
					break;
				case 9:
					params[0] = getValueforParamMode(paramModes[0], pc + 1);
					this.relativeBase += params[0];
					pc += 2;
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

	public void setInput(long input)
	{
		if(waitingForInput)
		{
			setValueforParamMode(paramModes[0], pc + 1, input);
			pc += 2;
			waitingForInput = false;
			run = true;
		}
	}

	public boolean isHalted()
	{
		return isHalted;
	}

	public long getValueforParamMode(int posmode, int pc)
	{
		if(posmode == 0)
			return memory[(int) memory[pc]];
		else if(posmode == 1)
			return memory[pc];
		else
			return memory[relativeBase + (int) memory[pc]];
	}

	public void setValueforParamMode(int posmode, int pc, long value)
	{
		if(posmode == 0 || posmode == 1)
			memory[(int) memory[pc]] = value;
		else
			memory[relativeBase + (int) memory[pc]] = value;
	}

	public interface Output
	{
		void write(long out);
	}
}
