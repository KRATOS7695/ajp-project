package de.deadlocker8.budgetmaster.backup;

import de.deadlocker8.budgetmaster.database.DatabaseService;
import de.deadlocker8.budgetmaster.settings.SettingsService;
import de.deadlocker8.budgetmaster.utils.DateHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;

public abstract class GitBackupTask extends BackupTask
{
	private static final Logger LOGGER = LoggerFactory.getLogger(GitBackupTask.class);
	private static final String DATE_PATTERN = "yyyy-MM-dd_HH-mm-ss";

	protected Path gitFolder;

	protected GitBackupTask(DatabaseService databaseService, SettingsService settingsService)
	{
		super(databaseService, settingsService);
		this.gitFolder = getBackupFolder().resolve("git/.git");
	}

	public void setGitFolder(Path gitFolder)
	{
		this.gitFolder = gitFolder;
	}

	protected abstract AutoBackupStrategy getBackupStrategy();

	protected boolean addAndCommitChanges(Git git) throws GitAPIException, GitBackupException
	{
		if(!GitHelper.isFileUntracked(git, DATABASE_FILE_NAME))
		{
			// skip if database file is not modified and not (already) staged for commit
			if(!GitHelper.isFileModified(git, DATABASE_FILE_NAME) && !GitHelper.isFileAddedOrChanged(git, DATABASE_FILE_NAME))
			{
				LOGGER.debug(MessageFormat.format("Skipping commit because \"{0}\" is not modified", DATABASE_FILE_NAME));
				LOGGER.debug("Backup DONE");
				return false;
			}
		}

		git.add().addFilepattern(DATABASE_FILE_NAME).call();

		// check if database file is successfully added
		if(!GitHelper.isFileAddedOrChanged(git, DATABASE_FILE_NAME))
		{
			setBackupStatus(BackupStatus.ERROR);
			throw new GitBackupException(MessageFormat.format("Error adding \"{0}\" to git", DATABASE_FILE_NAME));
		}

		LOGGER.debug("Committing changes...");
		GitHelper.commitChanges(git, DateHelper.getCurrentDateTime().format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
		return true;
	}

	protected void exportDatabase()
	{
		databaseService.exportDatabase(gitFolder.getParent().resolve(DATABASE_FILE_NAME));
	}
}
