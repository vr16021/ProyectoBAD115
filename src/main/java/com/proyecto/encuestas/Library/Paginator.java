 
package com.proyecto.encuestas.Library;

import java.util.List;
import java.util.stream.Collectors;


public class Paginator<T> {

    //cantidad de resultados por página 
    private int pagi_cuantos = 5;
    //cantidad de enlaces que se mostrarán como máximo en la barra de navegación 
    private final int pagi_nav_num_enlaces = 5;
    private int pagi_actual;
    //definimos qué irá en el enlace a la página anterior 
    private final String pagi_nav_anterior = " &laquo; Anterior ";
    //definimos qué irá en el enlace a la página siguiente 
    private final String pagi_nav_siguiente = " Siguiente &raquo; ";
    //definimos qué irá en el enlace a la página siguiente 
    private final String pagi_nav_primera = " &laquo; Primero ";
    private final String pagi_nav_ultima = " Último &raquo; ";
    private String pagi_navegacion = "";

    public Object[] paginator(List<T> table, int pagina, int registros,
            String action, String host) {
        pagi_cuantos = registros > 0 ? registros : pagi_cuantos;
        pagi_actual = pagina == 0 ? 1 : pagina;

        int pagi_totalReg = table.size();
        double valor1 = Math.ceil((double) pagi_totalReg / (double) pagi_cuantos);
        int pagi_totalPags = (int) Math.ceil(valor1);
        if (pagi_actual != 1) {
           // Si no estamos en la página 1. Ponemos el enlace "primera" 
            int pagi_url = 1; //será el número de página al que enlazamos 
            pagi_navegacion += "<a class='btn btn-default' href='" + host + action
                    + "?page=" + pagi_url + "&registros=" + pagi_cuantos + "'>" + pagi_nav_primera + "</a>";
            
            // Si no estamos en la página 1. Ponemos el enlace "anterior" 
            pagi_url = pagi_actual - 1; //será el número de página al que enlazamos 
            pagi_navegacion += "<a class='btn btn-default' href='" + host + action
                    + "?page=" + pagi_url + "&registros=" + pagi_cuantos + "'>" + pagi_nav_anterior + " </a>";
        }
         // Si se definió la variable pagi_nav_num_enlaces 
        // Calculamos el intervalo para restar y sumar a partir de la página actual 
        float valor = (pagi_nav_num_enlaces / 2);
        int pagi_nav_intervalo = Math.round(valor);
         // Calculamos desde qué número de página se mostrará 
        int pagi_nav_desde = pagi_actual - pagi_nav_intervalo;
        // Calculamos hasta qué número de página se mostrará 
        int pagi_nav_hasta = pagi_actual + pagi_nav_intervalo;
         if (pagi_nav_desde < 1){
              // Le sumamos la cantidad sobrante al final para mantener
            //el número de enlaces que se quiere mostrar.  
            pagi_nav_hasta -= (pagi_nav_desde - 1);
            // Establecemos pagi_nav_desde como 1. 
            pagi_nav_desde = 1;
         }
         // Si pagi_nav_hasta es un número mayor que el total de páginas 
        if (pagi_nav_hasta > pagi_totalPags) {
            // Le restamos la cantidad excedida al comienzo para mantener 
            //el número de enlaces que se quiere mostrar. 
            pagi_nav_desde -= (pagi_nav_hasta - pagi_totalPags);
            // Establecemos pagi_nav_hasta como el total de páginas. 
            pagi_nav_hasta = pagi_totalPags;
            // Hacemos el último ajuste verificando que al cambiar pagi_nav_desde 
            //no haya quedado con un valor no válido. 
            if (pagi_nav_desde < 1) {
                pagi_nav_desde = 1;
            }
        }
        for (int pagi_i = pagi_nav_desde; pagi_i <= pagi_nav_hasta; pagi_i++){
            if (pagi_i == pagi_actual){
                 // Si el número de página es la actual (pagi_actual). Se escribe el número, pero sin enlace y en negrita. 
                pagi_navegacion += "<span class='btn btn-default' disabled='disabled'>" + pagi_i + "</span>";
            }else{
                 // Si es cualquier otro. Se escribe el enlace a dicho número de página. 
                pagi_navegacion += "<a class='btn btn-default' href='" + host
                        + action + "?page=" + pagi_i + "&registros=" + pagi_cuantos + "'>"
                        + pagi_i + " </a>";
            }
        }
         if (pagi_actual < pagi_totalPags){
              // Si no estamos en la última página. Ponemos el enlace "Siguiente" 
            int pagi_url = pagi_actual + 1; //será el número de página al que enlazamos 
            pagi_navegacion += "<a class='btn btn-default' href='" + host + action
                    + "?page=" + pagi_url + "&registros=" + pagi_cuantos + "'>"
                    + pagi_nav_siguiente + "</a>";
            
              // Si no estamos en la última página. Ponemos el enlace "Última" 
            pagi_url = pagi_totalPags; //será el número de página al que enlazamos 
            pagi_navegacion += "<a class='btn btn-default' href='" + host
                    + action + "?page=" + pagi_url + "&registros=" + pagi_cuantos + "'>"
                    + pagi_nav_ultima + "</a>";
         }
         /* Obtención de los registros que se mostrarán en la página actual. 
        *------------------------------------------------------------------------ 
         */
        // Calculamos desde qué registro se mostrará en esta página 
        // Recordemos que el conteo empieza desde CERO. 
        int pagi_inicial = (pagi_actual - 1) * pagi_cuantos;
         // Consulta SQL. Devuelve cantidad registros empezando desde pagi_inicial
        List<T> query = table.stream().skip(pagi_inicial).limit(pagi_cuantos)
                .collect(Collectors.toList());
         String pagi_info = "Resultados de <b>" + pagi_actual + "</b> al <b>" + pagi_totalPags + "</b> de <b>"
                + pagi_totalReg + "</b> <b>/" + pagi_cuantos + "</b>";
        Object[] data = {pagi_info, pagi_navegacion, query};
        return data;
    }
}
