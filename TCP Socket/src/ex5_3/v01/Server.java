package ex5_3.v01;

import java.net.*;
import java.io.*;

public class Server {
	public static int port = 8000;
	private Socket socket;

	public void init() {
		try {
			ServerSocket server = new ServerSocket(port);

			System.out.println("等待客户机连接请求 ");
			// 创建一个倾听Socket
			socket = server.accept(); // 等待客户机连接请求

			System.out.println("socket:  " + socket);
			// 连接建立，通过Socket获取连接上的输入流
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// 连接建立，通过Socket获取连接上的输出流  字节流缓冲流
			PrintStream out = new PrintStream(socket.getOutputStream());

			// 创建标准输入流，从键盘接收数据
			BufferedReader userin = new BufferedReader(new InputStreamReader(System.in));

			while (true) {
				System.out.println("等待客户端的消息...");
				String str = in.readLine(); // 读取客户端发送的数据
				System.out.println("客户端:" + str);
				if (str.equals("bye"))
					break;
				System.out.print("给客户端发送:");

				str = userin.readLine(); // 从键盘接收数据
				out.println(str); // 发送数据给客户端
				if (str.equals("bye"))
					break;
			}

			socket.close();
			out.close();
			in.close();
			userin.close();
		} catch (IOException e) {
			System.out.println("异常:" + e);
		}
	}

	public static void main(String args[]) {
		Server se = new Server();
		se.init();

	}

}
