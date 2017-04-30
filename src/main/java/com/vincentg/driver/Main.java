package com.vincentg.driver;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.vincentg.controller.ArticleController;
import com.vincentg.dto.CrawlParams;
import com.vincentg.extractors.ArticleExtractor;
import com.vincentg.util.PropertiesConverter;
import com.vincentg.util.PropertiesValidator;
import lombok.NoArgsConstructor;

import java.util.Properties;

import static com.vincentg.common.AppConstants.*;

/**
 * @author vincentg
 */
@NoArgsConstructor
public class Main {

    @Parameter(names = {"-t","-threads"})
    private int crawlThreads = 1;

    @Parameter(names = {"-d","--depth"})
    private int depth = 0; //default depth

    @Parameter(names = {"-c", "--config"},
            converter = PropertiesConverter.class,
            validateValueWith = PropertiesValidator.class,
            required = true)
    Properties config;

    public static void main(String... args) {
        Main main = new Main();
        JCommander.newBuilder().addObject(main).args(args).build();
        main.run();
    }

    private void run() {

        System.out.println(config.getProperty(CONFIG_AUTHOR_PATTERN));
        System.out.println(config.getProperty(CONFIG_CRAWL_PATTERN));
        CrawlParams crawlParams = CrawlParams.builder()
                .crawlThreads(crawlThreads)
                .depth(depth)
                .sourceURL(config.getProperty(CONFIG_SOURCE_URL))
                .crawlPattern(config.getProperty(CONFIG_CRAWL_PATTERN))
                .extractor(ArticleExtractor.builder()
                    .authorPattern(config.getProperty(CONFIG_AUTHOR_PATTERN))
                    .headlinePattern(config.getProperty(CONFIG_HEADLINE_PATTERN))
                    .contentPattern(config.getProperty(CONFIG_CONTENT_PATTERN))
                    .build())
                .mongoURI(config.getProperty(CONFIG_MONGO_URI))
                .mongoDB(config.getProperty(CONFIG_MONGO_DB))
                .mongoCollection(config.getProperty(CONFIG_MONGO_COLLECTION))
                .build();

        ArticleController articleCtrl = new ArticleController(crawlParams);
        articleCtrl.start();
    }
}
