package chat;

import java.io.DataInputStream;
import java.util.concurrent.LinkedBlockingQueue;

public class ReceiveThread extends Thread {

	private String msj;
	private LinkedBlockingQueue<String> outgoingMessages;
	private DataInputStream in;

	public ReceiveThread(LinkedBlockingQueue<String> outgoingMes, DataInputStream dataIn) {
		this.outgoingMessages = outgoingMes;
		this.in = dataIn;
	}

	public void run() {
		try {
			while (true) {
				this.msj = leerRespuesta();
				outgoingMessages.add(msj);
			}
		} catch (Exception e) {
			System.err.println("Error recibiendo el mensaje: \n");
			e.printStackTrace();
		}
	}

	synchronized public String leerRespuesta() {
		try {
			String resp = in.readUTF();
			return resp;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
