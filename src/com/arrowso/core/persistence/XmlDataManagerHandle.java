package com.arrowso.core.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.context.annotation.Bean;

import com.arrowso.core.data.WebsiteInfo;
import com.arrowso.core.unit.XmlUnit;

public class XmlDataManagerHandle extends BasicDataManagerHandle {

	private static XmlDataManagerHandle xmlDataManagerHandle = null;
	private Log4JLogger log = new Log4JLogger("XmlDataManagerHandle");
	private XmlDataManagerHandle() {
		super();
	}
	
	public static XmlDataManagerHandle getInstance(){
		System.out.println("XmlDataManagerHandle.getInstance");
		if(xmlDataManagerHandle==null){
			synchronized(XmlDataManagerHandle.class){
				if(xmlDataManagerHandle==null){
					xmlDataManagerHandle = new XmlDataManagerHandle();
				}
			}
		}
		return xmlDataManagerHandle;
	}
	
	@Override
	protected <T> void updateLocal(T data) {
		 Class cls = data.getClass();
		 String xmlPath = cls.getResource("").getPath()+cls.getSimpleName()+".xml";
		 OutputStream output = null;
		 try {
			output = new FileOutputStream(xmlPath);
			XmlUnit.generateXml(output, data);
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException");
			e.printStackTrace();
		}finally {
			try {
				if(output!=null)
					output.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.error("IOException");
			}
		}
	}

	@Override
	public <T> T load(Class<T> cls) {
		String xmlPath = cls.getResource("").getPath()+cls.getSimpleName()+".xml";
		File file = new File(xmlPath);
		if(file.exists()&&file.isFile()){
			return XmlUnit.parseXml(file, cls);
		}
		return null;
	}
	
	public static void main(String[] args) {
		XmlDataManagerHandle instance = XmlDataManagerHandle.getInstance();
		System.out.println(instance.getData(WebsiteInfo.class));
	}
}
