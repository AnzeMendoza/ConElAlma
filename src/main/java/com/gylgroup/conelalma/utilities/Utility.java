package com.gylgroup.conelalma.utilities;

import org.apache.commons.lang3.RandomStringUtils;

public class Utility {

    public static String randomName() {
        return RandomStringUtils.randomAlphanumeric(4).toUpperCase();
    }

}
