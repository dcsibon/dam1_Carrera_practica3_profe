
## Práctica Carrera 3

Analiza y modifica el código de este repositorio para realizar los siguientes puntos:

### PARTE 1

#### 1. Crear 2 nuevas clases derivadas: Camion es una clase específica de Automovil y Quad es una clase específica de Motocicleta.

   - El camión tiene un consumo de 16L/100Km. Además tendrá un peso expresado en Kg (min 1000 máx 10000) que afectará en su autonomía... ya que cada 1000 Kg de peso afecta negativamente en la autonomía, reduciendo los KM por litro en 0.2.
   
   - El quad tiene una autonomía que será la mitad de la que tiene una Motocicleta con la misma cilindrada. Tiene cómo característica diferencial el tipo que será un valor entre los siguientes: "Cuadriciclos ligeros", "Cuadriciclos no ligeros" y "Vehículos especiales".

#### 2. Pedir el número de participantes y por cada uno solicitar su nombre... El programa debe mostrar de forma aleatoria los datos del vehículo que le ha tocado a cada participante:

***Por ejemplo:***

   ```kotlin
   Introduce el número de participantes: 6

      * Nombre del vehículo 1 -> flash

      Te ha tocado un Automóvil(nombre=Flash, marca=Toyota, modelo=CHR, capacidad=53L, combustible=35,73L, eléctrico=SI)

      * Nombre del vehículo 2 -> torrente dos

      Te ha tocado una Motocicleta(nombre=Torrente Dos, marca=Derbi, modelo=Variant, capacidad=16L, combustible=9,44L, cc=125)
   ...
   ```


***Premisas:***

   - Para generar aleatoriamente la información de marca y modelo de Automovil y Motocicleta debéis tener un juego de posibles datos. Pero un camión y un quad no almacenarán marca y modelo, es decir, siempre las tendrán inicializadas con la cadena vacía.

   - La capacidad debe ser un número Float pero sin decimales... Automóvil de 30 a 60L, Motocicleta de 15 a 30L, Camión de 90 a 150L y Quad de 20 a 40L.

   - Para las motocicletas y quads solo existirán las cilindradas 125, 250, 400, 500, 750, 900 y 1000.

   - El combustible debe ser un número con dos valores decimales entre el 20% y 100% de su capacidad.
 
   - En cuanto al nombre del vehículo que introduce el usuario, no puede repetirse el nombre de ningún vehículo... dicho nombre no puede estar vacío o relleno con espacios. Los nombres deben almacenarse en minúsculas, pero mostrarse siempre capitalizadas cada palabra que contenga (por si se ha introducido un nombre compuesto).
	 
   - Debéis controlar que se produzca una excepción.
   
### PARTE 2

   Una vez terminada la parte 1, debe comenzar la carrera y se debe mostrar la información de la siguiente manera:
   
   - Cada vez que se realicen un número de iteraciones concreto mostrar una clasificación parcial con el nombre, tipo de vehiculo (Automovil, Motocicletqa, Camion o Quad), kms recorridos y combustible de cada coche. Como título podéis mostrar Clasificación Parcial (número de iteraciones)
   
   ```
   *** CLASIFICACIÓN PARCIAL (ronda 3) ***
   
   1. Flash Automovil(km = 12.56, combustible = 34.76 L)
   
   ...
   
   ```
   
   - Modificar la lógica de la carrera para que todos lleguen a la meta, pero se muestre la clasificación según hayan llegado a la misma. Mostrar también el número de iteraciones que han tardado cada uno en llegar.
   
   - Modificar la lógica interna para que el número de filigranas que realiza un vehículo sea de 0 a 3, pero también retrase al vehículo unos kms de forma aletaoria entre 10 y 50 kms en cada filigrana... registrando ese retraso de kms en la acción.
