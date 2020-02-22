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
		setTitle("����SM4�㷨�Ľ���ϵͳ");//���ô�����Ŀ
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//���ùرհ�ť
		setBounds(100, 100, 681, 523); 
		//���ô��ڴ�С
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//��contentPane�ڲ��߿�Ϊ�գ�������5�����صĺ��		
		setContentPane(contentPane);
		//��contentPane����Ϊ�������
		contentPane.setLayout(null);
		//�رմ��ڲ��ֹ�����ʹ�ú����setBounds��Ч
		
		/***********�����������************/
		JScrollPane scrollPane_C = new JScrollPane();//�����Ŵ�����������
		scrollPane_C.setBounds(10, 30, 625, 148);
		contentPane.add(scrollPane_C);
		
		area_C = new JTextArea();
		area_C.setFont(new Font("Monospaced", Font.PLAIN, 16));
		area_C.setLineWrap(true);
		scrollPane_C.setViewportView(area_C);
		
		JScrollPane scrollPane_P = new JScrollPane();//���������ɵ�����
		scrollPane_P.setBounds(10, 220, 625, 148);
		contentPane.add(scrollPane_P);
		
		area_P = new JTextArea();
		area_P.setFont(new Font("Monospaced", Font.PLAIN, 16));
		area_P.setLineWrap(true);
		scrollPane_P.setViewportView(area_P);
		
		/***********��������************/
		JButton Setbtn = new JButton("����");
		Setbtn.setFont(new Font("����", Font.PLAIN, 15));
		Setbtn.setBounds(310, 390, 93, 34);
		contentPane.add(Setbtn);
		
		JButton getText = new JButton("��ȡ");
		getText.setFont(new Font("����", Font.PLAIN, 15));
		getText.setBounds(420, 390, 93, 34);
		contentPane.add(getText);
		
		JButton jiemibtn = new JButton("����");
		jiemibtn.setFont(new Font("����", Font.PLAIN, 15));
		jiemibtn.setBounds(530, 390, 93, 34 );
		contentPane.add(jiemibtn);
		

		/**********Ϊ���������Ӧ����***********/
		//��ͻ��˽�������
		Setbtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed (ActionEvent e) {
				// TODO Auto-generated method stub
				/**********Ϊ�������˴����׽���************/
				try {
					ss = new ServerSocket(2333);//�����˿ں�Ϊ2333��socket������
					System.out.println("�˿ں�Ϊ 2333 �ķ������Ѵ�����׼����ȡ����...");
					area_C.append("�˿ں�Ϊ 2333 �ķ������Ѵ�����׼����ȡ����...\n");
				
//					while(true){
//						Socket s = ss.accept();
//						System.out.println("��������");
//						while(true){
//						DataInputStream dis = new DataInputStream(s.getInputStream());//��ȡ����	
//						System.out.println("��ȡ����");
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
//						//�����Ĵ�byteת��ΪString
//						//String cipherText = new BASE64Encoder().encode(ctext);
//						cipherText = new String(ctext);
////						area_C.append("���յ������ģ�"+cipherText+"\n");
////						area_C.append("���յ������ģ�"+ctext+"\n");
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
		//��ȡ����
		getText.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try{
					while(true){
						Socket s = ss.accept();
						System.out.println("��������");
						while(true){
						DataInputStream dis = new DataInputStream(s.getInputStream());//��ȡ����	
						System.out.println("��ȡ����");
						int keylen = dis.readInt();
						System.out.println(keylen);
						getkey = new byte[keylen];
						dis.read(getkey, 0, keylen);
					    System.out.println(getkey);
	
						int textlen = 0;
						textlen = dis.readInt();
						ctext = new byte[textlen];
						dis.read(ctext, 0, textlen);
						//�����Ĵ�byteת��ΪString
						//String cipherText = new BASE64Encoder().encode(ctext);
						cipherText = new String(ctext);
//						area_C.append("���յ������ģ�"+cipherText+"\n");
//						area_C.append("���յ������ģ�"+ctext+"\n");
						
						
						s.close();
						}
					}				
				}catch(IOException e1){
					e1.printStackTrace();
				}
				if(ctext == null){
					area_C.append("δ�յ�����");
				}
				else if(getkey == null){
					area_C.append("δ�յ���Կ");
				}
				else {
					area_C.append("���յ������ģ�"+cipherText+"\n");
				}
			}
			
		});
		//Ϊ���Ľ���
		jiemibtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sm4_Decrypt decrypt = new sm4_Decrypt();
				try {
					String plainText = decrypt.sm4(cipherText,getkey);
					area_P.append("���ܺ�����ģ�"+plainText+"\n");
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

//ss = new ServerSocket(8000);//�����˿ں�Ϊ8000��socket������
//DataInputStream dis;
//System.out.println("�˿ں�Ϊ 8000 �ķ������Ѵ�����׼����ȡ����...");
//area_C.append("�˿ں�Ϊ 8000 �ķ������Ѵ�����׼����ȡ����...\n");
//while(true){
//	System.out.println("�˿ں�Ϊ 8000 �ķ������Ѵ�����׼����ȡ����...");
//	Socket s = ss.accept();
//	while(true){
//		dis = new DataInputStream(s.getInputStream());//��ȡ����	
//		int keylength = dis.readInt();
//		byte[] key =new byte[keylength];
//		dis.read(key, 0, keylength); //��ȡ����ļ�����Կ
//		
//		int len =dis.readInt();
//		byte[] ctext = new byte[len];
//		dis.read(ctext, 0, len); //��ȡ����
//		
//		System.out.println("��ȡ����"+ctext);
