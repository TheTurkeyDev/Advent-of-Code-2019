package com.theprogrammingturkey.aoc2019;

import java.util.List;

public class Day1
{
	public static void main(String[] args)
	{
		List<String> input = FileUtil.loadFile("res/day1-1.txt");
		int sum = 0;
		for(String s : input)
		{
			int mass = Integer.parseInt(s);
			while(mass > 0)
			{
				int result = (mass / 3) - 2;
				if(result > 0)
					sum += result;
				mass = result;
			}
		}
		System.out.println(sum);
	}
}
