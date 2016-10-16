package chat;

import java.io.DataInputStream;

public class ReceiveThreadMesage extends Thread {

	private DataInputStream in;

	public ReceiveThreadMesage( DataInputStream dataIn) {
		in=dataIn;
	}

	public void run(){
		while(true){
			leerRespuesta();
		}
	}
	public void leerRespuesta() {
		try {
			System.out.println(in.readUTF());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
