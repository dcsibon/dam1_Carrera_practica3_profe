class Camion(
    nombre: String,
    capacidadCombustible: Float,
    combustibleActual: Float,
    kilometrosActuales: Float,
    private val peso: Int
) : Automovil(nombre, "", "", capacidadCombustible, combustibleActual, kilometrosActuales, false) {

    init {
        require(peso in 1000..10000) { "El peso debe estar entre 1000 y 10000 kg." }
    }

    companion object {
        const val KM_POR_LITRO_BASE = 6.25f
        const val PESO_POR_REDUCCION = 0.2f
    }

    override fun obtenerKmLitroAjustado() : Float {
        val reduccionPorPeso = (peso / 1000) * PESO_POR_REDUCCION
        return KM_POR_LITRO_BASE - reduccionPorPeso
    }

    override fun toString(): String {
        return "Camion(nombre=$nombre, capacidadCombustible=$capacidadCombustible, combustibleActual=$combustibleActual, kilometrosActuales=$kilometrosActuales, peso=${peso}Kg)"
    }
}