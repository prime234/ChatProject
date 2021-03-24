package Server;
import java.io.*;

import java.util.*;
import java.net.*;
public class Server {

	public Server(int port) throws IOException{
		ServerSocket server=new ServerSocket(port);
		
		while(true){
			Socket conn=server.accept(); //建立客户端套接字
			DataInputStream in =new DataInputStream(conn.getInputStream());
			String who=in.readUTF();
			System.out.print("Client" +"(ip:"+conn.getInetAddress()+")"+who+" 进入!"+"\n");
			
			ServerHander cn=new ServerHander(who,conn);
			cn.start();
		}
		
	}
	public static void main(String []args)throws IOException{
		new Server(9992);
		}
}

class ServerHander extends Thread{
	Socket socket;
	
	DataInputStream in;
	DataOutputStream out;
	String who;
	protected static Vector<ServerHander>clientlist=new Vector<ServerHander>() ;  //把用户线程放入可变对象数组
	public ServerHander(String name,Socket socket) throws IOException{
		this.who = name;
		this.socket = socket;
		in =new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		out =new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	}
	public void run(){
		try{
			 clientlist.addElement(this);       //VECTOR中增加新线程
			 sendallclient(who+"        上线");
			 sendclientlist();                  //为每一个客户端更新在线用户
			
			 while(true){                    
				  
				 String msg=in.readUTF();
				 int index = msg.lastIndexOf("@");                    //对客户端发过来的信息做相应的处理
            	 String str = msg.substring(index+1,msg.length());    //取得发送对象和发送的消息
            	 msg=msg.substring(0,index);
                 if(str.equals("所有人")==false)
                   {
                	 sendallclient(who+"     对"+str+"     说: "+msg+"@"); //不是发给所有人则增添标记@
                   }
                 else
					 
				     sendallclient(who+"   对所有人       说: "+msg);	 
			       }
			}
		catch(IOException e){
			System.out.println("error");
		}
		finally{
			clientlist.removeElement(this);
			System.out.print("Client" +"(ip:"+socket.getInetAddress()+")"+who+" 退出!"+"\n");
			sendallclient(who+"     下线");
			
			try{
				socket.close();
			}
			catch(IOException ex){
				System.out.println("clsing");
			}
		}
	}
	
	//发送消息给所有用户
	protected static void sendallclient(String msg){         
		synchronized (clientlist){
			Enumeration <ServerHander> allclients=clientlist.elements();
			while(allclients.hasMoreElements())
			{
				ServerHander serh=(ServerHander)allclients.nextElement();
				try{
					serh.out.writeUTF(msg);
					serh.out.flush();
				}
				catch(IOException exc){
					serh.interrupt();
				}
			}
		}
	}

	//更新用户列表
	public static synchronized void sendclientlist(){
		
			for(int j=0;j<clientlist.size();j++)
			
			{
				ServerHander allclients1=clientlist.elementAt(j);
			    if(j==clientlist.size()-1)                       //若是新增用户，把vector中的所有用户都发送过去
				{
				 for(int i = 0;i<clientlist.size();i++)  
				    {
					 ServerHander allclients2=clientlist.elementAt(i);
                        try{
						      allclients1.out.writeUTF(allclients2.who+"$");
						      allclients1.out.flush();
				           }catch(IOException exc){
					           allclients1.interrupt();
				           }
				    }
				}
				 else {                                         //老用户，只增加新来的用户
					 ServerHander allclients3=clientlist.lastElement();
					 try{
					      allclients1.out.writeUTF(allclients3.who+"$");
					      allclients1.out.flush();
			            }catch(IOException exc){
				           allclients1.interrupt();
			           }
				     }
			}
		}

}