package de.deadlocker8.budgetmaster.transactions.keywords;

import de.deadlocker8.budgetmaster.services.Resettable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class TransactionNameKeywordService implements Resettable
{
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionNameKeywordService.class);
	private static final List<String> DEFAULT_KEYWORDS = List.of("income", "einnahme");

	private final TransactionNameKeywordRepository transactionNameKeywordRepository;


	@Autowired
	public TransactionNameKeywordService(TransactionNameKeywordRepository transactionNameKeywordRepository)
	{
		this.transactionNameKeywordRepository = transactionNameKeywordRepository;

		createDefaults();
	}

	public TransactionNameKeywordRepository getRepository()
	{
		return transactionNameKeywordRepository;
	}

	@Override
	public void deleteAll()
	{
		LOGGER.info("Resetting transaction name keywords...");
		transactionNameKeywordRepository.deleteAll();
		LOGGER.info("All transaction name keywords reset.");
	}

	@Override
	public void createDefaults()
	{
		if(transactionNameKeywordRepository.findAll().isEmpty())
		{
			for(String keyword : DEFAULT_KEYWORDS)
			{
				transactionNameKeywordRepository.save(new TransactionNameKeyword(keyword));
			}
		}
	}

	public List<String> getMatchingKeywords(String text)
	{
		final List<TransactionNameKeyword> keywords = transactionNameKeywordRepository.findAll();
		final String textLowerCase = text.toLowerCase();

		return keywords.stream()
				.map(TransactionNameKeyword::getValue)
				.filter(value -> textLowerCase.contains(value.toLowerCase(Locale.ROOT)))
				.toList();
	}
}
