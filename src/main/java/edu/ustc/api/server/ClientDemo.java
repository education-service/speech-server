package edu.ustc.api.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import edu.ustc.api.core.PostData;
import edu.ustc.utils.JsonUtils;

public class ClientDemo {

	public static void main(String[] args) throws IOException {

		String url = "http://hdp322:8080/recognize";
		// 获取音频字节数组
		byte[] data;
		File waveFile = new File("data/test/male/5.wav");
		try (FileInputStream fis = new FileInputStream(waveFile);) {
			// 从文件中创建字节数组
			data = new byte[(int) waveFile.length()];
			fis.read(data);
		}
		// POST请求
		PostData postData = new PostData("test", data);
		System.out.println(doPost(url, JsonUtils.toJsonWithoutPretty(postData)));

	}

	public static String doPost(String url, String data) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-Type", "application/json");
		CloseableHttpResponse response = null;
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
			EntityUtils.consume(entity2);

			return sb.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
