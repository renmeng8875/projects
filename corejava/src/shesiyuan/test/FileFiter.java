/**
 * 
 */
package shesiyuan.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**      
 * 项目名称：corejava   
 * 类名称：FileFiter   
 * 类描述：   
 * 创建人：wuwh   
 * 创建时间：2012-11-24 上午10:15:05   
 * 修改人：Administrator   
 * 修改时间：2012-11-24 上午10:15:05   
 * 修改备注：   
 * @version  1.0     
 */

public class FileFiter {
	
	public static void filtFilter(String src,String desc){
		File srcdir = new File(src);
		File[] filearr = srcdir.listFiles();
		for(int i=0;i<filearr.length;i++)
		{
			File f = filearr[i];
			if(f.isDirectory()){
				filtFilter(f.getAbsolutePath(),desc);
			}else{
				if(f.getName().endsWith(".pdf")){
					System.out.println(desc+f.getName());
					copyFile(f.getAbsolutePath(),desc+File.separator+f.getName());
				}
			}
			
			
		}	
		
		
		
	}
	
	
	public static void copyFile(String oldPath,String newPath){
		InputStream  inStream = null;
		FileOutputStream  fs  = null;
		  try  {  
	           int  bytesum  =  0;  
	           int  byteread  =  0;  
	           File  oldfile  =  new  File(oldPath);  
	           if  (oldfile.exists())  {  //文件存在时  
	               inStream  =  new  FileInputStream(oldPath);  //读入原文件  
	               fs  =  new  FileOutputStream(newPath);  
	               byte[]  buffer  =  new  byte[1444];  
	             
	               while  (  (byteread  =  inStream.read(buffer))  !=  -1)  {  
	                   bytesum  +=  byteread;  //字节数  文件大小  
	                  
	                   fs.write(buffer,  0,  byteread);  
	               }  
	              
	           }  
	       }  
	       catch  (Exception  e)  {  
	           System.out.println("复制单个文件操作出错");  
	           e.printStackTrace();  
	 
	       }  finally{
	    	   try {
				inStream.close();
				 fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
              
	       }
	       
	      // System.out.println(newPath);

		
		
	}
	
	
	public static void main(String[] args) {
		filtFilter("F:/前端技术视频/flash","F:/flash_doc/");
	}

}
