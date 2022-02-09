package support;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.junit.Assert;
import org.openqa.selenium.By;

public class CommonActions {

    public MobileElement waitAndGetElementByLocator(AppiumDriver driver, By elePath, int waitTimeInSecs) throws InterruptedException {

        final long startTime = System.currentTimeMillis();
        boolean found = false;
        MobileElement mobEle = null;
        while( (System.currentTimeMillis() - startTime) < 20000 )
        {
            try {
                mobEle = (MobileElement) driver.findElement(elePath);
                found = mobEle.isDisplayed();
                System.out.println(found + " - found");
                break;
            } catch ( Exception e) {
                Thread.sleep(waitTimeInSecs * 1000);
            }
        }
        if (!found) {
            Assert.assertTrue("Fail to locate Element: "+elePath,false);
        }
        return mobEle;
    }

    public boolean isPinScreenClosed(AppiumDriver driver, String id)
    {
        boolean isClosed = false;
        try {
            driver.findElementByAccessibilityId(id);
        }
        catch(Exception e)
        {
            isClosed = true;
        }
        return isClosed;
    }

    public boolean isNFCScreenClosed(AppiumDriver driver, String id)
    {
        boolean isClosed = false;
        try {
            driver.findElementByAccessibilityId(id);
        }
        catch(Exception e)
        {
            isClosed = true;
        }
        return isClosed;
    }

    public boolean isSuccessfulScreenClosed(AppiumDriver driver, String doneBtnXpath)
    {
        boolean isClosed = false;
        try {
            System.out.println("isSuccessfulScreenClosed");
            driver.findElement(By.xpath(doneBtnXpath));
        }
        catch(Exception e)
        {
            isClosed = true;
        }
        return isClosed;
    }

    public boolean isErrorScreenClosed(AppiumDriver driver, String id)
    {
        boolean isClosed = false;
        try {
            System.out.println(id);
            driver.findElementByAccessibilityId(id);
        }
        catch(Exception e)
        {
            isClosed = true;
        }
        return isClosed;
    }
}
