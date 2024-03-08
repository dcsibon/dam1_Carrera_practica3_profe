/**
 * Representa una motocicleta, que es una especialización de [Vehiculo], con la adición de la cilindrada como propiedad única.
 *
 * @property cilindrada La cilindrada de la motocicleta.
 * @constructor Crea una motocicleta con los parámetros especificados, heredando propiedades y funcionalidades de [Vehiculo].
 */
open class Motocicleta(
    nombre: String,
    marca: String,
    modelo: String,
    capacidadCombustible: Float,
    combustibleActual: Float,
    kilometrosActuales: Float,
    protected val cilindrada: Cilindrada
) : Vehiculo(nombre, marca, modelo, capacidadCombustible, combustibleActual, kilometrosActuales) {

    companion object {
        const val KM_POR_LITRO = 20.0f // 20 KM por litro.
        const val KM_POR_CABALLITO = 6.5f // Gasto equivalente a 6,5 km.
    }

    /**
     * Sobrescribe el método obtenerKmLitroAjustado de la clase [Vehiculo] para calcular el rendimiento ajustado de
     * kilómetros por litro para la motocicleta basándose en su cilindrada.
     *
     * @return El rendimiento ajustado de kilómetros por litro como un [Float]
     */
    override fun obtenerKmLitroAjustado() = (KM_POR_LITRO - (1 - (cilindrada.cc/1000)))

    /**
     * Devuelve una cadena de texto con la información del vehículo dentro de una frase.
     *
     * @return Una cadena de texto que representa la información del vehículo.
     */
    override fun obtenerInformacion(): String {
        return "Te ha tocado una ${toString()}"
    }

    /**
     * Ejecuta una maniobra de caballito con la motocicleta, consumiendo una cantidad fija de combustible.
     * Este método simula el caballito sin importar el nivel de combustible actual.
     *
     * @return El nivel de combustible restante después de realizar el caballito, como [Float].
     */
    fun realizaCaballito(): Float {
        actualizaCombustible(KM_POR_CABALLITO)
        return combustibleActual
    }

    /**
     * Sobrescribe el método toString de la clase [Vehiculo] para ofrecer una representación textual de la
     * motocicleta que incluye su cilindrada, además de los atributos heredados de la clase base.
     *
     * @return Una representación en cadena de texto de la motocicleta, detallando su identificación, características, y estado actual.
     */
    override fun toString(): String {
        return "Motocicleta(nombre=$nombre, marca=$marca, modelo=$modelo, capacidadCombustible=$capacidadCombustible, combustibleActual=$combustibleActual, kilometrosActuales=$kilometrosActuales, cilindrada=${cilindrada.cc}cc)"
    }
}