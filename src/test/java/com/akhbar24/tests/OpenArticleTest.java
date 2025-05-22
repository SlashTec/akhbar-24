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

public class OpenArticleTest extends BaseTest {
    private WebElement waitForElement(By locator) {
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




    @Test(priority = 1)
    public void testOpenFirstArticleFromHome() throws InterruptedException {

//55454644654
        System.out.println("🚀 بدء تسجيل الدخول...");

        // تسجيل الدخول
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

        boolean onHome = driver.getPageSource().contains("الرئيسية");
        Assert.assertTrue(onHome, "❌ لم يتم الوصول إلى الصفحة الرئيسية بعد تسجيل الدخول.");

        Thread.sleep(5000);
        verifyUserIsLoggedIn();
        // تأكد أنك على الرئيسية بالنقر عليها في حال كنت على شاشة أخرى
        try {
            waitForElement(AppiumBy.accessibilityId("الرئيسية")).click();
        } catch (Exception e) {
            System.out.println("⚠️ لم يتم العثور على زر الرئيسية، قد تكون فعلاً على الصفحة الرئيسية.");
        }

        // الانتظار لظهور أول عنصر خبر بالترتيب في الصفحة باستخدام موقعه وليس النص
        WebElement firstArticle = waitForElement(
                By.xpath("//android.widget.ScrollView/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View/android.view.View[1]"
                        ));

        firstArticle.click();
        System.out.println("افتح  اول خبر ");
        Thread.sleep(6000);

        // التحقق من ظهور أيقونة المشاركة كدليل على فتح المقال
        WebElement shareIcon = waitForElement(
                By.xpath("//android.widget.ScrollView/android.widget.ImageView[4]"));


        Thread.sleep(6000);

        Assert.assertTrue(shareIcon.isDisplayed(), "❌ لم يتم العثور على أيقونة المشاركة - ربما لم يتم فتح المقال بشكل صحيح");
        System.out.println("✅✅ تم فتح أول مقال والتحقق من وجود زر المشاركة بنجاح!");
    }

    @Test (priority = 2)
    public void testShareFunctionality() throws InterruptedException {
        System.out.println("🔗 بدء اختبار وظيفة المشاركة...");


        // انقر على أيقونة المشاركة
        WebElement shareIcon = waitForElement(By.xpath("//android.widget.ScrollView/android.widget.ImageView[4]"));
        shareIcon.click();
        Thread.sleep(3000);

        // تحقق من ظهور نافذة المشاركة
        WebElement sharingPopupTitle = waitForElement(By.id("com.android.intentresolver:id/headline"));
        String popupText = sharingPopupTitle.getText();
        Assert.assertTrue(popupText.contains("Sharing link"), "❌ نافذة المشاركة لم تظهر بالشكل المتوقع");

        System.out.println("✅ وظيفة المشاركة تعمل بنجاح!");
    }










    }





