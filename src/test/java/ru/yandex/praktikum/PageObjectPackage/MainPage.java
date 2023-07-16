package ru.yandex.praktikum.PageObjectPackage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.junit.Assert.assertEquals;
import org.openqa.selenium.JavascriptExecutor;


// Класс главной страницы
public class MainPage {

    private WebDriver driver;

    //локатор заголовка FAQ
    private By faqTitle = By.cssSelector(".Home_FourPart__1uthg > div:nth-child(1)");

    //кнопка согласия на сохранение куки
    private By cookieButton = By.id("rcc-confirm-button");

    //кнопка заказа в заголовке
    private By orderButtonHeader = By.cssSelector(".Header_Nav__AGCXC > button:nth-child(1)");

    //кнопка заказа внизу страницы
    private By orderButtonMiddle = By.xpath(".//div/button[@class = 'Button_Button__ra12g Button_Middle__1CSJM']");



    public MainPage(WebDriver driver){
        this.driver = driver;
    }

    public void cookieYes() {
        //нажимаем кнопку куки
        driver.findElement(cookieButton).click();
    }

    public void scrollToFAQ() {

        WebElement element = driver.findElement(faqTitle);
        //находим FAQ заголовок и прокручиваем страницу до него

        //scroll to FAQ
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        //считываем текст заголовка и проверяем его
        String testMessage = driver.findElement(faqTitle).getText();
        assertEquals("должен быть найден текст 'Вопросы о важном'", "Вопросы о важном", testMessage);

    }

    public void scrollToOrderButton() {
        WebElement element = driver.findElement(orderButtonMiddle);
        //находим кнопку заказа внизу страницы и прокручиваем страницу до нее
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        //считываем текст заголовка и проверяем его
        String testMessage = driver.findElement(orderButtonMiddle).getText();
        assertEquals("должен быть найден текст 'Заказать'", "Заказать", testMessage);

    }

    public void clickOrderButtonHeader() {
        driver.findElement(orderButtonHeader).click(); //клик на кнопку заказа в заголовке
    }

    public void clickOrderButtonMiddle() {
        //scrollToOrderButton();
        driver.findElement(orderButtonMiddle).click(); //клик на кнопку заказа внизу страницы
    }



}