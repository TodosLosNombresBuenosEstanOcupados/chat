package chat;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Servidor {

	private ServerSocket socket;
	private int puerto;
	private ArrayList<Socket> sala1;
	private ArrayList<Socket> sala2;
	private ArrayList<Socket> sala3;
	private int id=1;

	public Servidor() throws FileNotFoundException {
		sala1 = new ArrayList<Socket>();
		sala2 = new ArrayList<Socket>();
		sala3 = new ArrayList<Socket>();
		leerConfig();
		try {
			socket = new ServerSocket(this.puerto);
			while(true){
				Socket clientSocket = socket.accept();
				HiloSala sala = new HiloSala(clientSocket,sala1,sala2,sala3,id);
				sala.start();
				id++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void leerConfig() throws FileNotFoundException {
		String linea;
		String[] splitLine;
		Scanner sc = new Scanner(new File("ServerApp.config"));
		linea =sc.nextLine();
		splitLine=linea.split(":");
		this.puerto=Integer.parseInt(splitLine[1]);
	}

	public static void main(String[] args) throws FileNotFoundException {
		new Servidor();
	}
}

