package chat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	private Socket cliente;
	private DataInputStream in;
	private String msj;
	private static DataOutputStream out;
	private int puerto;
	private ReceiveThreadMesage r;
	private String ip;

	public Cliente() {
		try {
			leerConfig();
			cliente = new Socket(this.ip, this.puerto);
			in = new DataInputStream(cliente.getInputStream());
			out = new DataOutputStream(cliente.getOutputStream());
			r = new ReceiveThreadMesage(in);
			r.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void leerConfig() throws FileNotFoundException {
		String linea;
		String[] splitLine;
		Scanner sc = new Scanner(new File("app.config"));
		linea =sc.nextLine();
		splitLine=linea.split(":");
		this.ip=splitLine[1];

		linea =sc.nextLine();
		splitLine=linea.split(":");
		this.puerto=Integer.parseInt(splitLine[1]);
	}

	public static void enviarMensaje(String msj) {
		try {
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
				enviarMensaje(input);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
