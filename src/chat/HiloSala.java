package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;

public class HiloSala extends Thread {

	private DataInputStream in;
	private DataOutputStream out;
	private Socket sk;
	private Gson gson;

	public HiloSala(Socket clientSocket, ArrayList<Socket> sala1, ArrayList<Socket> sala2, ArrayList<Socket> sala3, int id) {
		this.sk = clientSocket;
		this.gson= new Gson();
		String salasDisp = "Ingrese el numero de sala a la cual conectarse 1 - 2 - 3:";
		String resp = "";
		try {
			in = new DataInputStream(sk.getInputStream());
			out = new DataOutputStream(sk.getOutputStream());

			out.writeUTF(gson.toJson(salasDisp));
			resp = gson.fromJson(in.readUTF(),String.class);

			if (resp.equals("1")) {
				sala1.add(clientSocket);
				HiloServidor hilo = new HiloServidor(clientSocket, sala1, id);
				hilo.start();
			}
			if (resp.equals("2")) {
				sala2.add(clientSocket);
				HiloServidor hilo = new HiloServidor(clientSocket, sala2, id);
				hilo.start();
			}
			if (resp.equals("3")) {
				sala3.add(clientSocket);
				HiloServidor hilo = new HiloServidor(clientSocket, sala3, id);
				hilo.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
