package ua.com.qatestlab.prestashop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.qameta.allure.Description;
import ua.com.qatestlab.prestashop.enums.Currency;
import ua.com.qatestlab.prestashop.enums.SortType;
import ua.com.qatestlab.prestashop.pages.MainPage;
import ua.com.qatestlab.prestashop.pages.SearchPage;

/**
 * 
 * @author A. Karpenko
 * @date 15 апр. 2020 г. 18:18:40
 */
public class PrestashopAutomationTests {

	private static final Logger LOG = LoggerFactory.getLogger("Test");

	private static PrestashopAutomation webSite;

	@BeforeClass
	public static void setUp() {
		webSite = new PrestashopAutomation();
	}

	@Test
	@Description(value = "Проверка соответсвия валюты товаров и цены, указанной в шапке сайта.")
	public void testPrice() {
		final MainPage mainPage = webSite.getMainPage();
		for (WebElement product : mainPage.getProductsList()) {
			assertEquals("Product currency does not match declared",
					Currency.getByText(mainPage.getCurrencySelector().getText()),
					Currency.getBySymbol(product.findElement(By.cssSelector(".price")).getText().split(" ")[1]));
		}
		LOG.info("Price test passed");
	}

	@Test
	@Description(value = "Проверка изменения типа валюты.")
	public void changeCurrencyTypeTest() {
		final MainPage mainPage = webSite.getMainPage();
		mainPage.setCurrencyType(Currency.USD);
		assertEquals("Currency type has not changed", Currency.getByText(mainPage.getCurrencySelector().getText()),
				Currency.USD);
		LOG.info("Change currency test passed");
	}

	@Test
	@Description(value = "Проверка соответсвия указателя количества товаров реальному количеству найденных товаров по слову \"dress\".")
	public void searchTest() {
		webSite.getMainPage().searchInCatalog("dress");
		final SearchPage searchPage = webSite.getSearchPage();
		assertEquals("The number of products found does not match the declared", searchPage.getNumberOfFound(),
				searchPage.getProductsList().size());
		LOG.info("Search test passed");
	}

	@Test
	@Description(value = "Проверка соответсвия валюты найденных товаров USD.")
	public void searchCurrencyTypeTest() {
		for (WebElement product : webSite.getMainPage().getProductsList()) {
			assertEquals("Product price not in USD", Currency.USD,
					Currency.getBySymbol(product.findElement(By.cssSelector(".price")).getText().split(" ")[1]));
		}
		LOG.info("Search currency test passed");
	}

	@Test
	@Description(value = "Проверка сортировки товаров по цене \"от низкой к высокой\".")
	public void sortPriceTest() {
		final SearchPage searchPage = webSite.getSearchPage();
		searchPage.sortProducts(SortType.PRICE_ASC);

		String priceString;
		double oldPrice = 0;
		double price;
		for (WebElement product : searchPage.getProductsList()) {
			priceString = product.findElement(By.cssSelector(".price")).getText().split(" ")[0];

			price = Double.parseDouble(priceString.replace(',', '.'));
			assertTrue("Product not sorted", price >= oldPrice);
			oldPrice = price;
		}
		LOG.info("Sort price test passed");
	}

	@Test
	@Description(value = "Проверка соответствия цены товаров заявленной скидке.")
	public void sortPriceDiscountTest() {
		final SearchPage searchPage = webSite.getSearchPage();

		String regularPriceString;
		String priceString;
		double price;
		for (WebElement product : searchPage.getProductsList()) {

			try {
				regularPriceString = product.findElement(By.cssSelector(".regular-price")).getText().split(" ")[0];
				priceString = product.findElement(By.cssSelector(".price")).getText().split(" ")[0];

				price = Double.parseDouble(priceString.replace(',', '.'));

				if (regularPriceString != null) {
					final String percentageString = product.findElement(By.cssSelector(".discount-percentage"))
							.getText();
					final double percentage = Double
							.parseDouble(percentageString.substring(1, percentageString.length() - 1));
					final double regularPrice = Double.parseDouble(regularPriceString.replace(',', '.'));

					assertEquals("Product discount does not match claimed",
							regularPrice - (regularPrice / 100. * percentage), price, 0.1);
				}
			} catch (NoSuchElementException e) {

			}
		}
		LOG.info("Sort price discount test passed");
	}

	@AfterClass
	public static void cleanUp() {
		webSite.cleanUp();
	}
}
