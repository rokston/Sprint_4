package ru.yandex.praktikum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.PageObjectPackage.MainPage;
import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestFaqData {
    private final String question;
    private final String answer;
    private final boolean result;

    public TestFaqData(String question, String answer, boolean result) {
        this.question = question;
        this.answer = answer;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Object[][] getTestFaqData() {
        //тестовые данные -- вопрос и ответ, ожидаемый результат теста
        return new Object[][]{
                {"Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.", true},
                {"Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.", true},
                {"Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру.", false},
        };
    }

    // Класс с автотестом

@Test
            public void FaqPageAnswers(){

            WebDriver driver = new ChromeDriver();
          //  WebDriver driver = new FirefoxDriver();
            driver.get("https://qa-scooter.praktikum-services.ru/");
            By faqButton = By.className("accordion__button");
            By faqOpenAnswer = By.xpath(".//div[@class='accordion__panel' and not(@hidden)]");
            boolean isVisible = true;
            boolean isSame = false;

            MainPage objMainPage = new MainPage(driver); //создали объект класса Главной страницы
            objMainPage.cookieYes(); //кликнули на кнопку куки, чтобы ее убрать
            objMainPage.scrollToFAQ(); //пролистнули до отображения FAQ


            List<WebElement> faqElements = driver.findElements(faqButton); //нашли все пункты FAQ

            assertEquals(isVisible, faqElements.size() != 0); //Проверка, что нашёлся хотя бы один нужный элемент -- отладка

            int size = faqElements.size(); //размер массива FAQ
            //System.out.println(size); //вывод длины массива FAQ -- отладка
            String[] faqElementsQuestion = new String[size]; // массив для текста вопросов
            String[] faqElementsAnswer = new String[size]; // массив для текста ответов

            for (int i = 0; i < size; i++) {

                faqElements = driver.findElements(faqButton); //перестраиваем список после клика из предыдущей итерации
                faqElementsQuestion[i] = faqElements.get(i).getText(); //заполняем массив вопросов FAQ
               // System.out.println(faqElementsQuestion[i]); //вывод элементов вопросов -- отладка
                faqElements.get(i).click(); //кликаем на элемент, раскрывая ответ

                new WebDriverWait(driver, Duration.ofSeconds(10)).
                        until(ExpectedConditions.visibilityOfElementLocated(faqOpenAnswer)); //нужно подождать загрузку нового ответа на странице

                faqElementsAnswer[i] = driver.findElement(faqOpenAnswer).getText(); //заполняем массив ответов FAQ
                //System.out.println(faqElementsAnswer[i]); //вывод элементов ответов -- отладка
            }

            //проверяем, что тестовые пары вопрос-ответ на странице отображаются вместе
            for (int i = 0; i < size; i++){
            if (faqElementsQuestion[i].equals(question)){
                if (faqElementsAnswer[i].equals(answer)){
                    isSame = true;
                }
            }
            }
             assertEquals(result, isSame);

                // Закрываем браузер
                   driver.quit();
            }
   }



