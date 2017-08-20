package com.arrowso.core.unit;


import java.io.File;
import java.io.OutputStream;

import com.thoughtworks.xstream.XStream;

public class XmlUnit {

	public static <T> T parseXml(File xmlFile,Class<T> cls){
		XStream xStream = new XStream();
		xStream.alias(cls.getSimpleName(), cls);
		Object obj = xStream.fromXML(xmlFile);
		return (T)obj;
	}
	
	public static void generateXml(OutputStream out,Object obj){
		XStream xStream = new XStream();
		xStream.alias(obj.getClass().getSimpleName(), obj.getClass());
		xStream.toXML(obj, out);
	}
	
	public static File readXmlFile(Class cls){
		String xmlPath = cls.getResource("/").getPath()+cls.getSimpleName()+".xml";
		File file = new File(xmlPath);
		if(file.exists()&&file.isFile()){
			return file;
		}
		return null;
	}
}
