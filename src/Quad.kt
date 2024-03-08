class Quad(
    nombre: String,
    capacidadCombustible: Float,
    combustibleActual: Float,
    kilometrosActuales: Float,
    cilindrada: Cilindrada,
    private val tipo: TipoQuad
) : Motocicleta(nombre, "", "", capacidadCombustible, combustibleActual, kilometrosActuales, cilindrada) {

    /**
     * Sobrescribe el método obtenerKmLitroAjustado de la clase [Motocicleta] para calcular el rendimiento ajustado de
     * kilómetros por litro para el quad siendo siempre la mitad del rendimiento de la motocicleta (clase base).
     *
     * @return El rendimiento ajustado de kilómetros por litro como un [Float]
     */
    override fun obtenerKmLitroAjustado(): Float {
        return super.obtenerKmLitroAjustado() / 2
    }

    /**
     * Sobrescribe el método toString de la clase [Motocicleta] para proporcionar una representación en cadena de texto
     * específica del quad, incluyendo el tipo de quad.
     *
     * @return Una cadena de texto que representa al quad.
     */
    override fun toString(): String {
        return "Quad(nombre=$nombre, capacidadCombustible=$capacidadCombustible, combustibleActual=$combustibleActual, kilometrosActuales=$kilometrosActuales, cilindrada=${cilindrada.cc}cc, tipo=${tipo.desc})"
    }
}