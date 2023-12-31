package ru.yandex.praktikum.page_object_package;
import org.openqa.selenium.*;




// Класс первой страницы заказа
public class OrderPageFirst {
    private WebDriver driver;
    private static By pageTitle = By.cssSelector(".Order_Header__BZXOb"); //заголовок "Для кого самокат"
    private By fieldName = By.xpath(".//div/input[@placeholder = '* Имя']"); //поле Имя
    private By fieldSurname = By.xpath(".//div/input[@placeholder = '* Фамилия']"); //поле Фамилия
    private By fieldAddress = By.xpath(".//div/input[@placeholder = '* Адрес: куда привезти заказ']"); //поле Адрес
    private By fieldStation = By.xpath(".//div/input[@placeholder ='* Станция метро']"); // поле Станция метро
    private By fieldPhone = By.xpath(".//div/input[@placeholder = '* Телефон: на него позвонит курьер']"); // поле Телефон
    private By buttonNext = By.xpath(".//button[@class = 'Button_Button__ra12g Button_Middle__1CSJM']"); // кнопка "Далее"
    public OrderPageFirst(WebDriver driver){
        this.driver = driver;
    }

    public void setPersonalData(String name, String surname, String address, String phone){
        //заполняем персональные данные
        driver.findElement(fieldName).sendKeys(name);
        driver.findElement(fieldSurname).sendKeys(surname);
        driver.findElement(fieldAddress).sendKeys(address);
        driver.findElement(fieldPhone).sendKeys(phone);
    }
    //заполняем поле станции метро
    public void setStation(String station) {
        driver.findElement(fieldStation).click(); //кликнуть на поле станции метро для выпадения списка

        String pathToStation = ".//div[@class='select-search__select']/ul/li/button/div[text()= '";
        pathToStation = pathToStation + station + "']"; //xpath для кнопки станции с заданным названием
        By stationName = By.xpath(pathToStation);

        WebElement element = driver.findElement(stationName);
        //находим станцию и прокручиваем страницу до нее
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        //кликаем на кнопку станции
        driver.findElement(stationName).click();

    }

    public void clickNextButton(){ //кликаем на кнопку "Далее"
        driver.findElement(buttonNext).click();
    }



    public static By getPageTitle() {
        return pageTitle;
    }
}