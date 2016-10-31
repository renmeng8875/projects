<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %><%@ page import="java.io.*" %><%

	// 文件下载
  	String filedownload = "E:/test.rar";
    File f = new File(filedownload);
    response.reset();//可以加也可以不加  
    response.setContentType("application/x-download");  
    response.setContentLength((int)f.length());
   	String filedisplay = "down.zip";   
    response.addHeader("Content-Disposition","attachment;filename=" + filedisplay);  
    
    java.io.OutputStream outp = null;  
    java.io.FileInputStream in = null; 
    
    try  {  
	    outp = response.getOutputStream();  
	    in = new FileInputStream(filedownload);  
    
	    byte[] b = new byte[1024];  
	    int i = 0;  
	    
	    while((i = in.read(b)) > 0){  
	    	outp.write(b, 0, i);  
	    }  
   
		outp.flush();  
		out.clear();  
  	} catch(Exception e)  {  
    	e.printStackTrace();  
    } finally {  
	    if(in != null){  
		    in.close();  
		    in = null;  
	    }  
    }   
%>