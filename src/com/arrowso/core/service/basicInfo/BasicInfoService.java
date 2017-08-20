package com.arrowso.core.service.basicInfo;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arrowso.core.data.WebsiteInfo;
import com.arrowso.core.persistence.XmlDataManagerHandle;

@Service("BasicInfoService")
public class BasicInfoService {

	@Resource
	private XmlDataManagerHandle xmlDataManagerHandle;
	
	public WebsiteInfo getWebStieInfo(){
		return xmlDataManagerHandle.getData(WebsiteInfo.class);
	}
	public void setWebStieInfo(WebsiteInfo websiteInfo){
		xmlDataManagerHandle.updateData(websiteInfo);
	}
	
}
