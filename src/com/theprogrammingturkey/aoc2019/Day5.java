package com.theprogrammingturkey.aoc2019;

import java.util.Scanner;

public class Day5
{
	public Day5()
	{
		Scanner scanner = new Scanner(System.in);
		String program = FileUtil.loadFile("res/day5-1.txt").get(0);

		IntCodeProgram currentProgram = new IntCodeProgram(program);

		while(!currentProgram.isHalted())
		{
			if(currentProgram.isWaitingForInput())
			{
				System.out.print("Input: ");
				currentProgram.setInput(scanner.nextLong());
			}
			currentProgram.execute(System.out::println);
		}
		scanner.close();
	}

	public static void main(String[] args)
	{
		new Day5();
	}
}
