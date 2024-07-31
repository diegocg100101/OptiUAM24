
package optiuam.bc.model;

/**
 * Clase NumeroComplejo la cual contiene los atributos principales de un numero 
 * complejo
 * @author Arturo Borja
 * @author Karen Cruz
 * @author Daniel Hernandez
 */
public class NumeroComplejo {
    
    /**Parte real del numero complejo*/
    private float parteReal;
    /**Parte imaginaria del numero complejo*/
    private float parteImaginaria;
    /**Distancia*/
    private double r;
    /**Angulo*/
    private double theta;
    
    /**
     * Metodo constructor con parametros
     * @param realPart Parte real del numero complejo
     * @param imaginaryPart Parte imaginaria del numero complejo
     */
    public NumeroComplejo(float realPart, float imaginaryPart) {
        this.parteReal = realPart;
        this.parteImaginaria = imaginaryPart;
        r = Math.sqrt(realPart*realPart + imaginaryPart*imaginaryPart);
        theta = Math.atan2(imaginaryPart, realPart);
    }
    
    /**
     * Metodo que retorna la amplitud del numero complejp
     * @return amplitud
     */
    public float getAmplitud() {
        return (float) Math.sqrt(Math.pow(parteReal, 2) + 
                Math.pow(parteImaginaria, 2));
    }
    
    /**
     * Metodo que retorna la fase del numero complejo
     * @return fase
     */
    public float getFase() {
        if (parteReal == 0) {
            if (parteImaginaria == 0) {
                return 0;
            } else if (parteImaginaria > 0) {
                return (float) (Math.PI / 2);
            } else {
                return (float) (-Math.PI / 2);
            }
        } else {
            return (float) Math.atan(parteImaginaria / parteReal);
        }
    }
    
    /**
     * Metodo que retorna la magnitud de un numero complejo
     * @return Magnitud
     */
    public float magnitud(){
        float x = parteReal;
        float y = parteImaginaria;
        float magnitud = (float) Math.sqrt((Math.pow(x, 2))+(Math.pow(y, 2)));
        return magnitud;
    }
    
    /**
     * Metodo que suma un numero complejo con otro this+sumando. 
     * Si nuevo= true regresa el resultado en un nuevo objeto
     * @param sumando Numero complejo sumador
     * @param nuevo Nueva suma
     * @return resultado de la suma
     */
    public NumeroComplejo sumar(NumeroComplejo sumando, boolean nuevo) {
        
        if (nuevo) {
            return new NumeroComplejo(this.parteReal + sumando.parteReal, 
                    this.parteImaginaria + sumando.parteImaginaria);
        }
        this.parteReal += sumando.parteReal;
        this.parteImaginaria += sumando.parteImaginaria;
        return this;
    }
    
    /**
     * Metodo que resta un numero complejo con otro this-sustraendo.
     * Si nuevo= true regresa el resultado en un nuevo objeto
     * @param sustraendo Numero complejo restador
     * @param nuevo Nueva resta
     * @return resultado de la resta
     */
    public NumeroComplejo restar(NumeroComplejo sustraendo, boolean nuevo) {
        if (sustraendo == null) {
            if (nuevo) {
                return new NumeroComplejo(this.parteReal, this.parteImaginaria);
            }
            return this;
        }
        if (nuevo) {
            return new NumeroComplejo(this.parteReal - sustraendo.parteReal, 
                    this.parteImaginaria - sustraendo.parteImaginaria);
        }
        this.parteReal -= sustraendo.parteReal;
        this.parteImaginaria -= sustraendo.parteImaginaria;
        return this;
    }
    
    /**
     * Metodo que multiplica un numero complejo con otro this*multiplicador. 
     * Si nuevo= true regresa el resultado en un nuevo objeto
     * @param multiplicador Numero complejo multiplicador
     * @param nuevo Nueva multiplicacion
     * @return resultado de la multiplicacion
     */
    public NumeroComplejo multiplicar(NumeroComplejo multiplicador, 
            boolean nuevo) {
        if (multiplicador == null) {
            if (nuevo) {
                return new NumeroComplejo(this.parteReal, this.parteImaginaria);
            }
            return this;
        }

        float auxReal = this.parteReal * multiplicador.parteReal - 
                this.parteImaginaria * multiplicador.parteImaginaria;
        float auxImaginaria = this.parteReal * multiplicador.parteImaginaria + 
                this.parteImaginaria * multiplicador.parteReal;
        if (nuevo) {
            return new NumeroComplejo(auxReal, auxImaginaria);
        }
        this.parteReal = auxReal;
        this.parteImaginaria = auxImaginaria;
        return this;
    }
    
    /**
     * Metodo que multiplica un numero complejo con una constante 
     * this*multiplicador. 
     * Si nuevo= true regresa el resultado en un nuevo objeto
     * @param multiplicador Constante multiplicador
     * @param nuevo Nueva multiplicacion
     * @return resultado de la multiplicacion
     */
    public NumeroComplejo multiplicar(float multiplicador, boolean nuevo) {
        if (nuevo) {
            return new NumeroComplejo(this.parteReal * multiplicador, 
                    this.parteImaginaria * multiplicador);
        }
        this.parteReal *= multiplicador;
        this.parteImaginaria *= multiplicador;
        return this;
    }
    
    /**
     * Metodo que multiplica un numero complejo con un numero real
     * @param c Numero complejo
     * @param d Numero real
     * @return Multiplicacion entre numero complejo y numero real
     */
    public static NumeroComplejo producto(NumeroComplejo c, float d){
       float x=c.parteReal*d;
       float y=c.parteImaginaria*d;
       return new NumeroComplejo(x, y);
    }
    
    /**
     * Metodo que multiplica dos numeros complejos
     * @param c1 Numero complejo 1
     * @param c2 Numero complejo 2
     * @return Multiplicacion entre dos numeros complejos
     */
    public static NumeroComplejo productoComplejos(NumeroComplejo c1, 
            NumeroComplejo c2){
       float x=c1.parteReal*c2.parteReal-c1.parteImaginaria*c2.parteImaginaria;
       float y=c1.parteReal*c2.parteImaginaria+c1.parteImaginaria*c2.parteReal;
       return new NumeroComplejo(x, y);
    }
    
    /**
     * Metodo que divide un numero complejo con otro this/divisor.
     * Si nuevo= true regresa el resultado en un nuevo objeto
     * @param divisor Numero complejo divisor
     * @param nuevo Nueva division
     * @return resultado de la division
     */
    public NumeroComplejo dividir(NumeroComplejo divisor, boolean nuevo) {
        if (divisor == null) {
            if (nuevo) {
                return new NumeroComplejo(this.parteReal, this.parteImaginaria);
            } else {
                return this;
            }
        }
        float sumBase = (float) (Math.pow(divisor.parteReal, 2) + 
                Math.pow(divisor.parteImaginaria, 2));
        float auxReal = (this.parteReal * divisor.parteReal + 
                this.parteImaginaria * divisor.parteImaginaria) / sumBase;
        float auxImaginaria = (this.parteReal * divisor.parteImaginaria - 
                this.parteImaginaria * divisor.parteReal) / sumBase;
        if (nuevo) {
            return new NumeroComplejo(auxReal, auxImaginaria);
        }
        this.parteReal = auxReal;
        this.parteImaginaria = auxImaginaria;
        return this;
    }
    
    /**
     * Metodo que realiza la division entre un numero complejo y un numero real
     * @param c Numero complejo
     * @param d Numero real
     * @return Division entre numero complejo y numero real
     * @throws optiuam.bc.model.ExcepcionDivideCero Lanza una excepcion si se
     * divide entre cero
     */
    public static NumeroComplejo cociente(NumeroComplejo c, float d)throws 
            ExcepcionDivideCero{
      float x, y;
      if(d==0.0){
            throw new ExcepcionDivideCero("Divide entre cero");
      }else{
          x=c.parteReal/d;
          y=c.parteImaginaria/d;
      }
       return new NumeroComplejo(x, y);
    }
    
    /**
     * Metodo que regresa un nuevo numero complejo conjugado o el mismo.
     * Si nuevo= true regresa el resultado en un nuevo objeto
     * @param nuevo Nueva conjugacion
     * @return resultado de conjugacion
     */
    public NumeroComplejo conjugar(boolean nuevo) {
        if (nuevo) {
            return new NumeroComplejo(this.parteReal, -this.parteImaginaria);
        }
        this.parteImaginaria = -this.parteImaginaria;
        return this;
    }
    
    //el número e elevado a un número complejo
    public static NumeroComplejo exponencial(NumeroComplejo c){
       float x=(float) (Math.cos(c.parteImaginaria)*Math.exp(c.parteReal));
       float y=(float) (Math.sin(c.parteImaginaria)*Math.exp(c.parteReal));
       return new NumeroComplejo(x, y);
    }
    
    
    /**
     * Metodo que muestra la parte real del numero complejo
     * @return parte real del numero complejo
     */
    public float getRealPart() {
        return parteReal;
    }

    /**
     * Metodo que modifica la parte real del numero complejo
     * @param realPart Parte real del numero complejo
     */
    public void setRealPart(float realPart) {
        this.parteReal = realPart;
    }
    
    /**
     * Metodo que muestra la parte imaginaria del numero complejo
     * @return parte imaginaria del numero complejo
     */
    public float getImaginaryPart() {
        return parteImaginaria;
    }

    /**
     * Metodo que modifica la parte imaginaria del numero complejo
     * @param imaginaryPart Parte imaginaria del numero complejo
     */
    public void setImaginaryPart(float imaginaryPart) {
        this.parteImaginaria = imaginaryPart;
    }
    
    /**
     * Metodo que modifica los valores del numero complejo
     * @param real Parte real
     * @param imaginaria Parte imaginaria
     */
    public void setValores(float real, float imaginaria) {
        this.parteReal = real;
        this.parteImaginaria = imaginaria;
    }
    
     /**
     * Asigna el valor de 0 al numero complejo
     */
    public void Limpiar() {
        this.parteReal = 0;
        this.parteImaginaria = 0;
    }
    
     /**
      * Metodo que retorna un nuevo numero complejo por X numero complejo
     * @param b Numero complejo muliplicador
     * @return Nuevo numero complejo
      */
    public NumeroComplejo times(NumeroComplejo b) {
        NumeroComplejo a = this;
        float real = a.parteReal * b.parteReal - a.parteImaginaria * 
                b.parteImaginaria;
        float imag = a.parteReal * b.parteImaginaria + a.parteImaginaria * 
                b.parteReal;
        return new NumeroComplejo(real, imag);
    }

    /**
     * Metodo que retorna este numero complejo mas X numero complejo
     * @param b Numero complejo sumador
     * @return Nuevo numero complejo
     */
    public NumeroComplejo plus(NumeroComplejo b) {
        NumeroComplejo a = this;
        float real = a.parteReal + b.parteReal;
        float imag = a.parteImaginaria + b.parteImaginaria;
        return new NumeroComplejo((float)real, (float)imag);
    }
    
    /**
     * Metodo que retorna este numero complejo menos X numero complejo
     * @param b Numero complejo restador
     * @return Nuevo numero complejo
     */
    public NumeroComplejo minus(NumeroComplejo b) {
        NumeroComplejo a = this;
        double real = a.parteReal - b.parteReal;
        double imag = a.parteImaginaria - b.parteImaginaria;
        return new NumeroComplejo((float)real, (float)imag);
    }
    
    /**
     * Metodo que retorna este numero complejo por un angulo
     * @param alpha Angulo multiplicador
     * @return Nuevo numero complejo
     */
    public NumeroComplejo scale(float alpha) {
        return new NumeroComplejo(alpha * parteReal, alpha * parteImaginaria);
    }

    /**
     * Metodo toString que retorna la parte real e imaginaria del numero 
     * complejo
     * @return parte real e imaginaria del numero complejo
     */
    @Override
    public String toString() {
        if (parteImaginaria < 0) {
            return parteReal + "" + parteImaginaria + "i";
        }
        return parteReal + "+" + parteImaginaria + "i";
    }
    
}