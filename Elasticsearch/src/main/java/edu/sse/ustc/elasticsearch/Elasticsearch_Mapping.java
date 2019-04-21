package edu.sse.ustc.elasticsearch;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Requests;
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

/** Elasticsearch Mapping
 * @author imarklei90
 * @since 2019.04.21
 */
public class Elasticsearch_Mapping {

    PreBuiltTransportClient client = null;

    @Before
    public void getClient() throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", "my-application").build();
        client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("hadoop101"), 9300));
    }

    @Test
    public void mapping() throws IOException {
        // 设置Mapping
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("article")
                .startObject("properties")
                .startObject("id")
                .field("type", "string")
                .field("store", "yes")
                .endObject()
                .startObject("title")
                .field("type", "string")
                .field("store", "no")
                .endObject()
                .startObject("content")
                .field("type", "string")
                .field("store", "yes")
                .endObject()
                .endObject()
                .endObject()
                .endObject();

        // 添加Mapping
        PutMappingRequest mapping = Requests.putMappingRequest("website").type("article").source(builder);
        client.admin().indices().putMapping(mapping);

        // 关闭资源
        client.close();
    }
}
