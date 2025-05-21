package com.akhbar24.tests;


import com.akhbar24.utils.BaseTest;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class TemperatureTest extends BaseTest {



    private WebElement waitForElement(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    @Test
    public void testTemperatureValueIsDisplayedCorrectly() {
        try {
            // ⏱ فتح القائمة من أي حالة (زائر أو مستخدم)
            waitForElement(AppiumBy.accessibilityId("القائمة")).click();

            // ⏱ ابحث عن عنصر يحتوي على رمز درجة الحرارة
            By tempLocator = By.xpath("//android.view.View[contains(@content-desc, '°C')]");
            WebElement tempElement = waitForElement(tempLocator);

            // ⏱ جلب النص من العنصر
            String content = tempElement.getAttribute("content-desc");

            // ⏱ تحقق أنه يحتوي على رقم و°C
            boolean hasValidTemp = content.matches(".*\\d+\\s?°C.*");

            Assert.assertTrue(hasValidTemp, "❌ لم يتم العثور على درجة حرارة صحيحة (رقم + °C).");
            System.out.println("✅ تم عرض درجة حرارة صحيحة في القائمة: " + content);

        } catch (Exception e) {
            takeScreenshot("temperature_validation_failed");
            Assert.fail("❌ فشل في التحقق من درجة الحرارة: " + e.getMessage());
        }
    }









}
