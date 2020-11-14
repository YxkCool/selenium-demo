package com.test;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;
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
public class WeChatLogin {

    @Test
    void wechatLogin() throws IOException, InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://work.weixin.qq.com/wework_admin/loginpage_wx");
        Thread.sleep(15000);
        Set<Cookie> cookies = driver.manage().getCookies();
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.writeValue(new File("cookies.yml"), cookies);
    }


    @Test
    void wechatLogined() throws IOException, InterruptedException {
        WebDriver driver = new ChromeDriver();
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


}
