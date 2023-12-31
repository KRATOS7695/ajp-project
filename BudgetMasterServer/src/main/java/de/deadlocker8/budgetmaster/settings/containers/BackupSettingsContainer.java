package de.deadlocker8.budgetmaster.settings.containers;

import de.deadlocker8.budgetmaster.backup.AutoBackupStrategy;
import de.deadlocker8.budgetmaster.backup.AutoBackupTime;
import de.deadlocker8.budgetmaster.settings.Settings;
import de.deadlocker8.budgetmaster.settings.SettingsService;
import de.deadlocker8.budgetmaster.utils.Strings;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import static de.deadlocker8.budgetmaster.settings.SettingsController.PASSWORD_PLACEHOLDER;

public final class BackupSettingsContainer implements SettingsContainer
{
	private Boolean backupReminderActivated;

	private String autoBackupStrategyType;
	private Integer autoBackupDays;
	private String autoBackupTimeType;

	private Integer autoBackupFilesToKeep;
	private final String autoBackupGitUrl;
	private final String autoBackupGitBranchName;
	private String autoBackupGitUserName;
	private String autoBackupGitToken;

	public BackupSettingsContainer(Boolean backupReminderActivated, String autoBackupStrategyType, Integer autoBackupDays, String autoBackupTimeType, Integer autoBackupFilesToKeep, String autoBackupGitUrl, String autoBackupGitBranchName, String autoBackupGitUserName, String autoBackupGitToken)
	{
		this.backupReminderActivated = backupReminderActivated;
		this.autoBackupStrategyType = autoBackupStrategyType;
		this.autoBackupDays = autoBackupDays;
		this.autoBackupTimeType = autoBackupTimeType;
		this.autoBackupFilesToKeep = autoBackupFilesToKeep;
		this.autoBackupGitUrl = autoBackupGitUrl;
		this.autoBackupGitBranchName = autoBackupGitBranchName;
		this.autoBackupGitUserName = autoBackupGitUserName;
		this.autoBackupGitToken = autoBackupGitToken;
	}

	public Boolean getBackupReminderActivated()
	{
		return backupReminderActivated;
	}

	public Integer getAutoBackupDays()
	{
		return autoBackupDays;
	}

	public Integer getAutoBackupFilesToKeep()
	{
		return autoBackupFilesToKeep;
	}

	public String getAutoBackupGitUrl()
	{
		return autoBackupGitUrl;
	}

	public String getAutoBackupGitBranchName()
	{
		return autoBackupGitBranchName;
	}

	public String getAutoBackupGitUserName()
	{
		return autoBackupGitUserName;
	}

	public String getAutoBackupGitToken()
	{
		return autoBackupGitToken;
	}

	private AutoBackupStrategy getAutoBackupStrategy()
	{
		if(autoBackupStrategyType == null)
		{
			return AutoBackupStrategy.NONE;
		}
		else
		{
			return AutoBackupStrategy.fromName(autoBackupStrategyType);
		}
	}

	private AutoBackupTime getAutoBackupTime()
	{
		return AutoBackupTime.valueOf(autoBackupTimeType);
	}


	@Override
	public void validate(Errors errors)
	{
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "autoBackupDays", Strings.WARNING_EMPTY_NUMBER);

		if(getAutoBackupStrategy() == AutoBackupStrategy.LOCAL)
		{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "autoBackupFilesToKeep", Strings.WARNING_EMPTY_NUMBER_ZERO_ALLOWED);
		}

		if(getAutoBackupStrategy() == AutoBackupStrategy.GIT_REMOTE)
		{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "autoBackupGitUrl", Strings.WARNING_EMPTY_GIT_URL);
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "autoBackupGitBranchName", Strings.WARNING_EMPTY_GIT_BRANCH_NAME);
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "autoBackupGitUserName", Strings.WARNING_EMPTY_GIT_USER_NAME);
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "autoBackupGitToken", Strings.WARNING_EMPTY_GIT_TOKEN);
		}
	}

	@Override
	public void fixBooleans()
	{
		// nothing to do
	}

	@Override
	public String getErrorLocalizationKey()
	{
		return "notification.settings.backup.error";
	}

	@Override
	public String getSuccessLocalizationKey()
	{
		return "notification.settings.backup.saved";
	}

	@Override
	public String getTemplatePath()
	{
		return "settings/containers/settingsBackup";
	}

	private void fillMissingFieldsWithDefaults(Settings settings)
	{
		if(backupReminderActivated == null)
		{
			backupReminderActivated = false;
		}

		if(autoBackupStrategyType == null)
		{
			autoBackupStrategyType = AutoBackupStrategy.NONE.getName();
		}

		if(autoBackupGitToken.equals(PASSWORD_PLACEHOLDER))
		{
			autoBackupGitToken = settings.getAutoBackupGitToken();
		}

		if(getAutoBackupStrategy() == AutoBackupStrategy.NONE)
		{
			final Settings defaultSettings = Settings.getDefault();
			autoBackupDays = defaultSettings.getAutoBackupDays();
			autoBackupTimeType = defaultSettings.getAutoBackupTime().name();
			autoBackupFilesToKeep = defaultSettings.getAutoBackupFilesToKeep();
			autoBackupGitUserName = defaultSettings.getAutoBackupGitUserName();
			autoBackupGitToken = defaultSettings.getAutoBackupGitToken();
		}
	}

	@Override
	public Settings updateSettings(SettingsService settingsService)
	{
		final Settings settings = settingsService.getSettings();

		fillMissingFieldsWithDefaults(settings);

		settings.setBackupReminderActivated(backupReminderActivated);
		settings.setAutoBackupStrategy(getAutoBackupStrategy());
		settings.setAutoBackupDays(autoBackupDays);
		settings.setAutoBackupTime(getAutoBackupTime());
		settings.setAutoBackupFilesToKeep(autoBackupFilesToKeep);
		settings.setAutoBackupGitUrl(autoBackupGitUrl);
		settings.setAutoBackupGitBranchName(autoBackupGitBranchName);
		settings.setAutoBackupGitUserName(autoBackupGitUserName);
		settings.setAutoBackupGitToken(autoBackupGitToken);

		return settings;
	}

	@Override
	public void persistChanges(SettingsService settingsService, Settings previousSettings, Settings settings)
	{
		settingsService.updateSettings(settings);
	}
}
