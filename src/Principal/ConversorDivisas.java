package com.aluracursos.challenge.divisas.Principal;
import com.aluracursos.challenge.divisas.Modelos.CalculadoraDeDivisas;
import com.aluracursos.challenge.divisas.Modelos.DivisaDestino;
import com.aluracursos.challenge.divisas.Modelos.DivisasExGen;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class ConversorDivisas {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner entrada = new Scanner(System.in);

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

            //SELECCION Y ENTRADA DE USUARIO

            try {
                var seleccionUsuario = entrada.nextInt();
                entrada.nextLine();

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

                    String nuevaConversion = "Convertir de " + monedaUno + " a " + monedaDos + " ";
                    if (conversionesDisponibles.contains(nuevaConversion)) {
                        System.out.println("Conversion ya existente.");
                    } else {
                        conversionesDisponibles.add(conversionesDisponibles.size() - 1 ,nuevaConversion);
                        System.out.println("Nueva conversión disponible.");
                    }

                } else {

                    String monedaUno = (conversionesDisponibles.get(seleccionUsuario - 1).substring(13, 16));
                    String monedaDos = (conversionesDisponibles.get(seleccionUsuario - 1).substring(19, 22));

                    String nombreMonedaUno = Currency.getInstance(monedaUno).getDisplayName();
                    String nombreMonedaDos = Currency.getInstance(monedaDos).getDisplayName();

                    System.out.println("¿Qué cantidad de " + nombreMonedaUno + " quieres convertir?");

                    var factorConversor = entrada.nextDouble();

                    //SOLICITUD A API

                    String direccion = "https://v6.exchangerate-api.com/v6/357f2b9ea22f7450728071a6/pair/"
                            + monedaUno + "/" + monedaDos;

                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(direccion))
                            .build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    //INTERPRETACION DE RESPUESTA DE API

                    Gson gson = new GsonBuilder().create();
                    String objetoJson = response.body();
                    DivisasExGen misDivisasExGen = gson.fromJson(objetoJson, DivisasExGen.class);

                    //*******************************************************
                    DivisaDestino miDivisaDestino = new DivisaDestino();
                    miDivisaDestino.setNombre(nombreMonedaDos);
                    miDivisaDestino.setValorRelativo(misDivisasExGen.conversion_rate());

                    CalculadoraDeDivisas calculadora = new CalculadoraDeDivisas();
                    calculadora.convierte(miDivisaDestino, factorConversor);
                    System.out.println(factorConversor + " de " + nombreMonedaUno
                            + " equivalen a " + calculadora.getDivisaConvertida() + " de "
                            + miDivisaDestino.getNombre() + "\n");
                    //********************************************************
                }

            } catch (IOException e) {
                System.out.println("No existe esa opción. Vuelve a intentarlo.");
                System.out.println(e.getMessage());
            } catch (InterruptedException e) {
                System.out.println("No existe esa opción. Vuelve a intentarlo.");
                System.out.println(e.getMessage());
            } catch (JsonSyntaxException e) {
                System.out.println("No existe esa opción. Vuelve a intentarlo.");
                System.out.println(e.getMessage());
            } catch (InputMismatchException e){
                System.out.println("No existe esa opción. Vuelve a intentarlo.");
                entrada.nextLine();
            } catch (StringIndexOutOfBoundsException e){
                System.out.println("No existe esa opción. Vuelve a intentarlo.");
            } catch (ArrayIndexOutOfBoundsException e){
                System.out.println("No existe esa opción. Vuelve a intentarlo.");
            } catch (IllegalArgumentException e){
                System.out.println("Por favor, elige un código valido dentro del estandar ISO 4217");
            }catch (Exception e){
                System.out.println("Ha ocurrido un error. Vuelve a intentarlo.");
            }
        }
    }
}
