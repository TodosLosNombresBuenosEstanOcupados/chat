package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class HiloServidor extends Thread {

	private DataInputStream in;
	private DataOutputStream out;
	private ArrayList<Socket> users;
	private SendThread sender;
	private ReceiveThread receiver;
	private Socket sk;
	private LinkedBlockingQueue<String> outgoingMessages;

	public HiloServidor(Socket cliente, ArrayList<Socket> usuarios, int id) {
		setUser(usuarios);
		this.sk = cliente;
		try {
			in = new DataInputStream(sk.getInputStream());
			out = new DataOutputStream(sk.getOutputStream());
			outgoingMessages = new LinkedBlockingQueue<String>();
			sender = new SendThread(outgoingMessages,out,id,users);
			receiver = new ReceiveThread(outgoingMessages,in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setUser(ArrayList<Socket> usuarios) {
		this.users = usuarios;
	}

	public void run() {
		sender.start();
		receiver.start();
	}
}
