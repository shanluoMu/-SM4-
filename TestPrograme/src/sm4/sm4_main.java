package sm4;

public class sm4_main {
	private String input;
	private String output;
	
	public static  String secretKey = "";
	public static final int SM4_ENCRYPT = 1;
	public static final int SM4_DECRYPT = 0;
	

	public static void main(String[] args) throws Exception{
		
		String input = "在此输入明文";
		System.out.println("明文: " + input);
		
		sm4_Encrypt Encrypt = new sm4_Encrypt();
		sm4_Decrypt Decrypt = new sm4_Decrypt();
		Util util = new Util();
		
		secretKey = "JeF8U9wHFOMfs2Y8";
		String cipherText = Encrypt.sm4(input,secretKey,util);
		System.out.println("密钥: " + secretKey);
		System.out.println("密文: " + cipherText);
		System.out.println(" " );
		
		String plainText = Decrypt.sm4(cipherText,secretKey.getBytes());
		//System.out.println("明文: " + plainText);
	}

	
	

}
