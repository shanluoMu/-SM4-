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
	
	private int port;//Ŀ�Ķ˿ں�
	private int sendToport;//����˶˿ں�
	private JPanel contentPane; //����һ��JPanel�����Ķ���
	private String text; //Ҫ���͵�����
	private String result; //�õ�������
	private String inputkey; //Ҫ�������Կ
	private long[] outputkey;//���ܺ���Կ
	private String sendToip;//���շ�ip��ַ
	private Socket socket = null;//�����׽���
	
	JTextArea mingwen; //���������
    JTextArea ipArea;//ip��ַ�����
    JTextArea portArea;//Ŀ�Ķ˿������
    JTextArea portSelf;//�����˿������
    JTextArea jiamiArea;//������Կ�����
    JTextArea jiemiArea;//������Կ�����
    JTextArea area_P;//�����������
    JTextArea area_C;//������

    
	public CLient() throws Exception
	{
		setTitle("����SM4�㷨�ļ���ϵͳ");
		//setBounds(100,100,681,523);
		setBounds(100,100,681,623);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/********�����������********/
		JScrollPane scrollPane_P = new JScrollPane();//��������
		scrollPane_P.setBounds(10, 50, 625, 148);
		contentPane.add(scrollPane_P);
		
		area_P = new JTextArea();
		area_P.setFont(new Font("Monospaced", Font.PLAIN, 16));
		area_P.setLineWrap(true);
		scrollPane_P.setViewportView(area_P);
		
		JScrollPane resultArea = new JScrollPane();//�������
		resultArea.setBounds(10, 220, 625, 148);
		contentPane.add(resultArea);
		
		area_C = new JTextArea();
		area_C.setFont(new Font("Monospaced", Font.PLAIN, 16));
		area_C.setLineWrap(true);
		resultArea.setViewportView(area_C);
		
		/**********������ѡ�������**********/	
		ipArea = new JTextArea();//IP��ַ�����
		ipArea.setFont(new Font("����", Font.PLAIN, 18));
		ipArea.setBounds(135, 390, 350, 34);
		ipArea.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		contentPane.add(ipArea);
		
		JLabel sendIPlabel = new JLabel("���շ�IP");
		sendIPlabel.setFont(new Font("����", Font.PLAIN, 14));
		sendIPlabel.setBounds(65, 390, 70, 34);
		contentPane.add(sendIPlabel);
		
		
		portArea = new JTextArea();//���ն˶˿����봰
		portArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
		portArea.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		portArea.setBounds(135, 440, 120, 34);
		contentPane.add(portArea);
		
		JLabel portLabel = new JLabel("���ն˿�");
		portLabel.setFont(new Font("����", Font.PLAIN, 14));
		portLabel.setBounds(65, 440, 70, 34);
		contentPane.add(portLabel);
		
		portSelf = new JTextArea();//���ͷ��˿�����
		portSelf.setFont(new Font("Monospaced", Font.PLAIN, 18));
		portSelf.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		portSelf.setBounds(365, 440, 120, 34);
		contentPane.add(portSelf);
		
		JLabel Portlable_benji = new JLabel("���ͷ��˿�");
		Portlable_benji.setFont(new Font("����", Font.PLAIN, 14));
		Portlable_benji.setBounds(280, 440, 70, 34);
		contentPane.add(Portlable_benji);
		
		
		jiamiArea=new JTextArea();//������Կ�˿�����
	    jiamiArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
	    jiamiArea.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		jiamiArea.setBounds(135, 490, 350, 34);
		contentPane.add(jiamiArea);
		
		JLabel jiamiLabel = new JLabel("������Կ");
		jiamiLabel.setFont(new Font("����", Font.PLAIN, 14));
		jiamiLabel.setBounds(65, 490, 70, 34);
		contentPane.add(jiamiLabel);
//		
//		jiemiArea=new JTextArea();//������Կ�˿�����
//	    jiemiArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
//	    jiemiArea.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
//		jiemiArea.setBounds(380, 387, 120, 34);
//		contentPane.add(jiemiArea);		
		
		/**********��������**********/
		JButton jiamibtn = new JButton("����");
		jiamibtn.setFont(new Font("����", Font.PLAIN, 15));
		jiamibtn.setBounds(530, 390, 93, 34 );
		contentPane.add(jiamibtn);
		
		JButton set = new JButton("����");
		set.setFont(new Font("����", Font.PLAIN, 15));
		set.setBounds(530, 430, 93, 34);
		contentPane.add(set);

		JButton connect = new JButton("����");
		connect.setFont(new Font("����", Font.PLAIN, 15));
		connect.setBounds(530, 470, 93, 34);
		contentPane.add(connect);		
		
		JButton sendbtn = new JButton("����");
		sendbtn.setFont(new Font("����", Font.PLAIN, 15));
		sendbtn.setBounds(530, 510, 93, 34 );
		contentPane.add(sendbtn);

		//**********Ϊ���������Ӧ����***********//
		jiamibtn.addActionListener(new ActionListener(){	
			public void actionPerformed(ActionEvent e) {
				Util util = new Util();
				inputkey = jiamiArea.getText() ;//�������Կ		
				text = area_P.getText();
				sm4_Encrypt encrypt = new sm4_Encrypt();
				try {
					
					result = encrypt.sm4(text, inputkey,util);
					outputkey = util.rk;//���ܺ����Կ
					System.out.println("outputkey~"+outputkey);////////////
					System.out.println("util.rk~"+util.rk);////////////
					area_C.append("�������ģ�\n"+result+"\n");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}							
			}		
		});
		
		set.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				port = Integer.parseInt(portSelf.getText());//��ȡĿ�Ķ˿ں�
				
				sendToip = ipArea.getText();
				sendToport = Integer.parseInt(portArea.getText());//��ö˿ںź�ip
				area_C.append(port+"�˿ں����ڼ�����������\n");
			}
		});
		
		connect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					socket = new Socket(sendToip, sendToport);//����������Ϣ���׽���
					JOptionPane.showMessageDialog(null, "���ӽ����ɹ���");
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
//					for(int i = 0; i<keylengthL; i++)//��longת��Ϊbyte
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
		
		/**********ѡ������**********/
		JLabel input_JL = new JLabel("������...");
		input_JL.setFont(new Font("����",Font.PLAIN, 15));
		input_JL.setBounds(10, 15, 70, 34);
		contentPane.add(input_JL);
		
//		JLabel localport = new JLabel("���ͷ��˿�");
//		localport.setFont(new Font("����",Font.PLAIN, 14));
//		localport.setBounds(290, 337, 70, 34);
//		contentPane.add(localport);
		
		//**********Ϊ���������Ӧ����***********//		
	}
	private void PUT_ULONG(long l, byte[] a, int k) { //����ת��
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
