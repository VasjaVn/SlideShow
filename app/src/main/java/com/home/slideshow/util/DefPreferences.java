package com.home.slideshow.util;

public class DefPreferences {

    public static final String DEFAULT_TYPE_SOURCE_OF_IMAGES = "local";

    public static final int DEFAULT_INTERVAL_IN_SECONDS = 5;

    public static final int DEFAULT_TIME_START_HOUR   = 8;
    public static final int DEFAULT_TIME_START_MINUTE = 0;

    public static final int DEFAULT_TIME_STOP_HOUR    = 8;
    public static final int DEFAULT_TIME_STOP_MINUTE  = 5;


    public static class Postfix {
        public static final String DOT_HOURS = ".hours";
        public static final String DOT_MINUTES = ".minutes";
    }

    public static class TypeSourceOfImages {
        public static final String LOCAL = "local";
        public static final String HTTP  = "http";
    }

}
