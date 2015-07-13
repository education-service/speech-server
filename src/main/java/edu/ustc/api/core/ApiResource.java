package edu.ustc.api.core;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ustc.utils.URLCodecUtils;

/**
 * 接口服务资源类
 *
 * @author wanggang
 *
 */
public class ApiResource extends ServerResource {

	private static Logger logger = LoggerFactory.getLogger(ApiResource.class);

	private ApiApplication application;

	@Override
	public void doInit() {
		logger.info("Request Url: " + URLCodecUtils.decoder(getReference().toString(), "utf-8") + ".");
		application = (ApiApplication) getApplication();
	}

	@Post("json")
	public String recognize(PostData data) {
		return application.recognizeSex(data.getData());
	}

}
