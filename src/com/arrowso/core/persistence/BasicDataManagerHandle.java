package com.arrowso.core.persistence;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.impl.Log4JLogger;

public abstract class BasicDataManagerHandle implements DataManagerHandle {

	private Map<String,Object> dataPool = new HashMap<String,Object>();
	
	private String[] packages = {"com.arrowso.core.data"};
	private Log4JLogger log = new Log4JLogger("BasicDataManagerHandle");
	private Class[] loadClassformPackage(){
		Class[][] allPageageClass = new Class[packages.length][];
		int size = 0;
		for(int pkIndex = 0;pkIndex<packages.length;pkIndex++){
			String packageName = packages[pkIndex];
			if(packageName!=null){
				URL classPath = this.getClass().getResource("/");
				File classFolder = new File(classPath.getPath()+"/"+packageName.replace(".", "/"));
				if(classFolder.isDirectory()){
					String[] names = classFolder.list(new classFileFilter());
					Class[] clss = new Class[names.length]; 
					for(int i=0;i<names.length;i++){
						try {
							clss[i] = Class.forName(packageName+"."+names[i].replace(".class", ""));
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
							log.error(" Class.forName("+packageName+"."+names[i]+") error");
						}
					}
					size+=clss.length;
					allPageageClass[pkIndex] = clss;
				}
			}
		}
		Class[] allCls = new Class[size];
		size = 0;
		for(Class[] clss:allPageageClass){
			System.arraycopy(clss, 0, allCls, size, clss.length);
			size += clss.length;
		}
		return allCls;
	}
	
	private class classFileFilter implements FilenameFilter{
		@Override
		public boolean accept(File dir, String name) {
			if(name.endsWith(".class"))
				return true;
			return false;
		}
	}
	
	@Override
	public <T> T getData(Class<T> c) {
		T t = (T) dataPool.get(c.getName());
		return t;
	}
	@Override
	public <T> void updateData(T data) {
		updateLocal(data);
		updateCache(data);
	}

	protected <T> void updateCache(T data) {
		String key = data.getClass().getName();
		dataPool.put(key, data);
	}

	protected abstract <T> void updateLocal(T data);
	
	protected BasicDataManagerHandle(){
		Class[] clss = loadClassformPackage();
		for(Class cls:clss){
			dataPool.put(cls.getName(), load(cls));
		}
	}
}
