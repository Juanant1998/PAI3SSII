
package ssii;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class BYODClienteThreads extends Thread {

	private static final String[]	protocols	= new String[] {
		"TLSv1.3"
	};
	private int						hilo;
    private static final String[] cipher_suites = new String[] {"TLS_AES_128_GCM_SHA256"};



	public BYODClienteThreads(final int hilo) {
		this.hilo = hilo;
	}
	/**
	 * @param args
	 * @throws IOException
	 */
	public void execute() {
		try {

			SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

			SSLSocket socket = (SSLSocket) socketFactory.createSocket("localhost", 7070);

			socket.setEnabledProtocols(BYODClienteThreads.protocols);
			socket.setEnabledCipherSuites(cipher_suites);

			// crea PrintWriter para enviar login a servidor
			final PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			// prompt user for user name
			final String userName = "Rafael";

			// coloca al usuario en el flujo
			output.println(userName);

			// solicita el password
			final String password = "D23icOp.78";

			// coloca el password en el flujo
			output.println(password);

			final String message = "Holaaa";
			output.println(message);
			// envia ambos al servidor
			output.flush();

			// crea BufferedReader para leer respuesta del servidor
			final BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// leemos respuesta del servidor
			final String response = input.readLine();

			// muestra la respuesta al usuario
			System.out.println("Hilo " + this.hilo + ": OK, Respuesta:" + response);

			// cierra los streams y Socket
			output.close();
			input.close();
			socket.close();

		} catch (final IOException ioException) {
			ioException.printStackTrace();
		} finally {
			//System.exit(0);
		}

	}
	@Override
	public void run() {
		this.execute();
	}
}
