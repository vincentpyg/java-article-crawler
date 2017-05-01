package com.vincentg.crawler;

import com.vincentg.dto.CrawlParams;
import com.vincentg.util.ArticleExtractorIF;
import com.vincentg.util.MongoDbWriter;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * @author vincentg
 */
public class ArticleCrawler extends WebCrawler {

    private final Logger LOG = LoggerFactory.getLogger(ArticleCrawler.class.getName());

    private Pattern SITE_FILTER;
    private CrawlParams crawlParams;
    private ArticleExtractorIF extractor;
    private MongoDbWriter mongoWriter;

    @Override
    public boolean shouldVisit(Page referringPage, WebURL webURL) {
        //visit site rules
        return SITE_FILTER.matcher(webURL.getURL()).find();
    }


    @Override
    public void init(int id, CrawlController crawlController) throws InstantiationException, IllegalAccessException {
        super.init(id, crawlController);
        //set crawl application parameters
        crawlParams = (CrawlParams) this.getMyController().getCustomData();
        extractor = crawlParams.getExtractor();
        SITE_FILTER = Pattern.compile(crawlParams.getCrawlPattern());
        mongoWriter = new MongoDbWriter(crawlParams);
    }

    @Override
    public void visit(Page page) {
        String pageContent = new String(page.getContentData());
        try {
            JSONObject article = extractor.extractArticle(pageContent);
            //write article to mongo and log it to debug
            if (article != null) {
                LOG.debug(article.toJSONString());
                mongoWriter.insert(article.toJSONString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBeforeExit() {
        super.onBeforeExit();
        //close mongodb connection
        mongoWriter.close();
    }
}
