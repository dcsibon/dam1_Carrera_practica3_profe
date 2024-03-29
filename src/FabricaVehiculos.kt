/**
 * Módulo dedicado a la creación de vehículos mediante el patrón de diseño Factory.
 * Incluye fábricas específicas para la creación de diferentes tipos de vehículos: Automóviles, Motocicletas, Camiones y Quads.
 * Utiliza datos simulados para marcas y modelos, y proporciona funcionalidades para generar características aleatorias de los vehículos.
 */

import kotlin.random.Random

// Datos simulados para marcas, modelos, cilindradas, y tipos de Quad
val marcasAutomovil = listOf("Toyota", "Honda", "BMW", "Ford")
val modelosAutomovil = listOf("Basic", "Basic A", "Premium A", "Premium B")
val marcasMotos = listOf("Harley Davidson", "Ducati", "Aprilia", "BMW", "Yamaha", "Honda", "Suzuki", "Kawasaki", "KTM")
val modelosMotos = listOf("Moto A", "Moto B", "Moto C", "Moto D")

/**
 * Define una interfaz de fábrica genérica para la creación de vehículos.
 */
interface VehiculoFactory<T : Vehiculo> {
    /**
     * Crea una instancia de un vehículo.
     * @param nombre El nombre del vehículo a crear.
     * @return Una instancia del tipo [T] de vehículo.
     */
    fun crearVehiculo(nombre: String): T
}

/**
 * Representa una fábrica específica para la creación de objetos [Automovil].
 * Esta clase implementa la interfaz [VehiculoFactory] para proporcionar una implementación concreta
 * del método `crearVehiculo`, especializado en la creación de instancias de [Automovil].
 */
class AutomovilFactory : VehiculoFactory<Automovil> {
    /**
     * Crea y devuelve una nueva instancia de [Automovil] con propiedades aleatorias dentro de rangos predefinidos.
     * @param nombre El nombre que se asignará al nuevo automóvil.
     * @return Una nueva instancia de [Automovil].
     */
    override fun crearVehiculo(nombre: String): Automovil {
        val capacidadCombustible = Random.nextInt(30, 61).toFloat()
        val combustibleActual = (capacidadCombustible * Random.nextDouble(0.2, 1.0)).toFloat().redondear(2)
        return Automovil(nombre, marcasAutomovil.random(), modelosAutomovil.random(), capacidadCombustible, combustibleActual, 0f, Random.nextBoolean())
    }
}

/**
 * Representa una fábrica específica para la creación de objetos [Motocicleta].
 * Proporciona una implementación específica para crear instancias de [Motocicleta] con propiedades
 * inicializadas aleatoriamente.
 */
class MotocicletaFactory : VehiculoFactory<Motocicleta> {
    /**
     * Crea y devuelve una nueva instancia de [Motocicleta] con propiedades aleatorias dentro de rangos predefinidos.
     * @param nombre El nombre que se asignará a la nueva motocicleta.
     * @return Una nueva instancia de [Motocicleta].
     */
    override fun crearVehiculo(nombre: String): Motocicleta {
        val capacidadCombustible = Random.nextInt(15, 31).toFloat()
        val combustibleActual = (capacidadCombustible * Random.nextDouble(0.2, 1.0)).toFloat().redondear(2)
        return Motocicleta(nombre, marcasMotos.random(), modelosMotos.random(), capacidadCombustible, combustibleActual, 0f, obtenerCilindradaAleatoria())
    }
}

/**
 * Representa una fábrica específica para la creación de objetos [Camion].
 * Esta clase provee una implementación concreta para la creación de instancias de [Camion],
 * con características y propiedades determinadas aleatoriamente.
 */
class CamionFactory : VehiculoFactory<Camion> {
    /**
     * Crea y devuelve una nueva instancia de [Camion] con propiedades aleatorias dentro de rangos predefinidos.
     * @param nombre El nombre que se asignará al nuevo camión.
     * @return Una nueva instancia de [Camion].
     */
    override fun crearVehiculo(nombre: String): Camion {
        val capacidadCombustible = Random.nextInt(90, 151).toFloat()
        val combustibleActual = (capacidadCombustible * Random.nextDouble(0.2, 1.0)).toFloat().redondear(2)
        val peso = Random.nextInt(1000, 10001)
        return Camion(nombre, capacidadCombustible, combustibleActual, 0f, peso)
    }
}

/**
 * Representa una fábrica específica para la creación de objetos [Quad].
 * Esta clase implementa la interfaz [VehiculoFactory] para ofrecer una forma concreta de crear
 * instancias de [Quad] con propiedades inicializadas de forma aleatoria.
 */
class QuadFactory : VehiculoFactory<Quad> {
    /**
     * Crea y devuelve una nueva instancia de [Quad] con propiedades aleatorias dentro de rangos predefinidos.
     * @param nombre El nombre que se asignará al nuevo quad.
     * @return Una nueva instancia de [Quad].
     */
    override fun crearVehiculo(nombre: String): Quad {
        val capacidadCombustible = Random.nextInt(20, 41).toFloat()
        val combustibleActual = (capacidadCombustible * Random.nextDouble(0.2, 1.0)).toFloat().redondear(2)
        return Quad(nombre, capacidadCombustible, combustibleActual, 0f, obtenerCilindradaAleatoria(), obtenerTipoQuadAleatorio())
    }
}

/*
EXPLICACIÓN del modificador out en la función generarVehiculo()
---------------------------------------------------------------

El modificador `out` se utiliza en la declaración de tipos genéricos para indicar una variante de covarianza.

Cuando se utiliza `out`, significa que el tipo genérico puede ser consumido pero no producido.

En otras palabras, podemos devolver el tipo genérico como salida de la función, pero no podemos pasar el objeto como entrada de métodos que modifican el tipo.

Se utiliza `out` para indicar que la lista `factories` puede contener instancias de `VehiculoFactory` para cualquier subtipo de `Vehiculo`, no solo para `Vehiculo` específicamente. Esto es útil para trabajar con colecciones de objetos genéricos cuando queremos asegurar la seguridad de tipo al leer de la colección, pero no necesitamos modificarla.

- Seguridad de tipo en lectura: Podemos obtener objetos de tipo `Vehiculo` o de cualquier subtipo de `Vehiculo` de las fábricas sin comprometer la seguridad de tipo.

- Invarianza superada: Sin el modificador `out`, la lista sería invariante. Esto significa que `List<VehiculoFactory<Automovil>>` no sería considerado un subtipo de `List<VehiculoFactory<Vehiculo>>`, lo cual limitaría su utilidad.

Sin `out`, tendríamos que limitarte a una lista de fábricas específicas para un único tipo de `Vehiculo`, lo cual sería menos flexible.

El uso de `out` en Kotlin facilita la programación genérica segura al permitirnos ser más expresivos sobre cómo se pueden utilizar los tipos, especialmente cuando trabajamos con colecciones de objetos genéricos.
*/
/**
 * Genera un vehículo de un tipo específico seleccionando aleatoriamente una fábrica de la lista proporcionada.
 *
 * Esta función toma una lista de fábricas que pueden crear vehículos de tipo `T`, que es un subtipo de `Vehiculo` y un nombre que se asignará al vehículo creado. La selección de la fábrica se hace de manera aleatoria. El vehículo es entonces creado por la fábrica seleccionada con el nombre proporcionado.
 *
 * @param factories Una lista de [VehiculoFactory] que son capaces de crear instancias de vehículos del tipo `T`. Cada fábrica en esta lista debe ser un subtipo de `VehiculoFactory` que produce instancias de `T`.
 * @param nombre El nombre que se asignará al vehículo creado.
 * @return Retorna una instancia del tipo `T`, que es un subtipo de `Vehiculo`, creado por una de las fábricas proporcionadas. El vehículo creado tendrá el nombre especificado en el parámetro `nombre`.
 * @throws NoSuchElementException si la lista de fábricas está vacía.
 * @see Vehiculo
 * @see VehiculoFactory
 */
fun <T : Vehiculo> generarVehiculo(factories: List<VehiculoFactory<out T>>, nombre: String): T {
    val factory = factories.random()
    return factory.crearVehiculo(nombre)
}

/**
 * Obtener un tipo de quad aleatorio de la clase enumerada TipoQuad
 */
fun obtenerTipoQuadAleatorio(): TipoQuad {
    return TipoQuad.entries.toTypedArray().random()
}

/**
 * Obtener un tipo de cilindrada aleatoria de la clase enumerada Cilindrada
 */
fun obtenerCilindradaAleatoria(): Cilindrada {
    return Cilindrada.entries.toTypedArray().random()
}