package chat;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.Gson;

public class SendThread extends Thread {

	private LinkedBlockingQueue<String> outgoingMessages;
	private DataOutputStream out;
	private ArrayList<Socket> users;
	private Socket sk;
	private int id;
	private Gson gson;

	public SendThread(LinkedBlockingQueue<String> outgoingMess, DataOutputStream dataOut, int idUser,
			ArrayList<Socket> lstUsers) {
		this.outgoingMessages=outgoingMess;
		this.out=dataOut;
		this.id=idUser;
		this.users=lstUsers;
		this.gson=new Gson();
	}

	public void run() {
		String msj="";
		try {
			while (true) {
				for (int i = 0; i < users.size(); i++) {
					out = new DataOutputStream(users.get(i).getOutputStream());
					msj=id+": "+outgoingMessages.take();
					out.writeUTF(gson.toJson(msj));
					out.flush();
				}
			}
		} catch (Exception e) {
			for (int i = 0; i < users.size(); i++) {
				if (sk == users.get(i)) {
					users.remove(sk);
				}
			}
			System.err.println("Error recibiendo el mensaje: \n");
			e.printStackTrace();
		}
	}
}
