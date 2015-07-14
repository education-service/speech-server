package edu.ustc.api.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ustc.api.server.ApiServer;

public class SpeechServerDriver {

	private static Logger logger = LoggerFactory.getLogger(SpeechServerDriver.class);

	/**
	 * 主函数
	 */
	public static void main(String[] args) {

		if (args.length == 0) {
			System.err.println("Usage: Input <class-name>, eg: \n" + //
					"`apiServer` 语音性别识别接口服务");
			System.exit(-1);
		}
		String[] leftArgs = new String[args.length - 1];
		System.arraycopy(args, 1, leftArgs, 0, leftArgs.length);

		switch (args[0]) {
		// hdp322
		case "apiServer":
			logger.info("语音性别识别接口服务");
			ApiServer.main(leftArgs);
			break;
		default:
			return;
		}

	}

}
