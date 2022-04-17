package de.deadlocker8.budgetmaster.migration;

public class MigrationException extends Exception
{
	public MigrationException(String message)
	{
		super(message);
	}

	public MigrationException(String message, Throwable cause)
	{
		super(message, cause);
	}
}

