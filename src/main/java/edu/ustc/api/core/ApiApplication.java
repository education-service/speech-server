package edu.ustc.api.core;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import edu.ustc.audio.feature.MFCCFeature;
import edu.ustc.knn.core.VoiceSexKNN;

/**
 * 接口服务应用类
 *
 * @author wanggang
 *
 */
public class ApiApplication extends Application {

	MFCCFeature mfccFeature;

	public ApiApplication() {
		mfccFeature = new MFCCFeature();
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("", ApiResource.class);
		return router;
	}

	/**
	 * 插入站点组合数据
	 */
	public String recognizeSex(byte[] data) {
		// MFCC特征提取
		double[] features = mfccFeature.getFeature(data);
		// 基于KNN识别
		String sex = VoiceSexKNN.classifyKNN(20, features);
		return sex;
	}

}
