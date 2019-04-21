package edu.sse.ustc.elasticsearch.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author imarklei90
 * @since 2019.01.26
 */
public class BasicHBase {
    // 创建Hadoop及HBase的管理配置对象
    public static Configuration conf;

    static {
        // 实例化配置对象
        conf = HBaseConfiguration.create();

        // 连接HBase服务器
        conf.set("hbase.master", "hadoop101:16000");
        conf.set("hbase.zookeeper.quorum", "hadoop101:2181");

    }

    /**
     * 判断HBASE中表是否存在
     * @param tableName HBASE中的表名称
     * @return
     */
    public static boolean isExists(String tableName) throws IOException {
        // 创建HBaseAdmin对象管理HBASE
        HBaseAdmin admin = new HBaseAdmin(conf);
        return admin.tableExists(tableName);
    }

    /**
     * 创建表
     * @param tableName 表名
     * @param columnFamily 列族
     */
    public static void createTable(String tableName, String... columnFamilys) throws IOException {
        HBaseAdmin admin = new HBaseAdmin(conf);
        // 1. 判断表是否存在
        if(isExists(tableName)) {
            System.out.println("表：" + tableName + " 已存在");
        }else {
            // 2. 不存在，则创建表
            // 2.1 创建表属性对象
            HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(tableName));

            // 2.2 创建列族
            for(String columnFamily : columnFamilys){
                descriptor.addFamily(new HColumnDescriptor(columnFamily));
            }

            // 2.3 根据表的配置创建表
            admin.createTable(descriptor);
            System.out.println("表 ： " + tableName + " 创建成功");
        }
    }

    /**
     * 删除表
     * @param tableName 表名
     */
    public static void dropTable(String tableName) throws IOException {
        HBaseAdmin admin = new HBaseAdmin(conf);
        // 判断表是否存在，存在，则删除
        if(isExists(tableName)){
            admin.deleteTable(tableName);
            System.out.println("表： " + tableName + " 已删除");
        }

    }

    /**
     *  向表中插入数据
     * @param tableName
     * @param rowKey
     * @param columnFamily
     * @param column
     * @param value
     * @throws IOException
     */
    public static void addRowData(String tableName, String rowKey, String columnFamily, String column, String value) throws IOException {
        // 1. 创建HTable对象
        HTable hTable = new HTable(conf, tableName);

        // 2. 向表中插入数据
        Put put = new Put(Bytes.toBytes(rowKey));

        // 3. 向Put对象中组装数据
        put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
        hTable.put(put);
        hTable.close();
    }

    /**
     * 删除Row
     * @param tableName
     * @param rowKeys
     */
    public static void deleteMultiRows(String tableName, String... rowKeys) throws IOException {
        HTable hTable = new HTable(conf, tableName);
        List<Delete> deleteLists = new ArrayList<Delete>();
        for (String row : rowKeys) {
            Delete delete = new Delete(Bytes.toBytes(row));
            deleteLists.add(delete);
        }

        hTable.delete(deleteLists);
        hTable.close();
    }

    /**
     * 获取所有行的数据
     * @param tableName
     * @throws IOException
     */
    public static void getAllRows(String tableName) throws IOException {
        HTable hTable = new HTable(conf, tableName);

        Scan scan = new Scan();

        // 获得用于扫描Region的对象
        ResultScanner resultScanner = hTable.getScanner(scan);

        // 使用HTable得到ResultScanner实现类的对象
        for (Result result : resultScanner) {
            Cell[] cells = result.rawCells();
            for(Cell cell : cells){
                // 得到RowKey
                byte[] rows = CellUtil.cloneRow(cell);
                System.out.println(Bytes.toString(rows));
                // 得到列组Family
                byte[] families = CellUtil.cloneFamily(cell);
                System.out.println(Bytes.toString(families));
                // 得到列
                byte[] columns = CellUtil.cloneQualifier(cell);
                System.out.println(Bytes.toString(columns));
                //得到Value
                byte[] values = CellUtil.cloneValue(cell);
                System.out.println(Bytes.toString(values));
            }
        }
    }

    public static void main(String[] args) throws IOException {

        HBaseAdmin admin = new HBaseAdmin(conf);
        HConnection connection = admin.getConnection();

        // 1. 创建表
        createTable("person", "basicInfo", "healthy");
    }
}
