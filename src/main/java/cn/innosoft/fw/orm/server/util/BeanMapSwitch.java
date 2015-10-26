package cn.innosoft.fw.orm.server.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanMapSwitch {

	/**
	 * bean转换为map
	 * 
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> beanToMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		Class c;
		try {
			c = Class.forName(obj.getClass().getName());
			Method[] m = c.getMethods();
			for (int i = 0; i < m.length; i++) {
				String method = m[i].getName();
				if (method.startsWith("get")) {
					try {
						Object value = m[i].invoke(obj);
						if (value != null) {
							String key = method.substring(3);
							key = key.substring(0, 1).toLowerCase() + key.substring(1);
							map.put(key, value);
						}
					} catch (Exception e) {
						System.out.println("error:" + method);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 把map转换为实体类
	 * 
	 * @author zouch
	 * @param type
	 * @param map
	 * @return Object
	 * @throws IntrospectionException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object mapToBean(Class type, Map map) throws IntrospectionException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		BeanInfo beaninfo = Introspector.getBeanInfo(type);
		Object obj = type.newInstance();
		PropertyDescriptor[] propertyDescriptors = beaninfo.getPropertyDescriptors();
		for (PropertyDescriptor pd : propertyDescriptors) {
			String PropertyNmae = pd.getName();
			if (map.containsKey(PropertyNmae)) {
				Object[] args = new Object[] { map.get(PropertyNmae) };
				pd.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}
}
