package chat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.Gson;

public class Cliente {
	private Socket cliente;
	private static DataOutputStream out;
	private DataInputStream in;
	private int puerto;
	private String ip;
	private Gson gson;

	public Cliente() {
		try {
			leerConfig();
			this.cliente = new Socket(this.ip, this.puerto);
			out = new DataOutputStream(cliente.getOutputStream());
			//gson= new Gson();
			new ClienteHilo(cliente).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void leerConfig() throws FileNotFoundException {
		String linea;
		String[] splitLine;
		Scanner sc = new Scanner(new File("app.config"));
		linea = sc.nextLine();
		splitLine = linea.split(":");
		this.ip = splitLine[1];

		linea = sc.nextLine();
		splitLine = linea.split(":");
		this.puerto = Integer.parseInt(splitLine[1]);
	}

	public void enviarMensaje(String msj) {
		try {
			//out.writeUTF(gson.toJson(msj));
			out.writeUTF(msj);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		Cliente c = new Cliente();
		boolean conect = true;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			while (conect) {
				String input = br.readLine();
				c.enviarMensaje(input);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
