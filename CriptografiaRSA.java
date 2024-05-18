import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.PBEKeySpec;
import java.util.Scanner;
import java.util.Base64;

/**
 * Esta clase proporciona funcionalidades para generación de claves RSA, firma y verificación de archivos utilizando RSA y AES.
 * Utiliza archivos para almacenar claves públicas y privadas, y para guardar archivos firmados.
 */
public class CriptografiaRSA {

    /**
     * Ruta donde se almacenan las claves (pública y privada) y los archivos firmados.
     */
    private static final String RUTA_CLAVES = "claves/";
    private static final String RUTA_ARCHIVO_FIRMADO = "archivofirmado/";
    private static final String RUTA_ARCHIVO_FIRMAR = "archivofirmar/";
    
    // Tamaño de la clave simétrica, iteraciones para generación de clave y longitud de clave

    private static final int ITERACIONES = 65536;
    private static final int LONGITUD_CLAVE = 256;

    /**
     * Método principal que permite al usuario seleccionar opciones como generar claves, firmar archivos y verificar firmas.
     * @param args Argumentos de línea de comandos (no se utilizan en este programa).
     */
    public static void main(String[] args) {
        // Código principal que maneja las opciones del usuario.
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Selecciona una opción:");
            System.out.println("1) Generar par de claves RSA");
            System.out.println("2) Firmar archivo");
            System.out.println("3) Verificar firma");
            System.out.println("4) Salir");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print("Ingrese la contraseña para cifrar la clave privada: ");
                    String contrasenaCifrado = scanner.nextLine();
                    generarParClaves(contrasenaCifrado);
                    break;
                case "2":
                    System.out.print("Ingrese el nombre del archivo a firmar: ");
                    String archivoAFirmar = scanner.nextLine();
                    firmarArchivo(archivoAFirmar, scanner);
                    break;
                case "3":
                    System.out.print("Ingrese el nombre del archivo original: ");
                    String archivoOriginal = scanner.nextLine();
                    System.out.print("Ingrese el nombre del archivo que contiene la firma: ");
                    String archivoFirma = scanner.nextLine();
                    System.out.print("Ingrese el nombre del archivo de clave pública: ");
                    String archivoClavePublica = scanner.nextLine();
                    verificarFirma(archivoOriginal, archivoFirma, archivoClavePublica);
                    break;
                case "4":
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
                    break;
            }
        }
    }

    /**
     * Genera un par de claves RSA y las guarda en archivos.
     * @param contrasenaCifrado Contraseña para cifrar la clave privada.
     */
    public static void generarParClaves(String contrasenaCifrado) {
        // Código para generar y guardar el par de claves RSA.
        File directorioClaves = new File(RUTA_CLAVES);
        if (!directorioClaves.exists()) {
            directorioClaves.mkdirs();
        }

        try {
            // Genera un par de claves RSA.
            KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");
            generador.initialize(2048);
            KeyPair parClaves = generador.generateKeyPair();

            // Cifra y guarda la clave privada usando AES.
            Cipher cifrador = Cipher.getInstance("AES");
            cifrador.init(Cipher.ENCRYPT_MODE, generarClaveSimetrica(contrasenaCifrado));
            byte[] claveCifrada = cifrador.doFinal(parClaves.getPrivate().getEncoded());
            try (FileOutputStream fos = new FileOutputStream(RUTA_CLAVES + "clave_privada.dat")) {
                fos.write(claveCifrada);
            }

            // Guarda la clave pública en formato DER.
            try (FileOutputStream fos = new FileOutputStream(RUTA_CLAVES + "clave_publica.der")) {
                fos.write(parClaves.getPublic().getEncoded());
            }

            // Guarda la clave pública en formato PEM.
            exportarClavePublicaPEM(parClaves.getPublic(), RUTA_CLAVES + "clave_publica.pem");

            System.out.println("Par de claves RSA generado con éxito.");
        } catch (Exception e) {
            System.err.println("Error al generar el par de claves RSA: " + e.getMessage());
        }
    }

    /**
     * Exporta la clave pública en formato PEM.
     * @param clavePublica Clave pública a exportar.
     * @param rutaArchivo Ruta del archivo donde se guardará la clave.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    private static void exportarClavePublicaPEM(PublicKey clavePublica, String rutaArchivo) throws IOException {
        // Código para exportar la clave pública en formato PEM.
        Base64.Encoder encoder = Base64.getEncoder();
        String clavePEM = "-----BEGIN PUBLIC KEY-----\n" 
                + new String(encoder.encode(clavePublica.getEncoded())) 
                + "\n-----END PUBLIC KEY-----";
        try (FileWriter fw = new FileWriter(rutaArchivo)) {
            fw.write(clavePEM);
        }
    }

    /**
     * Genera una clave simétrica a partir de una contraseña.
     * @param contrasena Contraseña para generar la clave.
     * @return La clave simétrica generada.
     * @throws Exception Si ocurre un error durante la generación de la clave.
     */
    private static SecretKeySpec generarClaveSimetrica(String contrasena) throws Exception {
        // Código para generar una clave simétrica a partir de la contraseña.
        byte[] salt = new byte[16]; // Salt should be generated securely and stored/reused
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec spec = new PBEKeySpec(contrasena.toCharArray(), salt, ITERACIONES, LONGITUD_CLAVE);
        SecretKey secretKey = factory.generateSecret(spec);
        byte[] clave = secretKey.getEncoded();
        return new SecretKeySpec(clave, "AES");
    }

 /**
     * Firma un archivo utilizando RSA y lo guarda en formato binario.
     * @param archivoAFirmar Nombre del archivo a firmar.
     * @param scanner       Objeto Scanner para la entrada de usuario.
     */
    public static void firmarArchivo(String archivoAFirmar, Scanner scanner) {
        try {
            System.out.print("Ingrese el nombre del archivo de clave privada: ");
            String archivoClavePrivada = scanner.nextLine();
            System.out.print("Ingrese la contraseña de la clave privada: ");
            String contrasena = scanner.nextLine();
            PrivateKey clavePrivada = obtenerClavePrivada(archivoClavePrivada, contrasena);

            byte[] contenidoArchivo = Files.readAllBytes(Paths.get(RUTA_ARCHIVO_FIRMAR + archivoAFirmar));
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(contenidoArchivo);

            Signature firma = Signature.getInstance("SHA256withRSA");
            firma.initSign(clavePrivada);
            firma.update(hash);
            byte[] firmaBytes = firma.sign();

            try (FileOutputStream fos = new FileOutputStream(RUTA_ARCHIVO_FIRMADO + archivoAFirmar.split("\\.")[0] + "_firma.bin")) {
                fos.write(firmaBytes);
            }

            System.out.println("Archivo firmado con éxito.");
        } catch (Exception e) {
            System.err.println("Error al firmar el archivo: " + e.getMessage());
        }
    }

    /**
     * Verifica la firma de un archivo utilizando RSA.
     * @param archivoOriginal   Nombre del archivo original.
     * @param archivoFirma      Nombre del archivo que contiene la firma.
     * @param archivoClavePublica Nombre del archivo de clave pública.
     */
    public static void verificarFirma(String archivoOriginal, String archivoFirma, String archivoClavePublica) {
        try {
            PublicKey clavePublica = obtenerClavePublica(archivoClavePublica);

            byte[] contenidoOriginal = Files.readAllBytes(Paths.get(RUTA_ARCHIVO_FIRMAR + archivoOriginal));
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashOriginal = md.digest(contenidoOriginal);

            Signature firma = Signature.getInstance("SHA256withRSA");
            firma.initVerify(clavePublica);
            firma.update(hashOriginal);

            byte[] firmaBytes = Files.readAllBytes(Paths.get(RUTA_ARCHIVO_FIRMADO + archivoFirma));
            boolean verificado = firma.verify(firmaBytes);

            if (verificado) {
                System.out.println("La firma es válida.");
            } else {
                System.out.println("La firma no es válida.");
            }
        } catch (Exception e) {
            System.err.println("Error al verificar la firma: " + e.getMessage());
        }
    }

    /**
     * Obtiene la clave privada a partir de un archivo y una contraseña.
     * @param archivoClavePrivada Nombre del archivo de clave privada.
     * @param contrasena          Contraseña para descifrar la clave privada.
     * @return La clave privada.
     * @throws Exception Si ocurre un error durante el proceso de obtención de la clave privada.
     */
    private static PrivateKey obtenerClavePrivada(String archivoClavePrivada, String contrasena) throws Exception {
        byte[] claveBytes = Files.readAllBytes(Paths.get(RUTA_CLAVES + archivoClavePrivada));
        Cipher cifrador = Cipher.getInstance("AES");
        cifrador.init(Cipher.DECRYPT_MODE, generarClaveSimetrica(contrasena));
        byte[] claveDescifrada = cifrador.doFinal(claveBytes);
        PKCS8EncodedKeySpec clavePrivadaSpec = new PKCS8EncodedKeySpec(claveDescifrada);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(clavePrivadaSpec);
    }

    /**
     * Obtiene la clave pública a partir de un archivo.
     * @param archivoClavePublica Nombre del archivo de clave pública.
     * @return La clave pública.
     * @throws Exception Si ocurre un error durante el proceso de obtención de la clave pública.
     */
    private static PublicKey obtenerClavePublica(String archivoClavePublica) throws Exception {
        byte[] claveBytes = Files.readAllBytes(Paths.get(RUTA_CLAVES + archivoClavePublica));
        X509EncodedKeySpec clavePublicaSpec = new X509EncodedKeySpec(claveBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(clavePublicaSpec);
    }
}