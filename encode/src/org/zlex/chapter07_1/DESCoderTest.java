/**
 * 2008-6-11
 */
package org.zlex.chapter07_1;

import static org.junit.Assert.assertEquals;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

/**
 * DES安全编码组件校验
 * 
 * @author 梁栋
 * @version 1.0
 */
public class DESCoderTest {

	/**
	 * 测试
	 * 
	 * @throws Exception
	 */
	@Test
	public final void test() throws Exception {
		String inputStr = "DES我爱你中国";
		byte[] inputData = inputStr.getBytes();
		System.err.println("原文:" + inputStr);

		// 初始化密钥
		byte[] key = DESCoder.initKey();
		
	
		String keyStr = new String(key);
		System.err.println("密钥:" + Base64.encodeBase64String(key));

		// 加密
		inputData = DESCoder.encrypt(inputData, key);
		System.err.println("加密后:" + Base64.encodeBase64String(inputData));

		byte[] decodeKey = keyStr.getBytes("iso8859-1");
		// 解密
		byte[] outputData = DESCoder.decrypt(inputData, key);

		String outputStr = new String(outputData);
		System.err.println("解密后:" + outputStr);

		// 校验
		assertEquals(inputStr, outputStr);
	}
}
