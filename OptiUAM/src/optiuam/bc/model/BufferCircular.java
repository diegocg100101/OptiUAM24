
package optiuam.bc.model;

/**
 * Clase BufferCircular para gestionar los puntos al graficar
 * @author Carlos Elizarraras
 */
public class BufferCircular{
    private double[] buffer;
    private int tamaño;
    private int cabeza; // Índice para el próximo elemento a ser leído
    private int cola; // Índice para el próximo lugar donde se insertará un elemento

    public BufferCircular(int tamaño){
        this.tamaño = tamaño;
        buffer = new double[tamaño];
        cabeza = 0;
        cola = 0;
    }

    public void agregar(double valor){
        buffer[cola] = valor;
        cola = (cola + 1) % tamaño; // Avanza el índice de inserción, si se llega al final del array, vuelve al principio
        if (cola == cabeza) {
            cabeza = (cabeza + 1) % tamaño; // Si el índice de inserción alcanza al de lectura, avanzamos este último
        }
    }

    public double[] leerBuffer() {
        if (estaVacio()) {
            throw new IllegalStateException("El buffer está vacío");
        }
        int cantidadElementos = cola >= cabeza ? cola - cabeza : tamaño - cabeza + cola;
        double[] elementos = new double[cantidadElementos];
        int indice = cabeza;
        for (int i = 0; i < cantidadElementos; i++) {
            elementos[i] = buffer[indice];
            indice = (indice + 1) % tamaño;
        }
        return elementos;
    }
    
    public double remover(){
        if (estaVacio()) {
            throw new IllegalStateException("El buffer está vacío");
        }
        double valor = buffer[cabeza];
        cabeza = (cabeza + 1) % tamaño; // Avanza el índice de lectura
        return valor;
    }

    public boolean estaVacio(){
        return cabeza == cola;
    }

    public boolean estaLleno(){
        return (cola + 1) % tamaño == cabeza;
    }
}

