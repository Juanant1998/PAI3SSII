
package ssii;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.TreeMap;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class BYODServerThreads extends Thread {

	private static final String		CORRECT_USER_NAME	= "Rafael";
	private static final String		CORRECT_PASSWORD	= "D23icOp.78";

	private static final String[]	protocols			= new String[] {
		"TLSv1.3"
	};
    private static final String[] cipher_suites = new String[] {"TLS_AES_128_GCM_SHA256"};



	public BYODServerThreads() {

	}
	/**
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	
	public static TreeMap<String, String> readFromFile(final String ruta) throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileReader(ruta));

		TreeMap<String, String> map = new TreeMap<String, String>();

		while (scanner.hasNextLine()) {

			String lineToProcess = scanner.nextLine();

			String[] columns = lineToProcess.split(",");
			map.put(columns[0], columns[1]);
		}

		scanner.close();
		return map;
	}

	public static void saveMessage(final String ruta, final String mensaje, String usuario){
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(ruta, true)))) {
			out.println("\n" + usuario + " - " + mensaje);
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	
	public void execute() throws IOException {
		// espera conexiones del cliente y comprueba login
				SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

				SSLServerSocket serverSocket = (SSLServerSocket) socketFactory.createServerSocket(7070);
				serverSocket.setEnabledProtocols(protocols);
				serverSocket.setEnabledCipherSuites(cipher_suites);
				
				TreeMap<String, String> passMap = readFromFile("passMap.txt");


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

						String savedPass = passMap.get(userName);
						if (password.equals(savedPass)) {
							output.println("Bienvenido, " + userName + ". Tu mensaje ha sido guardado");
							saveMessage("storedMessages.txt", message, userName);
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
	@Override
	public void run() {
		try {
			this.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
