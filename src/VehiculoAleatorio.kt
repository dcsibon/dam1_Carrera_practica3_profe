import kotlin.random.Random

interface VehiculoFactory<T : Vehiculo> {
    fun crearVehiculo(nombre: String): T
}

class AutomovilFactory : VehiculoFactory<Automovil> {
    override fun crearVehiculo(nombre: String): Automovil {
        val capacidadCombustible = Random.nextInt(30, 61).toFloat()
        val combustibleActual = (capacidadCombustible * Random.nextDouble(0.2, 1.0)).toFloat().redondear(2)
        return Automovil(nombre, marcas.random(), modelos.random(), capacidadCombustible, combustibleActual, 0f, Random.nextBoolean())
    }
}

// Fábrica para Motocicleta
class MotocicletaFactory : VehiculoFactory<Motocicleta> {
    override fun crearVehiculo(nombre: String): Motocicleta {
        val capacidadCombustible = Random.nextInt(15, 31).toFloat()
        val combustibleActual = (capacidadCombustible * Random.nextDouble(0.2, 1.0)).toFloat().redondear(2)
        return Motocicleta(nombre, "", "", capacidadCombustible, combustibleActual, 0f, obtenerCilindradaAleatoria())
    }
}

// Fábrica para Camion
class CamionFactory : VehiculoFactory<Camion> {
    override fun crearVehiculo(nombre: String): Camion {
        val capacidadCombustible = Random.nextInt(90, 151).toFloat()
        val combustibleActual = (capacidadCombustible * Random.nextDouble(0.2, 1.0)).toFloat().redondear(2)
        val peso = Random.nextInt(1000, 10001)
        return Camion(nombre, capacidadCombustible, combustibleActual, 0f, peso)
    }
}

// Fábrica para Quad
class QuadFactory : VehiculoFactory<Quad> {
    override fun crearVehiculo(nombre: String): Quad {
        val capacidadCombustible = Random.nextInt(20, 41).toFloat()
        val combustibleActual = (capacidadCombustible * Random.nextDouble(0.2, 1.0)).toFloat().redondear(2)
        return Quad(nombre, capacidadCombustible, combustibleActual, 0f, obtenerCilindradaAleatoria(), obtenerTipoQuadAleatorio())
    }
}

fun <T : Vehiculo> generarVehiculo(factory: VehiculoFactory<T>, nombre: String): T {
    return factory.crearVehiculo(nombre)
}

fun obtenerTipoQuadAleatorio(): TipoQuad {
    return TipoQuad.entries.toTypedArray().random()
}

fun obtenerCilindradaAleatoria(): Cilindrada {
    return Cilindrada.entries.toTypedArray().random()
}