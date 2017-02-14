package com.alibaba.fastjson.parser;

@Deprecated
public class JavaBeanMapping extends ParserConfig {
    private static final JavaBeanMapping instance = new JavaBeanMapping();

    public static JavaBeanMapping getGlobalInstance() {
        return instance;
    }
}
