package com.vincentg.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.vincentg.dto.CrawlParams;
import org.bson.Document;

/**
 * @author vincentg
 */
public class MongoDbWriter {
    private final MongoClient mongoClient;
    private final MongoCollection<Document> collection;

    /**
     * instantiate mongodb client from crawlParams
     * @param crawlParams parameters required to instantiate a mongodb client
     */
    public MongoDbWriter(CrawlParams crawlParams) {
        mongoClient = new MongoClient(new MongoClientURI(crawlParams.getMongoURI()));
        collection = mongoClient
                .getDatabase(crawlParams.getMongoDB())
                .getCollection(crawlParams.getMongoCollection());
    }

    /**
     * Insert an article into mongodb
     * @param json article in json format
     */
    public void insert(String json) {
        collection.insertOne(Document.parse(json));
    }

    public void close() {
        mongoClient.close();
    }
}
