package stepdefs;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;

public class AppPage {
    public AppiumDriver driver;

    public AppPage() {

        driver = BaseDriver.driver;
    }
    @Given("I Launch the CC6000 DSIB app")
    public void i_Launch_the_CC6000_DSIB_app() throws Throwable {
        driver.findElementById("android:id/content").isDisplayed();
    }
}
