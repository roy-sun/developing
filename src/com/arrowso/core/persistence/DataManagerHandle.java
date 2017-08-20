package com.arrowso.core.persistence;

public interface DataManagerHandle {

	<T> T getData(Class<T> c);
	
	<T> void updateData(T t);
	abstract <T> T load(Class<T> data);
}
