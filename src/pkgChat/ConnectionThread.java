package pkgChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.LinkedList;
import java.util.List;

public class ConnectionThread extends Thread {

  // Conexiones
  private int conexionNumero;
  private static List<ConnectionThread> listaClientesConectados = new LinkedList<ConnectionThread>();

  // Socket cliente y servidor asociado a una conexion
  private Socket clienteSocket;
  private ServerSocket serverSocket;

 
  private PrintStream socketPrintStream = null;
  private BufferedReader socketInputStream = null;

 
  public ConnectionThread(ServerSocket server, Socket client) {
    super("ConnectionThread");

    this.conexionNumero = listaClientesConectados.size();
    listaClientesConectados.add(this);

    this.serverSocket = server;
    this.clienteSocket = client;
  }

  
  public void run() {

    // buffer e/s cliente
    try {
      socketPrintStream = new PrintStream(clienteSocket.getOutputStream());
      socketInputStream = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
    } catch (IOException e) {
      e.printStackTrace();
    }

    // Muestra quien se conecto al chat
    
    mensajeATodos(" ", "Conectado " );

    
    String linea;
      try {
      while ((linea = socketInputStream.readLine()) != null) {
        mensajeATodos(": ", linea);
      }
    } catch (IOException e) { }

  }

  //Es un mensaje + cabecera
  public void mensaje(String name, String connector, String message) {
    this.socketPrintStream.println(name + connector + message);
  }

  //Enviar mensajes a todos los clientes que esten conectados
  public void mensajeATodos(String connector, String message) {
    for(ConnectionThread ct : listaClientesConectados) {
      ct.mensaje("Usuario N-" + String.valueOf(this.conexionNumero), connector, message);
    }
  }

}
