package ex5_3.v01;

import java.net.*;
import java.io.*;

public class Client {
	public static void main(String[] args) {
		String str;
		try {
			InetAddress addr = InetAddress.getByName("127.0.0.1");
			Socket socket = new Socket(addr, 8000);// ������������
			System.out.println("socket=" + socket);
			// ���ӽ�����ͨ��Socket��ȡ�����ϵ�������
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// ���ӽ�����ͨ��Socket��ȡ�����ϵ������
			PrintStream out = new PrintStream(socket.getOutputStream());

			BufferedReader userin = new BufferedReader(new InputStreamReader(System.in));
			// ������׼���������Ӽ��̽�������
			while (true) {
				System.out.print("�����ַ���:");
				str = userin.readLine(); // �ӱ�׼�����ж�ȡһ��
				out.println(str); // ���͸�������
				if (str.equals("bye"))
					break;
				System.out.println("�ȴ�����������Ϣ...");
				str = in.readLine(); // ��ȡ�������˵ķ��͵�����
				System.out.println("���������ַ�:" + str);
				if (str.equals("bye"))
					break;
			}
			out.close();
			in.close();
			socket.close();
		} catch (Exception e) {
			System.out.println("�쳣:" + e);
		}
	}
}