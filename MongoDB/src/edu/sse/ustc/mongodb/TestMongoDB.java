package edu.sse.ustc.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/** Java 操作 MongoDB
 * @author imarklei90
 * @since 2019.04.22
 */
public class TestMongoDB {
    public static void main(String[] args) {
        // 连接MongoDB
        MongoClient mongoClient = new MongoClient("172.20.10.10", 27017);

        // 获取数据库
        MongoDatabase testDB = mongoClient.getDatabase("test");

        // 获取集合
        MongoCollection<Document> usersCollection = testDB.getCollection("users");

        // 创建文档
        Document document = new Document("username", "JSON");

        // 向文档中添加属性
        document.append("age", 20);
        document.append("gender","女");

        // 插入文档
        usersCollection.insertOne(document);
    }
}
