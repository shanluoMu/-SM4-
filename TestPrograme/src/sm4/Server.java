package sm4;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import sun.misc.BASE64Encoder;

public class Server extends JFrame {
	
	public static void main(String[] arg) throws IOException {
		Server frame = new Server();
		frame.setVisible(true);
	}
	
	ServerSocket ss;
	JTextArea area_C;
	JTextArea area_P;
	private String cipherText;
	private byte[] ctext = null; 
	private byte[] getkey = null;
	
	public  Server() throws IOException{
		setTitle("基于SM4算法的解密系统");//设置窗口题目
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置关闭按钮
		setBounds(100, 100, 681, 523); 
		//设置窗口大小
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//让contentPane内部边框为空，并且有5个像素的厚度		
		setContentPane(contentPane);
		//把contentPane设置为面板内容
		contentPane.setLayout(null);
		//关闭窗口布局管理器使得后面的setBounds生效
		
		/***********滚动面板设置************/
		JScrollPane scrollPane_C = new JScrollPane();//用来放传过来的密文
		scrollPane_C.setBounds(10, 30, 625, 148);
		contentPane.add(scrollPane_C);
		
		area_C = new JTextArea();
		area_C.setFont(new Font("Monospaced", Font.PLAIN, 16));
		area_C.setLineWrap(true);
		scrollPane_C.setViewportView(area_C);
		
		JScrollPane scrollPane_P = new JScrollPane();//用来放生成的明文
		scrollPane_P.setBounds(10, 220, 625, 148);
		contentPane.add(scrollPane_P);
		
		area_P = new JTextArea();
		area_P.setFont(new Font("Monospaced", Font.PLAIN, 16));
		area_P.setLineWrap(true);
		scrollPane_P.setViewportView(area_P);
		
		/***********按键设置************/
		JButton Setbtn = new JButton("设置");
		Setbtn.setFont(new Font("宋体", Font.PLAIN, 15));
		Setbtn.setBounds(310, 390, 93, 34);
		contentPane.add(Setbtn);
		
		JButton getText = new JButton("获取");
		getText.setFont(new Font("宋体", Font.PLAIN, 15));
		getText.setBounds(420, 390, 93, 34);
		contentPane.add(getText);
		
		JButton jiemibtn = new JButton("解密");
		jiemibtn.setFont(new Font("宋体", Font.PLAIN, 15));
		jiemibtn.setBounds(530, 390, 93, 34 );
		contentPane.add(jiemibtn);
		

		/**********为按键添加响应函数***********/
		//与客户端建立连接
		Setbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed (ActionEvent e) {
				// TODO Auto-generated method stub
				/**********为服务器端创建套接字************/
				try {
					ss = new ServerSocket(2333);//创建端口号为2333的socket服务器
					System.out.println("端口号为 2333 的服务器已创建，准备获取数据...");
					area_C.append("端口号为 2333 的服务器已创建，准备获取数据...\n");
				
//					while(true){
//						Socket s = ss.accept();
//						System.out.println("监听到啦");
//						while(true){
//						DataInputStream dis = new DataInputStream(s.getInputStream());//获取数据	
//						System.out.println("获取到啦");
//						int keylen = dis.readInt();
//						System.out.println(keylen);
//						getkey = new byte[keylen];
//						dis.read(getkey, 0, keylen);
//					    System.out.println(getkey);
//	
//						int textlen = 0;
//						textlen = dis.readInt();
//						ctext = new byte[textlen];
//						dis.read(ctext, 0, textlen);
//						//将密文从byte转化为String
//						//String cipherText = new BASE64Encoder().encode(ctext);
//						cipherText = new String(ctext);
////						area_C.append("接收到的密文："+cipherText+"\n");
////						area_C.append("接收到的密文："+ctext+"\n");
//						
//						
//						s.close();
//						}
//					}				
				} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}

			}
		});	
		//获取数据
		getText.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{
					while(true){
						Socket s = ss.accept();
						System.out.println("监听到啦");
						while(true){
						DataInputStream dis = new DataInputStream(s.getInputStream());//获取数据	
						System.out.println("获取到啦");
						int keylen = dis.readInt();
						System.out.println(keylen);
						getkey = new byte[keylen];
						dis.read(getkey, 0, keylen);
					    System.out.println(getkey);
	
						int textlen = 0;
						textlen = dis.readInt();
						ctext = new byte[textlen];
						dis.read(ctext, 0, textlen);
						//将密文从byte转化为String
						//String cipherText = new BASE64Encoder().encode(ctext);
						cipherText = new String(ctext);
//						area_C.append("接收到的密文："+cipherText+"\n");
//						area_C.append("接收到的密文："+ctext+"\n");
						
						
						s.close();
						}
					}				
				}catch(IOException e1){
					e1.printStackTrace();
				}
				if(ctext == null){
					area_C.append("未收到密文");
				}
				else if(getkey == null){
					area_C.append("未收到密钥");
				}
				else {
					area_C.append("接收到的密文："+cipherText+"\n");
				}
			}
			
		});
		//为密文解密
		jiemibtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sm4_Decrypt decrypt = new sm4_Decrypt();
				try {
					String plainText = decrypt.sm4(cipherText,getkey);
					area_P.append("解密后的明文："+plainText+"\n");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
	}
//	class Connect{
//		
//	}
}

//ss = new ServerSocket(8000);//创建端口号为8000的socket服务器
//DataInputStream dis;
//System.out.println("端口号为 8000 的服务器已创建，准备获取数据...");
//area_C.append("端口号为 8000 的服务器已创建，准备获取数据...\n");
//while(true){
//	System.out.println("端口号为 8000 的服务器已创建，准备获取数据...");
//	Socket s = ss.accept();
//	while(true){
//		dis = new DataInputStream(s.getInputStream());//获取数据	
//		int keylength = dis.readInt();
//		byte[] key =new byte[keylength];
//		dis.read(key, 0, keylength); //获取传输的加密密钥
//		
//		int len =dis.readInt();
//		byte[] ctext = new byte[len];
//		dis.read(ctext, 0, len); //获取密文
//		
//		System.out.println("获取明文"+ctext);
