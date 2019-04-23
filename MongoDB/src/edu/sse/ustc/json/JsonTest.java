package edu.sse.ustc.json;

import com.google.gson.Gson;
import org.junit.Test;

import java.sql.Struct;
import java.util.Map;

/** JSON
 * @author imarklei90
 * @since 2019.04.22
 */
public class JsonTest {

    @Test
    public void testJSON(){
        String json = "{\"name\":\"json\", \"age\":10, \"gender\":\"male\"}";
        System.out.println("json: " + json);
    }

    @Test
    public void testJsonToObj(){
        String json = "{\"name\":\"json\", \"age\":10, \"gender\":\"male\"}";
        System.out.println("json: " + json);

        // 将JSON转换为Java对象
        Gson gson = new Gson();
        Map map = gson.fromJson(json, Map.class);
        System.out.println(map);

        System.out.println("name :" + map.get("name"));

        // 将JSON转换成自定义对象
        Gson studentJSON = new Gson();
        Student student = studentJSON.fromJson(json, Student.class);
        System.out.println("student : " + student);
    }

    @Test
    public void testObjToJson(){
        Student student = new Student("小明", 12, "合肥");

        Gson gson = new Gson();
        String studentJson = gson.toJson(student);
        System.out.println("Student JSON : " + studentJson);

    }

}
