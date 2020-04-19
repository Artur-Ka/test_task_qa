package ua.com.qatestlab.prestashop.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.com.qatestlab.prestashop.enums.Currency;

/**
 * 
 * @author A. Karpenko
 * @date 15 апр. 2020 г. 18:17:42
 */
public class MainPage {

	private static final Logger LOG = LoggerFactory.getLogger(MainPage.class);

	private final WebDriverWait wait;

	@FindBy(id = "_desktop_currency_selector")
	private WebElement currencySelector;

	@FindBy(id = "search_widget")
	private WebElement searchWidget;

	@FindBy(tagName = "article")
	private List<WebElement> productsList;

	public MainPage(WebDriver webDriver) {
		LOG.info("Open main page");
		wait = new WebDriverWait(webDriver, 20);
		PageFactory.initElements(webDriver, this);
	}

	public WebElement getCurrencySelector() {
		return currencySelector;
	}

	public List<WebElement> getProductsList() {
		return productsList;
	}

	public void setCurrencyType(Currency currency) {
		LOG.info("Try to change currency type on: {}", currency);

		final WebElement selectElement = currencySelector.findElement(By.cssSelector(".hidden-sm-down"));
		selectElement.click();

		final WebElement option = wait.until(ExpectedConditions
				.visibilityOf(currencySelector.findElements(By.cssSelector(".dropdown-item")).get(currency.ordinal())));
		option.click();

		LOG.info("Current currency type: {}", currencySelector.getText());
	}

	public void searchInCatalog(String text) {
		LOG.info("Search in catalog by text: {}", text);

		final WebElement input = searchWidget.findElement(By.cssSelector(".ui-autocomplete-input"));
		input.clear();
		input.sendKeys(text);

		wait.until(ExpectedConditions.textToBePresentInElementValue(input, text));
		final WebElement button = searchWidget.findElement(By.tagName("button"));
		button.click();
	}
}
