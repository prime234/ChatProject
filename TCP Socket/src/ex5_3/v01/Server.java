package ex5_3.v01;

import java.net.*;
import java.io.*;

public class Server {
	public static int port = 8000;
	private Socket socket;

	public void init() {
		try {
			ServerSocket server = new ServerSocket(port);

			System.out.println("�ȴ��ͻ����������� ");
			// ����һ������Socket
			socket = server.accept(); // �ȴ��ͻ�����������

			System.out.println("socket:  " + socket);
			// ���ӽ�����ͨ��Socket��ȡ�����ϵ�������
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// ���ӽ�����ͨ��Socket��ȡ�����ϵ������  �ֽ���������
			PrintStream out = new PrintStream(socket.getOutputStream());

			// ������׼���������Ӽ��̽�������
			BufferedReader userin = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				System.out.println("�ȴ��ͻ��˵���Ϣ...");
				String str = in.readLine(); // ��ȡ�ͻ��˷��͵�����
				System.out.println("�ͻ���:" + str);
				if (str.equals("bye"))
					break;
				System.out.print("���ͻ��˷���:");

				str = userin.readLine(); // �Ӽ��̽�������
				out.println(str); // �������ݸ��ͻ���
				if (str.equals("bye"))
					break;
			}

			socket.close();
			out.close();
			in.close();
			userin.close();
		} catch (IOException e) {
			System.out.println("�쳣:" + e);
		}
	}

	public static void main(String args[]) {
		Server se = new Server();
		se.init();

	}

}
