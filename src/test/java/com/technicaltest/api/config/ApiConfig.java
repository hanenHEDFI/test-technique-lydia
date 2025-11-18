package com.technicaltest.api.config;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiConfig {

    private static final Properties props = new Properties();

    static {
        try (InputStream is = ApiConfig.class
                .getClassLoader()
                .getResourceAsStream("api/api-config.properties")) {

            if (is == null) {
                throw new IllegalStateException("api-config.properties not found in classpath");
            }
            props.load(is);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static String getBaseUri() {
        return get("base.uri");
    }

    public static String getApiKey() {
        return get("api.key");
    }

    public static String getRegisterEmail() {
        return get("register.email");
    }

    public static String getRegisterPassword() {
        return get("register.password");
    }

    public static String getRegisterFirst_name() {
        return get("register.first_name");
    }

    public static String getRegisterLast_name() {
        return get("register.last_name");
    }

}
