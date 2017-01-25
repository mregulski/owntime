package com.zespolowe.server.delays;

import com.zespolowe.server.dataFormats.Point;

public class TimespacePoint
{
	private Point point;
	private int time;
	
	public Point getPoint()
	{
		return this.point;
	}
	public int getTime()
	{
		return this.time;
	}
	
	public TimespacePoint(Point p, int t)// throws Exception
	{
		/*if(p == null)
		{
			throw new Exception("TimespacePoint's constructor called with p = null");
		}*/
		this.point = p;
		this.time = t;
	}
	
	public String toString()
	{
		return "TimespacePoint(point.id = " + this.point.getId() + ", time = " + this.time + ")";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((point == null) ? 0 : point.hashCode());
		result = prime * result + time;
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		TimespacePoint other = (TimespacePoint) obj;
		if(point == null)
		{
			if(other.point != null) return false;
		}
		else if(!point.equals(other.point)) return false;
		if(time != other.time) return false;
		return true;
	}
}