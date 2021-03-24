package ex5_5;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Frame {
	private TextArea ta = null;
	private TextField tf = null;
	private Panel southPanel = null;
	private Button btn = null;
	private Thread tReader = null;
	private Socket sConnect = null;
	private boolean keepRunning = true;
	TCPServer myFrame = null;
	
	DataInputStream dis = null;
	DataOutputStream dos = null;
	
	/**
	 * 主函数
	 * @param args
	 */
	public static void main(String[] args) {
		new TCPServer("TCP Server V1.0").launchServer(8887);
	}
	
	public TCPServer(String title){
		super(title);
		setLocation(100, 150);
		ta = new TextArea();
		add(ta, BorderLayout.CENTER);
		
		southPanel = new Panel(new GridLayout(1,2));
		tf = new TextField();
		btn = new Button("Send");//"发送数据"
		southPanel.add(tf);
		southPanel.add(btn);
		add(southPanel, BorderLayout.SOUTH);
		pack();
		
		myFrame = this;
		btn.addMouseListener(new BtnMouseAdapter());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				keepRunning = false;
				try {
					if (tReader != null) {
						tReader.join(2000);
					}
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				myFrame.dispose();
				System.exit(0);
			}
		});
		setVisible(true);
	}
	
	public void launchServer(int port){
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(port);
			sConnect = ss.accept();
			dos = new DataOutputStream(sConnect.getOutputStream());
			dis = new DataInputStream(sConnect.getInputStream());
			tReader = new Thread(new SocketReader());
			tReader.start();
			ta.append("客户端" + sConnect.getInetAddress().toString() + "已经连接到此服务器。\n");
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if (ss != null) {
					ss.close();
					ss = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void sendMsg(){
		String tem = tf.getText();
		if (tem != null && dos != null) {
			try {
				dos.writeUTF(tem);
				ta.append("向 " + sConnect.getInetAddress().toString()+ ":" + sConnect.getPort() + " 发送：" + tem + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			tf.setText("");
		}
	}
	
	private class BtnMouseAdapter extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			sendMsg();
		}
	}
	
	private class SocketReader implements Runnable{
		public void run() {
			String strRead = null;
			while (keepRunning) {
				try {
					strRead = dis.readUTF();
				} catch (IOException e) {
					//e.printStackTrace();
					System.out.println("连接已断开。");
				}
				ta.append(sConnect.getInetAddress().toString()+ ":" + sConnect.getPort() + " 说：" + strRead + "\n");
				strRead = null;
			}
		}
	}

}