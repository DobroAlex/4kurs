
import java.util.Scanner;
import static java.lang.Double.parseDouble;



public class Main {
    
    public static void main(String[] args) 
    {
        double a = 0, b = 0, c = 0;   /*коэффициенты квадратного уравнения*/
        try 
        {
            if (!IsArrayNullOrEmpty(args)) 
            { /*Проверяем, есть ли хоть один параметр с консоли*/

                double[] input = new double[args.length];
                input = GetInputFromConsole(args); 
                a = input[0]; /*присваиваем весь ненулевой ввод коэф-ам*/
                b = input[1];
                c = input[2];
            } 
            else 
            {   /*Если с консоли ничего не введено, то получаем коэффициенты с клавиатуры*/
                double[] input = HandleInputFromKeyBoard();
                a = input[0];
                b = input[1];
                c = input[2];
            }

        } 
        catch (Exception ex) 
        {
            System.out.println(String.format("Среди введных чисел имеется нечисленный ввод, проверьте правильность, завершение. \n %s \n %s \n %s", ex.toString(), ex.getMessage(), ex.getCause())+"\n"+"/*---------------*/");
                    
            return;
        } 
        
        try 
        {

            System.out.println("Решается ур-ние:"+Signum(a)+"x^2"+Signum(b)+"x"+c+"=0");
            
            System.out.println("Решение: " + (SquareEquationSolution.SolveSquareEquation(a,b,c)));
            System.out.println("/*---------------*/");
        } 
        catch (Exception ex) 
        {
            System.out.println(ex.getMessage());
            return ;
        }
        
    }

    /**
     * Анализатор ввода с консоли, при его налчии заполняет массив соответсвующими занченями 
     * @param args - массив строк, переданный как stdin при вызове из консоли. Может быть пуст 
     * @return массив той же длины, что и <b>args</b>. При наличии соответсвующего аргумента, элемент массива заполняется, иначе 0.
     * 
     */
    public static double[] GetInputFromConsole(String[] args) throws Exception 
    {
        double[] parsedValues = new double[args.length];
        for (int i = 0; i < args.length; i++) 
        {
            if (!isStringNullOrEmpty(args[i])) 
            {   /*Если аргумент не пуст*/
                
                {
                    parsedValues[i] = parseDouble(args[i]);
                }
            } else 
            {
                parsedValues[i] = 0 ;
            }
        }
        return parsedValues;
    }
    
    /**
     * Проверяет массив объектов на null и пустоту.
     * @param array Массив входных данных для проверки
     * @return true, если массив пустой или состоит только из null элементов. False, если хотя бы один элемент не null
     */
    public static boolean IsArrayNullOrEmpty(Object[] array)  
    {
        if (array.length == 0) {
            return true;
        }
        boolean isNull = true;
        for (int i = 0; i < array.length; i++) 
        {
            if (array[i] != null) 
            {
                isNull = false;
                break;
            }
        }
        return isNull;
    }

    /**
     * Проверяет строку на пустоту или равенство null
     * @param str Строка для провекри
     * @return true, если строка пустая (=="") или равна null
     */
    public static boolean isStringNullOrEmpty(String str) 
    {
        if (str == null || "".equals(str)) 
        {
            return true;
        } else 
        {
            return false;
        }
    }

    /**
     * Обертка над java.lang.Double.compare(double,double)
     * @param d1 Первый операнд для сравнения
     * @param d2 Второй операнд для сравнения
     * @return true, если числа равны. Иначе false
     */
    public static boolean DoubleEqual(double d1, double d2) 
    {
        if (java.lang.Double.compare(d1, d2) == 0) 
        {
            return true;
        } 
        else 
        {
            return false;
        }
    }

    /**
     * Решает линейноу уравнение вида kX=b
     * @param k коэфц-т k
     * @param b константа b
     * @return корень уравнения
     * @throws ArithmeticException  Если уравнение имеет вид 0X*=0, то сообщение о бесконечном множестве решений 
     */
    public static double SolveLinearEquation(double k, double b) throws ArithmeticException 
    {
        if (DoubleEqual(k, 0) && DoubleEqual(b, 0)) 
        {
            throw new ArithmeticException("Уравнение имеет вид 0*X=0 => континум решений."+"\n"+"/*---------------*/");

        } else 
        {
            return b / k;
        }
    }

    /**
     * Анализатор ввода с клавиатуры при отсутствии ввода через параметры вызова приложения
     * @return массив введенных значений 
     */
    public static double[] HandleInputFromKeyBoard() 
    {
        int wrongElement  = 0;
        double[] input = new double[3];
        try {
            Scanner scanner = new Scanner (System.in);
            System.out.println("Введите a");
            input[0] = scanner.nextDouble();
            System.out.println("Введите b");
            wrongElement = 1;
            input[1] = scanner.nextDouble();
            System.out.println("Введите c");
            wrongElement = 2;
            input[2] = scanner.nextDouble();
            scanner.close();
        } 
        catch (Exception ex) {
            System.out.println("Ввод был нечисленным или непполным c эл-та "+wrongElement+", зануляем ");
            for (int i = wrongElement; i < input.length; i++)
            {
                input[i]  = 0;
            }
            //return HandleInputFromKeyBoard();
            
        }
        finally 
        {
            return input;
        }
    }
public static String Signum(double num)
{
    if (num > 0)
    {
        return "+"+Math.abs(num);
    }
    else if (num < 0)
    {
        return "-"+Math.abs(num);
    }
    else 
    {
        return "+0";
    }
}
    
}