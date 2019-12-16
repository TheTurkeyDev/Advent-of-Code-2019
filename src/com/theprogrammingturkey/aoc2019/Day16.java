package com.theprogrammingturkey.aoc2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day16
{
	public Day16()
	{
		String input = FileUtil.loadFile("res/day16-1.txt").get(0);
		int firstSeven = 0;
		Integer[] fft = new Integer[input.length()];
		for(int i = 0; i < fft.length; i++)
		{
			fft[i] = input.charAt(i) - 48;
			if(i < 7)
				firstSeven = (firstSeven * 10) + (input.charAt(i) - 48);
		}

		System.out.println(firstSeven);

		Integer[] fftCopy = fft.clone();

		int[] base = new int[]{0, 1, 0, -1};
		for(int i = 0; i < 100; i++)
		{
			Integer[] newfft = fft.clone();
			for(int j = 0; j < fft.length; j++)
			{
				int index = 1;
				int repeat = j + 1;
				int val = 0;
				for(int k = j; k < fft.length; k++)
				{
					repeat--;
					if(repeat < 0)
					{
						repeat = j;
						index = (index + 1) % base.length;
					}
					val += fft[k] * base[index];
				}
				newfft[j] = Math.abs(val) % 10;
			}
			fft = newfft;
		}
		System.out.println(Arrays.toString(fft));

		List<Integer> list = new ArrayList<>();

		for(int i = 0; i < 10000; i++)
			list.addAll(Arrays.asList(fftCopy));

		fft = list.toArray(new Integer[0]);

		for(int i = 0; i < 100; i++)
		{
			Integer[] newfft = fft.clone();
			int val = 0;
			for(int j = fft.length - 1; j >= firstSeven; j--)
			{
				val += fft[j];
				newfft[j] = Math.abs(val) % 10;
			}
			fft = newfft;
		}

		System.out.println(Arrays.toString(Arrays.copyOfRange(fft, firstSeven, firstSeven + 8)));
	}

	public static void main(String[] args)
	{
		new Day16();
	}
}
