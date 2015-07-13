package edu.ustc.knn.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import edu.ustc.knn.core.KNNCore.Point;

/**
 * 基于KNN的单个音频性别识别
 *
 * @author wgybzb
 *
 */
public class VoiceSexKNN {

	/**
	 * 根据特征进行分类
	 *
	 * @param K：最邻近数
	 * @param features：MFCC特征数组
	 * @return
	 */
	public static String classifyKNN(int K, double[] features) {

		// 读取训练数据
		HashMap<String, Point> train = getData("data/knn/train.data");
		// 当前点与样本库距离最近的K个数据key列表，k是由文件编号和类别组成
		List<String> keys = KNNCore.calcKNN(new Point(features), train, K);
		// 统计K个数据的类别分布
		HashMap<String, Integer> kmap = new HashMap<>();
		for (String key : keys) {
			if (kmap.get(key.split("-")[1]) == null) {
				kmap.put(key.split("-")[1], 1);
			} else {
				kmap.put(key.split("-")[1], kmap.get(key.split("-")[1]) + 1);
			}
		}
		// 计算出类别
		String calcCate = "female";
		if (kmap.get("female") == null) {
			calcCate = "male";
		} else if (kmap.get("male") != null) {
			if (kmap.get("male") > kmap.get("female")) {
				calcCate = "male";
			}
		}

		return calcCate;
	}

	public static HashMap<String, Point> getData(String dir) {
		HashMap<String, Point> data = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(Thread.currentThread()
				.getContextClassLoader().getResourceAsStream(dir)));) {
			String str = null;
			String[] strs = null;
			int count = 1;
			while ((str = br.readLine()) != null) {
				strs = str.split(",");
				double[] coordinate = new double[strs.length - 1];
				for (int i = 0; i < strs.length - 1; i++) {
					coordinate[i] = Double.parseDouble(strs[i]);
				}
				data.put(count++ + "-" + strs[strs.length - 1], new Point(coordinate));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return data;
	}

}
