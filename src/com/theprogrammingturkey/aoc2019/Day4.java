package com.theprogrammingturkey.aoc2019;

import java.util.ArrayList;
import java.util.List;

public class Day4
{
	public Day4(String low, String high)
	{
		List<Integer> validPasswords = new ArrayList<>();
		int currentPassword = Integer.parseInt(low);
		int endingPassword = Integer.parseInt(high);

		while(currentPassword <= endingPassword)
		{
			int[] currentPasswordDigits = convertToDigits(currentPassword);
			if(meetsCriteria(currentPasswordDigits))
			{
				validPasswords.add(currentPassword);
			}

			currentPassword++;
		}


		System.out.println("Number of passwords: " + validPasswords.size());
	}

	public boolean meetsCriteria(int[] password)
	{
		if(!checkAdjacentDigits(password))
			return false;

		return checkIncreasingDigits(password);
	}

	public boolean checkAdjacentDigits(int[] password)
	{
		int digitCounter = 0;
		int lastDigit = -1;
		for(int i = 0; i < password.length; i++)
		{
			if(lastDigit == password[i])
			{
				digitCounter++;
			}
			else
			{
				if(digitCounter == 2)
					return true;
				digitCounter = 1;
				lastDigit = password[i];
			}
		}

		return digitCounter == 2;
	}

	public boolean checkIncreasingDigits(int[] password)
	{
		for(int i = 1; i < password.length; i++)
			if(password[i] < password[i - 1])
				return false;
		return true;
	}

	public int[] convertToDigits(int pass)
	{
		return convertToDigits(String.valueOf(pass));
	}

	public int[] convertToDigits(String passString)
	{
		int[] digits = new int[6];
		for(int i = 0; i < digits.length; i++)
			digits[i] = Integer.parseInt(passString.substring(i, i + 1));
		return digits;
	}

	public static void main(String[] args)
	{
		List<String> input = FileUtil.loadFile("res/day4-1.txt");
		String[] passwordRange = input.get(0).split("-");
		new Day4(passwordRange[0], passwordRange[1]);
	}
}
