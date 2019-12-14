package com.theprogrammingturkey.aoc2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day14
{
	private int oreMade = 0;
	private HashMap<String, Long> avialable = new HashMap<>();
	private HashMap<Component, List<Component>> recpies = new HashMap<>();

	public Day14()
	{
		List<String> input = FileUtil.loadFile("res/day14-1.txt");
		for(String recipe : input)
		{
			String[] parts = recipe.split("=>");

			String[] inputsSplit = parts[0].trim().split(",");
			List<Component> inputs = new ArrayList<>();
			for(String in : inputsSplit)
			{
				String[] inComponentSplit = in.trim().split(" ");
				Component inComponent = new Component(inComponentSplit[1], Integer.parseInt(inComponentSplit[0]));
				inputs.add(inComponent);
			}

			String[] output = parts[1].trim().split(" ");
			Component outComponent = new Component(output[1], Integer.parseInt(output[0]));
			recpies.put(outComponent, inputs);
		}

		make("FUEL", 1);
		System.out.println("PART 1: " + oreMade);

//		int low = 0;
//		int high = 20000;
//		while(low < high)
//		{
//			int mid = (low + high) / 2;
//			oreMade = 0;
//			components.clear();
//			components.put("FUEL", 0L);
//
//			for(int i = 0; i < mid; i++)
//				makeMore(fuelComponent);
//
//			if(oreMade > 1000000000000L)
//				high = mid - 1;
//			else if(oreMade < 1000000000000L)
//				low = mid;
//			System.out.println(low + " " + high + " " + oreMade);
//		}

		//System.out.println("PART 2: " + low);
	}

	public long make(String key, long amount)
	{
		System.out.println("MAKE: " + amount + " of " + key);
		if(key.equals("ORE"))
		{
			//System.out.println("MAKE: " + oreMade);
			oreMade += amount;
			return amount;
		}
		if(avialable.containsKey(key))
		{
			long have = avialable.get(key);
			if(have >= amount)
			{
				avialable.put(key, have - amount);
				return amount;
			}
			else
			{
				amount -= avialable.remove(key);
			}
		}

		List<Component> inputs = null;
		long yield = 0;
		for(Component c : recpies.keySet())
		{
			if(c.name.equals(key))
			{
				inputs = recpies.get(c);
				yield = c.amount;
			}
		}

		amount = (amount / yield) + (amount % yield == 0 ? 0 : 1);
		for(Component input : inputs)
		{
			long toMake = (input.amount * amount);
			long made = make(input.name, toMake);
			if(made > toMake)
				avialable.put(input.name, made - toMake);
		}
		return amount * yield;
	}

	public static class Component
	{
		String name;
		int amount;

		public Component(String name, int amount)
		{
			this.name = name;
			this.amount = amount;
		}
	}

	public static void main(String[] args)
	{
		new Day14();
	}

}
