package chat;

import java.io.DataInputStream;

public class ReceiveThreadMesage extends Thread {

	private DataInputStream in;
	private String msj;

	public ReceiveThreadMesage(String m, DataInputStream dataIn) {
		in=dataIn;
		this.msj=m;
	}

	public void run(){
		while(true){
			this.msj=leerRespuesta();
		}
	}
	public String leerRespuesta() {
		try {
			return in.readUTF();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return msj;
	}

}
