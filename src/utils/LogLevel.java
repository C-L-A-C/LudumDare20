package utils;

public enum LogLevel {
	VOID(0), WARNING(1), INFO(2), DEBUG(3), NET_DEBUG(4);
	
	private int lvl;
	
	private LogLevel(int l)
	{
		lvl = l;
	}
	
	public boolean estInclusDans(LogLevel base)
	{
		return base.lvl >= lvl;
	}
	
	public static LogLevel fromLevel(int level)
	{
		return values()[level];
	}

}
