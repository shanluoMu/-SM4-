package sm4;

public class Util
{
	public long[] rk;
	public byte[] bk;
	public Util()
	{
		this.rk = new long[32];
	}
//��Stringת��Ϊbyte
	public static byte[] hexStringtoBytes(String hexString)
	{
		int i,pos;	
		if (hexString == null || hexString.equals("")) //////
		{
			return null;
		}

		hexString = hexString.toUpperCase();//ת��Ϊ��д
		int length = hexString.length()/2;
		char[] hexChars = hexString.toCharArray();//�����ַ���ת��Ϊ�µ��ַ�����
		byte[] hexbyte = new byte[length];
		
		for(i = 0; i < length; i++)
		{
			pos = i * 2;
			hexbyte[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
	
		return hexbyte;
		
	}

//��charת��Ϊbyte	
	public static byte charToByte(char ch) 
	{
		return (byte) "0123456789ABCDEF".indexOf(ch);
	}

}

