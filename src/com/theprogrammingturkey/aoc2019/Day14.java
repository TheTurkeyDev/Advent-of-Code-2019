package com.theprogrammingturkey.aoc2019;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day14
{
	private long oreMade = 0;
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

		request("FUEL", 1);
		System.out.println("PART 1: " + oreMade);
		System.out.println("Calculating part 2! This will take a long time!!!!");

		long low = 0;
		long high = 5000000;
		while(low < high)
		{
			long mid = (low + high) / 2;
			oreMade = 0;
			avialable.clear();

			request("FUEL", mid);
			if(oreMade > 1000000000000L)
				high = mid - 1;
			else if(oreMade < 1000000000000L)
				low = mid + 1;
			else
				low = mid;
			System.out.println("Apart: " + (high - low));
		}

		System.out.println("PART 2: " + low);
	}

	public void request(String key, long amount)
	{
		Component out = null;
		for(Component c : recpies.keySet())
			if(c.name.equals(key))
				out = c;

		long made = avialable.computeIfAbsent(out.name, k -> 0L);

		long multiplier = 0;
		while(made < amount)
		{
			made += out.amount;
			multiplier++;
		}

		for(Component input : recpies.get(out))
		{
			long needed = input.amount * multiplier;
			if(input.name.equals("ORE"))
			{
				oreMade += needed;
			}
			else
			{
				request(input.name, needed);
				avialable.put(input.name, avialable.get(input.name) - needed);
			}
		}


		avialable.put(key, made);
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