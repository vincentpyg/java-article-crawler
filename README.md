## java-article-crawler

Web crawler used to crawl and extract information from articles in bbc.com and theguardian.com

### Commandline Arguments

|Parameter|Value|Required|Default|
|---|---|---|---|
|`-c`|configuration properties file|Yes|N/A|
|`-d`|Crawl depth where `0` = source page only, `1` = one level of links from the source page, etc.|No|0|
|`-t`|Number of crawl threads|No|1|

### Configuration properties file
|Configuration Parameter|Value|
|---|---|
|`sourceURL`|Source URL where the crawl will start|
|`crawlPattern`|Regex pattern to identify pages that should be crawled|
|`authorPattern`|Regex pattern used to extract the author of the article|
|`headlinePattern`|Regex pattern used to extract the headline of the article|
|`contentPattern`|class name where the article's content is located|
|`mongoURI`|mongodb URI (complete server address)|
|`mongoDB`|mongodb database name|
|`mongoCollection`|mondodb collection where the articles will be stored|

### Running the application
```$Bash
$ java -jar ${APPLICATION_JAR} -d ${CRAWL_DEPTH} -t ${CRAWL_THREADS} -c ${CONFIG_PROPERTIES_FILE}

```