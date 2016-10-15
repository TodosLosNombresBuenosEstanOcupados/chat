package pkgChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;


public class StreamSwapper extends Thread {

  
  private InputStream inputStream;

  
  private OutputStream outputStream;

  public StreamSwapper(InputStream input, OutputStream output) throws IOException {
    this.inputStream = input;
    this.outputStream = output;
  }


  public void run() {

    //buffer de entrada
    BufferedReader bufferEntrada = new BufferedReader(new InputStreamReader(inputStream));

    // buffer de salida
    PrintWriter bufferSalida = new PrintWriter(outputStream, true);

    // Buffer para leer cada linea y escribirla en buffer de salida
    try {
      String linea;
      while ((linea = bufferEntrada.readLine()) != null) {
        bufferSalida.println(linea);
      }

      bufferEntrada.close();
      bufferSalida.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}