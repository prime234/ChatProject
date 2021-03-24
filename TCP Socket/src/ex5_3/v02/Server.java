package ex5_3.v02;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server  implements Runnable {
    public static int port=8000;  
    private  Socket socket;
    static int count=1;

    public Server(Socket socket) {
        this.socket = socket;
    }

    public Server() {
    	
    }
    
    
    
    public void init() {
        try{
            ServerSocket server=new ServerSocket(port);  
            
          while(true) {
               System.out.println("�ȴ���"+count+"�ͻ����������� "); 
             //����һ������Socket 
              socket=server.accept(); // �ȴ��ͻ�����������
              new Thread(new Server(socket)).start();
              System.out.println("socket:  "+socket); 
              count++;
          } //ѭ��
           
           
        } catch(IOException e) {
            System.out.println("�쳣:"+e);
        }
    }
    
    public static void main(String args[]) {
       Server se= new Server();
       se.init();
       
    }        

    @Override
    public void run() {                            
             BufferedReader  in = null;
        try {
            // ���ӽ�����ͨ��Socket��ȡ�����ϵ�������
            in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            // ���ӽ�����ͨ��Socket��ȡ�����ϵ������
            PrintStream out = new PrintStream(socket.getOutputStream());
            // ������׼���������Ӽ��̽�������
            BufferedReader userin=new BufferedReader( new InputStreamReader(System.in));
            while(true) {
                System.out.println("�ȴ��ͻ��˵���Ϣ...");
                String str=in.readLine();      // ��ȡ�ͻ��˷��͵�����
                System.out.println("�ͻ���:"+str);
                if(str.equals("bye")) break;
                System.out.print("���ͻ��˷���:");
                
                str=userin.readLine(); // �Ӽ��̽�������
                out.println(str);      //  �������ݸ��ͻ���
                if(str.equals("bye"))    break;
            }
            socket.close();
            out.close();
            in.close();
            userin.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
      
