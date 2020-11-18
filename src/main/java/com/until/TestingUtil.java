package com.until;

import com.model.TestConfig;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

/**
 * TestingUtil
 *
 * @author Kayenee
 * @date 2020/11/18 下午3:42
 * @description 测试工具类
 * @since 1.0.0
 */
public class TestingUtil {

    /**
     * 获取配置文件.
     *
     * @return 配置文件对象
     */
    public static TestConfig getTestingConfig() {
        Yaml yaml = new Yaml(new Constructor(TestConfig.class));
        InputStream inputStream = TestingUtil.class.getClassLoader().getResourceAsStream("config.yml");
        return yaml.load(inputStream);
    }

}
