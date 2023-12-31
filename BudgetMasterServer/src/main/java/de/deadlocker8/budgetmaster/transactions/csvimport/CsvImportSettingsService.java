package de.deadlocker8.budgetmaster.transactions.csvimport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;

@Service
public class CsvImportSettingsService
{
	private static final Logger LOGGER = LoggerFactory.getLogger(CsvImportSettingsService.class);

	private final CsvImportSettingsRepository csvImportSettingsRepository;

	@Autowired
	public CsvImportSettingsService(CsvImportSettingsRepository csvImportSettingsRepository)
	{
		this.csvImportSettingsRepository = csvImportSettingsRepository;
	}

	@PostConstruct
	public void postInit()
	{
		this.createDefaultSettingsIfNotExists();
	}

	public void createDefaultSettingsIfNotExists()
	{
		if(csvImportSettingsRepository.findById(1).isEmpty())
		{
			csvImportSettingsRepository.save(CsvImportSettings.getDefault());
			LOGGER.debug("Created default csv import settings");
		}
	}

	public CsvImportSettings getCsvImportSettings()
	{
		return csvImportSettingsRepository.findById(1).orElseThrow();
	}

	@Transactional
	public void updateSettings(CsvImport csvImport)
	{
		final CsvImportSettings settings = getCsvImportSettings();
		if(hasContent(csvImport.separator()))
		{
			settings.setSeparatorChar(csvImport.separator());
		}

		if(hasContent(csvImport.encoding()))
		{
			settings.setEncoding(csvImport.encoding());
		}

		settings.setNumberOfLinesToSkip(csvImport.numberOfLinesToSkip());
	}

	@Transactional
	public void updateSettings(CsvColumnSettings columnSettings)
	{
		final CsvImportSettings settings = getCsvImportSettings();

		settings.setColumnDate(columnSettings.columnDate());

		if(hasContent(columnSettings.datePattern()))
		{
			settings.setDatePattern("dd.MM.yyyy");
		}

		settings.setColumnName(columnSettings.columnName());
		settings.setColumnAmount(columnSettings.columnAmount());

		if(hasContent(columnSettings.decimalSeparator()))
		{
			settings.setDecimalSeparator(columnSettings.decimalSeparator());
		}
		if(hasContent(columnSettings.groupingSeparator()))
		{
			settings.setGroupingSeparator(columnSettings.groupingSeparator());
		}

		settings.setColumnDescription(columnSettings.columnDescription());
	}

	@Transactional
	public void updateSettings(CsvImportSettings newSettings)
	{
		final CsvImportSettings settings = getCsvImportSettings();

		settings.setSeparatorChar(newSettings.getSeparatorChar());
		settings.setEncoding(newSettings.getEncoding());
		settings.setNumberOfLinesToSkip(newSettings.getNumberOfLinesToSkip());
		settings.setColumnDate(newSettings.getColumnDate());
		settings.setDatePattern(newSettings.getDatePattern());
		settings.setColumnName(newSettings.getColumnName());
		settings.setColumnAmount(newSettings.getColumnAmount());
		settings.setDecimalSeparator(newSettings.getDecimalSeparator());
		settings.setGroupingSeparator(newSettings.getGroupingSeparator());
		settings.setColumnDescription(newSettings.getColumnDescription());
	}

	private boolean hasContent(String value)
	{
		if(value == null)
		{
			return false;
		}

		if(value.isEmpty())
		{
			return false;
		}

		return !value.isBlank();
	}

	public CsvImportSettingsRepository getRepository()
	{
		return csvImportSettingsRepository;
	}
}