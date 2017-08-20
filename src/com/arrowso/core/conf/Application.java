package com.arrowso.core.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.arrowso.core.persistence.XmlDataManagerHandle;

@Configuration
@ComponentScan(basePackages="com.arrowso.*")
public class Application {

	@Bean("xmlDataManagerHandle")
	XmlDataManagerHandle getXmlDataManagerHandle(){
		return XmlDataManagerHandle.getInstance();
	}
	
}
