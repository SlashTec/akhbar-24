package com.akhbar24.utils;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("🚀 Starting Test: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✅ Test Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            File srcFile = ((TakesScreenshot) BaseTest.driver).getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            File screenshotsDir = new File("screenshots/" + timestamp);
            if (!screenshotsDir.exists()) {
                screenshotsDir.mkdirs();
            }

            File destFile = new File(screenshotsDir, "FAILED_" + result.getName() + ".png");
            FileUtils.copyFile(srcFile, destFile);

            Allure.addAttachment("❌ " + result.getName(), new FileInputStream(destFile));
            System.out.println("📸 Screenshot saved at: " + destFile.getAbsolutePath());

        } catch (Exception e) {
            System.err.println("⚠️ Error while taking screenshot: " + e.getMessage());
        }
    }





    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⚠️ Test Skipped: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("🎬 Suite started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("🏁 Suite finished: " + context.getName());
    }


}