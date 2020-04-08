import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLServerSocket;


public class BYODServer {

	private static final String CORRECT_USER_NAME = "Rafael";
	private static final String CORRECT_PASSWORD = "D23icOp.78";

	private static final String[] protocols = new String[] {"TLSv1.3"};

	

	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(final String[] args) throws IOException, InterruptedException {
		// espera conexiones del cliente y comprueba login
		SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

		SSLServerSocket serverSocket = (SSLServerSocket) socketFactory.createServerSocket(7070);
		serverSocket.setEnabledProtocols(protocols);

		while (true) {

			try {
				System.err.println("Esperando conexiones..");

				final Socket socket = serverSocket.accept();

				// abre BufferedReader para leer datos del cliente
				final BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				// abre PrintWriter para enviar datos al cliente
				final PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
				final String userName = input.readLine();
				final String password = input.readLine();
				final String message = input.readLine();
				if (userName.equals(CORRECT_USER_NAME) && password.equals(CORRECT_PASSWORD)) {
					output.println("Bienvenido, " + userName + ". Your message was " + message);
				} else {
					output.println("Login Fallido.");
				}

				output.close();
				input.close();
				socket.close();

			} catch (final IOException ioException) {
				ioException.printStackTrace();
			}
		} // end while
	}
	//serverSocket.close();
}
