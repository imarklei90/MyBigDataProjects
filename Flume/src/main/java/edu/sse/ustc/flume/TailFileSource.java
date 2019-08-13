package edu.sse.ustc.flume;

import org.apache.commons.io.FileUtils;
import org.apache.flume.Context;
import org.apache.flume.EventDrivenSource;
import org.apache.flume.channel.ChannelProcessor;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.source.AbstractSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/** 自定义Flume Source
 *  生命周期
 *  构造器->configure->start->processor.process()
 *
 *  步骤：
 *  读取配置文件（编码集、偏移量写到什么文件、多久检查文件是否有新内容）
 *
 *
 * @author imarklei90
 * @since 2019.08.13
 */
public class TailFileSource extends AbstractSource implements EventDrivenSource, Configurable {

	private static final Logger logger = LoggerFactory.getLogger(TailFileSource.class);

	private String fileName;
	private String charset;
	private String positionFile;
	private Long interval;

	private ExecutorService executor;
	private FileRunnable fileRunnable;

	@Override
	public void configure(Context context) {
		fileName = context.getString("fileName");
		charset = context.getString("charset", "UTF-8");
		positionFile = context.getString("positionFile");
		interval = context.getLong("interval", 1000L);
	}

	/**
	 * 创建一个线程
	 */
	@Override
	public synchronized void start() {
		// 创建一个单线程的线程池
		executor = Executors.newSingleThreadExecutor();

		// 定义实现Runnable接口的类
		fileRunnable = new FileRunnable(fileName, charset, positionFile, interval, getChannelProcessor());

		// 实现runnable接口的类提交到的线程池
		executor.submit(fileRunnable);

		// 调用父类的start方法
		super.start();
	}

	@Override
	public synchronized void stop() {
		fileRunnable.setFlag(false);

		executor.shutdown();

		while (!executor.isTerminated()){
			try {
				executor.awaitTermination(500, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				// e.printStackTrace();
				Thread.currentThread().interrupt();

			}
		}

		super.stop();
	}


	private static class FileRunnable implements Runnable {

		private String charset;
		private Long interval;
		private ChannelProcessor channelProcessor;
		private long offset = 0L;
		private RandomAccessFile accessFile;
		private boolean flag = true;
		private File posFile;

		FileRunnable(String fileName, String charset, String positionFile, Long interval, ChannelProcessor channelProcessor) {
			this.charset = charset;
			this.interval = interval;
			this.channelProcessor = channelProcessor;

			// 读取偏移量，如果有就接着读，没有就从头开始读
			posFile = new File(positionFile);
			if (!posFile.exists()) {
				try {
					posFile.createNewFile();
				} catch (IOException e) {
					//e.printStackTrace();
					logger.error("create position file error", e);
				}
			}

			// 读取读取偏移量
			try {
				String offsetString = FileUtils.readFileToString(posFile);
				// 如果第一次为空
				if (offsetString != null && !"".equalsIgnoreCase(offsetString)) {
					// 将offset转换为long
					offset = Long.parseLong(offsetString);
				}
				// 从指定的位置读取数据
				accessFile = new RandomAccessFile(fileName, "r");
				// 按照指定的偏移量读取
				accessFile.seek(offset);

			} catch (IOException e) {
				//e.printStackTrace();
				logger.error("read position file error", e);
			}

		}

		@Override
		public void run() {
			while (flag) {
				try {
					// 每隔一段时间读取文件中的新数据
					String line = accessFile.readLine();
					if (line != null) {
						line = new String(line.getBytes("iso-8859-1"), Charset.forName("UTF-8"));
						// 将数据发送到Channel
						channelProcessor.processEvent(EventBuilder.withBody(line.getBytes()));
						// 获取最新的偏移量，再更新偏移量
						offset = accessFile.getFilePointer();
						// 将offset写回到位置文件中
						FileUtils.writeStringToFile(posFile, offset + "");
					} else {
						Thread.sleep(interval);
					}
				} catch (InterruptedException e) {
					//e.printStackTrace();
					logger.error("Thread Interrupted", e);
				} catch (IOException e) {
					//e.printStackTrace();
					logger.error("read log file error", e);
				}
			}
		}

		private void setFlag(boolean flag) {
			this.flag = flag;
		}
	}
}
