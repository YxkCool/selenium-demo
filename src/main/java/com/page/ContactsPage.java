package com.page;

import com.until.TestingUtil;
import org.openqa.selenium.By;

import java.io.File;

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

    private By partyInfo = By.cssSelector(".js_party_info");
    private String company = TestingUtil.getTestingConfig().getCompany();
    //部门删除按钮
    private By departmentDelete = By.xpath("//*[contains(text(),'部门ID')]/../preceding-sibling::*/*[text()='删除']");

    /**
     * 添加部门
     *
     * @return
     */
    public ContactsPage addDepartment(String departName) {
        implicitlyWait(2);
        click(By.linkText("添加"));
        click(By.linkText("添加部门"));
        sendKeys(By.name("name"), departName);
        click(By.linkText("选择所属部门"));
        click(By.xpath("//*[text()='所属部门']/../descendant::*[text()='" + company + "']"));
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
        String content = getText(partyInfo);
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
        webDriverWait(partyInfo, 3);
        click(partyInfo);
        String content = getText(partyInfo);
        System.out.println(content);
        return content;
    }

    /**
     * 清理人员
     *
     * @param
     */
    public void clearAllDeparts() {
//        deleteAllMember();
        deleteAllDeparts();
    }

    /**
     * 删除所有的成员
     */
    public void deleteAllMember() {
        implicitlyWait(2);
        click(By.xpath("//th/input"));
        click(By.linkText("删除"));
        click(By.linkText("确认"));
    }

    public void deleteAllDeparts() {
        implicitlyWait(2);
        //公司下的部门数
        By by = By.xpath("//*[text()='" + company + "']/../ul");
        long count = elementCount(by);
        for (int i = 1; i < count; i++) {
            click(by);
            click(departmentDelete);
            click(By.linkText("确定"));
        }
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

    /**
     * 删除部门失败
     *
     * @param keyword
     */
    public ContactsPage deleteDepartment(String keyword) {
        //点击更多按钮
        click(By.xpath("//*[text()='" + keyword + "']/child::*[@class='icon jstree-contextmenu-hover']\n"));
        //点击删除
        click(departmentDelete);
        implicitlyWait(3);
        click(By.linkText("确定"));
        return this;
    }

    /**
     * 更新部门
     *
     * @param keyword
     * @param department
     * @return
     */
    public ContactsPage updateDepartment(String keyword, String department) {
        //点击更多按钮
        click(By.xpath("//*[text()='" + department + "']/child::*[@class='icon jstree-contextmenu-hover']\n"));

        click(By.xpath("//*[contains(text(),'部门ID')]/../preceding-sibling::*/*[text()='修改名称']"));
        //输入名称
        sendKeys(By.name("name"), keyword);
        click(By.linkText("保存"));
        return this;
    }

    public ContactsPage enterMember(String name) {
        click(By.xpath("//*[@title='" + name + "']/.."));

        return this;
    }

    /**
     * 更新人员
     *
     * @param name
     * @param phoneNum
     */
    public ContactsPage updateMember(String name, String phoneNum) {
        By phoneNumInput = By.id("memberEdit_phone");
        enterMember(name);
        click(By.linkText("编辑"));
        //清空文本框
        clear(phoneNumInput);
        sendKeys(phoneNumInput, phoneNum);
        click(By.linkText("保存"));
        return this;
    }

    /**
     * 返回
     */
    public ContactsPage back() {
        click(By.linkText("返回"));
        return this;
    }

    /**
     * 成员详情
     *
     * @return
     */
    public String getMember() {
        implicitlyWait(2);
        String content = getText(By.cssSelector(".member_colRight"));
        System.out.println(content);
        return content;
    }

    /**
     * 删除成员
     *
     * @param name
     * @return
     */
    public ContactsPage deleteMember(String name) {
        enterMember(name);
        click(By.linkText("删除"));
        implicitlyWait(2);
        click(By.linkText("确认"));
        return this;
    }

    /**
     * 导入成员
     * @param path
     */
    public ContactsPage importMember(String path) {
        click(By.linkText("批量导入"));
        click(By.linkText("文件导入"));
        sendKeys(By.className("ww_fileImporter_fileContainer_uploadInputMask"),path);
        click(By.linkText("导入"));
        webDriverWait(By.linkText("完成"),20);
        click(By.linkText("完成"));
        return this;
    }
}
