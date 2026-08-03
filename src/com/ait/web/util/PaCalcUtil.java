package com.ait.web.util;

import org.apache.log4j.Logger;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaCalcUtil.java
 * @Description:
 * @Create date: 2012-5-15 下午01:42:06
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public class PaCalcUtil {

	private static final Logger logger = Logger.getLogger(PaCalcUtil.class);

	private static int paCalcFlag = 0; // 工资计算结果是否在导出的标示

	private static int paInsuranceCalcFlag = 0; // 保险结果是否在导出的标示

	private static int paBonusCalcFlag = 0; // 奖金结果是否在导出的标示

	public static synchronized final int getPaCalcFlag() {
		int flag = paCalcFlag;

		paCalcFlag = paCalcFlag == 0 ? 1 : paCalcFlag;
		logger.debug("工资计算结果导出的标示: " + flag);

		return flag;
	}

	public static synchronized final void setPaCalcFlag(int calcFlagValue) {
		paCalcFlag = calcFlagValue;
		logger.debug("setpaCalcFlag: " + paCalcFlag);
	}

	public static synchronized final int getPaInsuranceCalcFlag() {
		int flag = paInsuranceCalcFlag;

		paInsuranceCalcFlag = paInsuranceCalcFlag == 0 ? 1
				: paInsuranceCalcFlag;
		logger.debug("保险结果导出的标示: " + flag);

		return paInsuranceCalcFlag;
	}

	public static synchronized final void setPaInsuranceCalcFlag(
			int calcFlagValue) {
		paInsuranceCalcFlag = calcFlagValue;
	}

	public static synchronized final int getPaBonusCalcFlag() {
		int flag = paBonusCalcFlag;

		paBonusCalcFlag = paBonusCalcFlag == 0 ? 1 : paBonusCalcFlag;
		logger.debug("奖金结果导出的标示: " + flag);

		return flag;
	}

	public static synchronized final void setPaBonusCalcFlag(int calcFlagValue) {
		paBonusCalcFlag = calcFlagValue;
	}
}
