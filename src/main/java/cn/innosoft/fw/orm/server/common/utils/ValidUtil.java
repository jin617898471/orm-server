package cn.innosoft.fw.orm.server.common.utils;

import java.util.regex.Pattern;

public class ValidUtil {

	/**
	 * 校验手机号码是否合法.
	 * 
	 * @param phone
	 *            手机号码
	 * @return 手机号码是否合法
	 */
	public static boolean validPhone(String phone) {
		Pattern p = Pattern.compile("^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
		return p.matcher(phone).matches();
	}

	/**
	 * 校验原始密码是否合法.
	 * 
	 * @param plainPassword
	 *            原始密码
	 * @return 原始密码是否合法
	 */
	public static boolean validPlainPassword(String plainPassword) {
		Pattern p = Pattern.compile("^[a-zA-Z\\d]{6,20}$");
//		Pattern p = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z\\d]{6,20}$");
		return p.matcher(plainPassword).matches();
	}
}
