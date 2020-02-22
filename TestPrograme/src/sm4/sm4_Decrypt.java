package sm4;

import sun.misc.BASE64Decoder;

public class sm4_Decrypt {

	public String sm4(String cipherText,byte[] keyBytes) throws Exception {
		// TODO Auto-generated method stub
//		byte[] keyBytes;
//		keyBytes = sm4_main.secretKey.getBytes();
		
		Util util = new Util();
		SM4_another sm4 = new SM4_another();

		sm4.setKey_dec(util.rk, keyBytes); //生成解密轮密钥
		

		byte[] decrypted = sm4.crypt_dec(util, new BASE64Decoder().decodeBuffer(cipherText));
		
//		for(int i = 0; i < 30; i++)
//		{
//			System.out.println("明文: " + decrypted[i]);
//		}
//		
		return new String(decrypted, "GBK");
	}

}
