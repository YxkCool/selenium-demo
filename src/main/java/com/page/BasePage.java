package com.page;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.until.TestingUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * BasePage
 *
 * @author Keyenee
 * @date 2020/11/17 下午10:52
 * @description 基础操作
 * @since 1.0.0
 */
public class BasePage {
    static WebDriver driver;

    public WebDriver driverClient() {
        //利用cookie复用session登录
        if (TestingUtil.getTestingConfig().getBrowser().equals("chrome")) {
            driver = new ChromeDriver();
        } else if (TestingUtil.getTestingConfig().getBrowser().equals("firefox")) {
            driver = new FirefoxDriver();
        }
        return driver;
    }

    public void getUrl(String url) {
        driver.get(url);
    }


    public String getText(By partyInfo) {
        return driver.findElement(partyInfo).getText();

    }

    public void implicitlyWait(long time) {
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);

    }

    public void login() throws InterruptedException, IOException {
        implicitlyWait(10);
        getUrl("https://work.weixin.qq.com/wework_admin/frame");
        //sleep 20
        Thread.sleep(15000);
        Set<Cookie> cookies = driver.manage().getCookies();
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.writeValue(new File("cookies.yaml"), cookies);
        System.exit(0);

    }

    public void logined() throws IOException, InterruptedException {
        File file = new File("cookies.yaml");
        if (file.exists()) {

            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.get("https://work.weixin.qq.com/wework_admin/frame");

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            List<HashMap<String, Object>> cookies = mapper.readValue(file, new TypeReference<List<HashMap<String, Object>>>() {
            });
            System.out.println(cookies);

            cookies.forEach(cookieMap -> {
                driver.manage().addCookie(new Cookie(cookieMap.get("name").toString(), cookieMap.get("value").toString()));
            });

            driver.navigate().refresh();
        } else {
            login();
        }
    }

    /**
     * 单击
     *
     * @param by 元素
     */
    public void click(By by) {
        driver.findElement(by).click();
    }

    /**
     * 输入文本
     *
     * @param by 元素
     */
    public void sendKeys(By by, String content) {
        driver.findElement(by).sendKeys(content);
    }

    /**
     * 清理文本框
     *
     * @param phoneNumInput
     */
    public void clear(By phoneNumInput) {
        driver.findElement(phoneNumInput).clear();
    }

    /**
     * 关闭浏览器
     */
    public void close() {
        driver.quit();
    }


    /**
     * 显示等待
     * @param element
     * @param timeout
     */
    public void webDriverWait(By element, int timeout) {
        WebDriverWait webDriverWait = new WebDriverWait(this.driver, timeout);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public long elementCount(By by){
       return driver.findElements(by).stream().count();
    }

}
