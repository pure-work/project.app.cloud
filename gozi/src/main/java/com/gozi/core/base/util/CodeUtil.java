package com.gozi.core.base.util;

import java.util.UUID;

public class CodeUtil {

    public static String randomUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

}
