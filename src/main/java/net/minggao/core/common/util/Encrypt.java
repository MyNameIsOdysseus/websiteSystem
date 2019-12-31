package net.minggao.core.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 通用加密类
 * 
 * 加密工具类
 *  md5加密出来的长度是32位
 *  sha加密出来的长度是40位
 *  base64加密可以指定字符集，可以解密
 *  
 *  在使用SEC加解密的时候，可以指定算法 AES、DES、DESede，默认为AES
 *  在调用加密前，先获取对应的安全Key（调用getSecretKey）
 *  在调用SECencrypt方法进行加密，SECdetrypt方法进行解密
 *  
 * @author <a href="mailto:254840868@qq.com">edision</a>
 * @version v1.00 2012-8-22
 */
public class Encrypt {
	private static String 	CHAR_SET = "UTF-8";
//	private  String DES = "AES"; // optional value AES/DES/DESede  
	private  String CIPHER_ALGORITHM = "AES"; // optional value AES/DES/DESede  
	private  String key = "minggaoSoftWare";
	
	/**
	 * 设置加密算法
	 * @param algorithm  AES、DES、DESede
	 */
	public  void setEncryptAlgorithm(String algorithm){
		CIPHER_ALGORITHM = algorithm; 
	}
	public void setSecurityKey(String secKey){
		this.key = secKey;
	}
	
	/**
	 * 获取密匙
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Key getSecretKey(String key,SecureRandom sr) throws Exception{  
		        SecretKey securekey = null;  
		        if(key == null){  
		            key = "";  
		        }  
		        KeyGenerator keyGenerator = KeyGenerator.getInstance(CIPHER_ALGORITHM);  
		        keyGenerator.init(128,sr);  
		        securekey = keyGenerator.generateKey();  
		        return securekey;  
	}  
		      
	/**
	 * 根据密匙，把明文转成密文
	 * @param data  明文
	 * @param key   密匙
	 * @return
	 * @throws Exception
	 */
		    public  String SECencrypt(String data,String key) throws Exception {  
		    	return SecurityAES.encryptAES(data, key);
		    	
		    }  
		    public  String SECencrypt(String data)  throws Exception{  
		    	return SecurityAES.encryptAES(data, key);
		    } 
		    
		    
		      
		    /**
		     * 根据密匙，把密文转成明文
		     * @param message   密文
		     * @param key       密匙
		     * @return
		     * @throws Exception
		     */
		    public  String SECdetrypt(String message,String key) throws Exception{  
		    	return SecurityAES.decrypt(message, key);
		    }  
		    public  String SECdetrypt(String message) throws Exception{  
		    	return SecurityAES.decrypt(message, key);
		    	
		    }  
		    
		    
		    
		    /*
		     * 加密
		     * 1.构造密钥生成器
		     * 2.根据ecnodeRules规则初始化密钥生成器
		     * 3.产生密钥
		     * 4.创建和初始化密码器
		     * 5.内容加密
		     * 6.返回字符串
		     */
		      public String AESEncode(String encodeRules,String content){
		          try {
		              //1.构造密钥生成器，指定为AES算法,不区分大小写
		              KeyGenerator keygen=KeyGenerator.getInstance(CIPHER_ALGORITHM);
		              //2.根据ecnodeRules规则初始化密钥生成器
		              //生成一个128位的随机源,根据传入的字节数组
		              SecureRandom sr = SecureRandom.getInstance("SHA1PRNG"); 
					    sr.setSeed(key.getBytes()); 
		              keygen.init(128, sr);
		                //3.产生原始对称密钥
		              SecretKey original_key=keygen.generateKey();
		                //4.获得原始对称密钥的字节数组
		              byte [] raw=original_key.getEncoded();
		              //5.根据字节数组生成AES密钥
		              SecretKey key=new SecretKeySpec(raw, CIPHER_ALGORITHM);
		                //6.根据指定算法AES自成密码器
		              Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
		                //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
		              cipher.init(Cipher.ENCRYPT_MODE, key);
		              //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
		              byte [] byte_encode=content.getBytes("utf-8");
		              //9.根据密码器的初始化方式--加密：将数据加密
		              byte [] byte_AES=cipher.doFinal(byte_encode);
		            //10.将加密后的数据转换为字符串
		              //这里用Base64Encoder中会找不到包
		              //解决办法：
		              //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
		              String AES_encode=new String(new BASE64Encoder().encode(byte_AES));
		            //11.将字符串返回
		              return AES_encode;
		          } catch (NoSuchAlgorithmException e) {
		              e.printStackTrace();
		          } catch (NoSuchPaddingException e) {
		              e.printStackTrace();
		          } catch (InvalidKeyException e) {
		              e.printStackTrace();
		          } catch (IllegalBlockSizeException e) {
		              e.printStackTrace();
		          } catch (BadPaddingException e) {
		              e.printStackTrace();
		          } catch (UnsupportedEncodingException e) {
		              e.printStackTrace();
		          }
		          
		          //如果有错就返加nulll
		          return null;         
		      }
		      /*
		       * 解密
		       * 解密过程：
		       * 1.同加密1-4步
		       * 2.将加密后的字符串反纺成byte[]数组
		       * 3.将加密内容解密
		       */
		      public String AESDncode(String encodeRules,String content){
		          try {
		              //1.构造密钥生成器，指定为AES算法,不区分大小写
		              KeyGenerator keygen=KeyGenerator.getInstance(CIPHER_ALGORITHM);
		              //2.根据ecnodeRules规则初始化密钥生成器
		              //生成一个128位的随机源,根据传入的字节数组
		              SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");   //PRNG
					  sr.setSeed(key.getBytes()); 
		              keygen.init(128, sr);
		                //3.产生原始对称密钥
		              SecretKey original_key=keygen.generateKey();
		                //4.获得原始对称密钥的字节数组
		              byte [] raw=original_key.getEncoded();
		              //5.根据字节数组生成AES密钥
		              SecretKey key=new SecretKeySpec(raw, CIPHER_ALGORITHM);
		                //6.根据指定算法AES自成密码器
		              Cipher cipher=Cipher.getInstance(CIPHER_ALGORITHM);
		                //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
		              cipher.init(Cipher.DECRYPT_MODE, key);
		              //8.将加密并编码后的内容解码成字节数组
		              byte [] byte_content= new BASE64Decoder().decodeBuffer(content);
		              /*
		               * 解密
		               */
		              byte [] byte_decode=cipher.doFinal(byte_content);
		              String AES_decode=new String(byte_decode,"utf-8");
		              return AES_decode;
		          } catch (NoSuchAlgorithmException e) {
		              e.printStackTrace();
		          } catch (NoSuchPaddingException e) {
		              e.printStackTrace();
		          } catch (InvalidKeyException e) {
		              e.printStackTrace();
		          } catch (IOException e) {
		              e.printStackTrace();
		          } catch (IllegalBlockSizeException e) {
		              e.printStackTrace();
		          } catch (BadPaddingException e) {
		              e.printStackTrace();
		          }
		          
		          //如果有错就返加nulll
		          return null;         
		      }
	
	/**
	 * 系统通用加密(邮件,知识)
	 * base64加密
	 * @param inputText
	 * @return
	 */
	public static String e(String inputText) {
		if(StringUtil.isEmpty(inputText)){
			return inputText;
		}
		//return Base64.encode(inputText, "UTF-8");
		return getBase64(inputText);
	}

	/**
	 * 系统通用解密(邮件，知识)
	 *
	 * @param inputText
	 * @return
	 */
	public static String d(String inputText) {
		if(StringUtil.isEmpty(inputText)){
			return inputText;
		}
		//return Base64.decode(inputText, "UTF-8");
		return getFromBase64(inputText);
	}
	/**
	 * 
	 * @param inputText
	 * @return
	 */
	public static byte[] Based64decode(String inputText){
		byte[] result = null;
		if(!StringUtil.isBlank(inputText)){
			sun.misc.BASE64Decoder decorder = new sun.misc.BASE64Decoder();
			try {
				return decorder.decodeBuffer(inputText);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;		
	}
	
	// 加密  
	public static String getBase64(String str) {  
	        byte[] b = null;  
	        String s = null;  
	        try {  
	            b = str.getBytes("utf-8");  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }  
	        if (b != null) {  
	            s = new BASE64Encoder().encode(b);  
	        }  
	        return s;  
	    }  
	  
	    // 解密  
	    public static String getFromBase64(String s) {  
	        byte[] b = null;  
	        String result = null;  
	        if (s != null) {  
	            BASE64Decoder decoder = new BASE64Decoder();  
	            try {  
	                b = decoder.decodeBuffer(s);  
	                result = new String(b, "utf-8");  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return result;  
	    } 

	

	/**
	 * 二次加密，应该破解不了了吧？
	 *
	 * @param inputText
	 * @return
	 */
	public static String md5AndSha(String inputText) {
		if(StringUtil.isEmpty(inputText)){
			return inputText;
		}
		return sha(md5(inputText));
	}

	/**
	 * md5加密
	 *
	 * @param inputText
	 * @return
	 */
	public static String md5(String inputText) {
		if(StringUtil.isEmpty(inputText)){
			return inputText;
		}
		return encrypt(inputText, "md5");
	}
	
	
	public static String md5_16(String inputText){
		//System.out.println("MD5(" + sourceStr + ",16) = " + buf.toString().substring(8, 24));
		return md5(inputText).substring(8, 24);
	}

	/**
	 * sha加密
	 *
	 * @param inputText
	 * @return
	 */
	public static String sha(String inputText) {
		return encrypt(inputText, "sha-1");
	}

	/**
	 * md5或者sha-1加密
	 *
	 * @param inputText
	 *            要加密的内容
	 * @param algorithmName
	 *            加密算法名称：md5或者sha-1，不区分大小写
	 * @return
	 */
	private static String encrypt(String inputText, String algorithmName) {
		if (inputText == null || "".equals(inputText.trim())) {
			throw new IllegalArgumentException("请输入要加密的内容");
		}
		if (algorithmName == null || "".equals(algorithmName.trim())) {
			algorithmName = "md5";
		}
		String encryptText = null;
		try {
			MessageDigest m = MessageDigest.getInstance(algorithmName);
			m.update(inputText.getBytes("UTF8"));
			byte s[] = m.digest();
			// m.digest(inputText.getBytes("UTF8"));
			return hex(s);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encryptText;
	}

	/**
	 * 返回十六进制字符串
	 *
	 * @param arr
	 * @return
	 */
	private static String hex(byte[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}
	
	
	
	/**
	 * 
	 * 转换为16进制字符串 
	 * @param inputText
	 * @return      
	 * @author        liumc
	 * @Date          2017-6-28 上午11:42:00
	 */
	public static String e16(String inputText){
		try {
			byte[] arr  = inputText.getBytes(CHAR_SET);
			return bytesToHexString(arr);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * 把16进制字符串转换为明码 
	 * @param input16Text      
	 * @author        liumc
	 * @Date          2017-6-28 上午11:42:35
	 */
	public static String d16(String input16Text){
		byte[] arr = hexStringToByte(input16Text);
		try {
			return new String(arr,CHAR_SET);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 把16进制的字符串转换为字节数组
	 * @param hex 16进制字符串
	 * @return
	 */
	public static byte[] hexStringToByte(String hex) {   
	    int len = (hex.length() / 2);   
	    byte[] result = new byte[len];   
	    char[] achar = hex.toCharArray();   
	    for (int i = 0; i < len; i++) {   
	     int pos = i * 2;   
	     result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));   
	    }   
	    return result;   
	}
	private static byte toByte(char c) {   
	    byte b = (byte) "0123456789ABCDEF".indexOf(c);   
	    return b;   
	} 
	/**
	 * 把字节数组转换为16进制字符串
	 * @param bArray 字节数组
	 * @return
	 */
	public static final String bytesToHexString(byte[] bArray) {   
	    StringBuffer sb = new StringBuffer(bArray.length);   
	    String sTemp;   
	    for (int i = 0; i < bArray.length; i++) {   
	     sTemp = Integer.toHexString(0xFF & bArray[i]);   
	     if (sTemp.length() < 2)   
	      sb.append(0);   
	     sb.append(sTemp.toUpperCase());   
	    }   
	    return sb.toString();   
	}
	
	public static void main(String[] args) throws Exception{
//		String message = "password";  
//		        String key = "";  
//		        String entryptedMsg = SECencrypt(message,key);  
//		        System.out.println("encrypted message is below :");  
//		        System.out.println(entryptedMsg);  
//		          
//		        String decryptedMsg = SECdetrypt(entryptedMsg,key);  
//		        System.out.println("decrypted message is below :");  
//		        System.out.println(decryptedMsg);
//		System.out.println(""+SecurityAES.decrypt("Eoe+29nTVqXduwcD1rAXsp70wYGfxwDDOZwf2kJuGx6BlMsADQ1c8BH9zjUX/m+B", "12345678"));
	}
}
