package com.akhbar24.tests;

import com.akhbar24.utils.BaseTest;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public   class LogoutTest extends BaseTest{

    private static WebElement waitForElement(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    private void verifyUserIsLoggedIn() {
        boolean isHomeVisible = driver.getPageSource().contains("الرئيسية");
        Assert.assertTrue(isHomeVisible, "❌ لم يتم عرض صفحة الرئيسية بعد تسجيل الدخول.");

        waitForElement(AppiumBy.accessibilityId("القائمة")).click();
        String userStatus = waitForElement(By.xpath("//android.view.View[@content-desc]"))
                .getAttribute("content-desc");
        System.out.println("👤 الحالة الحالية للمستخدم: " + userStatus);
        Assert.assertFalse(userStatus.contains("زائر"), "❌ ما زال المستخدم زائرًا، يبدو أن تسجيل الدخول لم ينجح.");
    }
  private void verifyUserIsLoggedOut() {
        waitForElement(AppiumBy.accessibilityId("القائمة")).click();

        // التحقق من العنصر الذي يعرض "زائر"
        List<WebElement> elements = driver.findElements(By.xpath("//android.view.View[@content-desc]"));
        boolean isVisitor = false;

        for (WebElement el : elements) {
            String desc = el.getAttribute("content-desc");
            if (desc != null && desc.contains("زائر")) {
                isVisitor = true;
                System.out.println("👤 الحالة بعد تسجيل الخروج: " + desc);
                break;
            }
        }

        Assert.assertTrue(isVisitor, "❌ المستخدم لا يزال مسجل دخول بعد محاولة تسجيل الخروج.");
    }


    @Test
    public void testLoginAndLogoutFlow() throws InterruptedException {
        System.out.println("🚀 بدء تسجيل الدخول...");
        // تسجيل الدخول ...
        waitForElement(AppiumBy.accessibilityId("القائمة")).click();
        waitForElement(By.xpath("//android.view.View[@content-desc='تسجيل دخول']")).click();

        List<WebElement> inputs = driver.findElements(By.className("android.widget.EditText"));
        Actions actions = new Actions(driver);
        actions.click(inputs.get(0)).perform();
        inputs.get(0).sendKeys("asilyacoub1@gmail.com");

        actions.click(inputs.get(1)).perform();
        inputs.get(1).sendKeys("123456789");


        waitForElement(AppiumBy.accessibilityId("تسجيل الدخول")).click();
        Thread.sleep(5000);

        // تحقق من وجود الرئيسية
        boolean onHome = driver.getPageSource().contains("الرئيسية");
        Assert.assertTrue(onHome, "❌ لم يتم الوصول إلى الصفحة الرئيسية بعد تسجيل الدخول.");

        Thread.sleep(5000) ;
        // تحقق من أن المستخدم ليس زائر
        verifyUserIsLoggedIn();

        // تسجيل الخروج
        waitForElement(AppiumBy.accessibilityId("تسجيل خروج")).click();

        // 3. انتظر ظهور نافذة التأكيد
        Thread.sleep(2000); // أو استخدم انتظار أكثر ذكاءً لاحقاً

        // 4. اضغط على زر تأكيد تسجيل الخروج داخل النافذة
        waitForElement(AppiumBy.accessibilityId("تسجيل الخروج")).click();

        // 5. تحقق من أن المستخدم أصبح زائر
        Thread.sleep(3000); // انتظر التحديث بعد الخروج
        waitForElement(AppiumBy.accessibilityId("القائمة")).click();


        Thread .sleep(5000) ;
        verifyUserIsLoggedOut();

    }

    @Test
    public void testLogin_ThenCancelLogout() throws InterruptedException {
        System.out.println("🚀 بدء تسجيل الدخول...");
        // 1. تسجيل الدخول
        waitForElement(AppiumBy.accessibilityId("القائمة")).click();
        waitForElement(By.xpath("//android.view.View[@content-desc='تسجيل دخول']")).click();

        List<WebElement> inputs = driver.findElements(By.className("android.widget.EditText"));
        Actions actions = new Actions(driver);
        actions.click(inputs.get(0)).perform();
        inputs.get(0).sendKeys("asilyacoub1@gmail.com");

        actions.click(inputs.get(1)).perform();
        inputs.get(1).sendKeys("123456789");

        waitForElement(AppiumBy.accessibilityId("تسجيل الدخول")).click();
        Thread.sleep(5000);

        // 2. تأكيد أنه دخل بنجاح
        verifyUserIsLoggedIn();

        // 3. فتح القائمة واختيار تسجيل خروج
        waitForElement(AppiumBy.accessibilityId("تسجيل خروج")).click();
        Thread.sleep(2000); // انتظار ظهور النافذة

        // 4. الضغط على زر إلغاء بدل تأكيد الخروج
        waitForElement(AppiumBy.accessibilityId("إلغاء")).click();
        Thread.sleep(3000);

        // 5. تحقق أن المستخدم ما زال مسجل (ليس زائرًا)
        verifyUserIsLoggedIn();
    }










}
