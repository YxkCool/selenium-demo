package com.test;

import com.page.ContactsPage;
import com.page.MainPage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContactsTest {

    private static MainPage mainPage;

    private static ContactsPage contactsPage;

    static Stream<String> stringDepartment() {
        return Stream.of("测试部");
    }

    @BeforeAll
    @DisplayName("清理数据")
    static void beforeAll() throws IOException, InterruptedException {
        mainPage = new MainPage();
        contactsPage = mainPage.enterContactsPage();
//        contactsPage.clearAllDeparts();
    }


    @ParameterizedTest
    @MethodSource("stringDepartment")
    @DisplayName("添加部门")
    @Order(1)
    void testDepartAdd(String departName) {
        assertTrue(contactsPage.addDepartment(departName).searchDepart(departName).getPartyInfo().contains(departName));
    }


    @ParameterizedTest
    @CsvSource({"ky, 001,18700001234,测试部"})
    @DisplayName("添加人员")
    @Order(2)
    void testAddMember(String name, String accId, String phoneNum, String department) {
        //添加人员
        contactsPage.addMember(name, accId, phoneNum, department);
        String content = contactsPage.getPartyInfo();
        assertTrue(content.contains(name));
    }

    @ParameterizedTest
    @MethodSource("stringDepartment")
    @DisplayName("删除部门，失败")
    @Order(3)
    void testDeletedDepartFailed(String keyword) {
        contactsPage.deleteDepartment(keyword);
        String content = contactsPage.getPartyInfo();
        assertTrue(content.contains(keyword));
    }

    @ParameterizedTest
    @CsvSource({"修改测试部,测试部"})
    @DisplayName("更新部门")
    @Order(4)
    void testUpdateDepart(String update, String department) {
        contactsPage.updateDepartment(update, department);
        String content = contactsPage.getPartyInfo();
        //还原部门名称
        contactsPage.updateDepartment(department, update);
        assertTrue(content.contains(update));

    }


    @ParameterizedTest
    @CsvSource({"ky, 18700000000"})
    @DisplayName("更新人员信息")
    @Order(5)
    void testUpdateMember(String name, String phoneNum) {
        contactsPage.updateMember(name, phoneNum);
        String content = contactsPage.getMember();
        assertTrue(content.contains(""));
        //返回人员列表
        contactsPage.back();
    }

    @ParameterizedTest
    @ValueSource(strings = "ky")
    @DisplayName("删除人员信息")
    @Order(6)
    void testDeleteMember(String name) {
        contactsPage.deleteMember(name);
        String content = contactsPage.getPartyInfo();
        assertFalse(content.contains(name));

    }

//    @ParameterizedTest
//    @CsvFileSource(resources = "/member.csv")
//    @DisplayName("导入人员")
//    @Order(7)
//    void testImportMember(String name, String accId, String phoneNum, String department) {
//        //添加人员
//        contactsPage.addMember(name, accId, phoneNum, department);
//        String content = contactsPage.getPartyInfo();
//        contactsPage.deleteAllMember();
//        assertTrue(content.contains(name));
//    }

    @Test
    @DisplayName("导入人员")
    @Order(7)
    void testImportMember() {
        //添加人员
        File file = new File("src/main/resources/通讯录批量导入模板.xlsx");
        contactsPage.importMember(file.getAbsolutePath());
        String content = contactsPage.getPartyInfo();
        contactsPage.deleteAllMember();
        assertFalse(content.contains("无任何成员"));
    }

    @ParameterizedTest
    @MethodSource("stringDepartment")
    @DisplayName("删除部门,成功")
    @Order(8)
    void testDeletedDepart(String keyword) {
        contactsPage.deleteDepartment(keyword);
        String content = contactsPage.getPartyInfo();
        assertFalse(content.contains(keyword));
    }

}
