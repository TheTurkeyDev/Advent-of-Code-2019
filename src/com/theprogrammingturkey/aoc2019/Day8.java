package com.theprogrammingturkey.aoc2019;

import java.util.ArrayList;
import java.util.List;

public class Day8
{
	private static final int rows = 6;
	private static final int cols = 25;

	public Day8()
	{
		String input = FileUtil.loadFile("res/day8-1.txt").get(0);
		List<char[][]> layers = new ArrayList<>();
		int i = 0;
		int fewestZerosLayer = 0;
		int fewestZeros = Integer.MAX_VALUE;

		while(i < input.length())
		{
			char[][] layer = new char[rows][cols];
			int zeros = 0;
			for(int row = 0; row < rows; row++)
			{
				for(int col = 0; col < cols; col++)
				{
					char c = input.charAt(i);
					if(c == '0')
						zeros++;
					layer[row][col] = input.charAt(i);
					i++;
				}
			}
			if(zeros < fewestZeros)
			{
				fewestZeros = zeros;
				fewestZerosLayer = layers.size();
			}
			layers.add(layer);
		}

		char[][] layer = layers.get(fewestZerosLayer);
		int numOnes = 0;
		int numTwos = 0;
		for(int row = 0; row < rows; row++)
		{
			for(int col = 0; col < cols; col++)
			{
				if(layer[row][col] == '1')
					numOnes++;
				else if(layer[row][col] == '2')
					numTwos++;
			}
		}

		System.out.println("Part 1: " + (numOnes * numTwos));
		System.out.println("Part 2:");
		for(int row = 0; row < rows; row++)
		{
			for(int col = 0; col < cols; col++)
			{
				int layerIndex = 0;
				layer = layers.get(layerIndex);
				while(layer[row][col] == '2')
				{
					layerIndex++;
					layer = layers.get(layerIndex);
				}

				if(layer[row][col] == '1')
					System.out.print("█");
				else
					System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println("");
	}


	public static void main(String[] args)
	{
		new Day8();
	}
}
