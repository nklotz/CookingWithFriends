package server;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * A group ofClientHandlers representing all active windows.
 * Modified version of clientPool from lab.
 */
public class ClientPool {
	private LinkedList<ClientHandler> _clients;
	private HashMap<String, ClientHandler> _ids;
	
	/**
	 * Initialize a new {@link ClientPool}.
	 */
	public ClientPool() {
		_clients = new LinkedList<ClientHandler>();
		_ids = new HashMap<String, ClientHandler>();
	}
	
	/**
	 * Add a new client to the chat room.
	 * 
	 * @param client to add
	 */
	public synchronized void add(ClientHandler client) {
		_clients.add(client);
	}
	
	/**
	 * Remove a client from the pool. Only do this if you intend to clean up
	 * that client later.
	 * 
	 * @param client to remove
	 * @return true if the client was removed, false if they were not there.
	 */
	public synchronized boolean remove(ClientHandler client) {
		_ids.remove(client.getID());
		return _clients.remove(client);
	}
	
	/**
	 * Send a RequestReturn to all clients in the pool.
	 */
	public synchronized void broadcastAll(RequestReturn toReturn) {
		for (ClientHandler client : _clients) {
			client.send(toReturn);
		}
	}
	
	/**
	 * Send a RequestReturn to a list clients in the pool.
	 */
	public synchronized void broadcastList(HashSet<String> clients, RequestReturn toReturn) {
		for (String c : clients) {
			_ids.get(c).send(toReturn);
		}
	}
	
	/**
	 * Close all ClientHandlers and empty the pool
	 */
	public synchronized void killall() {

		for (ClientHandler client : _clients) {
			try {
				client.kill();
			} catch (IOException e) {
				// There's nothing we can do here.
			}
		}

		_clients.clear();
	}
	
	public void addID(String id, ClientHandler ch){
		_ids.put(id, ch);
	}
}
