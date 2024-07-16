# Conversor de divisas

Proyecto desarrollado para realizar la conversión de valores de las divisas seleccionadas mediante el consumo de API y el manejo de JSON.

## Desarrollo y lógica del código

El desarrollo de este proyecto tiene como foco la utilidad y el dinamismo que el usuario pueda obtener del mismo.

**¿Qué significa esto?**

La solución sugerida para este proyecto incluye un menú de 5 o 6 converiones predeterminadas por el desarrollador y la ejecución de la conversión seleccionada por el usuario mediante condicionales como el *switch statement*.

Si bien esto podría ser util y funcional para el usurio, también limita bastante el uso que este puede darle al conversor en caso de que se requiera una conversión no incluida previamente en el menú.

Para solucionar esto se optó por una alternativa un tanto más creativa que se expondrá a continuación.

**Solución alterna:**

En lugar de generar un menú mediante un String fijo, esta solución propone un menú creado a través de un Array al cual son adjuntadas una serie de opciones determinadas por el desarrollador indicadas en Strings individuales.

A continuación el menú se muestra en pantalla añadiendo un índice a cada uno de los elementos, así como el nombre de la divisa a la que se refieren cada uno de los códigos mostrados.

Por separado se imprime la última opción (Añadir nueva conversión) ya que esta no tendrá una posición fija durante toda la ejecución del programa.

```java
List <String> conversionesDisponibles = new ArrayList();
        conversionesDisponibles.add("Convertir de USD a MXN ");
        conversionesDisponibles.add("Convertir de MXN a USD ");
        conversionesDisponibles.add("Convertir de USD a BRL ");
        conversionesDisponibles.add("Convertir de MXN a BRL ");
        conversionesDisponibles.add("Convertir de USD a ARS ");
        conversionesDisponibles.add("Convertir de ARS a COP ");
        conversionesDisponibles.add("Añadir nueva conversión");

while (true) {

    System.out.println("""
                ***************************************
                Elige el tipo de conversión que deseas:
                0) Salir
                """);
    for (int i = 0; i < conversionesDisponibles.size()-1; i++){
            String nombreMoneda = Currency.getInstance(conversionesDisponibles.get(i).substring(13, 16)).getDisplayName();
            String nombreMonedaDos = Currency.getInstance(conversionesDisponibles.get(i).substring(19, 22)).getDisplayName();
            System.out.println((i+1) + ") " + conversionesDisponibles.get(i) + "(" + nombreMoneda + " a " + nombreMonedaDos + ")");
    }
    System.out.println(conversionesDisponibles.size() + ") " + conversionesDisponibles.get(conversionesDisponibles.size()-1));
```

A continuación el código tiene únicamente tres ramificaciones: Opción "0" para salir, opción "ultima" para crear una nueva conversión y la conversión elegida.

Cuando el usuario desea crear una conversión personalizada el programa solicita ingresar los códigos de las divisas requeridas y posteriormente formatea un String que indicará la nueva conversión. Si los códigos son validos y la conversión deseada no existe aún dentro de las opciones iniciales, el String formateado se agregará en la penultima posición del Array. Así, la opción para crear nuevas conversiones seguirá siendo siempre la última y la creada por el usuario estará disponible en el nuevo menú el cual se mostrará inmediatamente después de ser actualizado

```java
if (seleccionUsuario == 0) {
    System.out.println("Conversor finalizado");
    break;
} else if (seleccionUsuario == conversionesDisponibles.size()) {
    System.out.println("Convertir de: ");
    String monedaUno = entrada.nextLine().toUpperCase();
    Currency moneda = Currency.getInstance(monedaUno);
    System.out.println("Convertir a: ");
    String monedaDos = entrada.nextLine().toUpperCase();
    moneda = Currency.getInstance(monedaDos);

    String nuevaConversion = "Convertir de " + monedaUno + " a " + monedaDos + " ";if (conversionesDisponibles.contains(nuevaConversion)) {
    System.out.println("Conversion ya existente.");
} else {
conversionesDisponibles.add(conversionesDisponibles.size() - 1 ,nuevaConversion);
System.out.println("Nueva conversión disponible.");
}
}
```

Existe una sola ramificación para cuando el usuario elija una de las opciones de conversión disponibles ya que se ejecutará el mismo código para todas ellas.

```java

```
