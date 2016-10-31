package examples.Chapter4.Item13.staticfactory;

// Static factory version of complex class - Page 68
public class Complex {
    private final float re;
    private final float im;

    private Complex(float re, float im) {
        this.re = re;
        this.im = im;
    }

    public static Complex valueOf(float re, float im) {
        return new Complex(re, im);
    }

    public static Complex valueOfPolar(float r, float theta) {
        return new Complex((float) (r * Math.cos(theta)),
                           (float) (r * Math.sin(theta)));
    }

    // Accessors with no corresponding mutators
    public float realPart()      { return re; }
    public float imaginaryPart() { return im; }

    public Complex add(Complex c) {
        return new Complex(re + c.re, im + c.im);
    }

    public Complex subtract(Complex c) {
        return new Complex(re - c.re, im - c.im);
    }

    public Complex multiply(Complex c) {
        return new Complex(re*c.re - im*c.im,
                           re*c.im + im*c.re);
    }

    public Complex divide(Complex c) {
        float tmp = c.re*c.re + c.im*c.im;
        return new Complex((re*c.re + im*c.im)/tmp,
                           (im*c.re - re*c.im)/tmp);
    }

    public boolean equals(Object o) {
       if (o == this)
           return true;
       if (!(o instanceof Complex))
           return false;
       Complex c = (Complex)o;
       return (Float.floatToIntBits(re) ==    // See page 33 to
               Float.floatToIntBits(c.re)) && // find out why
              (Float.floatToIntBits(im) ==    // floatToIntBits
               Float.floatToIntBits(im));     // is used.
    }
    public int hashCode() {
        int result = 17 + Float.floatToIntBits(re);
        result = 37*result + Float.floatToIntBits(im);
        return result;
    }

    public String toString() {
        return "(" + re + " + " + im + "i)";
    }

    // Public constants - Page 66
    public static final Complex ZERO = new Complex(0, 0);
    public static final Complex ONE  = new Complex(1, 0);
    public static final Complex I  =   new Complex(0, 1);

    public static void main(String args[]) {
        Complex x = Complex.valueOf(2, 3);
        Complex y = Complex.valueOf(2,-3);
        System.out.println(x + " + " + y + " = " + x.add(y));
        System.out.println(x + " - " + y + " = " + x.subtract(y));
        System.out.println(x + " * " + y + " = " + x.multiply(y));
        System.out.println(x + " / " + y + " = " + x.divide(y));
        System.out.println(x.divide(y).multiply(y));

        Complex z = Complex.valueOfPolar(1, (float) (Math.PI/4));
        Complex w = Complex.valueOf(z.realPart(), -z.imaginaryPart());
        System.out.println(z + " * " + w + " = " + z.multiply(w));
    }
}
