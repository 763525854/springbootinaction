package readinglist;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ReadingListApplication.class)
@WebIntegrationTest(randomPort = true)
public class SimpleWebTest {
	@Value("${local.server.port}")
	private int port;
	private static FirefoxDriver browser;

	@BeforeClass
	public static void openBrowser() {
		browser = new FirefoxDriver();
		browser.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
	}

	@AfterClass
	public static void closeBrowser() {
		browser.quit();
	}

	@Test
	public void addBookToEmptyList() {
		String baseUrl = "http://localhost:" + port;
		browser.get(baseUrl);
		assertEquals("You have no books in your book list", browser.findElementByTagName("div").getText());
		browser.findElementByName("title").sendKeys("BOOK TITLE");
		browser.findElementByName("author").sendKeys("BOOK AUTHOR");
		browser.findElementByName("isbn").sendKeys("1234567890");
		browser.findElementByName("description").sendKeys("DESCRIPTION");
		browser.findElementByTagName("form").submit();
		WebElement dl = browser.findElementByCssSelector("dt.bookHeadline");
		assertEquals("BOOK TITLE by BOOK AUTHOR (ISBN: 1234567890)", dl.getText());
		WebElement dt = browser.findElementByCssSelector("dd.bookDescription");
		assertEquals("DESCRIPTION", dt.getText());
	}

	@Test(expected = HttpClientErrorException.class)
	public void pageNotFound() {
		try {
			RestTemplate rest = new RestTemplate();
			rest.getForObject("http://localhost:{port}/bogusPage", String.class, port);
			fail("Should result in HTTP 404");
		} catch (HttpClientErrorException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
			throw e;
		}
	}

}
