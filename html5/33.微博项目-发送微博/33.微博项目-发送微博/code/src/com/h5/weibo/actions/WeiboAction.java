package com.h5.weibo.actions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.struts2.convention.annotation.Action;

import com.h5.weibo.model.User;
import com.h5.weibo.model.Weibo;
import com.h5.weibo.service.WeiboService;
import com.h5.weibo.utils.FileUtils;


public class WeiboAction extends BaseAction{

	@Action(value="/wb/writer")
	public String add() {
		User my = getCurrentUser();
		
		Weibo wb = getBeanFromRequest(Weibo.class);
		wb.setWriterId(my.getId());
		File img = getFile("img");
		if(img != null && img.length() > 0) {
			String uploadName = getFileName("img");
			// 
			String uploadPath = getRequest().getRealPath("/wb_img");
			String sufix  = FileUtils.getSufix(uploadName);
			// 
			String logoName = UUID.randomUUID().toString() + sufix;
			String fileName = uploadPath + File.separator + logoName;

			FileUtils.copyFile(img.getAbsolutePath(),fileName);
			
			wb.setImg(logoName);
		}
		
		WeiboService.insertWeibo(wb);
		
		this.redirect(getRequest().getContextPath() + "/index.jsp");
		
		return null;
	}
}
