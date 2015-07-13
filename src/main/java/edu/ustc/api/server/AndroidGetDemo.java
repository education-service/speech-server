package edu.ustc.api.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HTTP工具类
 *
 * @author wanggang
 *
 */
public class AndroidGetDemo {

	public static void main(String[] args) throws Exception {
		String url = "http://api.weibo.com/2/statuses/user_timeline.json?source=140226478&uid=1644395354&page=1&count=1";
		System.out.println(AndroidGetDemo.executeHttpGet(url));
	}

	public static String executeHttpGet(String url) throws Exception {
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));) {
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

}
