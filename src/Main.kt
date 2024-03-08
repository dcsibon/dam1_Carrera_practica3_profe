
import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * Extiende la clase [Float] para permitir el redondeo del número a un número específico de posiciones decimales.
 *
 * @param posiciones El número de posiciones decimales a las que se redondeará el valor.
 * @return Un [Float] redondeado al número de posiciones decimales especificadas.
 */
fun Float.redondear(posiciones: Int): Float {
    val factor = 10.0.pow(posiciones.toDouble()).toFloat()
    return (this * factor).roundToInt() / factor
}

/**
 * Extiende la clase String para capitalizar las palabras de cualquier cadena de caracteres.
 * También elimina la secuencia de espacios al principio y final de la misma y delimita las palabras de la cadena por un
 * solo espacio.
 *
 * return Un [String] transformado con sus palabras capitalizadas y un solo espacio entre ellas.
 */
fun String.capitalizar(): String {
    // Elimina los espacios al principio y final de la cadena y reemplaza cualquier secuencia de espacios por uno solo.
    // Las palabras resultantes se asignan a una lista.
    val listaPalabras = this.trim().replace("\\s+".toRegex(), " ").split(" ")
    // Retorna la misma cadena de caracteres con las palabras capitalizadas y separadas por un solo espacio.
    return listaPalabras.joinToString(" ") { palabra -> palabra.lowercase().replaceFirstChar { it.uppercase() } }
}

/**
 * Punto de entrada del programa. Crea una lista de vehículos aleatorios y una carrera, e inicia la carrera mostrando
 * los resultados al finalizar.
 */
fun main() {

    /*--------------------------------------------------------------------------------------------*/
    /*SOLUCIÓN DE LA PARTE 1 DE LA PRÁCTICA + REFACTORIZACIÓN PARA CUMPLIR DIP + USO DE GENÉRICOS */
    /*--------------------------------------------------------------------------------------------*/

    /*
    En el fichero de Kotlin GestionInfoCarrera se explica cómo se ha modificado el código original de la clase Carrera en las clases "GestionCarrera", "GestionInfoCarrera" y el interface "InformacionCarrera" para cumplir dos principios SOLID.
    */

    /*
    En el fichero de Kotlin FabricaVehiculos se aplica el uso de genéricos y se explica el modificador out utilizado en la declaración de los tipos de datos Genéricos de la función generarVehiculo()
    También se declaran tipos de datos genéricos en el interface "InformacionCarrera" y la clase que lo implementa "GestionInfoCarrera".
    */

    //Creamos el listado de las fábricas de construcción de vehículos
    //De manera que agregamos las instancias de objetos que implementan el interface VehiculoFactory...
    val factories = listOf(AutomovilFactory(), MotocicletaFactory(), CamionFactory(), QuadFactory())

    //Lista de los vehículos vacía, que se crearán y agregarán de forma aleatoria a continuación...
    val vehiculos = mutableListOf<Vehiculo>()

    val numeroParticipantes = GestorConsola.pedirNumero("Introduce el número de participantes: ")

    repeat(numeroParticipantes) { index ->
        val vehiculo = GestorConsola.crearVehiculoAleatorio(factories, index + 1)
        vehiculos.add(vehiculo)
        println("\t${vehiculo.obtenerInformacion()}")
    }

    val infoCarrera = GestionInfoCarrera()
    infoCarrera.inicializaDatosParticipantes(vehiculos)

    //Inyección de dependencias (infoCarrera) en el constructor de GestionCarrera...
    val carrera = GestionCarrera("Gran Carrera de Filigranas", 1000f, vehiculos, infoCarrera)

    println("\n*** ${carrera.nombreCarrera} ***\n")
    carrera.iniciarCarrera()

    val resultados = infoCarrera.obtenerResultados(vehiculos)

    println("* Clasificación:\n")
    resultados.forEach { println("${it.posicion} -> ${it.vehiculo.nombre} (%.2f kms)".format(it.vehiculo.kilometrosActuales)) }

    println("\n" + resultados.joinToString("\n") { it.toString() })

    println("\n* Historial Detallado:\n")
    resultados.forEach { println("${it.posicion} -> ${it.vehiculo.nombre}\n${it.historialAcciones.joinToString("\n")}\n") }
}
