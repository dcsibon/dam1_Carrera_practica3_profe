/*
Aquí podéis observar cómo se crea un interfaz "InformacionCarrear" para que la clase "GestionInfoCarrera" pueda cumplir el principio SOLID DIP y depender de una abstracción y no de una clase concreta.

Además, en el Main se realiza la inyección de la dependencia por el constructor.

De esta manera vamos a mantener independientes y desacopladas ambas clases "GestionCarrera" y "GestionInfoCarrera".

Gracias a los cambios realizados, también pasamos a cumplir en este caso el principio SOLID SRP, ya que hemos separado responsabilidades y las clases "GestionCarrera" y "GestionInfoCarrera" ya tienen una única responsabilidad.
*/

/**
 * Representa el resultado final de un vehículo en la carrera, incluyendo su posición final, el kilometraje total recorrido,
 * el número de paradas para repostar, y un historial detallado de todas las acciones realizadas durante la carrera.
 *
 * @property vehiculo El [Vehiculo] al que pertenece este resultado.
 * @property posicion La posición final del vehículo en la carrera, donde una posición menor indica un mejor rendimiento.
 * @property kilometraje El total de kilómetros recorridos por el vehículo durante la carrera.
 * @property paradasRepostaje El número de veces que el vehículo tuvo que repostar combustible durante la carrera.
 * @property historialAcciones Una lista de cadenas que describen las acciones realizadas por el vehículo a lo largo de la carrera, proporcionando un registro detallado de su rendimiento y estrategias.
 */
data class ResultadoCarrera(
    val vehiculo: Vehiculo,
    val posicion: Int,
    val kilometraje: Float,
    val paradasRepostaje: Int,
    val historialAcciones: List<String>
)

/**
 * Interfaz que deben implementar las clases que quieran gestionar la información de la Carrera
 */
interface InformacionCarrera {
    val historialAcciones : MutableMap<String, MutableList<String>>
    val posiciones: MutableMap<String, Float>
    fun <T : Vehiculo> inicializaDatosParticipantes(participantes: List<T>)
    fun actualizarPosiciones(nombreVehiculo: String, kilometraje: Float)
    fun registrarAccion(nombreVehiculo: String, accion: String)
    fun <T : Vehiculo> obtenerResultados(participantes: List<T>): List<ResultadoCarrera>
}

/**
 * Clase que gestiona la información de una carrera.
 */
class GestionInfoCarrera : InformacionCarrera {

    override val historialAcciones = mutableMapOf<String, MutableList<String>>()
    override val posiciones = mutableMapOf<String, Float>()

    /**
     * Inicializa los datos de los participantes en la carrera, preparando su historial de acciones y estableciendo
     * su posición inicial.
     *
     * @param participantes vehículos de tipo T.
     */
    override fun <T : Vehiculo> inicializaDatosParticipantes(participantes: List<T>) {
        participantes.forEach { vehiculo ->
            historialAcciones[vehiculo.nombre] = mutableListOf()
            posiciones[vehiculo.nombre] = 0f
        }
    }

    /**
     * Actualiza la posición de un vehículo en la carrera, sumando la distancia recorrida en el último tramo
     * a su total acumulado.
     *
     * @param nombreVehiculo El nombre del [Vehiculo] cuya posición se actualizará.
     * @param kilometraje La distancia recorrida en el último tramo.
     */
    override fun actualizarPosiciones(nombreVehiculo: String, kilometraje: Float) {
        val kilometrosRecorridos = posiciones[nombreVehiculo] ?: 0f
        posiciones[nombreVehiculo] = kilometrosRecorridos + kilometraje
    }

    /**
     * Registra una acción específica realizada por un vehículo en su historial de acciones durante la carrera.
     *
     * @param nombreVehiculo El nombre del vehículo que realiza la acción.
     * @param accion La descripción de la acción realizada.
     */
    override fun registrarAccion(nombreVehiculo: String, accion: String) {
        historialAcciones[nombreVehiculo]?.add(accion)
    }

    /**
     * Genera y devuelve una lista de los resultados de la carrera, incluyendo la posición final,
     * el kilometraje total recorrido, el número de paradas para repostar, y el historial de acciones
     * para cada vehículo participante.
     *
     * @param participantes vehículos de tipo T.
     * @return Una lista de objetos [ResultadoCarrera] que representan los resultados finales de la carrera.
     */
    override fun <T : Vehiculo> obtenerResultados(participantes: List<T>): List<ResultadoCarrera> {
        val resultados = mutableListOf<ResultadoCarrera>()

        posiciones.toList().sortedByDescending { it.second }.forEachIndexed { posicion, (nombre, kilometraje) ->
            val vehiculo = participantes.find { it.nombre == nombre }
            val paradasRepostaje = historialAcciones[nombre]?.count { it.contains("Repostaje") } ?: 0
            val historial = historialAcciones[nombre] ?: emptyList()

            if (vehiculo != null)
                resultados.add(
                    ResultadoCarrera(
                        vehiculo,
                        posicion + 1,
                        kilometraje.redondear(2),
                        paradasRepostaje,
                        historial
                    )
                )
        }
        return resultados
    }

}