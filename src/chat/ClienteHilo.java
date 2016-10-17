package chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

public class ClienteHilo extends Thread {

	private DataInputStream in;
	private Socket sk;
	private Gson gsonRead;

	public ClienteHilo(Socket cliente) throws IOException {
		this.sk = cliente;
		//gsonRead= new Gson();
		this.in = new DataInputStream(sk.getInputStream());
	}

	public void run() {
		while (true) {
			leerRespuesta();
		}
	}

	public void leerRespuesta() {
		try {
			//System.out.println(gsonRead.fromJson(in.readUTF(),String.class) + "\n");
			System.out.println(in.readUTF()+ "\n");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
