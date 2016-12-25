
import java.net.*;
import java.util.Scanner;
import java.io.*;  
public class Chat{  

public static void main(String args[])throws Exception{ 
	
System.out.println("Type h if you want to host or g to be a guest: ");
Scanner in = new Scanner(System.in);
String choice = in.nextLine();

if (choice.equals("h")){
	System.out.println("Choose port: ");
	int myPort = in.nextInt();
	
	ServerSocket myServerSocket=new ServerSocket(myPort);  
	System.out.println("waiting for someone to connect");
	Socket mySocket=myServerSocket.accept();
	System.out.println(mySocket.getInetAddress().getHostName()+" has connected with you");
	
	
	DataInputStream din=new DataInputStream(mySocket.getInputStream());  
	DataOutputStream dout=new DataOutputStream(mySocket.getOutputStream());  
	BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));  
	  
	String friendMessage="";
	String myMessage="";
	boolean friendLeft= false;
	
	while(true){  
		try {
			friendMessage=din.readUTF();
		} catch (Exception e) {
			friendLeft= true;
			break;
		}
		
		if(friendMessage.equals("exit") || friendMessage.contains("terminate")){break;}
		
		System.out.println(mySocket.getInetAddress().getHostAddress()+ " port "+mySocket.getPort()+": "+friendMessage);  
		myMessage=reader.readLine(); 
		if(myMessage.equals("exit")|| myMessage.contains("terminate")){break;}
		
		while(myMessage.equals("help") || myMessage.equals("myport") || myMessage.equals("myip") 
				|| myMessage.equals("list") ){
			
			switch(myMessage){
				case "help": 
					System.out.println("Command List: help, list, myip, myport, exit, terminate<id> \n");
					break;
				case "myport": 
					System.out.println("your port is: "+ myServerSocket.getLocalPort());
					break;
				case "myip": 
					System.out.println("your ip is: "+ mySocket.getInetAddress().getHostAddress());
					break;
				case "list": 
					System.out.println("id:  IP Address       Port No. ");
					System.out.println("1:   "+ InetAddress.getLocalHost().getHostAddress()+"        "+mySocket.getLocalPort());
					System.out.println("2:   "+ mySocket.getInetAddress().getHostAddress()+"        "+mySocket.getPort());
					break;
				default:
					System.out.println("An error ocurred");
					
			}
			
			myMessage=reader.readLine(); 	
		}
		
			System.out.println("You: "+myMessage);  
			dout.writeUTF(myMessage);
			dout.flush();
			
	
	} 
		try {
			din.close();  
			mySocket.close();  
			myServerSocket.close();
			if(friendLeft==true){System.out.println("closing... friend left");}
			else{System.out.println("closing...");}
		} catch (Exception e) {
			
		}
		
	
	 
	
}

if (choice.equals("g")){
	System.out.println("Connect to a host by typing: connect ip portNo"); 
	String userInput = in.nextLine();
	String[] splitedInput = userInput.split("\\s+");
	
	String hostIPAddress=splitedInput[1]; 
	int hostPort=Integer.parseInt(splitedInput[2]);
	
	Socket s=null;
	DataInputStream din=null;
	DataOutputStream dout=null;
	BufferedReader br=null;
	try {
		s = new Socket(hostIPAddress,hostPort);
		System.out.println("Connection established with "+ s.getInetAddress().getHostName());
		 
		din = new DataInputStream(s.getInputStream());  
		dout = new DataOutputStream(s.getOutputStream());  
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("you can now start chatting");
		
	} catch (Exception e1) {
		System.out.println("invalid ip or couldn't connect");
	}  
	  
	String str="";
	String str2=""; 
	boolean friendLeft=false;
	
	try {
		while(true){  
		str=br.readLine(); 
		if(str.equals("exit")|| str.contains("terminate")){break;}
		
		while(str.equals("help") || str.equals("myport") || str.equals("myip") 
				|| str.equals("list") ){
			
			switch(str){
				case "help": 
					System.out.println("Command List: help, list, myip, myport, exit, terminate<id> \n");
					break;
				case "myport": 
					System.out.println("your port is: "+ s.getLocalPort());
					break;
				case "myip": 
					System.out.println("your ip is: "+ s.getInetAddress().getHostAddress());
					break;
				case "list": 
					System.out.println("id:  IP Address       Port No. ");
					System.out.println("1:   "+ InetAddress.getLocalHost().getHostAddress()+"        "+s.getLocalPort());
					System.out.println("2:   "+ s.getInetAddress().getHostAddress()+"        "+s.getPort());
					break;
				default:
					System.out.println("An error ocurred");
					
			}
			
			str=br.readLine(); 	
		}
		
		System.out.println("You: "+str);  
		dout.writeUTF(str); 
		
		try {
			str2=din.readUTF();
		} catch (Exception e) {
			friendLeft= true;
			break;
		} 

		if(str2.equals("exit")||str2.contains("terminate")){break;}
		
		dout.flush();  
		System.out.println(s.getInetAddress().getHostAddress()+ " port "+s.getPort()+": "+str2);  
		}
	} catch (Exception e1) {
		
	}  
	try {
		dout.close();  
		s.close();
		if(friendLeft==true){System.out.println("closing... friend left");}
		else{System.out.println("closing...");}
		
	} catch (Exception e) {
		
	}  
	
	
	
}

	 
}

}