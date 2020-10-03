package utils;

import java.io.PrintStream;

import config.Config;
import config.ConfigKey;

/**
 * Logger thread safe
 * @author adrien
 *
 */
public class Logger {
	
	private static Object lockErr = new Object(), lockOut = new Object();
	private static PrintStream out = System.out, err = System.err;
	private static LogLevel base = LogLevel.fromLevel(Config.readInt(ConfigKey.LOG_LEVEL));
			
	public static void setLogLevel(LogLevel level)
	{
		if (base != level)
		{
			base = level;
			println("Niveau de verbosité défini à : " + base, LogLevel.INFO);
		}
	}
	
	public static LogLevel getLogLevel() {
		return base;
	}
	
	public static void setErrStream(PrintStream e)
	{
		synchronized(lockErr)
		{
			err = e;
		}
	}
	
	public static void setStdStream(PrintStream o)
	{
		synchronized(lockOut)
		{
			out = o;
		}
	}
	
	public static void setStreams(PrintStream err, PrintStream out)
	{
		setErrStream(err);
		setStdStream(out);
	}
	
	public static void printlnErr(String str)
	{
		printErr(str + "\n");
	}
	
	public static void printErr(String str)
	{
		synchronized(lockErr)
		{
			if (err != null)
				err.print(str);
		}
	}
	
	public static void println(String str)
	{
		print(str + "\n");
	}
	
	public static void print(String str)
	{
		print(str, LogLevel.INFO);
	}
	
	public static void println(String str, LogLevel level)
	{
		print(str + "\n", level);
	}
	
	public static void print(String str, LogLevel level)
	{
		if (! level.estInclusDans(base))
			return;
		
		synchronized(lockOut)
		{
			if (out != null)
				out.print(level + "\t| " + str);
		}
	}

}
