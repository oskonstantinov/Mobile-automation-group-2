import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class Ex4 {

    private AppiumDriver driver;                         // объявили переменную драйвера

    @Before                                              // маркер JUnit для выполнения перед каждым тестом
    public void setUp() throws Exception                 // метод установки и настройки аппиума
    {
        DesiredCapabilities capabilities = new DesiredCapabilities(); // задаем капабилити

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","6.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","D:/JavaAppiumAutomation/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities); // задали настройку драйвера
    }

    @After                                               // маркер JUnit для выполнения после каждого теста
    public void tearDown()                               // метод выключения драйвера
    {
        driver.quit();                                   // отключили драйвер
    }

    @Test
    public void TestWordInSearchResult()
    {
        waitForElementAndClick(                          // ищем форму поиска по id и нажимаем на нее
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(                       // ждем оотбражения поля ввода и эмулируем ввод с клавиатуры
                By.id("org.wikipedia:id/search_src_text"),
                "Pumpkin",
                "Cannot find search input",
                5
        );
        List<WebElement> elements = waitForElementsPresent(       // ждем появления элементов, содержащих указанный текст
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[contains(@text,'Pumpkin')]"),
                "Cannot find article title 'Pumpkin'",
                20
        );

        for(WebElement element: elements)                         // условие проверки для элементов из списка
        {
                String word = element.getText();
                Assert.assertTrue(word.toLowerCase().contains("Pumpkin".toLowerCase()));
                Assert.assertTrue(word.contains("Pumpkin"));
        }
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) // установка метода ожидания элемента и названий параметров
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n"); // текст ошибки, если не найден элемент + перенос на след строку
        return wait.until(ExpectedConditions.presenceOfElementLocated(by)); // условие появления нужного элемента
    }

    private List<WebElement> waitForElementsPresent(By by, String error_message, long timeoutInSeconds) // установка метода ожидания многих элементов
    {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by)); // условие появления всех элементов, удовлетворяющих запросу
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) // метод ожидания и нажатия
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) // метод ожидания и ввода с клавиатуры
    {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }
}