package bmp;

public class Limit
{
	public int xMin;
	public int xMax;
	public int yMin;
	public int yMax;
	
	public Limit()
	{
	}
	
	public Limit(int xMin, int xMax, int yMin, int yMax)
	{
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
	}
	
	public static Limit copy(Limit limit)
	{
		return new Limit(limit.xMin, limit.xMax, limit.yMin, limit.yMax);
	}
	
	public boolean equals(Limit limit)
	{
		return limit != null && this.xMin == limit.xMin && this.xMax == limit.xMax && this.yMin == limit.yMin && this.yMax == limit.yMax;
	}
}