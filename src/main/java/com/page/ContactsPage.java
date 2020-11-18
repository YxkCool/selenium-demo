package com.page;

import org.openqa.selenium.By;

/**
 * ContactsPage
 *
 * @author Kayenee
 * @date 2020/11/17 下午10:28
 * @description 通讯录
 * @since 1.0.0
 * 部门测试
 * 成员测试
 * 引入参数化
 */
public class ContactsPage extends BasePage {

    private By parterInfo = By.cssSelector(".js_party_info");

    /**
     * 添加部门
     *
     * @return
     */
    public ContactsPage addDepartment(String departName) {
        click(By.linkText("添加"));
        click(By.linkText("添加部门"));
        sendKeys(By.name("name"), departName);
        click(By.linkText("选择所属部门"));
        click(By.linkText("杭州叩叩叩网络科技有限公司"));
        click(By.linkText("确定"));
        return this;
    }


    /**
     * 部门搜索
     *
     * @param keyword
     * @return
     */
    public ContactsPage searchDepart(String keyword) {
        sendKeys(By.id("memberSearchInput"), keyword);
        String content = driver.findElement(parterInfo).getText();
        System.out.println(content);
        click(By.id("clearMemberSearchInput"));
        return this;

    }

    /**
     * 人员列表
     *
     * @return
     */
    public String getPartyInfo() {
        String content = driver.findElement(parterInfo).getText();
        System.out.println(content);
        return content;
    }

    /**
     * 清理人员
     *
     * @param departName
     */
    public void clearAllDeparts(String departName) {
        searchDepart(departName);
        //todo: 删除所有的成员
        //todo: 所有的部门
    }

    /**
     * 添加人员
     *
     * @param name     姓名
     * @param accId    账户
     * @param phoneNum 手机号
     */
    public ContactsPage addMember(String name, String accId, String phoneNum, String department) {
        click(By.linkText(department));
        click(By.linkText("添加成员"));
        sendKeys(By.id("username"), name);
        sendKeys(By.id("memberAdd_acctid"), accId);
        sendKeys(By.id("memberAdd_phone"), phoneNum);
        click(By.linkText("保存"));
        return this;
    }
}
