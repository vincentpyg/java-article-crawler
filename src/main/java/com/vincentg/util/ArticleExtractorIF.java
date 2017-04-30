package com.vincentg.util;

import org.json.simple.JSONObject;

/**
 * Support for creating custom extractors
 * @author vincentg
 */
public interface ArticleExtractorIF {
    JSONObject extractArticle(String page);
}
