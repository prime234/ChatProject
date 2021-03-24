package Server;
import java.io.*;

import java.util.*;
import java.net.*;
public class Server {

	public Server(int port) throws IOException{
		ServerSocket server=new ServerSocket(port);
		
		while(true){
			Socket conn=server.accept(); //�����ͻ����׽���
			DataInputStream in =new DataInputStream(conn.getInputStream());
			String who=in.readUTF();
			System.out.print("Client" +"(ip:"+conn.getInetAddress()+")"+who+" ����!"+"\n");
			
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
	protected static Vector<ServerHander>clientlist=new Vector<ServerHander>() ;  //���û��̷߳���ɱ��������
	public ServerHander(String name,Socket socket) throws IOException{
		this.who = name;
		this.socket = socket;
		in =new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		out =new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	}
	public void run(){
		try{
			 clientlist.addElement(this);       //VECTOR���������߳�
			 sendallclient(who+"        ����");
			 sendclientlist();                  //Ϊÿһ���ͻ��˸��������û�
			
			 while(true){                    
				  
				 String msg=in.readUTF();
				 int index = msg.lastIndexOf("@");                    //�Կͻ��˷���������Ϣ����Ӧ�Ĵ���
            	 String str = msg.substring(index+1,msg.length());    //ȡ�÷��Ͷ���ͷ��͵���Ϣ
            	 msg=msg.substring(0,index);
                 if(str.equals("������")==false)
                   {
                	 sendallclient(who+"     ��"+str+"     ˵: "+msg+"@"); //���Ƿ�����������������@
                   }
                 else
					 
				     sendallclient(who+"   ��������       ˵: "+msg);	 
			       }
			}
		catch(IOException e){
			System.out.println("error");
		}
		finally{
			clientlist.removeElement(this);
			System.out.print("Client" +"(ip:"+socket.getInetAddress()+")"+who+" �˳�!"+"\n");
			sendallclient(who+"     ����");
			
			try{
				socket.close();
			}
			catch(IOException ex){
				System.out.println("clsing");
			}
		}
	}
	
	//������Ϣ�������û�
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

	//�����û��б�
	public static synchronized void sendclientlist(){
		
			for(int j=0;j<clientlist.size();j++)
			
			{
				ServerHander allclients1=clientlist.elementAt(j);
			    if(j==clientlist.size()-1)                       //���������û�����vector�е������û������͹�ȥ
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
				 else {                                         //���û���ֻ�����������û�
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