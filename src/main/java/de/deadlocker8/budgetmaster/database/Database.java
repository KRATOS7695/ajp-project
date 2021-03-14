package de.deadlocker8.budgetmaster.database;

import com.google.gson.annotations.Expose;
import de.deadlocker8.budgetmaster.accounts.Account;
import de.deadlocker8.budgetmaster.categories.Category;
import de.deadlocker8.budgetmaster.charts.Chart;
import de.deadlocker8.budgetmaster.images.Image;
import de.deadlocker8.budgetmaster.templates.Template;
import de.deadlocker8.budgetmaster.transactions.Transaction;

import java.util.List;

public class Database
{
	@Expose
	private final String TYPE = "BUDGETMASTER_DATABASE";

	@Expose
	private final int VERSION = 5;

	@Expose
	private List<Category> categories;

	@Expose
	private List<Account> accounts;

	@Expose
	private List<Transaction> transactions;

	@Expose
	private List<Template> templates;

	@Expose
	private List<Chart> charts;

	@Expose
	private List<Image> images;

	public Database()
	{
	}

	public Database(List<Category> categories, List<Account> accounts, List<Transaction> transactions, List<Template> templates, List<Chart> charts, List<Image> images)
	{
		this.categories = categories;
		this.accounts = accounts;
		this.transactions = transactions;
		this.templates = templates;
		this.charts = charts;
		this.images = images;
	}

	public List<Category> getCategories()
	{
		return categories;
	}

	public List<Account> getAccounts()
	{
		return accounts;
	}

	public List<Transaction> getTransactions()
	{
		return transactions;
	}

	public List<Template> getTemplates()
	{
		return templates;
	}

	public List<Chart> getCharts()
	{
		return charts;
	}

	public List<Image> getImages()
	{
		return images;
	}
}