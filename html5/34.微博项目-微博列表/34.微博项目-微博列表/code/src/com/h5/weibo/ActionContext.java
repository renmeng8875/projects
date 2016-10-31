package com.h5.weibo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionContext {

private static ThreadLocal<ActionContext> datas = new ThreadLocal<ActionContext>();
	
	private HttpServletRequest request;

    private HttpServletResponse response;
    
    /**
     * 取当前线程保存的实例
     * @return
     */
    private static ActionContext getInstance() {  
         ActionContext ex = datas.get();  
   
         if (ex == null) {  
             ex = new ActionContext();  
             datas.set(ex);  
         }  
           
         return ex;  
     }
        
    public static void setRequest(HttpServletRequest request){
        ActionContext.getInstance().request = request;
    }

    public static HttpServletRequest getRequest() {
    	return  ActionContext.getInstance().request;

    }

    public static HttpServletResponse getResponse() {
        return ActionContext.getInstance().response;
    }

    public static void setResponse(HttpServletResponse aResponse) {
        ActionContext.getInstance().response = aResponse;
    }
}
