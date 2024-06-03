package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.ArrayList;

public class GoogleCloudPricingCalculatorPage {
    private WebDriver driver;
    private Wait<WebDriver> wait;

    @FindBy(id = "input_100")
    private WebElement numberOfInstancesField;

    @FindBy(id = "input_101")
    private WebElement instancesAreForField;

    @FindBy(id = "select_value_label_92")
    private WebElement operatingSystemDropdown;

    @FindBy(id = "select_value_label_93")
    private WebElement provisioningModelDropdown;

    @FindBy(id = "select_value_label_94")
    private WebElement machineFamilyDropdown;

    @FindBy(id = "select_value_label_95")
    private WebElement seriesDropdown;

    @FindBy(id = "select_value_label_96")
    private WebElement machineTypeDropdown;

    @FindBy(xpath = "//md-checkbox[@aria-label='Add GPUs']")
    private WebElement addGPUsCheckBox;

    @FindBy(xpath = "//md-select[@aria-label='Number of GPUs']")
    private WebElement numberOfGPUsDropdown;

    @FindBy(xpath = "//md-select[@placeholder='Local SSD']")
    private WebElement localSSDDropdown;

    @FindBy(xpath = "//md-select[@placeholder='Datacenter location']")
    private WebElement datacenterLocationDropdown;

    @FindBy(xpath = "//md-select[@placeholder='Committed usage']")
    private WebElement committedUsageDropdown;

    @FindBy(xpath = "(//button[contains(text(),'Add to Estimate')])[1]")
    private WebElement addToEstimateButton;

    @FindBy(xpath = "//span[@class='google-symbols ng-scope' and text()='email']")
    private WebElement emailEstimateButton;

    @FindBy(xpath = "//input[@type='email' and @aria-label='Email']")
    private WebElement emailInputField;

    @FindBy(xpath = "//button[@aria-label='Send Email']")
    private WebElement sendEmailButton;

    public GoogleCloudPricingCalculatorPage(WebDriver driver) {
        this.driver = driver;

        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id='mainForm']")));

        PageFactory.initElements(driver, this);
    }

    public GoogleCloudPricingCalculatorPage setFields() {
        // Number of Instances
        numberOfInstancesField.sendKeys("4");

        // What are these instances for?
        instancesAreForField.sendKeys("leave blank");

        // Operating System/Software
        selectDropDownOption(operatingSystemDropdown,
                "Free: Debian, CentOS, CoreOS, Ubuntu or BYOL");

        // Provisioning model
        selectDropDownOption(provisioningModelDropdown, "Regular");

        // Machine Family
        selectDropDownOption(machineFamilyDropdown, "General purpose");

        // Series
        selectDropDownOption(seriesDropdown, "N1");

        // Machine Type
        selectDropDownOption(machineTypeDropdown, "n1-standard-8");

        // Add GPUs
        addGPUsCheckBox.click();

        // GPU type
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//md-select[@aria-label='GPU type']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(getXPathForDropdownItem("NVIDIA Tesla V100")))).click();

        // Number of GPUs
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//md-select[@placeholder='Number of GPUs']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("select_option_520"))).click();

        // Local SSD
        selectDropDownOption(localSSDDropdown, "2x375 GB");

        // Committed usage
        committedUsageDropdown.click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("("+getXPathForDropdownItem("1 Year")+")[2]"))).click();

        addToEstimateButton.click();
        return this;
    }

    public void sendEstimateEmail(String email) {
        wait.until(ExpectedConditions.elementToBeClickable(emailEstimateButton)).click();
        wait.until(ExpectedConditions.visibilityOf(emailInputField)).sendKeys(email);
        wait.until(ExpectedConditions.elementToBeClickable(sendEmailButton)).click();
    }

    public void openYopmailAndGenerateEmail() {
        ((JavascriptExecutor) driver).executeScript("window.open('https://yopmail.com/ru/', '_blank');");
        switchToTab(1);
        WebElement emailInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));
        emailInput.sendKeys("testyopmail");
        WebElement checkInboxButton = driver.findElement(By.xpath("//i[@class='material-icons-outlined f36']"));
        checkInboxButton.click();
    }

    public void switchToTab(int index) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(index));
    }

    private String getXPathForDropdownItem(String itemName) {
        return ".//md-option[contains(@class,'md-ink-ripple')]/div[contains(text(),'"
                .concat(itemName)
                .concat("')]");
    }

    private void selectDropDownOption(WebElement dropDown, String selection) {
        dropDown.click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(getXPathForDropdownItem(selection)))).click();
    }
}



