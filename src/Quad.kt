class Quad(
    nombre: String,
    capacidadCombustible: Float,
    combustibleActual: Float,
    kilometrosActuales: Float,
    cilindrada: Cilindrada,
    private val tipo: TipoQuad
) : Motocicleta(nombre, "", "", capacidadCombustible, combustibleActual, kilometrosActuales, cilindrada) {

    override fun obtenerKmLitroAjustado(): Float {
        return super.obtenerKmLitroAjustado() / 2
    }

    override fun toString(): String {
        return "Quad(nombre=$nombre, capacidadCombustible=$capacidadCombustible, combustibleActual=$combustibleActual, kilometrosActuales=$kilometrosActuales, cilindrada=${cilindrada.cc}cc, tipo=${tipo.desc})"
    }
}