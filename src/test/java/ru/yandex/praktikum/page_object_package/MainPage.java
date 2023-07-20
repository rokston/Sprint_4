package ru.yandex.praktikum.page_object_package;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;


// Класс главной страницы
public class MainPage {

    private WebDriver driver;

    //локатор заголовка FAQ
    private By faqTitle = By.cssSelector(".Home_FourPart__1uthg > div:nth-child(1)");
    private By faqButton = By.className("accordion__button"); //локатор вопроса из FAQ
    //локатор раскрытого ответа из FAQ
    private By faqOpenAnswer = By.xpath(".//div[@class='accordion__panel' and not(@hidden)]");

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
    }

    public void scrollToOrderButton() {
        WebElement button = driver.findElement(orderButtonMiddle);
        //находим кнопку заказа внизу страницы и прокручиваем страницу до нее
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", button);

    }

    public void clickOrderButtonHeader() {
        driver.findElement(orderButtonHeader).click(); //клик на кнопку заказа в заголовке
    }

    public void clickOrderButtonMiddle() {

        driver.findElement(orderButtonMiddle).click(); //клик на кнопку заказа внизу страницы
    }

    public String getAnswerForQuestion(String question){ //найти (раскрыть) ответ для вопроса из FAQ

        String questionPath = ".//div[@class = 'accordion__button' and text()='"+ question + "']";//xpath для кнопки с вопросом
        By faqButtonByQuestion = By.xpath(questionPath);
        WebElement faqElement = driver.findElement(faqButtonByQuestion);//находим элемент с вопросом

            faqElement.click(); //кликаем на элемент, раскрывая ответ

            new WebDriverWait(driver, Duration.ofSeconds(10)).
                    until(ExpectedConditions.visibilityOfElementLocated(faqOpenAnswer)); //нужно подождать загрузку нового ответа на странице

            return driver.findElement(faqOpenAnswer).getText(); //ответ FAQ

        }

    public boolean checkQuestion(String question){ //проверка наличия заданного вопроса из FAQ на странице

        String questionPath = ".//div[@class = 'accordion__button' and text()='"+ question + "']"; //xpath к вопросу
        By faqButtonByQuestion = By.xpath(questionPath);
        List<WebElement> faqElements = driver.findElements(faqButtonByQuestion);//находим все элементы-вопросы FAQ по данному xpath
        if (faqElements.size() == 0) {// если размер массива нулевой, то текст вопроса не найден
            return false;
        }
        else {
            return true;
        }

    }
    }


