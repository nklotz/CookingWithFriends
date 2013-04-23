package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {

	private ThreadPoolExecutor _taskPool;
	private ClientPool _clients;
	private Boolean _running; 
	private ServerSocket _socket;
	
	public Server(int port) throws IOException {
		if (port <= 1024) {
			System.err.println("ERROR: Ports under 1024 are reserved!");
			return;
		}
		System.out.println("creating server");

		/* TODO: BOOT UP DATA BASE */
	
        try {
            _socket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port + ".");
            System.exit(1);
        }
           
		_taskPool = new ThreadPoolExecutor(64, 64, 1, TimeUnit.MINUTES,new ArrayBlockingQueue<Runnable>(64, true),new ThreadPoolExecutor.CallerRunsPolicy());
		_clients = new ClientPool();
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
	            ClientHandler thread = new ClientHandler(_clients, clientSocket, _taskPool);
	    		_clients.add(thread);
	            thread.start();

	        } catch (IOException e) {
	            System.err.println("ERROR: Accept failed.");
	            try {
					kill();
				} catch (IOException e1) {
					System.out.println("ERROR: IO Exception in server.kill()");
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
	
}
