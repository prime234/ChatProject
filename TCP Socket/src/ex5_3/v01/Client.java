package ex5_3.v01;

import java.net.*;
import java.io.*;

public class Client {
	public static void main(String[] args) {
		String str;
		try {
			InetAddress addr = InetAddress.getByName("127.0.0.1");
			Socket socket = new Socket(addr, 8000);// 发出连接请求
			System.out.println("socket=" + socket);
			// 连接建立，通过Socket获取连接上的输入流
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// 连接建立，通过Socket获取连接上的输出流
			PrintStream out = new PrintStream(socket.getOutputStream());

			BufferedReader userin = new BufferedReader(new InputStreamReader(System.in));
			// 创建标准输入流，从键盘接收数据
			while (true) {
				System.out.print("发送字符串:");
				str = userin.readLine(); // 从标准输入中读取一行
				out.println(str); // 发送给服务器
				if (str.equals("bye"))
					break;
				System.out.println("等待服务器端消息...");
				str = in.readLine(); // 读取服务器端的发送的数据
				System.out.println("服务器端字符:" + str);
				if (str.equals("bye"))
					break;
			}
			out.close();
			in.close();
			socket.close();
		} catch (Exception e) {
			System.out.println("异常:" + e);
		}
	}
}