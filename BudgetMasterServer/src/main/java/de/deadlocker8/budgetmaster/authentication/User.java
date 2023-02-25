package de.deadlocker8.budgetmaster.authentication;

import de.deadlocker8.budgetmaster.accounts.Account;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "login_user")
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ID;

	@NotNull
	@Size(min = 1)
	@Column(unique = true)
	private String name;

	@NotNull
	@Size(min = 1)
	private String password;

	@OneToOne
	private Account selectedAccount;


	public User(String name, String password)
	{
		this.name = name;
		this.password = password;
	}

	public User()
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

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Account getSelectedAccount()
	{
		return selectedAccount;
	}

	public void setSelectedAccount(Account selectedAccount)
	{
		this.selectedAccount = selectedAccount;
	}

	@Override
	public String toString()
	{
		return "User{" +
				"ID=" + ID +
				", name='" + name + '\'' +
				", password='" + password + '\'' +
				", selectedAccount=" + selectedAccount +
				'}';
	}
}