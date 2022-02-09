package stepdefs;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class BaseDriver {
    public static AppiumDriver driver;
    public static Logger APPLICATION_LOG = Logger.getLogger("Base Driver");
    AppiumDriverLocalService appiumService;
    AppiumDriverLocalService service ;
    String appiumUrl;

    @Before
    public  void startApp() throws IOException, InterruptedException {

        System.out.println("reaching hooks");
        appiumService = AppiumDriverLocalService.buildDefaultService();
//        appiumService.start();
        Thread.sleep(5000);
        appiumUrl = appiumService.getUrl().toString();
        System.out.println("Appium Service URL Address : - "+ appiumUrl);

        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "/apps/");
        File app = new File(appDir, "app-release-unsigned.apk");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","Tab_A_9.7_API_24");
        capabilities.setCapability("avd","Tab_A_9.7_API_24");
        capabilities.setCapability("device","Tab_A_9.7_API_24");
        capabilities.setCapability("version","7.0");
//        capabilities.setCapability("orientation","LANDSCAPE");
        capabilities.setCapability("waitForAppScript", true);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
        capabilities.setCapability("app",app.getAbsolutePath());

        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        pushDev();
    }

    public void pushDev() throws IOException, InterruptedException {
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "/apps/");
        File dev = new File(appDir, "dev");
        String MOBILE_SDCARD_PATH = "/mnt/sdcard/";
        ProcessBuilder pb = new ProcessBuilder("adb", "push", dev.getAbsolutePath(), MOBILE_SDCARD_PATH);
        Process pc = pb.start();
        pc.waitFor();
        System.out.println("Done");
    }

  @After
    public void resetApp(){
        driver.resetApp();
    //  driver.terminateApp("");
        System.out.println("Cleared app data.");
    }
    public  void closeApp(Scenario scenario){
        if(scenario.isFailed()) {
            try {
                System.out.println("Scenario is Failed Taking ScreenShot...!!!");
                byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
                System.out.println("Screenshot in byte: "+screenshot.toString());
                scenario.attach(screenshot, "image/jpg", driver.getCurrentUrl());
            } catch (WebDriverException somePlatformsDontSupportScreenshots) {
                System.err.println(somePlatformsDontSupportScreenshots.getMessage());
            }
        }
        System.out.println("Reaching Closing BaseDriver");
        driver.quit();
//        appiumService.stop();
    }

    public void takeScreenShot(AppiumDriver<MobileElement> d) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_YYYY_hh_mm_ss");
        Date date = new Date();
        String fileName = sdf.format(date);
        File des = d.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(des,new File(System.getProperty("user.dir")+"/target/screenshots/"+fileName+".png"));
    }

}
