package ru.yandex.praktikum.page_object_package;
import org.hamcrest.MatcherAssert;
import org.openqa.selenium.*;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;


// Класс второй страницы заказа
public class OrderPageSecond {
    private WebDriver driver;
    private static By pageTitle = By.cssSelector(".Order_Header__BZXOb"); //заголовок "Про аренду"
    private By fieldDate = By.xpath(".//div/input[@placeholder = '* Когда привезти самокат']");//поле Дата
    //поле Срок аренды
    private By fieldPeriod = By.xpath(".//div/div[@class = 'Dropdown-placeholder' and text() = '* Срок аренды']");
    private By fieldPeriodSelected = By.xpath(".//div[@class = 'Dropdown-placeholder is-selected']");//поле Срок аренды, если заполнено
    private By fieldColor = By.xpath(".//div[@Order_Checkboxes__3lWSI]");//поле Цвет
    private By checkboxColorBlack = By.xpath(".//div/label/input[@id = 'black']");//чекбокс Черный
    private By checkboxColorGrey = By.xpath(".//div/label/input[@id = 'grey']");//чекбокс Серый
    private By fieldComment = By.xpath(".//div/input[@placeholder ='Комментарий для курьера']");// поле Комментарий
    private By buttonNext = By.xpath(".//div[@class='Order_Buttons__1xGrp']/button[text()='Заказать']");//кнопка "Заказать"
    private By buttonBack = By.xpath(".//div[@class='Order_Buttons__1xGrp']/button[text()='Назад']");//кнопка "Назад"
    //кнопка "Да"
    private By buttonYes = By.xpath(".//div[@class='Order_Modal__YZ-d3']/div[@class='Order_Buttons__1xGrp']/button[text()='Да']");
    //кнопка "Нет"
    private By buttonNo = By.xpath(".//div[@class ='Order_Modal__YZ-d3']/div[@class='Order_Buttons__1xGrp']/button[text()='Нет']");
    private static By popupOrderFinishHeader = By.xpath(".//div[@class = 'Order_Modal__YZ-d3']/div[@class = 'Order_ModalHeader__3FDaJ']"); //заголовок попап окна после оформления заказа


    public OrderPageSecond(WebDriver driver){
        this.driver = driver;
    }

    public void setDate(String date) { //выбрать дату
        driver.findElement(fieldDate).click(); //кликнуть на поле даты для выпадения календаря
        driver.findElement(fieldDate).sendKeys(date); //вставить строку
        driver.findElement(fieldDate).sendKeys(Keys.ENTER); //нажать Enter

    }

    public void setPeriod(String period) {//выбрать период аренды
        driver.findElement(fieldPeriod).click(); //кликнуть на поле срока аренды для выпадения списка
        String pathToMenu = ".//div[@class='Dropdown-root is-open']/div[@class='Dropdown-menu']/div[text()= '";
        pathToMenu = pathToMenu + period + "']"; //xpath для кнопки периода с заданным названием
        By periodValue = By.xpath(pathToMenu);

        WebElement element = driver.findElement(periodValue);//скроллим до нужного периода
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);

        driver.findElement(periodValue).click();//кликаем на нужный период

    }

    public void setColor(String color){ //отмечаем цвет

        if (color.equals("black")) {
            driver.findElement(checkboxColorBlack).click();//если цвет "black", то чекаем черный
        }
        else {
            driver.findElement(checkboxColorGrey).click();//иначе чекаем серый
        }

    }

    public void setComment(String comment){//заполняем Комментарий
        driver.findElement(fieldComment).sendKeys(comment);
    }

    public void clickButtonNext(){ //кликаем кнопку отправки заказа
        driver.findElement(buttonNext).click();
    }

    public void clickButtonBack(){ //кликаем кнопку возврата
        driver.findElement(buttonBack).click();
    }

    public void clickButtonYes(){ //кликаем кнопку подтверждения заказа
        driver.findElement(buttonYes).click();
        /*  String tempString =
                driver.findElement(popupOrderFinishHeader).getText();
        MatcherAssert.assertThat(tempString, startsWith("Заказ оформлен"));// проверяем, что появилось сообщение об успешном заказе
   */ }
    public void clickButtonNo(){ //нажимаем кнопку отмены заказа
        driver.findElement(buttonNo).click();
    }

    public static By getPageTitle() { //получить заголовок страницы
        return pageTitle;
    }

    public static By getPopupOrderFinishHeader()
    {
       return popupOrderFinishHeader;


    }

}