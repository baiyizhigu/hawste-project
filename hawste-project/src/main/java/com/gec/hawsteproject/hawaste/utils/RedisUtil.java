package com.gec.hawsteproject.hawaste.utils;

/**
 * Redis 工具类
 *
 * @author gec
 * @date 2021/3/31
 */
public class RedisUtil {

    /**
     * 主数据系统标识
     */
    public static final String KEY_PREFIX = "hawaste";
    /**
     * 分割字符，默认[:]，使用:可用于可视化工具分组查看
     */
    private static final String KEY_SPLIT_CHAR = ":";

    /**
     * redis的key键规则定义
     * @param module 模块名称
     * @param func 方法名称
     * @param args 参数..
     * @return key
     */
    public static String keyBuilder(String module, String func, String... args) {
        return keyBuilder(null, module, func, args);
    }

    /**
     * redis的key键规则定义
     * @param module 模块名称
     * @param func 方法名称
     * @param objStr 对象.toString()
     * @return key
     */
    public static String keyBuilder(String module, String func, String objStr) {
        return keyBuilder(null, module, func, new String[]{objStr});
    }

    /**
     * redis的key键规则定义
     * @param prefix 项目前缀
     * @param module 模块名称
     * @param func 方法名称
     * @param objStr 对象.toString()
     * @return key
     */
    public static String keyBuilder(String prefix, String module, String func, String objStr) {
        return keyBuilder(prefix, module, func, new String[]{objStr});
    }

    /**
     * redis的key键规则定义
     * @param prefix 项目前缀
     * @param module 模块名称
     * @param func 方法名称
     * @param args 参数..
     * @return key
     */
    public static String keyBuilder(String prefix, String module, String func, String... args) {
        // 项目前缀
        if (prefix == null) {
            prefix = KEY_PREFIX;
        }
        StringBuilder key = new StringBuilder(prefix);
        // KEY_SPLIT_CHAR 为分割字符
        key.append(KEY_SPLIT_CHAR).append(module).append(KEY_SPLIT_CHAR).append(func);
        for (String arg : args) {
            key.append(KEY_SPLIT_CHAR).append(arg);
        }
        return key.toString();
    }
}