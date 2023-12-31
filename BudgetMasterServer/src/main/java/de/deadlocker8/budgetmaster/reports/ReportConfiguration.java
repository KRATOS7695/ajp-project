package de.deadlocker8.budgetmaster.reports;

import de.deadlocker8.budgetmaster.reports.settings.ReportSettings;
import de.deadlocker8.budgetmaster.transactions.Transaction;
import de.deadlocker8.budgetmaster.reports.categoryBudget.CategoryBudget;

import java.util.List;

public class ReportConfiguration
{
	private final List<Transaction> transactions;
	private final String accountName;
	private final List<CategoryBudget> categoryBudgets;
	private final ReportSettings reportSettings;
	private final Budget budget;

	public ReportConfiguration(List<Transaction> transactions, String accountName, List<CategoryBudget> categoryBudgets, ReportSettings reportSettings, Budget budget)
	{
		this.transactions = transactions;
		this.accountName = accountName;
		this.categoryBudgets = categoryBudgets;
		this.reportSettings = reportSettings;
		this.budget = budget;
	}

	public List<Transaction> getTransactions()
	{
		return transactions;
	}

	public List<CategoryBudget> getCategoryBudgets()
	{
		return categoryBudgets;
	}

	public String getAccountName()
	{
		return accountName;
	}

	public ReportSettings getReportSettings()
	{
		return reportSettings;
	}

	public Budget getBudget()
	{
		return budget;
	}

	@Override
	public String toString()
	{
		return "ReportConfiguration{" +
				"transactions=" + transactions +
				", accountName='" + accountName + '\'' +
				", categoryBudgets=" + categoryBudgets +
				", reportSettings=" + reportSettings +
				", budget=" + budget +
				'}';
	}
}
