package sm4;



import java.awt.Frame;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Encoder;

public class sm4_Encrypt {

	 private boolean hexString = false;


	 public sm4_Encrypt()
	 {
		 
	 }
	 
	public String sm4(String input, String secretKey, Util util) throws Exception{

		 byte[] keyBytes;
		 
//			if (hexString)
//			{
//				keyBytes = Util.hexStringtoBytes(secretKey);
//			}
//			else
//			{
				keyBytes = secretKey.getBytes();
//			}//��String����Կ��Ϊbyte����

//		 Util util = new Util();
		 SM4 sm4 = new SM4();
		 sm4.setKey_enc(util.rk, keyBytes); //��������Կ
//		 System.out.println("sm4.util.rk~"+util.rk);////////////
		 byte[] encrypted = sm4.crypt(util, input.getBytes("GBK"));//���ļ���
		 /////// 
//		 for(int k = 0;k<20;k++)
//		 {System.out.println("bytemiwen: " + encrypted[k]);}
		 
		 String cipherText = new BASE64Encoder().encode(encrypted);
		 
		 
//		 if(cipherText != null && cipherText.trim().length() > 0)
//		 {
//			 Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//			 Matcher m = p.matcher(cipherText);
//			 cipherText = m.replaceAll("");
//		 }
////		 cl_Window();
		 return cipherText;
	 }
      
//	private void cl_Window()
//	{
//		Frame f = new Frame();
//		f.setTitle("����SM4�㷨�ļ���ϵͳ");
//		f.setSize(100,100); //���ô��ڴ�С
//		f.setLocation(300, 200);
//		f.setVisible(true);
//		
//		
//	}

}
