package edu.sse.ustc.elasticsearch;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;

/** Elasticsearch 查询
 * @author imarklei90
 * @since 2019.04.21
 */
public class Elasticsearch_Query {

    PreBuiltTransportClient client = null;

    @Before
    public void getClient() throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", "my-application").build();
        client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("hadoop101"), 9300));
    }

    // 查询所有
    @Test
    public void queryAll(){
        // 执行查询
        SearchResponse searchResponse = client.prepareSearch("blog").setTypes("article").setQuery(QueryBuilders.matchAllQuery()).get();

        // 结果输出
        SearchHits hits = searchResponse.getHits();
        System.out.println("结果条数 ： " + hits.getTotalHits());
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            SearchHit hit = iterator.next();
            System.out.println(hit.getSourceAsString());
        }

        // 关闭资源
        client.close();
    }

    // 对所有字段分词查询
    @Test
    public void queryParticiple(){
        // 执行查询
        SearchResponse searchResponse = client.prepareSearch("blog").setTypes("article").setQuery(QueryBuilders.queryStringQuery("elasticsearch中国")).get();

        // 结果输出
        SearchHits hits = searchResponse.getHits();
        Iterator<SearchHit> iterator = hits.iterator();
        while(iterator.hasNext()){
            SearchHit hit = iterator.next();
            System.out.println(hit.getSourceAsString());
        }

        // 关闭资源
        client.close();
    }

    // 词条查询
    @Test
    public void queryItem(){
        //  执行查询
        SearchResponse searchResponse = client.prepareSearch("blog").setTypes("article").setQuery(QueryBuilders.termQuery("content", "搜")).get(); // 汉字一个个切分

        // 结果输出
        SearchHits hits = searchResponse.getHits();
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            SearchHit hit = iterator.next();
            System.out.println(hit.getSourceAsString());
        }

        // 关闭资源

    }

    // 通配符查询
    @Test
    public void queryWildcard(){
        // 执行查询
        SearchResponse searchResponse = client.prepareSearch("blog").setTypes("article").setQuery(QueryBuilders.wildcardQuery("content", "*搜*")).get();

        // 结果输出
        SearchHits hits = searchResponse.getHits();
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            SearchHit hit = iterator.next();
            System.out.println(hit.getSourceAsString());
        }

        // 关闭资源
        client.close();
    }

    // 模糊查询
    @Test
    public void fuzzyQuery(){
        // 执行查询
        SearchResponse searchResponse = client.prepareSearch("blog").setTypes("article").setQuery(QueryBuilders.fuzzyQuery("content", "elasticsearch")).get();

        // 结果输出
        SearchHits hits = searchResponse.getHits();
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            SearchHit hit = iterator.next();
            System.out.println(hit.getSourceAsString());
        }

        // 关闭资源
        client.close();
    }
}
