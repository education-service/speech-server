package edu.ustc.api.server;

import java.util.Properties;

import org.restlet.Component;
import org.restlet.data.Protocol;

import edu.ustc.api.core.ApiApplication;
import edu.ustc.utils.ConfigUtil;

/**
 * 示例： http://localhost:8888/recognize   POST: byte[]
 *
 * @author wanggang
 */
public class ApiServer {

	private final Component component;
	private final ApiApplication application;

	private final int PORT;

	public ApiServer() {
		Properties props = ConfigUtil.getProps("api.properties");
		PORT = Integer.parseInt(props.getProperty("api.port"));
		component = new Component();
		application = new ApiApplication();
	}

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		ApiServer server = new ApiServer();
		server.start();

	}

	public void start() {
		component.getServers().add(Protocol.HTTP, PORT);
		try {
			component.getDefaultHost().attach("/recognize", application);
			component.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void stop() {
		try {
			component.stop();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
