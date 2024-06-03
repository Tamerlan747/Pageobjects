package pages.pages;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import pages.GoogleCloudPricingCalculatorPage;

public class GoogleCloudPricingCalculatorTest {
    private WebDriver driver;
    private GoogleCloudPricingCalculatorPage calculatorPage;

    @Before
    public void setUp() {
        try {
            // Set the path to the EdgeDriver executable
            System.setProperty("webdriver.edge.driver", "C:\\Users\\apara\\OneDrive\\Изображения\\msedgedriver.exe");
            driver = new EdgeDriver();
            driver.manage().window().maximize();
            driver.get("https://cloudpricingcalculator.appspot.com/");
            calculatorPage = new GoogleCloudPricingCalculatorPage(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFillOutForm() {
        try {
            if (calculatorPage != null) {
                calculatorPage.setFields();
                // Open Yopmail in a new tab and generate an email
                calculatorPage.openYopmailAndGenerateEmail();

                // Switch back to the calculator tab
                calculatorPage.switchToTab(0);

                // Send the estimate email
                String generatedEmail = "testyopmail@yopmail.com";
                calculatorPage.sendEstimateEmail(generatedEmail);
            } else {
                System.out.println("calculatorPage is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

