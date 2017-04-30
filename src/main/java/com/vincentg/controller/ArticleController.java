package com.vincentg.controller;

import com.vincentg.crawler.ArticleCrawler;
import com.vincentg.dto.CrawlParams;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import lombok.AllArgsConstructor;

import static com.vincentg.common.AppConstants.TEMP_CRAWL_STORAGE_FOLDER;

/**
 * @author vincentg
 */
@AllArgsConstructor
public class ArticleController {

    private final CrawlParams crawlParams;

    public void start() {
        try {

            String crawlStorageFolder = TEMP_CRAWL_STORAGE_FOLDER;

            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(crawlStorageFolder);
            config.setMaxDepthOfCrawling(crawlParams.getDepth());

            PageFetcher pageFetcher = new PageFetcher(config);
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
            CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

            controller.setCustomData(crawlParams);
            controller.addSeed(crawlParams.getSourceURL());
            controller.start(ArticleCrawler.class, crawlParams.getCrawlThreads());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
