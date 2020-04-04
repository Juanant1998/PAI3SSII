# PAI3SSII
Repositorio para el PAI 3 de SSII

Para ejecutar correctamente las clases de cliente y servidor, es necesario hacerlo siguiendo las siguientes instrucciones:

PASO 1. Crear un KeyStore, con el siguiente comando: Keytool –genkey –keystore c:\SSLStore –alias SSLCertificate (Ejecutar desde una CMD con permisos de administrador, o no podremos guardarlo directamente en C. Además, escoger una única contraseña para los dos apartados que se nos preguntan, y recordarla posteriormente porque nos hará falta).

PASO 2: Cargar nuestras clases cliente y servidor, que tenemos disponibles en el repositorio.

PASO 3: Ejecutar los siguientes comandos dentro de la carpeta donde tenemos nuestras clases java de cliente y servidor:

* javac BYODServer.java
* javac BYODCliente.java

PASO 4: Ejecutar el servidor y el cliente desde sendas CMD con permisos de administrador con los siguientes comandos (NOTA: En PASSWORD, debemos poner la contraseña que escogimos al crear nuestro KeyStore:

SERVIDOR: java –Djavax.net.ssl.keyStore=C:\SSLStore –Djavax.net.ssl.keyStorePassword=PASSWORD BYODServer
CLIENTE: java –Djavax.net.ssl.trustStore=C:\SSLStore –Djavax.net.ssl.trustStorePassword=PASSWORD BYODCliente

Con esto ya habremos ejecutado el cliente y el servidor y podremos probar la funcionalidad.