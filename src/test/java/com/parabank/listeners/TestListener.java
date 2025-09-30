package com.parabank.listeners;

import com.parabank.managers.DriverManager;
import com.parabank.utils.ConfigReader;
import org.openqa.selenium.*;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Simple TestNG listener that logs test events and saves a screenshot on failure.
 * Screenshot folder comes from config.properties (screenshot.path) with a timestamped file name.
 */
public class TestListener implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		System.out.println("Starting Test: " + result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("Test Passed: " + result.getName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("Test Failed: " + result.getName());
		takeScreenshot(result.getName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("Test Skipped: " + result.getName());
	}

	@Override
	public void onStart(ITestContext context) {
		System.out.println("Starting Test Suite: " + context.getName());
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("Finished Test Suite: " + context.getName());
	}

	private void takeScreenshot(String testName) {
		// Try to get the current driver
		WebDriver driver;
		try {
			driver = DriverManager.getDriver();
		} catch (IllegalStateException e) {
			System.out.println("Driver not initialized, skipping screenshot for: " + testName);
			return;
		}

		// If the session is already gone, don't error out
		try {
			if (driver instanceof HasCapabilities) {
				if (((HasCapabilities) driver).getCapabilities() == null) {
					System.out.println("Driver capabilities not available, skipping screenshot for: " + testName);
					return;
				}
			}
		} catch (Throwable ignored) {}

		try {
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			// Use screenshot.path from config; default to "screenshots/" if missing
			String baseDir = ConfigReader.get("screenshot.path");
			if (baseDir == null || baseDir.isBlank()) baseDir = "screenshots/";
			String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
			File destFile = new File(baseDir + testName + "_" + ts + ".png");

			// Ensure folder exists
			File parent = destFile.getParentFile();
			if (parent != null && !parent.exists()) parent.mkdirs();

			Files.copy(srcFile.toPath(), destFile.toPath());
			System.out.println("Screenshot saved at: " + destFile.getAbsolutePath());
		} catch (WebDriverException wde) {
			System.out.println("Could not capture screenshot (session may be gone): " + wde.getClass().getSimpleName());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Throwable t) {
			System.out.println("Unexpected error taking screenshot: " + t.getMessage());
		}
	}
}
