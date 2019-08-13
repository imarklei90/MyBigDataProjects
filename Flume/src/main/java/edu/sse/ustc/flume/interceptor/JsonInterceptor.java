package edu.sse.ustc.flume.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** 自定义Interceptor
 * @author imarklei90
 * @since 2019.08.13
 */
public class JsonInterceptor implements Interceptor {
	private String separator;
	private String[] schemas;

	public JsonInterceptor(String fields, String separator) {
		this.schemas = fields.split(",");
		this.separator = separator;
	}

	@Override
	public void initialize() {
		// no-op
	}

	@Override
	public Event intercept(Event event) {

		Map<String, String> tuple = new LinkedHashMap<>();

		// 将传入的event的body加上schema，再放入event中
		String line = new String(event.getBody());
		String[] fields = line.split(separator);
		for (int i = 0; i < fields.length; i++) {
			tuple.put(schemas[i], fields[i]);
		}

		String value = JSONObject.toJSONString(tuple);
		// 将转化后的JSON再放入Event中
		event.setBody(value.getBytes());
		return event;
	}

	@Override
	public List<Event> intercept(List<Event> list) {
		for (Event event : list) {
			intercept(event);
		}
		return list;
	}

	@Override
	public void close() {
		// no-op
	}

	/**
	 * Interceptor.Builder的生命周期：
	 * 构造器 -> configure -> build
	 */
	public static class Builder implements Interceptor.Builder{

		private String fields;
		private String separator;

		@Override
		public Interceptor build() {
			// 创建外部类的实例

			return new JsonInterceptor(fields, separator);
		}

		@Override
		public void configure(Context context) {
			fields = context.getString("fields");
			separator = context.getString("separator");
		}
	}
}
