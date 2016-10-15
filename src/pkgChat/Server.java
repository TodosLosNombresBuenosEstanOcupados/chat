package pkgChat;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

  
  private ServerSocket serverSocket;
  private List<ConnectionThread> servidorConexiones;

  //un socket para escuchar conexiones y un hilo por cada conexion
  public Server(Integer puerto) {

    
    try {
      serverSocket = new ServerSocket(puerto);
      servidorConexiones = new ArrayList<ConnectionThread>();
      printServerInformation();

    } catch (IOException e) {
      System.out.println("Error en el puerto elegido" + String.valueOf(puerto));
      System.exit(-1);
    }

    // Start listening for connections on that port.
    boolean finishedListening = false;
    while(!finishedListening) {

      //Espero peticiones de los socketl cliente
      try {
        Socket conexionCliente = serverSocket.accept();
        if(conexionCliente.isConnected()) {
          ConnectionThread connectionThread = new ConnectionThread(serverSocket, conexionCliente);
           connectionThread.start();
          servidorConexiones.add(connectionThread);
          
        }

      } catch (Exception e) {
        System.out.println("Error de conexion: " + String.valueOf(puerto));
        System.exit(-1);
      }

    }

  }

  
  private void printServerInformation() {
    String estado = "Ejecutando servidor " +
      String.valueOf(serverSocket.getInetAddress().getHostName()) +
      " en el puerto " + String.valueOf(serverSocket.getLocalPort());
    System.out.println(estado);
  }

  
  public static void main(String[] args) {
    int puerto = 10016;
    
    Server server = new Server(puerto);
  }

}