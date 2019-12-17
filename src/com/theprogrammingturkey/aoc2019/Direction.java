package com.theprogrammingturkey.aoc2019;

public enum Direction
{
	None(-1, -1, -1),
	North(0, 3, 2),
	South(1, 2, 3),
	East(2, 0, 1),
	West(3, 1, 0);

	private int id;
	private int leftID;
	private int rightID;

	Direction(int id, int leftID, int rightID)
	{
		this.id = id;
		this.leftID = leftID;
		this.rightID = rightID;
	}

	public Direction turnLeft()
	{
		return getDirectionForID(leftID);
	}

	public Direction turnRight()
	{
		return getDirectionForID(rightID);
	}

	public static Direction getDirectionForID(int id)
	{
		for(Direction d : Direction.values())
			if(d.id == id)
				return d;
		return Direction.North;
	}
}
