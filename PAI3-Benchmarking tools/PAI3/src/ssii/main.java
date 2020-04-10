
package ssii;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class main {

	public static void main(final String[] args) throws InterruptedException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException, CertificateException, IOException {
		System.setProperty("javax.net.ssl.keyStoreType", "jks");
		System.setProperty("javax.net.ssl.trustStoreType", "jks");

		//System.setProperty("javax.net.debug", "SSL");

		//RUTA ABSOLUTA HACIA EL KEYSTORE (y su contraseña)
		System.setProperty("javax.net.ssl.keyStore", "C:\\Users\\juana\\Desktop\\Universidad\\SSII\\PAI3\\keystore");
		System.setProperty("javax.net.ssl.keyStorePassword", "passphrase");

		//RUTA ABSOLUTA HASTA EL TRUSTSORE (y su contraseña)
		System.setProperty("javax.net.ssl.trustStore", "C:\\Users\\juana\\Desktop\\Universidad\\SSII\\PAI3\\truststore");
		System.setProperty("javax.net.ssl.trustStorePassword", "passphrase");

		BYODServerThreads server = new BYODServerThreads();
		server.start();
		Thread.sleep(2000);
		for (int i = 0; i < 1000; i++) {
			BYODClienteThreads cliente = new BYODClienteThreads(i);
			cliente.start();
			Thread.sleep(10);
		}

	}

}
