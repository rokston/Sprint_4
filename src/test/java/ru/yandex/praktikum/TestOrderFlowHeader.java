package ru.yandex.praktikum;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.page_object_package.*;
import java.time.Duration;

import static org.hamcrest.CoreMatchers.startsWith;
import static ru.yandex.praktikum.page_object_package.Constants.MAIN_PAGE_URL;

@RunWith(Parameterized.class)
public class TestOrderFlowHeader {
    private final String name; //имя заказчика
    private final String surname; //фамилия заказчика
    private final String address; //его адрес
    private final String station; //ближайшая станция метро
    private final String phone; //телефон
    private final String date; // дата доставки
    private final String period; // период аренды
    private final String color; //цвет самоката
    private final String comment; //комментарий для курьера

    // WebDriver driver = new ChromeDriver(); //тестируем в Хроме
    WebDriver driver = new FirefoxDriver(); //тестируем в Файрфоксе

    //конструктор
    public TestOrderFlowHeader(String name, String surname, String address, String station,
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

    }

    public void testOrderScenario_header(){


        driver.get(MAIN_PAGE_URL); //адрес главной страницы


        MainPage objMainPage = new MainPage(driver); //создали объект класса Главной страницы
        objMainPage.cookieYes(); //кликнули на кнопку куки, чтобы ее убрать

        objMainPage.clickOrderButtonHeader(); //нажимаем кнопку заказа в заголовке
        //проверяем, что открылась следующая страница
        new WebDriverWait(driver, Duration.ofSeconds(10)).
                until(ExpectedConditions.visibilityOfElementLocated(OrderPageFirst.getPageTitle()));
        System.out.println(driver.findElement(OrderPageFirst.getPageTitle()).getText());
        fillInOrderForm(driver); //заполняем форму -- 1 страницу, 2 страницу и нажимаем кнопки отправки заказа

    }

    //метод заполняет поля формы заказа тестовыми данными и нажимает на кнопки отправки заказа
    private void fillInOrderForm(WebDriver driver){
        OrderPageFirst objOrderPageFirst = new OrderPageFirst(driver); //создали объект класса первой страницы заказа
        objOrderPageFirst.setPersonalData(name, surname,address,phone); //заполняем персональные данные
        objOrderPageFirst.setStation(station); //заполняем станцию метро
        objOrderPageFirst.clickNextButton(); //нажимаем кнопку "Далее"

        OrderPageSecond objOrderPageSecond = new OrderPageSecond(driver); //создали объект класса второй страницы заказа
        System.out.println(driver.findElement(OrderPageSecond.getPageTitle()).getText()); //проверяем, что открылась следующая страница

        objOrderPageSecond.setDate(date); //заполняем дату, период аренды, цвет самоката и комментарий
        objOrderPageSecond.setPeriod(period);
        objOrderPageSecond.setColor(color);
        objOrderPageSecond.setComment(comment);
        objOrderPageSecond.clickButtonNext(); //нажимаем "Заказать"
        objOrderPageSecond.clickButtonYes(); //подтверждаем заказ
        String tempString =
                driver.findElement(OrderPageSecond.getPopupOrderFinishHeader()).getText();
        MatcherAssert.assertThat(tempString, startsWith("Заказ оформлен"));// проверяем, что появилось сообщение об успешном заказе
    }



    @After
    public void teardown() {
        // Закрываем браузер
        driver.quit();
    }
}




