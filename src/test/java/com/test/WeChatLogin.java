package com.test;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Kayenee
 * @Date: 2020/11/14 10:05
 * @since 1.0
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WeChatLogin {
    private  WebDriver driver;

    @BeforeAll
    void wechatLogin() throws IOException, InterruptedException {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://work.weixin.qq.com/wework_admin/loginpage_wx");
        Thread.sleep(15000);
        Set<Cookie> cookies = driver.manage().getCookies();
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.writeValue(new File("cookies.yml"), cookies);
    }


    @BeforeEach
    void wechatLogined() throws IOException, InterruptedException {

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://work.weixin.qq.com/wework_admin/loginpage_wx");
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        List<HashMap<String, Object>> cookies = mapper.readValue(new File("cookies.yml"), new TypeReference<List<HashMap<String, Object>>>() {
        });
        System.out.println(cookies);
        cookies.forEach(n -> {
            driver.manage().addCookie(new Cookie(n.get("name").toString(), n.get("value").toString()));
        });
        driver.navigate().refresh();
    }

    @Test
    void addUser() throws InterruptedException {
        driver.findElement(By.xpath("//*[@node-type='addmember']")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("username")).sendKeys("阿佳");
        driver.findElement(By.id("memberAdd_acctid")).sendKeys("KAY");
        driver.findElement(By.xpath("//*[@class='qui_btn ww_btn js_btn_save']")).click();

    }


}
