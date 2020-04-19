package ua.com.qatestlab.prestashop.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.com.qatestlab.prestashop.enums.SortType;

/**
 * 
 * @author A. Karpenko
 * @date 16 апр. 2020 г. 20:39:09
 */
public class SearchPage {

	private static final Logger LOG = LoggerFactory.getLogger(SearchPage.class);

	private final WebDriverWait wait;

	@FindBy(xpath = "//div[@class='col-md-6 hidden-sm-down total-products']")
	private WebElement productsNumber;

	@FindBy(xpath = "//div[@class='col-sm-12 col-xs-12 col-md-9 products-sort-order dropdown']")
	private WebElement sortSelector;

	@FindBy(xpath = "//div[@class='dropdown-menu']")
	private WebElement dropdownMenu;

	@FindBy(tagName = "article")
	private List<WebElement> productsList;

	public SearchPage(WebDriver webDriver) {
		LOG.info("Open search page");
		wait = new WebDriverWait(webDriver, 20);
		PageFactory.initElements(webDriver, this);
	}

	public List<WebElement> getProductsList() {
		return productsList;
	}

	public int getNumberOfFound() {
		final String countString = productsNumber.getText().split(" ")[1];
		return Integer.parseInt(countString.substring(0, countString.length() - 1));
	}

	public void sortProducts(SortType sortType) {
		LOG.info("Try to set sort type on: {}", sortType);

		// To know than list was updated
		final int hash = productsList.hashCode();

		sortSelector.click();

		final WebElement option = wait.until(ExpectedConditions
				.elementToBeClickable(dropdownMenu.findElements(By.tagName("a")).get(sortType.ordinal())));
		option.click();

		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver input) {
				return productsList.hashCode() != hash;
			}
		});

		LOG.info("Current sort type: {}", sortSelector.getText());
	}
}
