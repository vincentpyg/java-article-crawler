package com.vincentg.common;

/**
 * @author vincentg
 */
public class AppConstants {

    private AppConstants(){} //prevents class from being instantiated

    public static String TEMP_CRAWL_STORAGE_FOLDER = "tmp/crawl/root";

    public static String CONFIG_SOURCE_URL = "sourceURL";
    public static String CONFIG_CRAWL_PATTERN = "crawlPattern";
    public static String CONFIG_AUTHOR_PATTERN = "authorPattern";
    public static String CONFIG_HEADLINE_PATTERN = "headlinePattern";
    public static String CONFIG_CONTENT_PATTERN = "contentPattern";
    public static String CONFIG_MONGO_URI = "mongoURI";
    public static String CONFIG_MONGO_DB = "mongoDB";
    public static String CONFIG_MONGO_COLLECTION = "mongoCollection";
}
