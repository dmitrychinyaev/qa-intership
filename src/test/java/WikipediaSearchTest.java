import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.dmitrychin.WikiPage;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WikipediaSearchTest {
    WebDriver driver;
    WebDriverWait wait10sec;
    WikiPage wikiPage;

    public WikipediaSearchTest() {
        // Создаем экземпляр WebDriver
        driver = new ChromeDriver();
        wikiPage = new WikiPage(driver);
        // Открываем страницу Википедии
        driver.get("https://ru.wikipedia.org/wiki");
        // Создаем явное ожидание
        wait10sec = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }


    @Test
    public void verifySuggestionStartsWithBold() {
        // Вводим запрос в поле поиска
        wikiPage.enterSearchQuery("Java");
        // Ожидаем 10 сек появления саджестов
        wait10sec.until(ExpectedConditions.visibilityOfElementLocated(By.className("suggestions-results")));
        // Находим все саджесты
        List<WebElement> suggestionList = driver.findElements(By.className("suggestions-result"));

        boolean ifSuggestionsBold = false;

        // Проверяем, что саджесты начинаются с поискового запроса
        for (WebElement suggestion : suggestionList) {
            if (suggestion.getText().startsWith("Java")) {
                // Проверяем, что поисковый запрос выделен жирным
                if (!suggestion.findElements(By.className("highlight")).isEmpty()) {
                    ifSuggestionsBold = true;
                    break;
                }
            }
        }
        assertTrue(ifSuggestionsBold);
    }

    @Test
    public void verifySearchLinkFirstSuggestion() {
        // Вводим запрос в поле поиска
        wikiPage.enterSearchQuery("Selenium");
        // Ожидаем 10 сек появления саджестов
        wait10sec.until(ExpectedConditions.visibilityOfElementLocated(By.className("suggestions-results")));
        // Находим все саджесты
        List<WebElement> suggestions = driver.findElements(By.className("suggestions-results"));
        // Если они не пустые, берем первый и проходим по нему и сравниваем
        if (!suggestions.isEmpty()) {
            suggestions.get(0).click();
        }

        String expected = "https://ru.wikipedia.org/wiki/Selenium";

        assertEquals(expected, driver.getCurrentUrl());
    }

    @Test
    public void verifySearchLinkNoSuggestions() {
        wikiPage.enterSearchQuery("Иваааанннннн");
        wikiPage.clickSearchButton();
        String currentUrl = driver.getCurrentUrl();
        // Проверяем, что осуществлен переход на страницу поиска
        assertTrue(currentUrl.contains("https://ru.wikipedia.org/w/index.php?go="));
    }
    @Test
    public void verifySearchLinkPressButtonBelowSuggestions(){
        wikiPage.enterSearchQuery("Иван");
        wait10sec.until(ExpectedConditions.visibilityOfElementLocated(By.className("suggestions-results")));
        // Ждём чтобы появления подсказки
        wikiPage.enterSearchQuery(" ");
        wait10sec.until(ExpectedConditions.visibilityOfElementLocated(By.className("suggestions-special")));
        wikiPage.clickSearchSuggestion();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("https://ru.wikipedia.org/w/index.php?fulltext="));
    }
}



