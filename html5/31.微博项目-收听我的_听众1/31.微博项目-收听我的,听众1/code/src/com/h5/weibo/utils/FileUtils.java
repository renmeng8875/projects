package com.h5.weibo.utils;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.Map.Entry;

/*******************************************
 * 文件操作常用类
 *******************************************/
public class FileUtils {
	
	/**
     * 获取文件名，不带后缀的
     * @param fileName
     * @return
     */
    public static String getNameWithoutSufix(String fileName) {
        int index1 = fileName.lastIndexOf(File.separator);
        int index2 = fileName.lastIndexOf(".");
        if (index1 == -1)
            index1 = 0;
        else
            index1++;
        if(index2 == -1) index2 = fileName.length();

        return fileName.substring(index1, index2);
     }

    /**
     * 获取后缀，带点的
     * @param fileName
     * @return
     */
    public static String getSufix(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return "";
        }

        return fileName.substring(index);
    }

     /**
     * 复制文件
     * @param src
     * @param dest
     * @throws java.io.IOException
     */
    public static void copyFile(String src, String dest) {
    	try {
	        FileInputStream in = new FileInputStream(src);
	        File file = new File(dest);
	        // 创建目录
	        File path = new File(file.getParent());
	        if (!path.exists()) {
	            path.mkdirs();
	
	        }
	        // 创建文件,先判断文件是否存在，避免文件名重复
	        if(file.exists()){
	            String name = getName(file.getName());
	            String ext = getExtension(file.getName());
	            if (ext.length() > 0) {
	                ext = "." + ext;
	            }
	            int iCount = 1;
	            while (file.exists()) {
	                // 文件已经存在，更改文件名
	                String tmpName = name + "_" + iCount;
	
	                file = new File(path.getAbsolutePath() + "\\" + tmpName + ext);
	                iCount++;
	            }
	        }
	        file.createNewFile();
	        // 复制文件
	        FileOutputStream out = new FileOutputStream(file);
	        int c;
	        byte buffer[] = new byte[1024];
	        while ((c = in.read(buffer)) != -1) {
	            for (int i = 0; i < c; i++) {
	                out.write(buffer[i]);
	            }
	        }
	        out.close();
	        in.close();
	        file = null;
	        path = null;
	        buffer = null;
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }

    /**
     * 递归删除目录
     * @param path
     */
    public static void delDir(String path) {
        File dir = new File(path);
        if (dir.exists()) {
            File[] tmp = dir.listFiles();
            for (int i = 0; i < tmp.length; i++) {
                if (tmp[i].isDirectory()) {
                    delDir(path + "/" + tmp[i].getName());
                } else {
                    tmp[i].delete();
                }
            }
            dir.delete();
        }
    }
    /**
     * 删除文件
     * @param path
     */
    public static void delFile(String fileName) {
        File f = new File(fileName);
        f.deleteOnExit();
        f = null;
    }

    /**
     * 得到文件的后缀
     * @param name 文件名: 123.jpg
     * @return jpg
     */
    public static String getExtension(String name) {
        int index1 = name.lastIndexOf(".");
        if (index1 == -1) {
            return "";
        }
        return name.substring(index1 + 1);
    }

    /**
     * 得到文件名(无后缀)
     * @param name 文件名: 123.jpg
     * @return 123
     */
    public static String getName(String name) {
        int index1 = name.lastIndexOf(".");
        if (index1 == -1) {
            index1 = name.length();
        }
        return name.substring(0, index1);
    }

    /**
     * 取文件名(包含后缀)
     * @param name 文件名: e:\123.jpg
     * @return 123.jpg
     */
    public static String getFileName(String filePath){
        int index1 = filePath.lastIndexOf("\\");
        if (index1 == -1) {
            index1 = 0;
        }else{
            index1++;
        }

        return filePath.substring(index1);
    }
     
    /**
     * 读取文件
     * @return
     * @throws IOException 
     */
    public static String readFile(String fileName) throws IOException{
    	return readFile(fileName, "UTF-8");
    }
    /**
     * 读取文件
     * @return
     * @throws IOException 
     */
    public static String readFile(String fileName,String encoding) throws IOException{
    	StringBuilder sb = new StringBuilder(); 
    	InputStreamReader inReader = new InputStreamReader(new FileInputStream(fileName),encoding);
    	LineNumberReader inLine = new LineNumberReader(inReader);
    	String line = null;
		while((line=inLine.readLine())!=null){
			sb.append(line).append("\r\n");
		}
		inReader.close();
		inLine.close();
		
		return sb.toString();
    }
}
