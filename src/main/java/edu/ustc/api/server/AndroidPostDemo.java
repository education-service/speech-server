package edu.ustc.api.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import edu.ustc.api.core.PostData;
import edu.ustc.utils.JsonUtils;

@SuppressWarnings("deprecation")
public class AndroidPostDemo {

	public static void main(String[] args) throws Exception {

		String url = "http://220.178.13.120:8081/recognize";
		// 获取音频字节数组
		byte[] data;
		File waveFile = new File("data/test/female/1.wav");
		try (FileInputStream fis = new FileInputStream(waveFile);) {
			// 从文件中创建字节数组
			data = new byte[(int) waveFile.length()];
			fis.read(data);
		}
		// POST请求
		PostData postData = new PostData("test", data);
		System.out.println(doPost(url, JsonUtils.toJsonWithoutPretty(postData)));

	}

	@SuppressWarnings({ "resource" })
	public static String doPost(String url, String data) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json");
		HttpResponse response = null;
		try {
			HttpEntity entity = new StringEntity(data, "UTF-8");
			httpPost.setEntity(entity);
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() != 200) {
				System.err.println("POST request fials with data=" + data);
			}
			HttpEntity entity2 = response.getEntity();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity2.getContent()));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}

			return sb.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
