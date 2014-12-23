package com.xhb.performance;

public class TimeAnalysis {
	
	private long start;
	
	public TimeAnalysis(){
		start = System.currentTimeMillis();
	}

	public void spendTime()	{
		long deta = System.currentTimeMillis() - start;
		System.out.println("spend time:"+deta/1000+"s");
	}
}
