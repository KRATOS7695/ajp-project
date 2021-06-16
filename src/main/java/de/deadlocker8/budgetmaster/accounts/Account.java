package de.deadlocker8.budgetmaster.accounts;

import com.google.gson.annotations.Expose;
import de.deadlocker8.budgetmaster.icon.Icon;
import de.deadlocker8.budgetmaster.icon.Iconizable;
import de.deadlocker8.budgetmaster.images.Image;
import de.deadlocker8.budgetmaster.transactions.Transaction;
import de.deadlocker8.budgetmaster.utils.ProvidesID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
public class Account implements ProvidesID, Iconizable
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Expose
	private Integer ID;

	@NotNull
	@Size(min = 1)
	@Column(unique = true)
	@Expose
	private String name;

	@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
	private List<Transaction> referringTransactions;

	private Boolean isSelected = false;
	private Boolean isDefault = false;

	@Deprecated(since = "v2.6.0", forRemoval = true)
	private Boolean isReadOnly = false;

	@Expose
	private AccountState accountState;

	@ManyToOne
	@Deprecated(since = "v2.7.0", forRemoval = true)
	private Image icon;

	@OneToOne(cascade = CascadeType.ALL)
	@Expose
	private Icon iconReference;

	@Expose
	private AccountType type;

	public Account(String name, AccountType type, Icon iconReference)
	{
		this.name = name;
		this.type = type;
		this.isSelected = false;
		this.isDefault = false;
		this.accountState = AccountState.FULL_ACCESS;
		this.iconReference = iconReference;
	}

	public Account(String name, AccountType type)
	{
		this(name, type, null);
	}

	public Account()
	{
	}

	public Integer getID()
	{
		return ID;
	}

	public void setID(Integer ID)
	{
		this.ID = ID;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<Transaction> getReferringTransactions()
	{
		return referringTransactions;
	}

	public void setReferringTransactions(List<Transaction> referringTransactions)
	{
		this.referringTransactions = referringTransactions;
	}

	public Boolean isSelected()
	{
		return isSelected;
	}

	public void setSelected(Boolean selected)
	{
		isSelected = selected;
	}

	public Boolean isDefault()
	{
		return isDefault;
	}

	public void setDefault(Boolean aDefault)
	{
		isDefault = aDefault;
	}

	@Deprecated(since = "v2.6.0", forRemoval = true)
	public Boolean isReadOnly()
	{
		return isReadOnly;
	}

	@Deprecated(since = "v2.6.0", forRemoval = true)
	public void setReadOnly(Boolean readOnly)
	{
		isReadOnly = readOnly;
	}

	public AccountState getAccountState()
	{
		return accountState;
	}

	public void setAccountState(AccountState accountState)
	{
		this.accountState = accountState;
	}

	public AccountType getType()
	{
		return type;
	}

	public void setType(AccountType type)
	{
		this.type = type;
	}

	@Deprecated(since = "v2.7.0", forRemoval = true)
	public Image getIcon()
	{
		return icon;
	}

	@Deprecated(since = "v2.7.0", forRemoval = true)
	public void setIcon(Image icon)
	{
		this.icon = icon;
	}

	public Icon getIconReference()
	{
		return iconReference;
	}

	public void setIconReference(Icon iconReference)
	{
		this.iconReference = iconReference;
	}

	@Override
	public String toString()
	{
		return "Account{" +
				"ID=" + ID +
				", name='" + name + '\'' +
				", referringTransactions=" + referringTransactions +
				", isSelected=" + isSelected +
				", isDefault=" + isDefault +
				", accountState=" + accountState +
				", type=" + type +
				", icon=" + icon +
				", iconReference=" + iconReference +
				'}';
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Account account = (Account) o;
		return Objects.equals(ID, account.ID) &&
				Objects.equals(name, account.name) &&
				Objects.equals(isSelected, account.isSelected) &&
				Objects.equals(isDefault, account.isDefault) &&
				accountState == account.accountState &&
				Objects.equals(icon, account.icon) &&
				Objects.equals(iconReference, account.iconReference) &&
				type == account.type;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(ID, name, isSelected, isDefault, accountState, icon, iconReference, type);
	}
}