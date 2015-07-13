package edu.ustc.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * URL编解码器工具类
 * 
 * @author wgybzb
 *
 */
public class URLCodecUtils {

	public static String encoder(String url, String codec) {
		try {
			String result = URLEncoder.encode(url, codec);
			return result;
		} catch (UnsupportedEncodingException e) {
			return url;
		}
	}

	public static String decoder(String url, String codec) {
		try {
			String result = URLDecoder.decode(url, codec);
			return result;
		} catch (UnsupportedEncodingException e) {
			return url;
		}
	}

}
