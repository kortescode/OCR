package util;

public class ArgumentsException extends Exception
{

	public ArgumentsException()
	{
		super("Invalid Arguments");
	}

	public ArgumentsException(String msg)
	{
		super(msg);
	}
}
