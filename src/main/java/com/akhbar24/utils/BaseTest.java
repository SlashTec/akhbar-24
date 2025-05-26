package com.akhbar24.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.*;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;

@Listeners({AllureTestNg.class, TestListener.class})
public class BaseTest {

    public static AppiumDriver driver;


    @BeforeMethod
    public void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setDeviceName("Pixel 7a");
        options.setAutomationName("UiAutomator2");
        options.setApp("C:\\Users\\user\\Downloads\\app-release (2).apk");
        options.setAppWaitDuration(Duration.ofSeconds(100));
        options.setCapability("chromedriverAutodownload", true);

        /// /////////////////////////////////////////////

        String server = System.getProperty("serverURL");
        URL serverURL = new URL(server);

        System.out.println("📱 جاري إنشاء الجلسة وفتح التطبيق...");
        driver = new AndroidDriver(serverURL, options);
        System.out.println("✅ تم فتح التطبيق!");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(130));

    }

    @AfterMethod

        public void tearDown(ITestResult result) {
            if (result.getStatus() == ITestResult.FAILURE) {
                takeScreenshot("FAILED_" + result.getName());
            }

            // بعد التقارير فقط نغلق الجلسة
            if (driver != null) {
                driver.quit();
                System.out.println("🛑 تم إنهاء جلسة Appium بعد هذا الاختبار.");
            }
    }


    public void takeScreenshot (String fileName){
            try {
                File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File screenshotsDir = new File("screenshots");
                if (!screenshotsDir.exists()) {
                    screenshotsDir.mkdir(); // إنشاء المجلد إذا مش موجود
                }

                File destFile = new File(screenshotsDir, fileName + ".png");
                FileUtils.copyFile(srcFile, destFile);

                // أضف للسحب في تقارير Allure
                Allure.addAttachment(fileName, new FileInputStream(destFile));

                System.out.println("📸 Screenshot saved: " + destFile.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("⚠️ Error while taking screenshot: " + e.getMessage());
            }
        }





    }


