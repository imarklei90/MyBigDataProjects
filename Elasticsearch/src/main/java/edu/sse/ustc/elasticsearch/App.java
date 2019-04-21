package edu.sse.ustc.elasticsearch;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

/**
 * Elasticsearch
 * @author imarklei90
 * @since 2019.04.20
 */
public class App 
{
    PreBuiltTransportClient client = null;

    //  获取客户端对象
    @Before
    public void test() throws UnknownHostException {
        // 1. 设置连接的Elasticsearch集群的名称
        Settings settings = Settings.builder().put("cluster.name", "my-application").build();

        // 2. 连接集群
        client = new PreBuiltTransportClient(settings);

        // 3. 添加需要连接的集群的地址
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("hadoop101"), 9300));

        System.out.println(client.toString());
    }

    // 创建索引
    @Test
    public void createIndex(){
        // 创建索引
        client.admin().indices().prepareCreate("website").get();

        // 关闭资源
        client.close();

    }

    // 删除索引
    @Test
    public void deleteIndex(){
        // 删除索引
        client.admin().indices().prepareDelete("blog").get();

        // 关闭资源
        client.close();
    }

    // 创建文档（Json格式）
    @Test
    public void createDocumentByJson(){

        // 构建Json串
        String json = "{" + "\"id\":\"1\",\"title\":\"elasticsearch\",\"content\":\"elasticsearch搜索服务器\"" + "}";

        // 创建文档
        IndexResponse indexResponse = client.prepareIndex("blog", "article", "1").setSource(json).execute().actionGet();

        // 输出信息
        System.out.println(indexResponse.getId());
        System.out.println(indexResponse.getResult());
        System.out.println(indexResponse.getShardId());
        System.out.println(indexResponse.getShardInfo());
        System.out.println(indexResponse.getType());
        System.out.println(indexResponse.getVersion());

        client.close();

    }

    // 创建文档（map形式）
    @Test
    public void createDocumentByMap(){
        // 构建Map
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", 2);
        dataMap.put("title", "mapTitle");
        dataMap.put("content", "mapContent");

        // 创建文档
        IndexResponse indexResponse = client.prepareIndex("blog", "article", "2").setSource(dataMap).execute().actionGet();

        // 输出信息
        System.out.println(indexResponse.getId());
        System.out.println(indexResponse.getResult());
        System.out.println(indexResponse.getShardId());
        System.out.println(indexResponse.getShardInfo());
        System.out.println(indexResponse.getType());
        System.out.println(indexResponse.getVersion());

        client.close();

    }

    // 创建文档（Builder形式）
    @Test
    public void createDocumentByBuilder() throws IOException {
        // 构建Builder
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("id", "3")
                .field("title", "4")
                .field("content", "builder Content")
                .endObject();

        // 创建文档
        IndexResponse indexResponse = client.prepareIndex("blog", "article", "builderContent").setSource(builder).execute().actionGet();

        // 输出信息
        System.out.println(indexResponse.getId());
        System.out.println(indexResponse.getResult());
        System.out.println(indexResponse.getShardId());
        System.out.println(indexResponse.getShardInfo());
        System.out.println(indexResponse.getType());
        System.out.println(indexResponse.getVersion());

        client.close();
    }

    // 搜索文档（单个索引）
    @Test
    public void queryDocumentByOneIndex(){
        // 查询文档
        GetResponse response = client.prepareGet("blog", "article", "1").get();

        // 输出结果
        System.out.println(response.getSourceAsString());

        // 关闭资源
        client.close();
    }

    //搜索文档（多个索引）
    @Test
    public void queryDocumentByMultiIndices(){
        // 查询文档
        MultiGetResponse multiResponse = client.prepareMultiGet()
                .add("blog", "article", "1", "2", "3")
                .add("blog", "article", "1", "2")
                .get();

        // 遍历结果输出
        for (MultiGetItemResponse response : multiResponse) {
            GetResponse resp = response.getResponse();
            // 判断是否存在
            if(resp.isExists()){
                System.out.println(resp.getSourceAsString());
            }

        }
        
        client.close();
    }

    // 更新文档
    @Test
    public void updateDocument() throws ExecutionException, InterruptedException, IOException {
        // 创建更新数据的请求对象
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("blog");
        updateRequest.type("article");
        updateRequest.id("2");

        updateRequest.doc(XContentFactory.jsonBuilder()
                .startObject()
                    .field("id", "2")
                    .field("title", "updateArticle")
                    .field("content", "update for elasticsearch")
                    .field("updateDate", "2019-04-20")
                .endObject());

        // 更新文档
        client.update(updateRequest).get();

        // 输出结果

        // 关闭资源
        client.close();
    }

    // 更新文档数据 (查找不到，则添加，查找到，则更新)
    @Test
    public void updateDocumentData() throws IOException, ExecutionException, InterruptedException {
        // 设置查询条件，查找不到，则添加
        IndexRequest indexRequest = new IndexRequest("blog", "article", "3")
                .source(XContentFactory.jsonBuilder()
                        .startObject()
                            .field("title","updateArticle")
                        .endObject()
                        );

        // 设置更新，查找到则更新
        UpdateRequest upSert = new UpdateRequest("blog", "article", "5").doc(
                XContentFactory.jsonBuilder()
                        .startObject()
                            .field("user", "elasticsearchuser")
                        .endObject()
        ).upsert(indexRequest);

        // 更新文档
        client.update(upSert).get();

        // 关闭资源
        client.close();

    }

    // 删除文档
    @Test
    public void deleteDocument(){
        // 删除文档数据
        client.prepareDelete("blog", "article", "3").get();

        // 关闭资源
        client.close();
    }
}
