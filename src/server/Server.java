package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.commons.codec.binary.Base64;

import API.Wrapper;
import API.WrapperDictionary;
import API.YummlyAPIWrapper;
import API.YummlyWrapperDictionary;
import Database.DBHelper;

//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class Server {

	private ThreadPoolExecutor _taskPool;
	private ClientPool _clients;
	private Boolean _running; 
	private ServerSocket _socket;
	private DBHelper _helper;
	private KitchenPool _activeKitchens;
	private WrapperDictionary _apiDictionary;
	private AutocorrectEngines _autocorrect;
	
	public Server(int port) throws IOException {
		if (port <= 1024) {
			System.err.println("ERROR: Ports under 1024 are reserved!");
			return;
		}

		_helper = new DBHelper();
		_apiDictionary = new YummlyWrapperDictionary();
		
		//Has all autocorrect suggestion engines.
		_autocorrect = new AutocorrectEngines(_apiDictionary);
		
		
		//TODO: package trie and lists to client handler
		
	
		
        try {
            _socket = new ServerSocket(port);
            
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port + ".");
            System.exit(1);
        }
           
		_taskPool = new ThreadPoolExecutor(64, 64, 1, TimeUnit.MINUTES,new ArrayBlockingQueue<Runnable>(64, true),new ThreadPoolExecutor.CallerRunsPolicy());
		_clients = new ClientPool();
		_activeKitchens = new KitchenPool(_helper, _clients);
    }

	/**
	 * Wait for and handle connections indefinitely.
	 */
	public void run() {
		_running = true;

		while(_running){	
			Socket clientSocket = null;
	        try {
	            clientSocket =_socket.accept();
	            ClientHandler thread = new ClientHandler(_clients, clientSocket, _taskPool, _activeKitchens, _helper, _autocorrect);
	    		_clients.add(thread);
	            thread.start();

	        } catch (IOException e) {
	            System.err.println("ERROR: Accept failed.");
	            try {
					kill();
				} catch (IOException e1) {
					System.out.println("ERROR: IO Exception in server.kill().");
				}
	            System.exit(1);
	        }
		}
	}
	
	/**
	 * Stop waiting for connections, close all connected clients, and close
	 * this server's ServerSocket.
	 * 
	 * @throws IOException if any socket is invalid.
	 */
	public void kill() throws IOException {
		_running = false;
		_clients.killall();
		_socket.close();
	}
	
	/**
	 * Serializes an object into into a string for storage
	 */
    private static String toString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        Base64 encoder = new Base64();
        return new String(encoder.encode(baos.toByteArray()));
    }
}
