package Server;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client {
     public ServiceFrame sf;
     private Socket csocket;
     private DataInputStream in;
     private DataOutputStream out;
    
     public static void main(String []args){
    	 
    	 BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
         System.out.println("������һ������: ");
         String who=" ";
         try{
        	 who=in.readLine().trim();        //����̨�������
         }
         catch(IOException e){
 			e.printStackTrace();
 		}
    	 
         new Client(who,"127.0.0.1",9992);
     }
     
    public Client(String who,String server,int port){    
    	sf=new ServiceFrame(who+" �Ŀͻ���");
    	String str1 = null;
    	sf.but.addActionListener(new ActListener(this,sf));
    	sf.addWindowListener(new ExitListener(this));
  
    	try{
    		csocket =new Socket(server,port);
    		in=new DataInputStream(new BufferedInputStream(csocket.getInputStream()));
    		out=new DataOutputStream(new BufferedOutputStream(csocket.getOutputStream()));
            out.writeUTF(who); 
            out.flush();            //���ַ���������
            
            while(true){
            	
            	str1 = in.readUTF();    //�ص㣡�������������˷�������Ϣ
            	
            	if(str1.endsWith("$"))  //����$��β���������û�
            	{
           		     int index = str1.lastIndexOf("$");
           		     String str2 = str1.substring(0,index);
         		     sf.friendlist.addItem(str2.trim());
           		
            	}
            	
            	else  if(str1.endsWith("@"))      //��@��βΪ˽����Ϣ
            	      {	
            		     str1=str1.substring(0,str1.length()-1);
            		     if(str1.contains(who))
            		    	 sf.showAT.append("-- "+str1+"\n"); 
            	      }  
            		
            	else{
            		if(str1.endsWith("����"))      //�û����ߣ�ɾ�����û�
            	     {
         		         int index = str1.lastIndexOf("����");
         		         String str2 = str1.substring(0,index);
         		         sf.friendlist.removeItem(str2.trim());
            	     }
            		sf.showAT.append("-- "+str1+"\n");
            		
            	}
            	sf.showCount.setText("��������:"+(sf.friendlist.getItemCount()-1));  //��̬������������
            }
            
    	}
    	catch(Exception e){
    		System.out.println("Server Error");
    		this.close();
    		System.exit(0);
    	}	
    }
    
    protected void send(String msg){          //������Ϣ���������ķ���
    	try{
    		out.writeUTF(msg);
    		out.flush();
    	}
    	catch(Exception e){}
    	}
    
    protected void close(){
    	try{
    		sf.dispose();
    		out.close();
    		in.close();
    		csocket.close();
    		}
    catch(IOException ex){}
    }
}

class ServiceFrame extends Frame{     
	 JTextArea showAT;
	 JTextField sendFD;
	 JComboBox friendlist;
	 JButton but;
	 JLabel showCount;
	 JScrollPane textAreaScrollPane;
	 JPanel textFieldPanel = new JPanel();
	
	 
	public ServiceFrame (String winname){
		super(winname);            //�̳и��������
		setSize(500,400);
		
		textFieldPanel.setLayout(new FlowLayout(0,10,10));
		showAT = new JTextArea(400,400);
		showAT.setEditable(false);
		textAreaScrollPane = new JScrollPane(showAT);
		add(textAreaScrollPane, BorderLayout.CENTER);
		add(textFieldPanel, BorderLayout.SOUTH);
		
		
		friendlist = new JComboBox();
		friendlist.addItem("������");
		textFieldPanel.add(friendlist);
		sendFD = new JTextField(20);
		textFieldPanel.add(sendFD);
		but = new JButton("����");
		but.setMnemonic(KeyEvent.VK_ENTER);
		textFieldPanel.add(but);
		showCount = new JLabel("��������:1");
		textFieldPanel.add(showCount);
        show();
	}
}

class ActListener implements ActionListener
{
  Client client;
  ServiceFrame sframe;
  
  public ActListener(Client c,ServiceFrame sf){
	  client=c;
	  sframe=sf;
	  
  }
  public void actionPerformed(ActionEvent e){  //������Ϣ������@��Ϊ˽�ı��
		  client.send(sframe.sendFD.getText().trim()+"@"+sframe.friendlist.getSelectedItem());
		  sframe.sendFD.setText(" ");
  }
}

class ExitListener extends WindowAdapter{
	Client client;
	public ExitListener(Client c){
		  client=c;
	  }
	public void windowClosing(WindowEvent e){
		client.close();
		System.exit(0);
	}
}