package de.deadlocker8.budgetmaster.integration.selenium.settings;

import de.deadlocker8.budgetmaster.authentication.UserService;
import de.deadlocker8.budgetmaster.integration.helpers.IntegrationTestHelper;
import de.deadlocker8.budgetmaster.integration.helpers.SeleniumTestBase;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class TransactionSettingsTest extends SeleniumTestBase
{
	private static IntegrationTestHelper helper;

	@Override
	protected void importDatabaseOnce()
	{
		helper = new IntegrationTestHelper(driver, port);
		helper.start();
		helper.login(UserService.DEFAULT_PASSWORD);
		helper.hideBackupReminder();
		helper.hideWhatsNewDialog();
	}

	@Test
	void test_unsavedChangesWarningIsShown()
	{
		driver.get(helper.getUrl() + "/settings");

		driver.findElement(By.id("transactionsSettingsContainerHeader")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("transactionsSettingsContainer")));

		driver.findElement(By.cssSelector("#transactionsSettingsContainer .lever")).click();

		// assert
		assertThat(driver.findElement(By.cssSelector("#transactionsSettingsContainerHeader .collapsible-header-button")).getText())
				.contains("unsaved");
	}

	@Test
	void test_save()
	{
		driver.get(helper.getUrl() + "/settings");

		driver.findElement(By.id("transactionsSettingsContainerHeader")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("transactionsSettingsContainer")));

		driver.findElement(By.cssSelector("#transactionsSettingsContainer .lever")).click();

		final WebElement keywordInput = driver.findElement(By.cssSelector("#settings-keywords input"));
		keywordInput.click();
		keywordInput.sendKeys("abc");
		keywordInput.sendKeys(Keys.ENTER);

		driver.findElement(By.cssSelector("#transactionsSettingsContainer button")).click();

		wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast")));

		// assert
		assertThat(driver.findElement(By.className("toast")).getText())
				.contains("Transactions settings saved");

		assertThat(driver.findElement(By.cssSelector("#transactionsSettingsContainerHeader .collapsible-header-button")).isDisplayed())
				.isFalse();

		final List<WebElement> chips = driver.findElements(By.cssSelector("#settings-keywords .chip"));
		assertThat(chips.get(chips.size() - 1).getText())
				.contains("abc");
	}
}