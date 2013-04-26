package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MockServer {

	private ServerSocket _socket;
	private ObjectInputStream _objectIn;
	private ObjectOutputStream _objectOut;
	
	public MockServer(){
		try {
			_socket = new ServerSocket(8888);
			Socket client = _socket.accept();
			_objectIn = new ObjectInputStream(client.getInputStream());
			_objectOut = new ObjectOutputStream(client.getOutputStream());
			
			while(true){
				Request request = (Request) _objectIn.readObject();
				System.out.println("recieved request type: " + request.getType());
				RequestReturn toReturn = new RequestReturn(1);
				toReturn.setCorrect(false);
			}
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
