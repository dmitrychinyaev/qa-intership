package ru.dmitrychin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WikiPage {
    private final WebDriver driver;
    private By searchInput = By.id("searchInput");
    private By searchButton = By.id("searchButton");
    private By searchButtonToPages = By.className("suggestions-special");

    public WikiPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterSearchQuery(String query) {
        WebElement input = driver.findElement(searchInput);
        input.sendKeys(query);
    }

    public void clickSearchButton() {
        WebElement button = driver.findElement(searchButton);
        button.click();
    }

    public void clickSearchSuggestion() {
        WebElement suggestion = driver.findElement(searchButtonToPages);
        suggestion.click();
    }
}
