
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JOptionPane;

public class BYODCliente {

	private static final String[] protocols = new String[] {"TLSv1.3"};
    private static final String[] cipher_suites = new String[] {"TLS_AES_128_GCM_SHA256"};


	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {
		try {


			SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

			SSLSocket socket = (SSLSocket) socketFactory.createSocket("localhost", 7070);
			
			socket.setEnabledProtocols(protocols);
			socket.setEnabledCipherSuites(cipher_suites);



			// crea PrintWriter para enviar login a servidor
			final PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			// prompt user for user name
			final String userName = JOptionPane.showInputDialog(null, "Enter User Name:");

			// coloca al usuario en el flujo
			output.println(userName);

			// solicita el password
			final String password = JOptionPane.showInputDialog(null, "Enter Password:");

			// coloca el password en el flujo
			output.println(password);


			final String message = JOptionPane.showInputDialog(null, "Enter your Message:");
			output.println(message);
			// envia ambos al servidor
			output.flush();

			// crea BufferedReader para leer respuesta del servidor
			final BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// leemos respuesta del servidor
			final String response = input.readLine();

			// muestra la respuesta al usuario
			JOptionPane.showMessageDialog(null, response);

			// cierra los streams y Socket
			output.close();
			input.close();
			socket.close();

		} catch (final IOException ioException) {
			ioException.printStackTrace();
		} finally {
			System.exit(0);
		}

	}
}
