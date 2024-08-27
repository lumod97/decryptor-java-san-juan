package DGDecryptor;

public class CryptorSJ {

    public static String descifradoSanJuan(String cadenaADescifrar) throws Exception {
        // PASO 0: CREACION DE VARIABLES
        int maximoIndiceCadena = cadenaADescifrar.length() - 3;
        int[] aCadena = new int[maximoIndiceCadena + 1];
        int sumaA = 0, sumaB = 0;
        int claveA, claveB;
        boolean claveAEsPar;
        int desplazamientoADerecha;
        String cadenaIzquierda = "";
        String cadenaDerecha = "";
        String cadenaFinal = "";

        // PASO 1: OBTENER CLAVES A, B Y LA CADENA SEPARADA
        claveA = Character.getNumericValue(cadenaADescifrar.charAt(maximoIndiceCadena + 1));
        claveB = Character.getNumericValue(cadenaADescifrar.charAt(maximoIndiceCadena + 2));
        cadenaFinal = cadenaADescifrar.substring(0, maximoIndiceCadena + 1);
        

        // PASO 2: VALIDAR SUMA B
        for (char c : cadenaFinal.toCharArray()) {
            sumaB += Character.getNumericValue(c);
        }
        if (claveB != sumaB % 10) {
            throw new Exception("Código no coincide con estructura San Juan.");
        }

        // PASO 3: DESPLAZAR
        desplazamientoADerecha = maximoIndiceCadena - ((maximoIndiceCadena < claveA) ? claveA - maximoIndiceCadena : claveA);
        
        cadenaIzquierda = "";
        cadenaDerecha = "";

        for (int i = 0; i <= maximoIndiceCadena; i++) {
            if (i <= desplazamientoADerecha) {
                cadenaIzquierda += cadenaFinal.charAt(i);
            } else {
                cadenaDerecha += cadenaFinal.charAt(i);
            }
        }

        cadenaFinal = cadenaDerecha + cadenaIzquierda;
        System.out.println(cadenaFinal);

        // PASO 4: INDIZAR CADENA EN ARRAY DE ENTEROS
        for (int i = 0; i <= maximoIndiceCadena; i++) {
            aCadena[i] = Character.getNumericValue(cadenaFinal.charAt(i));
        }

        // PASO 5: DETERMINAR SI LA CLAVE A ES PAR
        claveAEsPar = claveA % 2 == 0;

        // PASO 6: HALLAR EL COMPLEMENTO A DECENA DE CADA DIGITO Y REEMPLAZAR LOS DIGITOS ORIGINALES POR LOS COMPLEMENTOS
        for (int i = 0; i <= maximoIndiceCadena; i++) {
            if (aCadena[i] != 0) {
                aCadena[i] = 10 - aCadena[i];
            }
        }

        // PASO 7: OPERAR SOBRE LOS DIGITOS PARES SI LA CLAVE A ES PAR O VICEVERSA
        for (int i = 1; i <= maximoIndiceCadena; i++) {
            if ((claveAEsPar && i % 2 == 0) || (!claveAEsPar && i % 2 != 0)) {
                if (aCadena[i] < claveA) {
                    aCadena[i] += 10;
                }
                aCadena[i] -= claveA;
            }
        }

        // PASO 8: VALIDAR CLAVE A CON SUMA A
        for (int c : aCadena) {
            sumaA += c;
        }

        if (claveA != sumaA % 10) {
            throw new Exception("Código no coincide con estructura San Juan.");
        }

        // PASO 9: CONVERTIR RESULTADO A CADENA
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < aCadena.length; i++) {
            resultado.append(aCadena[i]);
        }

        return resultado.toString();
    }
}