package ru.yandex.praktikum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.PageObjectPackage.*;
import java.time.Duration;

@RunWith(Parameterized.class)
public class TestOrderFlow {
    private final String name; //имя заказчика
    private final String surname; //фамилия заказчика
    private final String address; //его адрес
    private final String station; //ближайшая станция метро
    private final String phone; //телефон
    private final String date; // дата доставки
    private final String period; // период аренды
    private final String color; //цвет самоката
    private final String comment; //комментарий для курьера

    //конструктор
    public TestOrderFlow(String name, String surname, String address, String station,
                         String phone, String date, String period, String color, String comment) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.station = station;
        this.phone = phone;
        this.date = date;
        this.period = period;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getTestOrderData() {
        //тестовые данные -- персональные данные заказчика и параметры заказа
        return new Object[][]{
                {"Маша", "Петрова", "8-я Парковая д.6. кв.4", "Киевская", "89996767675",
                        "30.07.2023", "двое суток", "black", "не звонить"},
                {"Саша", "Григорьев", "Яблоневая д.16. к.5 кв.410", "Котельники", "86996967625",
                        "01.08.2023", "семеро суток", "grey", "трам-пам-пам"},

        };
    }

    // Класс с автотестом

    @Test
    public void testOrderScenario(){
        testOrderScenario_header(); //заказ через кнопку заказа в заголовке
        testOrderScenario_middle(); //заказ через кнопку заказа внизу страницы
    }

        public void testOrderScenario_middle(){ // заказ через кнопку заказа внизу страницы

        WebDriver driver = new ChromeDriver(); //тестируем в Хроме
        // WebDriver driver = new FirefoxDriver(); //тестируем в Файрфоксе
        driver.get("https://qa-scooter.praktikum-services.ru/"); //адрес главной страницы

        MainPage objMainPage = new MainPage(driver); //создали объект класса Главной страницы
        objMainPage.cookieYes(); //кликнули на кнопку куки, чтобы ее убрать
        objMainPage.scrollToOrderButton(); //пролистнули до отображения кнопки заказа внизу
        objMainPage.clickOrderButtonMiddle(); //нажимаем кнопку заказа внизу страницы
            //проверяем, что открылась следующая страница
        new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Order_Header__BZXOb")));
         System.out.println(driver.findElement(By.cssSelector(".Order_Header__BZXOb")).getText());
            fillInOrderForm(driver); //заполняем форму -- 1 страницу, 2 страницу и нажимаем кнопки отправки заказа

        // Закрываем браузер
        driver.quit();

    }


    public void testOrderScenario_header(){

        WebDriver driver = new ChromeDriver();  //тестируем в Хроме
        // WebDriver driver = new FirefoxDriver();  //тестируем в Файрфоксе
        driver.get("https://qa-scooter.praktikum-services.ru/"); //адрес главной страницы


        MainPage objMainPage = new MainPage(driver); //создали объект класса Главной страницы
        objMainPage.cookieYes(); //кликнули на кнопку куки, чтобы ее убрать

        objMainPage.clickOrderButtonHeader(); //нажимаем кнопку заказа в заголовке
        //проверяем, что открылась следующая страница
        new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Order_Header__BZXOb")));
        System.out.println(driver.findElement(By.cssSelector(".Order_Header__BZXOb")).getText());
        fillInOrderForm(driver); //заполняем форму -- 1 страницу, 2 страницу и нажимаем кнопки отправки заказа

        // Закрываем браузер
        driver.quit();
    }

    //метод заполняет поля формы заказа тестовыми данными и нажимает на кнопки отправки заказа
    private void fillInOrderForm(WebDriver driver){
        OrderPage1 objOrderPage1 = new OrderPage1(driver); //создали объект класса первой страницы заказа
        objOrderPage1.setPersonalData(name, surname,address,phone); //заполняем персональные данные
        objOrderPage1.setStation(station); //заполняем станцию метро
        objOrderPage1.clickNextButton(); //нажимаем кнопку "Далее"

        OrderPage2 objOrderPage2 = new OrderPage2(driver); //создали объект класса второй страницы заказа
        System.out.println(driver.findElement(By.cssSelector(".Order_Header__BZXOb")).getText()); //проверяем, что открылась следующая страница

        objOrderPage2.setDate(date); //заполняем дату, период аренды, цвет самоката и комментарий
        objOrderPage2.setPeriod(period);
        objOrderPage2.setColor(color);
        objOrderPage2.setComment(comment);
        objOrderPage2.clickButtonNext(); //нажимаем "Заказать"
        objOrderPage2.clickButtonYes(); //подтверждаем заказ и проверяем, что появилось сообщение об успешном заказе
    }
}




