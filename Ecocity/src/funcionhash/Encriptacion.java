package funcionhash;

public class Encriptacion {

    public static String hashear(String texto) {
        int hash = 0;
        for (char c : texto.toCharArray()) {
            hash = hash + c; // Convierte el carácter en valor ASCII y suma los valores
        }
        return String.valueOf(hash);
    }

    public static boolean verificar(String textoIngresado, String hashAlmacenado) {
        return hashear(textoIngresado).equals(hashAlmacenado);
    }
}