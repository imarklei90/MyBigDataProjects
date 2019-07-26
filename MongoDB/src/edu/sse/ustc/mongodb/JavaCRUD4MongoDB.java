package edu.sse.ustc.mongodb;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import edu.sse.ustc.json.Student;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

/**
 * Java CRUD for MongoDB
 *
 * @author imarklei90
 * @since 2019.04.23
 */
public class JavaCRUD4MongoDB {

	private MongoCollection<Document> studentDBCollection = null;

	@Before
	public void getDBConnection() { // 获取连接
		// 连接 MongoDB
		MongoClient client = new MongoClient("172.20.10.10", 27017);

		// 获取数据库
		MongoDatabase studentDB = client.getDatabase("test");

		// 获取集合
		studentDBCollection = studentDB.getCollection("student");

	}

	@Test
	public void testInsert() { // 插入数据
		// 创建对象
		Student student = new Student("wangwu", 20, "男");

		// 将对象转换为JSON
		Gson gson = new Gson();
		String stuJSON = gson.toJson(student);

		// 将JSON转换为Document对象
		Document stuDocument = Document.parse(stuJSON);

		// 插入文档
		studentDBCollection.insertOne(stuDocument);
	}

	@Test
	public void testFind() { // 查询单个
		// 查询
		Document document = studentDBCollection.find().first(); // 相当于FindOne

		// 将Document转换为JSON
		String json = document.toJson();

		// 将JSON转换为对对象
		Gson gson = new Gson();
		Student student = gson.fromJson(json, Student.class);

		System.out.println(student);

	}

	@Test
	public void testFindMulti(){ // 查询多个
		// 查询
		FindIterable<Document> documents = studentDBCollection.find();
		MongoCursor<Document> iterator = documents.iterator();

		for(Document doc : documents){
			// 将Document转换为对象
			Student student = new Gson().fromJson(doc.toJson(), Student.class);
			System.out.println(student);
		}
	}

	@Test
	public void testFindCriteria(){ // 条件查询
		// 查询
		FindIterable<Document> documents = studentDBCollection.find(Filters.eq("name", "lisi"));
		for(Document document : documents){
			System.out.println(document);
		}
	}

	@Test
	public void testRemove(){ // 删除
		// 删除
		studentDBCollection.deleteOne(Filters.eq("name", "lisi"));

	}

	@Test
	public void testUpdate(){ // 更新
		// 更新
		studentDBCollection.updateOne(Filters.eq("name", "zhangsan"), new Document("$set", new Document("age", 10)));
	}

}