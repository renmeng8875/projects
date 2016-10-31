package org.zlex.chapter07_2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class FileKey {
	
	
	public  String keyFile = "F:/key.ser";

	public String getKeyFile(){
		return this.keyFile;
	}
	
	public void setKeyFile(String keyFile){
		this.keyFile=keyFile;
	}
	
	
	//用java生成一个key并保存到一个二进制文件中去的方法如下：
	public static void saveBytePriveKey(String file) {
        File keyFile = new File(file);
        if(!keyFile.exists()){
        	try {
				keyFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        try {
        	String keyString="de23a211";
			byte[] keyData=keyString.getBytes();
			SecretKey key=new SecretKeySpec(keyData,"DES");
            FileOutputStream fop = new FileOutputStream(file);
            fop.write(key.getEncoded());
            fop.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }



	//从密钥文件中读取内容生成密钥
    public static SecretKey getBytePriveKey(String file) throws Exception {
        File keyf = new File(file);
        long length = keyf.length();
        byte[] bytes = new byte[(int) length];

        FileInputStream fis = new FileInputStream(keyf);

        // Read in the bytes
        int offset = 0;
        int numRead = 0;

        while (offset < bytes.length && (numRead = fis.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        DESKeySpec dks = new DESKeySpec(bytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);

        return key;
    }

	//文件加密
	public static void encryptFile(String plainFile, String encryptedFile, String keyFile) {
        try {

            SecretKey key = getBytePriveKey(keyFile);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            FileInputStream fis = new FileInputStream(plainFile);
            FileOutputStream fos = new FileOutputStream(encryptedFile);
            crypt(fis, fos, cipher);

            fis.close();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	
    public static void crypt(InputStream in, OutputStream out, Cipher cipher) throws IOException,
            GeneralSecurityException {
        int blockSize = cipher.getBlockSize();
        int outputSize = cipher.getOutputSize(blockSize);
        System.out.println("blockSize " + blockSize + " outputSize" + outputSize);

        byte[] inBytes = new byte[blockSize];
        byte[] outBytes = new byte[outputSize];

        int inLength = 0;
        boolean more = true;
        while (more) {
            inLength = in.read(inBytes);
            if (inLength == blockSize) {
                int outLength = cipher.update(inBytes, 0, blockSize, outBytes);//加密流的解密
                out.write(outBytes, 0, outLength);
            } else {
                more = false;
            }
        }
        if (inLength > 0)
            outBytes = cipher.doFinal(inBytes, 0, inLength);
        else
            outBytes = cipher.doFinal();
        out.write(outBytes);
    }


    //文件解密
  public static void decryptFile(String encryptedFile, String decryptedFile, String keyFile) {
        try {

            SecretKey key = getBytePriveKey(keyFile);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            
            FileInputStream fis = new FileInputStream(encryptedFile);
            FileOutputStream fos = new FileOutputStream(decryptedFile);
            crypt(fis, fos, cipher);

            fis.close();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
	
  public static void main(String[] args) {
	  FileKey fk= new FileKey();
	  fk.saveBytePriveKey(fk.getKeyFile());
	  String keyFile=fk.getKeyFile();
	  String plainFile="F:\\AdvertisementAction.java";
	  String encryptedFile="F:\\AdvertisementAction.java-encode";
	  
	 
	  //fk.encryptFile(plainFile, encryptedFile, keyFile);
	
	  fk.decryptFile(encryptedFile, "F:\\AdvertisementAction.java-src", keyFile);
	  
  }
	
	
	
}