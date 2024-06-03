package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TemporaryEmailPage {
    private WebDriver driver;

    @FindBy(id = "login")
    private WebElement emailInputField;

    @FindBy(xpath = "//i[@class='material-icons-outlined f36']")
    private WebElement checkInboxButton;

    public TemporaryEmailPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void generateTemporaryEmail() {
        emailInputField.sendKeys("testyopmail");
        checkInboxButton.click();
    }

    public String getTemporaryEmail() {
        return "testyopmail@yopmail.com";
    }
}

