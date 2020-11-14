package com.test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author Kayenee
 * @Date: 2020/11/14 10:11
 * @since 1.0
 */
public class WebMockTest {

    @Test
    void testSearch() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://work.weixin.qq.com/wework_admin/loginpage_wx");
    }

}
