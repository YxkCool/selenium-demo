package com.page;

import org.openqa.selenium.By;

import java.io.IOException;

/**
 * MainPage
 *
 * @author Kayenee
 * @date 2020/11/17 下午10:25
 * @description 首页
 * @since 1.0.0
 */
public class MainPage extends BasePage {
    public MainPage() throws IOException, InterruptedException {
        driverClient();
        logined();

    }

    /**
     * 进入通讯录
     */
    public ContactsPage enterContactsPage() {
        click(By.id("menu_contacts"));
        return new ContactsPage();
    }


}
