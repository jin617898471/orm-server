package cn.innosoft.fw.orm.server.common.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工具类.
 * 
 * @author huangwb
 * @date 2014-4-2 下午2:30:06
 */
public final class StringUtil {

	/**
	 * 帮助类，私有构建函数.
	 */
	private StringUtil() {

	}

	/**
	 * 生成Uid.
	 * 
	 * @return 返回String型Id
	 */
	public static String getUUID() {
		String uuid = java.util.UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		return uuid;
	}

	/**
	 * 获取现在时间.
	 * 
	 * @return 返回时间类型yyyyMMddHHmmss
	 */
	public static String getNowDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static String convertListToString(List<String> ids, boolean hasQuotation) {
		String str = "";
		for (String id : ids) {
			str += ",";
			if (hasQuotation) {
				str += "'" + id + "'";
			} else {
				str += id;
			}
		}
		if (str.length() > 0) {
			str = str.substring(1);
		}
		return str;
	}

	public static List<String> convertListToStrList(List<String> ids, boolean hasQuotation) {
		List<String> strs = new ArrayList<String>();
		String str = "";
		for (int i = 0; i < ids.size(); i++) {
			str += ",";
			if (hasQuotation) {
				str += "'" + ids.get(i) + "'";
			} else {
				str += ids.get(i);
			}
			if ((i + 1) % 999 == 0) {
				strs.add(str.substring(1));
				str = "";
			}
		}
		if (str.length() > 0) {
			strs.add(str.substring(1));
		}
		return strs;
	}

	/**
	 * 根据List<String>中的值，以'xx','xx'的方式拼接起来
	 * 
	 * @param arrayList
	 * @return 拼接好的字符串
	 */
	public static StringBuffer appendIds(List<String> arrayList) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < arrayList.size(); i++) {
			String perValue = arrayList.get(i);
			if (i == 0) {
				buffer.append("'" + perValue + "'");
			} else {
				buffer.append(",'" + perValue + "'");
			}
		}
		return buffer;
	}

	/**
	 * 路径转换.
	 * 
	 * @param datas
	 *            结果集
	 * @return 转换后的结果集
	 */
	public static List<Map<String, Object>> switchPath(
			List<Map<String, Object>> datas) {
		for (Map<String, Object> map : datas) {
			String path = map.get("PHYSICAL_PATH").toString();
			path = path.replaceAll("\\\\", "_");
			map.put("PHYSICAL_PATH", path);
		}
		return datas;
	}
}
