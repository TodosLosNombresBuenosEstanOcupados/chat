package pkgChat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {

  
  private Socket serverSocket;

  // Hilo de lectura server
  private StreamSwapper serverReader;

  // Hilo de escritura
  private StreamSwapper serverWriter;

 
  public Client(String serverName, int portNumber) {

   
    try {
      serverSocket = new Socket(serverName, portNumber);
      
      
      // Iniciar hilo de lectura hacia el server
      serverReader = new StreamSwapper(serverSocket.getInputStream(), System.out);
      serverReader.start();

      //Iniciar hilo de escrtura hacia el server
      serverWriter = new StreamSwapper(System.in, serverSocket.getOutputStream());
      serverWriter.start();

     
      boolean finished = false;
      while(!finished);

      
      serverSocket.close();

    } catch (UnknownHostException e) {
      System.err.println("Server no encontrado.");
      System.exit(-1);

    } catch (IOException e) {
      System.err.println("Error en algún buffer I/O.");
      System.exit(-1);
    }

  }

  
  public static void main(String[] args) {
    
    Client cliente = new Client("localhost",10011);
  }

}