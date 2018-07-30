package de.deadlocker8.budgetmaster.database;

import com.google.gson.annotations.Expose;
import de.deadlocker8.budgetmaster.entities.Account;
import de.deadlocker8.budgetmaster.entities.Category;
import de.deadlocker8.budgetmaster.entities.Payment;

import java.util.List;

public class Database
{
	@Expose
	private final String TYPE = "BUDGETMASTER_DATABASE";

	@Expose
	private final int VERSION = 3;

	@Expose
	private List<Category> categories;

	@Expose
	private List<Account> accounts;

	@Expose
	private List<Payment> payments;

	public Database()
	{
	}

	public Database(List<Category> categories, List<Account> accounts, List<Payment> payments)
	{
		this.categories = categories;
		this.accounts = accounts;
		this.payments = payments;
	}

	public List<Category> getCategories()
	{
		return categories;
	}

	public List<Account> getAccounts()
	{
		return accounts;
	}

	public List<Payment> getPayments()
	{
		return payments;
	}
}