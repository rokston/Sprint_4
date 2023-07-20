package ru.yandex.praktikum;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.page_object_package.MainPage;
import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.yandex.praktikum.page_object_package.Constants.MAIN_PAGE_URL;

@RunWith(Parameterized.class)
public class TestFaqData {
    private final String question;
    private final String answer;
    private final boolean result;

    WebDriver driver = new ChromeDriver();
    //  WebDriver driver = new FirefoxDriver();


    public TestFaqData(String question, String answer, boolean result) {
        this.question = question;
        this.answer = answer;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Object[][] getTestFaqData() {
        //тестовые данные -- вопрос и ответ, ожидаемый результат теста
        return new Object[][]{
                {"Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой.", true},
                {"Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.", true},
                {"Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру.", false},
                {"Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.", true},
                {"Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее.", true},
                {"Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.", true},
                {"Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.", true},
                {"Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.", true},
                {"Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области.", true},
        };
    }

    // Класс с автотестом

@Test
            public void faqPageAnswers(){
            driver.get(MAIN_PAGE_URL);


            boolean isSame = false;

            MainPage objMainPage = new MainPage(driver); //создали объект класса Главной страницы
            objMainPage.cookieYes(); //кликнули на кнопку куки, чтобы ее убрать
            objMainPage.scrollToFAQ(); //пролистнули до отображения FAQ


             if (objMainPage.checkQuestion(question)) { //если текст вопроса FAQ найден, можно сравнивать ответ
                 String actualAnswer = objMainPage.getAnswerForQuestion(question);
                 if (actualAnswer.equals(answer)) {
                     isSame = true; //ответ совпал с ожиданием
                 } else {
                     isSame = false;
                     System.out.println("Ответ для '" + question + "' неверный"); //ответ на вопрос неверен
                 }
             }
             else {
                 isSame = false; //текст вопроса не найден, выводим сообщение об ошибке
                 System.out.println("Не найден вопрос '" + question + "'");
             }
             assertEquals(result, isSame);

            }

    @After
    public void teardown() {
        // Закрываем браузер
        driver.quit();
    }

   }



