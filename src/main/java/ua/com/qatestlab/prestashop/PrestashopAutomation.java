package ua.com.qatestlab.prestashop;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.com.qatestlab.prestashop.pages.MainPage;
import ua.com.qatestlab.prestashop.pages.SearchPage;

/**
 * 
 * @author A. Karpenko
 * @date 15 апр. 2020 г. 18:11:17
 */
public class PrestashopAutomation {

	public static final String URL = "http://prestashop-automation.qatestlab.com.ua/ru/";
	public static final String DRIVER_PATH = "drivers/chromedriver.exe";

	private static final Logger LOG = LoggerFactory.getLogger(PrestashopAutomation.class);

	private final WebDriver webDriver;

	private MainPage mainPage;
	private SearchPage searchPage;

	public PrestashopAutomation() {

		try {
			final String driverPath = ClassLoader.getSystemResource(DRIVER_PATH).getPath().replaceAll("%20", " ");
			System.setProperty("webdriver.chrome.driver", driverPath);

		} catch (SecurityException e) {
			LOG.error("Fatal error: {}", e);
			System.exit(-1);
		}

		webDriver = new ChromeDriver();
		webDriver.get(URL);
	}

	public PrestashopAutomation(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public MainPage getMainPage() {
		if (mainPage == null) {
			mainPage = new MainPage(webDriver);
		}
		return mainPage;
	}

	public SearchPage getSearchPage() {
		if (searchPage == null) {
			searchPage = new SearchPage(webDriver);
		}
		return searchPage;
	}

	public void cleanUp() {
		if (webDriver != null) {
			webDriver.quit();
		}
	}
}
