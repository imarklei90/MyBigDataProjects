package edu.sse.ustc.elasticsearch.hive;

import org.apache.hadoop.hive.ql.exec.UDF;

/** Hive自定义函数
 * @author imarklei90
 * @since 2019.01.06
 */
public class ToLowerCase extends UDF {

    public String evaluate(final String str){
        if(str == null){
            return null;
        }

        return str.toLowerCase();
    }
}
