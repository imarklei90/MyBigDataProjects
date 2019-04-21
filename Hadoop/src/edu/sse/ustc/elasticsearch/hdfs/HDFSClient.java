package edu.sse.ustc.elasticsearch.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author imarklei90
 * @since 2018.12.04
 */
public class HDFSClient {

    private static final  String inputFilePath = System.getProperty("user.dir") + File.separator + "data" +
            File.separator + "input" + File.separator + "test.txt";

    private static final String TEST_DIR = System.getProperty("user.dir") + File.separator + "data" +
            File.separator + "test";

    // 复制本地文件到HDFS
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        Configuration configuration = new Configuration();
        // configuration.set("fs.defaultFS", "hdfs://hadoop101:9000");
        // FileSystem fs = FileSystem.get(configuration);
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), configuration, "hadoop");

        fs.copyFromLocalFile(new Path(inputFilePath),
                new Path("/user/"));
        fs.close();
    }

    // 获取文件系统信息
    @Test
    public void hdfsFileSystem() throws IOException {
        FileSystem fs = FileSystem.get(new Configuration());
        System.out.println(fs);
    }

    // 上传文件到HDFS
    @Test
    public void putFileToHDFS() throws IOException, URISyntaxException, InterruptedException {
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), new Configuration(), "hadoop");
        // 获取输入流
        FileInputStream inputStream = new FileInputStream(new File(inputFilePath));

        // 获取输出流
        FSDataOutputStream outputStream = fs.create(new Path("/user/hadoop/putFile.txt"));

        // 执行流的拷贝
        IOUtils.copyBytes(inputStream, outputStream, new Configuration());

        // 关闭资源
        IOUtils.closeStream(outputStream);
        IOUtils.closeStream(inputStream);
    }

    // 从HDFS下载文件
    @Test
    public void getFileFromHDFS() throws IOException, URISyntaxException, InterruptedException {
        // 获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), new Configuration(), "hadoop");

        // 复制HDFS文件到本地
        fs.copyToLocalFile(false, new Path("/user/hadoop/putFile.txt"),
                new Path(TEST_DIR + File.separator + "copyFromHDFS.txt"), true);

        // 关闭文件系统
        fs.close();
    }

    // 在HDFS上创建目录
    @Test
    public void mkdir() throws IOException, URISyntaxException, InterruptedException {
        // 获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), new Configuration(), "hadoop");

        // 创建目录
        fs.mkdirs(new Path("/user/newDir/file/system"));

        // 关闭文件系统
        fs.close();
    }

    // 删除文件夹
    @Test
    public void deleteDir() throws IOException, URISyntaxException, InterruptedException {
        //获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), new Configuration(), "hadoop");

        // 删除文件夹 true 表示递归删除
        fs.delete(new Path("/user/newDir"), true);

        // 关闭文件系统
        fs.close();
    }

    // 修改HDFS文件名
    @Test
    public void renameHDFS() throws URISyntaxException, IOException, InterruptedException {
        // 获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), new Configuration(), "hadoop");

        // 重命名文件
        fs.rename(new Path("/user/hadoop/putFile.txt"), new Path("/user/hadoop/newPutFile.txt"));

        // 关闭文件系统
        fs.close();
    }

    // 获取文件系统详情
    @Test
    public void getFileSystemDetails() throws IOException, URISyntaxException, InterruptedException {
        // 获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), new Configuration(), "hadoop");

        // 获取文件详情迭代器
        RemoteIterator<LocatedFileStatus> fileStatusIterator = fs.listFiles(new Path("/"), true);
        while (fileStatusIterator.hasNext()){
            LocatedFileStatus next = fileStatusIterator.next();
            System.out.println("FileName : " + next.getPath().getName());
            System.out.println("group : " + next.getGroup());
            System.out.println("modification Time : " + next.getModificationTime());
            System.out.println("Owner :" + next.getOwner());
            System.out.println("Path :" + next.getPath());
            System.out.println("Permission : " + next.getPermission());
            System.out.println("Replication : " + next.getReplication());
            // System.out.println("synLink : " + next.getSymlink());
            System.out.println("access Time : " + next.getAccessTime());

            BlockLocation[] blockLocations = next.getBlockLocations();
            for (BlockLocation blockLocation : blockLocations) {
                String[] hosts = blockLocation.getHosts();
                for (String host : hosts) {
                    System.out.println(host);
                }
            }
            System.out.println("________________________");
        }
    }

    // 判断文件或者文件夹
    @Test
    public void isFileORDir() throws IOException, URISyntaxException, InterruptedException {
        // 获取文件系统
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), new Configuration(), "hadoop");

        // 遍历文件系统,获取到状态信息
        FileStatus[] fileStatuses = fs.listStatus(new Path("/user"));
        for (FileStatus fileStatus : fileStatuses) {
            if (fileStatus.isFile()){
                System.out.println("File: " + fileStatus.getPath().getName());
            }else {
                System.out.println("Dir: " + fileStatus.getPath().getName());
            }
        }
    }
}
