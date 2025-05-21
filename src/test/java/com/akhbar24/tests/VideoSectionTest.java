package com.akhbar24.tests;


import com.akhbar24.utils.BaseTest;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.time.Duration;
import java.util.List;

public class VideoSectionTest extends BaseTest {



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

        @Test
        public void testVideoPlaybackFromVideosSection() throws InterruptedException {

            try {
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

                verifyUserIsLoggedIn();


                // ⏱ الضغط على قسم "مرئيات"
                waitForElement(AppiumBy.accessibilityId("مرئيات")).click();

                // ⏱ انتظار تحميل الفيديوهات
                Thread.sleep(2000);

                // ⏱ الضغط على أول فيديو في القائمة
                WebElement firstVideo = waitForElement(By.xpath("//android.widget.ScrollView/android.view.View[1]/android.widget.ImageView[1]"));
                firstVideo.click();

                // ⏱ انتظار ظهور الفيديو
                Thread.sleep(3000);

                // ⏱ التحقق من ظهور عنصر فيه "فيديو أخبار 24"
                By videoIndicatorLocator = By.xpath("//android.view.View[contains(@content-desc, 'فيديو أخبار 24')]");
                waitForElement(videoIndicatorLocator); // نضمن ظهوره

                // ⏱ جلب العنصر من جديد بعد الانتظار
                WebElement freshVideoIndicator = driver.findElement(videoIndicatorLocator);
                Assert.assertTrue(freshVideoIndicator.isDisplayed(), "❌ الفيديو لم يظهر أو لم يبدأ التشغيل.");
            } catch (Exception e) {
                takeScreenshot("video_playback_failure");
                Assert.fail("❌ حدث خطأ أثناء اختبار / المرئيلات _ الفيديو: " + e.getMessage());
            }
        }


    }






