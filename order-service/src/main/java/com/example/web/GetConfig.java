package com.example.web;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;

public class GetConfig {
    public static void main(String[] args) {
        Config config = ConfigService.getAppConfig();

        String value = config.getProperty("sms.enable",null);

        System.out.println(value);

    }
}
