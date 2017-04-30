package com.vincentg.dto;

import com.vincentg.util.ArticleExtractorIF;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vincentg
 */
@Getter
public class CrawlParams {

    private final String sourceURL;
    private final ArticleExtractorIF extractor;
    private final String crawlPattern;
    private final int depth;
    private final int crawlThreads;

    private final String mongoURI;
    private final String mongoDB;
    private final String mongoCollection;

    private CrawlParams(Builder crawlParamBuilder) {

        sourceURL = crawlParamBuilder.sourceURL;
        extractor = crawlParamBuilder.extractor;
        crawlPattern = crawlParamBuilder.crawlPattern;
        depth = crawlParamBuilder.depth;
        crawlThreads = crawlParamBuilder.crawlThreads;

        mongoURI = crawlParamBuilder.mongoURI;
        mongoDB = crawlParamBuilder.mongoDB;
        mongoCollection = crawlParamBuilder.mongoCollection;

    }

    public static Builder builder() {
        return new Builder();
    }

    @Setter
    public static class Builder {

        private ArticleExtractorIF extractor;
        private String crawlPattern;
        private String sourceURL;
        private int depth;
        private int crawlThreads;

        private String mongoURI;
        private String mongoDB;
        private String mongoCollection;

        public Builder mongoURI(String mongoURI) {
            this.mongoURI = mongoURI;
            return this;
        }
        public Builder mongoDB(String mongoDB) {
            this.mongoDB = mongoDB;
            return this;
        }

        public Builder mongoCollection(String mongoCollection) {
            this.mongoCollection = mongoCollection;
            return this;
        }


        public Builder sourceURL(String sourceURL) {
            this.sourceURL = sourceURL;
            return this;
        }

        public Builder depth(int depth) {
            this.depth = depth;
            return this;
        }

        public Builder crawlThreads(int crawlThreads) {
            this.crawlThreads = crawlThreads;
            return this;
        }

        public Builder extractor(ArticleExtractorIF extractor) {
            this.extractor = extractor;
            return this;
        }

        public Builder crawlPattern(String crawlPattern) {
            this.crawlPattern = crawlPattern;
            return this;
        }

        public CrawlParams build() {
            return new CrawlParams(this);
        }
    }
}





