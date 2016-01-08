package cn.innosoft.fw.orm.server.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ExportJs {

	/**
	 * 用freemaker动态生成js代码
	 * 
	 * @param map
	 * @param template
	 * @param saveFileName
	 */
	public static String export(Map<String, Object> map) {
		Template jsTemp = getTemplate("OrmJsObj");
		Writer writer = new StringWriter();
		return createDoc(jsTemp, map, writer);
	}
	
	private static Template template = null;
	
	public static Template getTemplate(String templateName){
		if(null!=template){
			return template;
		}
		Template jsTemp = null;
		Configuration cfg = new Configuration();
		cfg.setDefaultEncoding("UTF-8");
		try {
			InputStream in=ExportJs.class.getResourceAsStream(templateName+".ftl"); 
	        Reader f = new InputStreamReader(in);         
	        BufferedReader fb = new BufferedReader(f);  
	        StringBuffer sb = new StringBuffer("");  
	        String s = "";  
	        while((s = fb.readLine()) != null) {  
	            sb = sb.append(s); 
	            sb.append("\r\n");
	        }  
	        String temp = sb.toString();
	        StringTemplateLoader stringLoader = new StringTemplateLoader();
	        stringLoader.putTemplate(templateName, temp);
	        cfg.setTemplateLoader(stringLoader);
			jsTemp = cfg.getTemplate(templateName, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		template = jsTemp;
		return template;
	}

	/**
	 * 创建Js
	 * 
	 * @param tempale
	 * @param dataMap
	 * @param out
	 */
	private static String createDoc(Template tempale, Map<String, Object> dataMap, Writer out) {
		try {
			tempale.process(dataMap, out);
			String result = out.toString();
			out.close();
			return result;
		} catch (TemplateException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
