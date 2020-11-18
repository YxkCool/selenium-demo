package com.test;

import com.page.ContactsPage;
import com.page.MainPage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ContactsTest
 *
 * @author Kayenee
 * @date 2020/11/17 下午10:35
 * @description 通讯录测试
 * @since 1.0.0
 */
public class ContactsTest {

    private static MainPage mainPage;

    static Stream<String> stringDepartment() {
        return Stream.of("测试部");
    }

    @BeforeAll
    static void beforeAll() throws IOException, InterruptedException {
        mainPage = new MainPage();
//        清理数据
        mainPage.enterContactsPage().clearAllDeparts("测试部");
    }

    @Order(1)
    @ParameterizedTest
    @DisplayName("检查人员是否存在")
    @CsvSource({ "ky"})
    void testSearchMember(String name,String accId,String phoneNum) throws IOException, InterruptedException {
        //进入通讯录
        ContactsPage contactsPage = mainPage.enterContactsPage();
        //搜索对应人员
        contactsPage.searchDepart(name);
        String content = contactsPage.getPartyInfo();
        assertFalse(content.contains("无任何成员"));
    }

    @Order(2)
    @ParameterizedTest
    @MethodSource("stringDepartment")
    @DisplayName("检查部门是否为空")
    void testDepartSearchChain(String keyword) throws IOException, InterruptedException {
        assertFalse(new MainPage().enterContactsPage().searchDepart(keyword).getPartyInfo().contains("测试部"));
    }

//    todo: 部门新建 部门搜索 部门的更新 部门内添加成员 导入成员

    @ParameterizedTest
    @MethodSource("stringDepartment")
    @DisplayName("添加部门")
    @Order(3)
    void testDepartAdd(String departName) {
        assertTrue(mainPage.enterContactsPage().addDepartment(departName).searchDepart(departName).getPartyInfo().contains(departName));
    }
    @Order(4)
    @ParameterizedTest
    @CsvSource({ "ky, 001,18700001234,测试部"})
    void testAddMember(String name,String accId,String phoneNum,String department){
        //进入通讯录
        ContactsPage contactsPage = mainPage.enterContactsPage();
        //添加人员
        contactsPage.addMember(name,accId,phoneNum,department);
        String content = contactsPage.getPartyInfo();
        assertTrue(content.contains(name));
    }


}
