package de.deadlocker8.budgetmaster.services;

import de.deadlocker8.budgetmaster.settings.SettingsService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DateService
{
	private final SettingsService settingsService;

	@Autowired
	public DateService(SettingsService settingsService)
	{
		this.settingsService = settingsService;
	}

	public String getDateStringNormal(DateTime date)
	{
		return getDateString(date, DateFormatStyle.NORMAL);
	}

	public String getDateStringWithoutYear(DateTime date)
	{
		return getDateString(date, DateFormatStyle.NO_YEAR);
	}

	public String getLongDateString(DateTime date)
	{
		return getDateString(date, DateFormatStyle.LONG);
	}

	public String getDateStringWithMonthAndYear(DateTime date)
	{
		return getDateString(date, DateFormatStyle.LONG_MONTH_AND_YEAR);
	}

	public String getDateTimeString(DateTime date)
	{
		return getDateString(date, DateFormatStyle.DATE_TIME);
	}

	private String getDateString(DateTime date, DateFormatStyle formatStyle)
	{
		return date.toString(DateTimeFormat.forPattern(formatStyle.getKey()).withLocale(settingsService.getSettings().getLanguage().getLocale()));
	}

	public DateTime getDateTimeFromCookie(String cookieDate)
	{
		if(cookieDate == null)
		{
			return DateTime.now();
		}
		else
		{
			return DateTime.parse(cookieDate, DateTimeFormat.forPattern(DateFormatStyle.NORMAL.getKey()).withLocale(settingsService.getSettings().getLanguage().getLocale()));
		}
	}

	public DateTime getCurrentDate()
	{
		return DateTime.now();
	}

	public String getDateTimeString(LocalDateTime localDateTime)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateFormatStyle.DATE_TIME.getKey()).withLocale(settingsService.getSettings().getLanguage().getLocale());
		return localDateTime.format(formatter);
	}
}
