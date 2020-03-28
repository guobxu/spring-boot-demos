package com.sf.demo;

/**
 * Creator: 01380994
 * Date: 2020/2/10
 *
 * 1. 安装chrome, 版本80.0.3987.87
 * 2. 安装chromedriver, 解压chromedriver_linux64.zip到目录/app/selenium/
 * 3. 安装中文字体
 *
 */
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class DemoMain implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoMain.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoMain.class, args);
    }

    @Value("${selenium.chromedriver.path}")
    private String chromeDriverPath;

    @Value("${selenium.screenshot.savePath}")
    private String screenshotSavePath;

    @Override
    public void run(String... strings) throws Exception {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        // ChromeDriver 在访问url时会打开本地安装的chrome浏览器，需根据本地安装chrome的版本下载对应版本的driver。
        // 下载地址： https://sites.google.com/a/chromium.org/chromedriver/downloads
//        WebDriver driver = new ChromeDriver();
//        driver.get("https://www.baidu.com/s?wd=hello%20world");
//
//        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("disable-infobars");
        options.addArguments("--headless");

        ChromeDriverExt driver = new ChromeDriverExt(options);
        driver.manage().window().setSize(new Dimension(1440, 900));

//        driver.get("http://cdbi.dev.sf-express.com/v2/#/external-download-mission");
        // Caused by: org.openqa.selenium.InvalidCookieDomainException: invalid cookie domain
        // Fix: 在get url之后设置cookie
        // https://stackoverflow.com/questions/45842709/unable-to-set-cookies-in-selenium-webdriver
//        Cookie cookie = new Cookie("cdbidevsession", "6f5b4f59-8bf6-4f71-b03f-c57672b6dd83",
//                ".sf-express.com", "/", null);
//        driver.manage().addCookie(cookie);

        driver.get("http://cdbi.sf-express.com/v2/#/external-snapshot/c35d9749006949e2a5ec7a486c9a01cc?dashboardIndex=0&appId=BDP_CDBI&token=MUMxMjU4REMxNDdFNzRBQUFGMzEzOTRFRThFNERFMERCNjU4QTk1QjIwNzBBNUVENDc4QjIzOTk1MDQ2MjQ5Mw==&extId=01380994&purge=1");

        if(!waitForElementPresentById(driver, "dashboardRenderedFlag", 300)) {
            LOGGER.warn("报表未能渲染");
        }

        File srcFile = driver.getFullScreenshotAs(OutputType.FILE);

        try {
            //复制内容到指定文件中
            FileUtils.copyFile(srcFile, new File(screenshotSavePath, "cdbireport.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver.quit();
    }

    public static boolean waitForElementPresentById(WebDriver webDriver, String elementId, int seconds) {
        try {
            new WebDriverWait(webDriver, seconds)
                    .until(ExpectedConditions.presenceOfElementLocated(By.id(elementId)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}

