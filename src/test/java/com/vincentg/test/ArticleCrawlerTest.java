package com.vincentg.test;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.vincentg.controller.ArticleController;
import com.vincentg.dto.CrawlParams;
import com.vincentg.extractors.ArticleExtractor;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.bson.Document;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author vincentg
 */
public class ArticleCrawlerTest {

    private static final MongodStarter starter = MongodStarter.getDefaultInstance();

    private static MongodExecutable mongodExecutable;
    private static MongodProcess mongodProcess;
    private static ServerAddress serverAddress;

    @BeforeClass
    public static void setup() throws Exception {
        mongodExecutable = starter.prepare(new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net("localhost", 12345, Network.localhostIsIPv6()))
                .build());
        mongodProcess = mongodExecutable.start();
    }
    @Test
    public void test() {
        CrawlParams crawlParams = CrawlParams.builder()
                .crawlThreads(5)
                .depth(1)
                .sourceURL("http://www.bbc.com/news")
                .crawlPattern("(www[.])?bbc.com(/news/).*")
                .extractor(ArticleExtractor.builder()
                        .authorPattern("\"author\":\"([^\"]*)\"")
                        .headlinePattern("\"headline\":\"([^\"]*)\"")
                        .contentPattern("story-body__inner")
                        .build())
                .mongoURI("mongodb://localhost:12345")
                .mongoDB("testDB")
                .mongoCollection("testCollection")
                .build();
        ArticleController bbcCtrl = new ArticleController(crawlParams);
        bbcCtrl.start();

        MongoClient client = new MongoClient("localhost",12345);
        MongoCollection<Document> dbCollection = client.getDatabase("testDB").getCollection("testCollection");
        System.out.println("totalRecords = "+dbCollection.count());
        Assert.assertNotEquals(dbCollection.count(),0);
    }


    @AfterClass
    public static void close() {
        mongodProcess.stop();
        mongodExecutable.stop();
    }
}
