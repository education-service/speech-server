package edu.ustc.audio.feature;

import java.util.ArrayList;
import java.util.List;

import edu.ustc.audio.FeatureExtract;
import edu.ustc.audio.PreProcess;
import edu.ustc.audio.WaveData;

/**
 * 提取单个音频的MFCC特征
 *
 * @author wgybzb
 *
 */
public class MFCCFeature {

	private static final int SAMPLING_RATE = 1020;
	private static final int SAMPLE_PER_FRAME = 256;
	private static final int FEATURE_DIMENSION = 39;
	private FeatureExtract featureExtract;
	private WaveData waveData;
	private PreProcess prp;
	private List<double[]> allFeaturesList = new ArrayList<double[]>();

	public MFCCFeature() {
		waveData = new WaveData();
	}

	/**
	 * 提取单个音频的特征数据
	 */
	public double[] getFeature(byte[] data) {
		int totalFrames = 0;
		FeatureVector feature = extractFeatureFromFile(data);
		//		FeatureVector feature = extractFeatureFromFile(new File(fileName));
		for (int k = 0; k < feature.getNoOfFrames(); k++) {
			allFeaturesList.add(feature.getFeatureVector()[k]);
			totalFrames++;
		}
		// 行代表帧数，列代表特征
		double allFeatures[][] = new double[totalFrames][FEATURE_DIMENSION];
		for (int i = 0; i < totalFrames; i++) {
			double[] tmp = allFeaturesList.get(i);
			allFeatures[i] = tmp;
		}
		// 计算每帧对应特征的平均值
		double avgFeatures[] = new double[FEATURE_DIMENSION];
		for (int j = 0; j < FEATURE_DIMENSION; j++) { // 循环每列
			double tmp = 0.0d;
			for (int i = 0; i < totalFrames; i++) { // 循环每行
				tmp += allFeatures[i][j];
			}
			avgFeatures[j] = tmp / totalFrames;
		}

		return avgFeatures;
	}

	private FeatureVector extractFeatureFromFile(byte[] data) {
		float[] arrAmp;
		arrAmp = waveData.extractAmplitudeFromFile(data);
		prp = new PreProcess(arrAmp, SAMPLE_PER_FRAME, SAMPLING_RATE);
		featureExtract = new FeatureExtract(prp.framedSignal, SAMPLING_RATE, SAMPLE_PER_FRAME);
		featureExtract.makeMfccFeatureVector();
		return featureExtract.getFeatureVector();
	}

	//	private FeatureVector extractFeatureFromFile(File speechFile) {
	//		float[] arrAmp;
	//		arrAmp = waveData.extractAmplitudeFromFile(speechFile);
	//		prp = new PreProcess(arrAmp, SAMPLE_PER_FRAME, SAMPLING_RATE);
	//		featureExtract = new FeatureExtract(prp.framedSignal, SAMPLING_RATE, SAMPLE_PER_FRAME);
	//		featureExtract.makeMfccFeatureVector();
	//		return featureExtract.getFeatureVector();
	//	}

}
