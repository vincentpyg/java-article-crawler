package com.vincentg.extractors;

import com.vincentg.util.ArticleExtractorIF;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author vincentg
 */
public class ArticleExtractor implements ArticleExtractorIF {

    private final Pattern authorPattern;
    private final Pattern headlinePattern;
    private final String contentPattern;

    @Override
    public JSONObject extractArticle(String page) {
        JSONObject jo = new JSONObject();

        String author = extractAuthor(page);
        String content = extractContent(page);
        String headline = extractHeadline(page);

        if (author.isEmpty() || content.isEmpty())
            return null;

        jo.put("author", author);
        jo.put("content", content);
        jo.put("headline", headline);

        return jo;
    }

    private ArticleExtractor(Builder articleExtractorBuilder) {
        authorPattern = Pattern.compile(articleExtractorBuilder.authorPattern);
        headlinePattern = Pattern.compile(articleExtractorBuilder.headlinePattern);
        contentPattern = articleExtractorBuilder.contentPattern;
    }

    private String extractAuthor(String page) {
        return extractArticleAttribute(authorPattern, page);
    }

    private String extractHeadline(String page) {
        return extractArticleAttribute(headlinePattern, page);
    }

    private String extractArticleAttribute(Pattern attributePattern, String page) {
        String attribute = "";
        try {
            Matcher m = attributePattern.matcher(page);
            if (m.find())
                attribute = m.group(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attribute;
    }

    private String extractContent(String page) {
        String content = "";
        Document document = Jsoup.parse(page);
        Elements story = document.getElementsByClass(contentPattern);
        Elements elements = story.select("p");
        for (Element e : elements)
            content += e.ownText();
        return content;
    }


    public static Builder builder() {
        return new Builder();
    }


    @Setter
    public static class Builder {

        private String authorPattern;
        private String headlinePattern;
        private String contentPattern;

        public Builder authorPattern(String authorPattern){
            this.authorPattern = authorPattern;
            return this;
        }
        public Builder headlinePattern(String headlinePattern){
            this.headlinePattern = headlinePattern;
            return this;
        }
        public Builder contentPattern(String contentPattern){
            this.contentPattern = contentPattern;
            return this;
        }

        public ArticleExtractor build() {
            return new ArticleExtractor(this);
        }
    }
}
