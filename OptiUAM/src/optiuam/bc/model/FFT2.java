
package optiuam.bc.model;

/**
 * Clase FFT2 la cual contiene los atributos principales de la Transformada
 * Rapida de Fourier al igual que otros metodos
 * @author Arturo Borja
 * @author Karen Cruz
 */
public class FFT2 {

    /**
     * Metodo que calcula la FFT de x[], asumiendo que su longitud n es una 
     * potencia de 2
     * @param x Arreglo de numeros complejos
     * @return FFT del arreglo de numeros complejos
     */
    public NumeroComplejo[] fft(NumeroComplejo[] x) {
        int n = x.length;

        //Caso base
        if (n == 1) return new NumeroComplejo[]{ x[0] };

        //Base 2 Cooley-Tukey FFT
        if (n % 2 != 0) {
            throw new IllegalArgumentException("n no es una potencia de 2");
        }

        //Calculo de FFT de numeros pares
        NumeroComplejo[] even = new NumeroComplejo[n/2];
        for (int k = 0; k < n/2; k++) {
            even[k] = x[2*k];
        }
        NumeroComplejo[] evenFFT = fft(even);

        //Calculo de FFT de numeros impares
        NumeroComplejo[] odd  = even;  // reutilizar la matriz (para evitar n log n space)
        for (int k = 0; k < n/2; k++) {
            odd[k] = x[2*k + 1];
        }
        NumeroComplejo[] oddFFT = fft(odd);

        //Combinacion 
        NumeroComplejo[] y = new NumeroComplejo[n];
        for (int k = 0; k < n/2; k++) {
            double kth = -2 * k * Math.PI / n;
            NumeroComplejo wk = new NumeroComplejo((float)Math.cos(kth), (float)Math.sin(kth));
            y[k] = evenFFT[k].plus(wk.times(oddFFT[k]));
            y[k + n/2] = evenFFT[k].minus(wk.times(oddFFT[k]));
            //System.out.println("real_ "+y[k].getRealPart());
            //System.out.println("imag_ "+y[k].getImaginaryPart());
            //System.err.println("magnitud:"+y[k].magnitud());
            //System.out.println(y[k + n/2].magnitud() );
        }
        return y;
    }

    /**
     * Metodo que calcula la FFT inversa de x[], asumiendo que su longitud n es 
     * una potencia de 2
     * @param x Arreglo de numeros complejos
     * @return IFFT del arreglo de numeros complejos
     */
    public NumeroComplejo[] ifft(NumeroComplejo[] x) {
        int n = x.length;
        NumeroComplejo[] y = new NumeroComplejo[n];

        //Tomar el conjugado
        for (int i = 0; i < n; i++) {
            y[i] = x[i].conjugar(true);
        }

        //Calcular FFT
        y = fft(y);

        //Tomar el conjugado de nuevo
        for (int i = 0; i < n; i++) {
            y[i] = y[i].conjugar(true);
        }

        //Dividir entre n
        for (int i = 0; i < n; i++) {
            y[i] = y[i].scale((float) (1.0 / n));
        }
        return y;
    }

    /**
     * Metodo que calcula la convolución circular de x e y
     * @param x Arreglo 1 de numeros complejos
     * @param y Arreglo 2 de numeros complejos
     * @return Convolucion circular de los dos arreglos de numeros complejos
     */
    public NumeroComplejo[] cconvolve(NumeroComplejo[] x, NumeroComplejo[] y) {
        //probablemente debería rellenar x e y con 0 para que tengan la misma 
        //longitud y sean potencias de 2
        if (x.length != y.length) {
            throw new IllegalArgumentException("Las dimensiones no concuerdan");
        }
        int n = x.length;

        //Calcula la FFT de cada secuencia
        NumeroComplejo[] a = fft(x);
        NumeroComplejo[] b = fft(y);

        //Multiplica punto a punto
        NumeroComplejo[] c = new NumeroComplejo[n];
        for (int i = 0; i < n; i++) {
            c[i] = a[i].times(b[i]);
        }

        //Calcula la FFT inversa
        return ifft(c);
    }
    
    /**
     * Metodo que calcula la convolución lineal de x e y
     * @param x Arreglo 1 de numeros complejos
     * @param y Arreglo 2 de numeros complejos
     * @return Convolucion lineal de los dos arreglos de numeros complejos
     */
    public NumeroComplejo[] convolve(NumeroComplejo[] x, NumeroComplejo[] y) {
        NumeroComplejo ZERO = new NumeroComplejo(0, 0);

        NumeroComplejo[] a = new NumeroComplejo[2*x.length];
        for (int i = 0; i < x.length; i++) a[i] = x[i];
        for (int i = x.length; i < 2*x.length; i++) a[i] = ZERO;

        NumeroComplejo[] b = new NumeroComplejo[2*y.length];
        for (int i = 0; i < y.length; i++) b[i] = y[i];
        for (int i = y.length; i < 2*y.length; i++) b[i] = ZERO;

        return cconvolve(a, b);
    }

    /**
     * Metodo que calcula el DFT de x [] n^2 veces
     * @param x Arreglo de numeros complejos
     * @return DFT del arreglo de numeros complejos
     */
    public NumeroComplejo[] dft(NumeroComplejo[] x) {
        int n = x.length;
        NumeroComplejo ZERO = new NumeroComplejo(0, 0);
        NumeroComplejo[] y = new NumeroComplejo[n];
        for (int k = 0; k < n; k++) {
            y[k] = ZERO;
            for (int j = 0; j < n; j++) {
                int power = (k * j) % n;
                double kth = -2 * power *  Math.PI / n;
                NumeroComplejo wkj = new NumeroComplejo((float)Math.cos(kth), 
                        (float)Math.sin(kth));
                y[k] = y[k].plus(x[j].times(wkj));
            }
        }
        return y;
    }

    /**
     * Metodo que muestra una matriz de numeros complejos en la salida estandar
     * @param x Arreglo de numeros complejos
     * @param title Titulo
     */
    public void show(NumeroComplejo[] x, String title) {
        System.out.println(title);
        System.out.println("-------------------");
        for (int i = 0; i < x.length; i++) {
            System.out.println(x[i]);
        }
        System.out.println();
    }
    
}