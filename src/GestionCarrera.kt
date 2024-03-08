import kotlin.math.ceil

/**
 * Representa una carrera que incluye múltiples vehículos como participantes. La carrera tiene un nombre, una distancia total
 * a recorrer, y maneja el estado y el avance de cada vehículo participante.
 *
 * @property nombreCarrera El nombre de la carrera para identificación.
 * @property distanciaTotal La distancia total que los vehículos deben recorrer para completar la carrera.
 * @constructor Inicializa una carrera con una lista de vehículos participantes y valida la distancia mínima requerida.
 */
class GestionCarrera<T : Vehiculo>(
    val nombreCarrera: String,
    private val distanciaTotal: Float,
    private val participantes: List<T> = listOf(),
    private val infoCarrera: InformacionCarrera
) {
    private var estadoCarrera = false // Indica si la carrera está en curso o ha finalizado.

    init {
        require(distanciaTotal >= 1000) { "La distancia total de la carrera debe ser al menos 1000 km." }
    }

    companion object {
        private const val KM_PARA_FILIGRANA = 20f // Cada 20 km, se realiza una filigrana.
    }

    /**
     * Proporciona una representación en cadena de texto de la instancia de la carrera, incluyendo detalles clave como
     * el nombre de la carrera, la distancia total a recorrer, la lista de participantes, el estado actual de la carrera
     * (en curso o finalizada), el historial de acciones realizadas por los vehículos durante la carrera y las posiciones
     * actuales de los participantes.
     *
     * @return Una cadena de texto que describe los atributos principales de la carrera, incluyendo el nombre,
     * distancia total, participantes, estado actual, historial de acciones y posiciones de los vehículos participantes.
     */
    override fun toString(): String {
        return "NombreCarrera: $nombreCarrera, DistanciaTotal: $distanciaTotal, Participantes: $participantes, EstadoCarrera: $estadoCarrera, HistorialAcciones: ${infoCarrera.historialAcciones}, Posiciones: ${infoCarrera.posiciones}." }

    /**
     * Inicia el proceso de la carrera, haciendo que cada vehículo avance de forma aleatoria hasta que un vehículo
     * alcanza o supera la distancia total de la carrera, determinando así el ganador.
     */
    fun iniciarCarrera() {
        println("¡Comienza la carrera!")

        estadoCarrera = true // Indica que la carrera está en curso.
        while (estadoCarrera) {

            Thread.sleep(100)
            print(".")

            val vehiculoSeleccionado = seleccionaVehiculoQueAvanzara()
            avanzarVehiculo(vehiculoSeleccionado)

            val vehiculoGanador = determinarGanador()
            if (vehiculoGanador != null) {
                estadoCarrera = false
                println("\n¡Carrera finalizada!")
                println("\n¡¡¡ENHORABUENA ${vehiculoGanador.nombre}!!!\n")
            }

        }
    }

    /**
     * Selecciona aleatoriamente un vehículo participante para avanzar en la carrera. Este método se utiliza dentro
     * del proceso de la carrera para decidir qué vehículo realiza el próximo movimiento.
     *
     * @return El [Vehiculo] seleccionado para avanzar.
     */
    private fun seleccionaVehiculoQueAvanzara() = participantes.random()

    /**
     * Calcula el número de tramos o segmentos en los que se divide la distancia que un vehículo intenta recorrer.
     * Esto se utiliza para simular el avance por etapas de los vehículos en la carrera.
     *
     * @param distancia La distancia total a recorrer en este turno por el vehículo.
     * @return El número de tramos como [Int] en los que se divide la distancia.
     */
    private fun obtenerNumeroDeTramos(distancia: Float) = ceil(distancia.toDouble() / KM_PARA_FILIGRANA).toInt()

    /**
     * Determina la distancia que un vehículo intentará recorrer en su próximo turno, asegurando que no exceda
     * la distancia total de la carrera.
     *
     * @param kilometrosRecorridos Los kilómetros ya recorridos por el vehículo.
     * @return La distancia a recorrer en el siguiente turno.
     */
    private fun obtenerDistanciaARecorrer(kilometrosRecorridos: Float) : Float {
        val distanciaAleatoria = (1000..20000).random().toFloat() / 100

        // Comprobar que no nos vamos a pasar de la distancia total a recorrer en la carrera
        // Asegurarnos que va a recorrer los km exactos para llegar a la meta
        return if (distanciaAleatoria + kilometrosRecorridos > this.distanciaTotal) {
            this.distanciaTotal - kilometrosRecorridos
        } else {
            distanciaAleatoria
        }
    }

    /**
     * Avanza un vehículo a lo largo de la carrera, dividiendo su avance en tramos y realizando las acciones
     * correspondientes en cada tramo, como realizar filigranas o repostar combustible.
     *
     * @param vehiculo El [Vehiculo] que avanzará en la carrera.
     */
    private fun avanzarVehiculo(vehiculo: Vehiculo) {
        val distanciaTotalEnAvance = obtenerDistanciaARecorrer(vehiculo.kilometrosActuales)
        val numeroDeTramos = obtenerNumeroDeTramos(distanciaTotalEnAvance) // Rompemos el recorrido en tramos de 20 km.

        infoCarrera.registrarAccion(
            vehiculo.nombre,
            "Inicia viaje: A recorrer %.2f kms (%.2f kms y %.2f L actuales)".format(distanciaTotalEnAvance, vehiculo.kilometrosActuales, vehiculo.combustibleActual)
        )

        var distanciaRestanteEnAvance = distanciaTotalEnAvance
        repeat(numeroDeTramos) { //Tramos de KM_PARA_FILIGRANA km
            val distanciaDeTramo = minOf(KM_PARA_FILIGRANA, distanciaRestanteEnAvance)

            avanzarTramo(vehiculo, distanciaDeTramo)
            distanciaRestanteEnAvance -= distanciaDeTramo
            repeat(2) { realizarFiligrana(vehiculo) }
        }

        infoCarrera.registrarAccion(
            vehiculo.nombre,
            "Finaliza viaje: Total Recorrido %.2f kms (%.2f kms y %.2f L actuales)".format(distanciaTotalEnAvance, vehiculo.kilometrosActuales, vehiculo.combustibleActual)
        )

        infoCarrera.actualizarPosiciones(vehiculo.nombre, distanciaTotalEnAvance)
    }

    /**
     * Avanza un vehículo a través de un tramo específico de la carrera, ajustando su combustible y kilometraje según
     * la distancia recorrida.
     * Realiza las operaciones necesarias para repostar si el combustible no es suficiente para completar el tramo.
     *
     * @param vehiculo El [Vehiculo] que avanza.
     * @param distanciaEnTramo La distancia del tramo a recorrer por el vehículo.
     */
    private fun avanzarTramo(vehiculo: Vehiculo, distanciaEnTramo: Float) {
        var distanciaRestante = vehiculo.realizaViaje(distanciaEnTramo)
        infoCarrera.registrarAccion(
            vehiculo.nombre,
            "Avance tramo: Recorrido %.2f kms".format(distanciaEnTramo - distanciaRestante)
        )

        // Si le queda alguna distancia por recorrer debe repostar
        while (distanciaRestante > 0) {
            val repostado = vehiculo.repostar() // Llenamos el tanque
            infoCarrera.registrarAccion(
                vehiculo.nombre,
                "Repostaje tramo: %.2f L".format(repostado)
            )

            // Necesitamos de nuevo una distancia para después compararla con la distanciaRestante que devuelve realizarViaje()
            val distancia = distanciaRestante
            distanciaRestante = vehiculo.realizaViaje(distancia)
            infoCarrera.registrarAccion(
                vehiculo.nombre,
                "Avance tramo: Recorrido %.2f kms".format(distancia - distanciaRestante)
            )
        }
    }

    /**
     * Determina de manera aleatoria si un vehículo debe realizar una filigrana durante su turno en la carrera.
     * La decisión se basa en una probabilidad del 50%, donde un resultado menor que 0.5 indica que el vehículo
     * debería realizar una filigrana.
     *
     * @return [Boolean] Verdadero si el vehículo debe realizar una filigrana, falso en caso contrario.
     */
    private fun comprobarSiTocaHacerFiligrana() = (Math.random() < 0.5)

    /**
     * Intenta que un vehículo realice una filigrana durante su avance en la carrera. La filigrana
     * (derrape o caballito) se realiza basada en una probabilidad aleatoria y consume combustible adicional.
     *
     * @param vehiculo El [Vehiculo] que intentará realizar la filigrana.
     */
    private fun realizarFiligrana(vehiculo: Vehiculo) {
        // Lógica para realizar filigranas de motociletas y automovil y registrarlas. Se hará o no aleatoriamente.
        if (comprobarSiTocaHacerFiligrana()) {
            val combustibleRestante: Float

            if (vehiculo is Automovil) {
                combustibleRestante = vehiculo.realizaDerrape()
                infoCarrera.registrarAccion(
                    vehiculo.nombre,
                    "Derrape: Combustible restante %.2f L.".format(combustibleRestante)
                )
            } else if (vehiculo is Motocicleta) {
                combustibleRestante = vehiculo.realizaCaballito()
                infoCarrera.registrarAccion(
                    vehiculo.nombre,
                    "Caballito: Combustible restante %.2f L.".format(combustibleRestante)
                )
            }
        }
    }

    /**
     * Determina si hay un ganador de la carrera, basado en si algún vehículo ha alcanzado la distancia
     * total de la carrera.
     *
     * @return El [Vehiculo] ganador, si existe; de lo contrario, devuelve null.
     */
    private fun determinarGanador(): Vehiculo? {
        val ganador: Vehiculo? = participantes.maxByOrNull { it.kilometrosActuales }

        return if ((ganador?.kilometrosActuales ?: 0f) >= distanciaTotal)
            ganador
        else
            null
    }

}