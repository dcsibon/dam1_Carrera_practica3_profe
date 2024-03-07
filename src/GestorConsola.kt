/**
 * Objeto dedicado a la gestión de interacciones a través de la consola.
 * Proporciona funcionalidades para solicitar datos al usuario y crear vehículos de manera aleatoria.
 */
object GestorConsola {

    /**
     * Solicita al usuario que ingrese un número entero a través de la consola.
     *
     * Repite la solicitud hasta que el usuario ingrese un número válido. Si el usuario introduce
     * un valor que no puede ser convertido a entero, muestra un mensaje de error y solicita de nuevo el número.
     *
     * @param mensaje El mensaje que se mostrará al usuario para solicitar el número.
     * @return El número entero introducido por el usuario.
     */
    fun pedirNumero(mensaje: String) : Int {
        var error = false
        var numero = 0

        do {
            try {
                print(mensaje)
                numero = readln().toInt()
            }
            catch (e: NumberFormatException) {
                error = true
                println("**Error** Debe introducir un número entero.")
            }
        } while (error)

        return numero
    }

    /**
     * Crea un vehículo de manera aleatoria utilizando una de las fábricas proporcionadas.
     *
     * Solicita al usuario que introduzca un nombre para el vehículo. Si se introduce un nombre que
     * no cumple con las validaciones de las fábricas (por ejemplo, provocando una `IllegalArgumentException`),
     * muestra un mensaje de error y solicita de nuevo el nombre. El proceso se repite hasta obtener un vehículo válido.
     *
     * @param factories Lista de fábricas de vehículos que pueden generar vehículos de distintos tipos.
     * @param index Un índice numérico que se usa para referenciar el vehículo durante la solicitud de nombre.
     * @return El vehículo generado aleatoriamente.
     * @throws IllegalArgumentException si el nombre del vehículo no cumple con las validaciones requeridas por la fábrica.
     */
    fun crearVehiculoAleatorio(factories: List<VehiculoFactory<out Vehiculo>>, index: Int): Vehiculo {
        var vehiculo: Vehiculo? = null

        do {
            try {
                print("\t* Nombre del vehículo $index -> ")
                val nombre = readln().capitalizar()
                vehiculo = generarVehiculo(factories, nombre)
            }
            catch (e: IllegalArgumentException) {
                println("**Error** ${e.message}")
            }
        } while (vehiculo == null)

        return vehiculo
    }

}