package com.akhbar24.tests;


import com.akhbar24.utils.BaseTest;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class SearchTest extends BaseTest {
    private WebElement waitForElement(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    @Test
    public void testSearchWithValidKeyword() throws InterruptedException {

        System.out.println("🔍 بدء سيناريو البحث بكلمة صحيحة...");

        // 1️⃣ انقر على أيقونة البحث من القائمة السفلية
        waitForElement(AppiumBy.accessibilityId("بحث")).click();
        Thread.sleep(2000);

        // 2️⃣ انقر على المكان الذي يحتوي على حقل البحث (ImageView) لمحاكاة التركيز
        WebElement fakeInput = waitForElement(By.xpath("//android.widget.ImageView[@bounds='[53,538][1028,664]']"));
        fakeInput.click();
        Thread.sleep(1000);

        // 3️⃣ استخدم Appium Script لإرسال النص مباشرة إذا لم يكن هناك EditText ظاهر
        driver.executeScript("mobile: type", ImmutableMap.of("text", "السعودية"));
        Thread.sleep(1000);

        // 4️⃣ نفذ زر البحث من الكيبورد
        driver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "search"));
        Thread.sleep(3000);

        // 5️⃣ تحقق من وجود نتائج تحتوي الكلمة
        boolean hasResults = driver.getPageSource().contains("السعودية");
        Assert.assertTrue(hasResults, "❌ لم تظهر نتائج تحتوي على 'السعودية'");

        System.out.println("✅ نتائج البحث ظهرت بنجاح.");
    }



    }



