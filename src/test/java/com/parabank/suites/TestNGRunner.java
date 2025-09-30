package com.parabank.suites;

import org.testng.TestNG;

import java.util.List;

/**
 * Small launcher if you want to run TestNG directly (outside Maven).
 * Note: Maven Surefire already points to src/test/resources/testng.xml.
 */
public class TestNGRunner {
	public static void main(String[] args) {
		TestNG testng = new TestNG();
		testng.setTestSuites(List.of("src/test/resources/testng.xml"));
		testng.run();
	}
}
