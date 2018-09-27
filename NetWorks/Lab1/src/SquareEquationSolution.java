/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author myfamily
 */
public final  class SquareEquationSolution {
    private double x1;
    private double x2;
    private boolean isComplex; // решение одно -- комплексное, при этом x1 = Re, x2 = Im;
                               // 
                               //
    private boolean isOnlySolution; // единственное решение и при том некомплексное
                                    // 
                                    //

    public double getx1() {
        return x1;
    }

    public double getx2() {
        return x2;
    }

    public boolean getisComplex() {
        return isComplex;
    }

    public boolean getisOnlySolution() {
        return isOnlySolution;
    }

    public void setx1(double x) {
        x1 = x;
    }

    public void setx2(double x) {
        x2 = x;
    }

    public void setisComplex(boolean complex) {
        isComplex = complex;
    }

    public void setisOnlySolution(boolean isOnly) {
        isOnlySolution = isOnly;
    }

    public void setisOnlySolution() {
        if (Main.DoubleEqual (x1, x2)) {
            isOnlySolution = true;
        } else {
            isOnlySolution = false;
        }
    }

    public SquareEquationSolution() {
        x1 = 0;
        x2 = 0;
        isComplex = false;
        this.setisOnlySolution();
    }
    public SquareEquationSolution(double X, double Y, boolean complex, boolean onlysolution)
    {
        x1 =X;
        x2 =Y;
        isComplex = complex;
        isOnlySolution = onlysolution;
    }
    public SquareEquationSolution(double X, double Y, boolean complex) {
        x1 = X;
        x2 = Y;
        isComplex = complex;
        isOnlySolution = false;
    }

    public SquareEquationSolution(double X) {
        x1 = X;
        isComplex = false;
        isOnlySolution = true;
    }

    @Override
    public String  toString() {
        if (isOnlySolution == true) {
            return ("" + x1);
        } else {
            if (isComplex == false) {
                return ("x1 = " + x1 + " x2 = " + x2);
            } else 
            {
                return ("x1 = " +x1 + " + " + Math.abs(x2) + "i \t"+ "x2 = " +x1 + " - " + Math.abs(x2) + "i");
            }
        }
    }
    /**
     * Решает квадратное уравнение вида aX^2+bX+c=0
     * @param a Коэфц-т при X^2
     * @param b Коэфц-т при X
     * @param c Свободный член
     * @return решение квадратного уравнения одним или двумя вещественнными значениями или парой мнимых @see #SquareEquationSolution
     * @throws ArithmeticException Исключения с соответствующими сообщениями, если возник континнум решений или если уравнения имеет вид k == 0 , k э R
     */
    public static SquareEquationSolution SolveSquareEquation(double a, double b, double c) throws ArithmeticException
    {
        if (Main.DoubleEqual(a, 0) && Main.DoubleEqual(b, 0) && Main.DoubleEqual(c, 0) ) 
        {
            throw new ArithmeticException("Уравнение имеет вид 0*X=0 => континум решений."+"\n"+"/*---------------*/");
        }
        if (Main.DoubleEqual(a, 0) && Main.DoubleEqual(b, 0) ) 
        {
            throw new ArithmeticException("Уравнение выродилось в случай число == 0, решений нет, отмена."+"\n"+"/*---------------*/");
        }
        

        double D = (b*b)-4*a*c;
        double x1=0,x2=0;
        boolean isOnlySolution = false, isComplex = false;
        if  (Main.DoubleEqual(a,0))
        {
            x1 = Main.SolveLinearEquation(b, -c);
            x2 =x1;
            isOnlySolution = true;
            return new SquareEquationSolution(x1,x2, isComplex, isOnlySolution );
        }
        if (Main.DoubleEqual(D,0))
        {
         x1 = -b/(2*a);
         x2=x1;
         isOnlySolution = true;
         
        }
        else if (D > 0)
        {
             x1 = (-b + Math.sqrt(D))/(2*a);
             x2 = (-b - Math.sqrt(D))/(2*a);
             
        }
        else if (D<0)
        {
             x1 = (-b + Math.sqrt(Math.abs(D)))/(2*a);
             x2 = (-b - Math.sqrt(Math.abs(D)))/(2*a);
             isComplex = true;
            
        }
        return new SquareEquationSolution(x1,x2, isComplex, isOnlySolution );
    }

}
