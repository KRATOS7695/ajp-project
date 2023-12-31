package de.deadlocker8.budgetmaster.transactions;

import de.deadlocker8.budgetmaster.accounts.AccountState;
import de.deadlocker8.budgetmaster.categories.Category;
import de.deadlocker8.budgetmaster.search.Search;
import de.deadlocker8.budgetmaster.tags.Tag;
import de.deadlocker8.budgetmaster.tags.Tag_;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TransactionSearchSpecifications
{
	private TransactionSearchSpecifications()
	{
	}

	public static Specification<Transaction> withDynamicQuery(final Search search)
	{
		return (transaction, query, builder) -> {
			final String searchText = search.getSearchText().toLowerCase();
			final List<String> searchTextParts = Arrays.stream(searchText.split(" "))
					.map(part -> "%" + part + "%")
					.toList();

			List<Predicate> predicates = new ArrayList<>();

			if(search.isSearchName())
			{
				final List<Predicate> predicatesName = new ArrayList<>();
				for(String part : searchTextParts)
				{
					predicatesName.add(builder.like(builder.lower(transaction.get(Transaction_.name)), part));
				}

				final Predicate[] predicatesArray = new Predicate[predicatesName.size()];
				predicates.add(builder.and(predicatesName.toArray(predicatesArray)));
			}

			if(search.isSearchDescription())
			{
				final List<Predicate> predicatesDescription = new ArrayList<>();
				for(String part : searchTextParts)
				{
					predicatesDescription.add(builder.like(builder.lower(transaction.get(Transaction_.description)), part));
				}

				final Predicate[] predicatesArray = new Predicate[predicatesDescription.size()];
				predicates.add(builder.and(predicatesDescription.toArray(predicatesArray)));
			}

			if(search.isSearchCategory())
			{
				final List<Predicate> predicatesCategories = new ArrayList<>();

				Join<Transaction, Category> categoryJoin = transaction.join(Transaction_.category, JoinType.INNER);
				for(String part : searchTextParts)
				{
					predicatesCategories.add(builder.like(builder.lower(categoryJoin.get("name").as(String.class)), part));
				}

				final Predicate[] predicatesArray = new Predicate[predicatesCategories.size()];
				predicates.add(builder.and(predicatesCategories.toArray(predicatesArray)));
			}

			if(search.isSearchTags())
			{
				final List<Predicate> predicatesTags = new ArrayList<>();
				Join<Transaction, Tag> tagJoin = transaction.join(Transaction_.tags, JoinType.LEFT);

				for(String part : searchTextParts)
				{
					predicatesTags.add(builder.like(builder.lower(tagJoin.get(Tag_.name).as(String.class)), part));
				}

				final Predicate[] predicatesArray = new Predicate[predicatesTags.size()];
				predicates.add(builder.and(predicatesTags.toArray(predicatesArray)));
			}

			Predicate[] predicatesArray = new Predicate[predicates.size()];
			Predicate predicatesCombined = builder.or(predicates.toArray(predicatesArray));

			Predicate accountStatePredicate = transaction.get(Transaction_.account).get("accountState").in(getAllowedAccountStates(search));

			query.orderBy(builder.desc(transaction.get(Transaction_.date)));
			query.distinct(true);

			final Predicate allPredicates = builder.and(accountStatePredicate, predicatesCombined);

			final Optional<Predicate> datePredicateOptional = getDatePredicate(builder, transaction, search);
			if(datePredicateOptional.isPresent())
			{
				return builder.and(datePredicateOptional.get(), allPredicates);
			}

			return allPredicates;
		};
	}

	private static List<AccountState> getAllowedAccountStates(Search search)
	{
		List<AccountState> allowedAccountStates = new ArrayList<>();
		allowedAccountStates.add(AccountState.FULL_ACCESS);
		allowedAccountStates.add(AccountState.READ_ONLY);

		if(search.isIncludeHiddenAccounts())
		{
			allowedAccountStates.add(AccountState.HIDDEN);
		}

		return allowedAccountStates;
	}

	private static Optional<Predicate> getDatePredicate(CriteriaBuilder builder, Root<Transaction> transaction, Search search)
	{
		if(search.getStartDate() != null && search.getEndDate() != null)
		{
			return Optional.of(builder.between(transaction.get(Transaction_.date), search.getStartDate(), search.getEndDate()));
		}

		if(search.getStartDate() != null)
		{
			return Optional.of(builder.between(transaction.get(Transaction_.date), search.getStartDate(), LocalDate.of(2100, 1, 1)));
		}

		if(search.getEndDate() != null)
		{
			return Optional.of(builder.between(transaction.get(Transaction_.date), LocalDate.of(2000, 1, 1), search.getEndDate()));
		}

		return Optional.empty();
	}
}
