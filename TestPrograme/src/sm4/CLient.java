package sm4;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class CLient extends JFrame {
	public static void main(String[] args) throws Exception{
		CLient frame = new CLient();
		frame.setVisible(true);
	}
	
	private int port;//目的端口号
	private int sendToport;//服务端端口号
	private JPanel contentPane; //定义一个JPanel面板类的对象
	private String text; //要发送的明文
	private String result; //得到的密文
	private String inputkey; //要输入的密钥
	private long[] outputkey;//加密后密钥
	private String sendToip;//接收方ip地址
	private Socket socket = null;//创建套接字
	
	JTextArea mingwen; //明文输入框
    JTextArea ipArea;//ip地址输入框
    JTextArea portArea;//目的端口输入框
    JTextArea portSelf;//本机端口输入框
    JTextArea jiamiArea;//加密密钥输入框
    JTextArea jiemiArea;//解密密钥输入框
    JTextArea area_P;//输入明文面板
    JTextArea area_C;//输出面板

    
	public CLient() throws Exception
	{
		setTitle("基于SM4算法的加密系统");
		//setBounds(100,100,681,523);
		setBounds(100,100,681,623);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/********滚动面板设置********/
		JScrollPane scrollPane_P = new JScrollPane();//输入明文
		scrollPane_P.setBounds(10, 50, 625, 148);
		contentPane.add(scrollPane_P);
		
		area_P = new JTextArea();
		area_P.setFont(new Font("Monospaced", Font.PLAIN, 16));
		area_P.setLineWrap(true);
		scrollPane_P.setViewportView(area_P);
		
		JScrollPane resultArea = new JScrollPane();//输出窗口
		resultArea.setBounds(10, 220, 625, 148);
		contentPane.add(resultArea);
		
		area_C = new JTextArea();
		area_C.setFont(new Font("Monospaced", Font.PLAIN, 16));
		area_C.setLineWrap(true);
		resultArea.setViewportView(area_C);
		
		/**********输入框和选项框设置**********/	
		ipArea = new JTextArea();//IP地址输入框
		ipArea.setFont(new Font("宋体", Font.PLAIN, 18));
		ipArea.setBounds(135, 390, 350, 34);
		ipArea.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		contentPane.add(ipArea);
		
		JLabel sendIPlabel = new JLabel("接收方IP");
		sendIPlabel.setFont(new Font("宋体", Font.PLAIN, 14));
		sendIPlabel.setBounds(65, 390, 70, 34);
		contentPane.add(sendIPlabel);
		
		
		portArea = new JTextArea();//接收端端口输入窗
		portArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
		portArea.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		portArea.setBounds(135, 440, 120, 34);
		contentPane.add(portArea);
		
		JLabel portLabel = new JLabel("接收端口");
		portLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		portLabel.setBounds(65, 440, 70, 34);
		contentPane.add(portLabel);
		
		portSelf = new JTextArea();//发送方端口输入
		portSelf.setFont(new Font("Monospaced", Font.PLAIN, 18));
		portSelf.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		portSelf.setBounds(365, 440, 120, 34);
		contentPane.add(portSelf);
		
		JLabel Portlable_benji = new JLabel("发送方端口");
		Portlable_benji.setFont(new Font("宋体", Font.PLAIN, 14));
		Portlable_benji.setBounds(280, 440, 70, 34);
		contentPane.add(Portlable_benji);
		
		
		jiamiArea=new JTextArea();//加密密钥端口输入
	    jiamiArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
	    jiamiArea.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		jiamiArea.setBounds(135, 490, 350, 34);
		contentPane.add(jiamiArea);
		
		JLabel jiamiLabel = new JLabel("加密密钥");
		jiamiLabel.setFont(new Font("宋体", Font.PLAIN, 14));
		jiamiLabel.setBounds(65, 490, 70, 34);
		contentPane.add(jiamiLabel);
//		
//		jiemiArea=new JTextArea();//解密密钥端口输入
//	    jiemiArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
//	    jiemiArea.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
//		jiemiArea.setBounds(380, 387, 120, 34);
//		contentPane.add(jiemiArea);		
		
		/**********按键设置**********/
		JButton jiamibtn = new JButton("加密");
		jiamibtn.setFont(new Font("宋体", Font.PLAIN, 15));
		jiamibtn.setBounds(530, 390, 93, 34 );
		contentPane.add(jiamibtn);
		
		JButton set = new JButton("设置");
		set.setFont(new Font("宋体", Font.PLAIN, 15));
		set.setBounds(530, 430, 93, 34);
		contentPane.add(set);

		JButton connect = new JButton("连接");
		connect.setFont(new Font("宋体", Font.PLAIN, 15));
		connect.setBounds(530, 470, 93, 34);
		contentPane.add(connect);		
		
		JButton sendbtn = new JButton("发送");
		sendbtn.setFont(new Font("宋体", Font.PLAIN, 15));
		sendbtn.setBounds(530, 510, 93, 34 );
		contentPane.add(sendbtn);

		//**********为按键添加响应函数***********//
		jiamibtn.addActionListener(new ActionListener(){	
			public void actionPerformed(ActionEvent e) {
				Util util = new Util();
				inputkey = jiamiArea.getText() ;//输入的密钥		
				text = area_P.getText();
				sm4_Encrypt encrypt = new sm4_Encrypt();
				try {
					
					result = encrypt.sm4(text, inputkey,util);
					outputkey = util.rk;//加密后的密钥
					System.out.println("outputkey~"+outputkey);////////////
					System.out.println("util.rk~"+util.rk);////////////
					area_C.append("生成密文：\n"+result+"\n");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}							
			}		
		});
		
		set.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				port = Integer.parseInt(portSelf.getText());//获取目的端口号
				
				sendToip = ipArea.getText();
				sendToport = Integer.parseInt(portArea.getText());//获得端口号和ip
				area_C.append(port+"端口号正在监听，请连接\n");
			}
		});
		
		connect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					socket = new Socket(sendToip, sendToport);//建立发送消息的套接字
					JOptionPane.showMessageDialog(null, "连接建立成功！");
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		sendbtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
					byte[] sendkey = inputkey.getBytes();
//					int keylengthL = outputkey.length;
//					for(int i = 0; i<keylengthL; i++)//将long转化为byte
//					{
//						 PUT_ULONG(outputkey[i], sendkey , i);
////						 System.out.println(i+"outputkey[i]~"+outputkey[i]);
//					}
					int keylength = sendkey.length;
					outputStream.writeInt(keylength);
					outputStream.write(sendkey,0,keylength);
						
					System.out.println(sendkey);//////
					outputStream.flush();
					
					byte[] ctext = result.getBytes("GBK");
					System.out.println("ctext~"+ctext);////////////
					int len;
					len = ctext.length;
					outputStream.writeInt(len);
					outputStream.write(ctext, 0 ,len);
					outputStream.flush();
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println(e1);
				}
			}
		});		
		
		/**********选项设置**********/
		JLabel input_JL = new JLabel("请输入...");
		input_JL.setFont(new Font("宋体",Font.PLAIN, 15));
		input_JL.setBounds(10, 15, 70, 34);
		contentPane.add(input_JL);
		
//		JLabel localport = new JLabel("发送方端口");
//		localport.setFont(new Font("宋体",Font.PLAIN, 14));
//		localport.setBounds(290, 337, 70, 34);
//		contentPane.add(localport);
		
		//**********为按键添加响应函数***********//		
	}
	private void PUT_ULONG(long l, byte[] a, int k) { //类型转换
		a[k*4] = (byte)(int)(0xFF & l >> 24);
		a[k*4+1] = (byte)(int)(0xFF & l >> 16);
		a[k*4+2] = (byte)(int)(0xFF & l >> 8);
		a[k*4+3] = (byte)(int)(0xFF & l);
		//System.out.println(k+"a[k] "+a[k*4]);////////////
	// TODO Auto-generated method stub	
	}


//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		DataInputStream dis;
//		while(true){
//			Socket s = ss.accept;
//			dis = new DataInputStream();
//		}
//	}
}
