/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 12 Sep 08; 16 Feb 09
 *          01 Feb 12; 08 Feb 12
 */

interface Functor { Object fn(Object x); }

interface Predicate { boolean pred(Object x); }

public class Cons
{
    // instance variables
    private Object car;
    private Cons cdr;
    private Cons(Object first, Cons rest)
       { car = first;
         cdr = rest; }
    public static Cons cons(Object first, Cons rest)
      { return new Cons(first, rest); }
    public static boolean consp (Object x)
       { return ( (x != null) && (x instanceof Cons) ); }
// safe car, returns null if lst is null
    public static Object first(Cons lst) {
        return ( (lst == null) ? null : lst.car  ); }
// safe cdr, returns null if lst is null
    public static Cons rest(Cons lst) {
      return ( (lst == null) ? null : lst.cdr  ); }
    public static Object second (Cons x) { return first(rest(x)); }
    public static Object third (Cons x) { return first(rest(rest(x))); }
    public static void setfirst (Cons x, Object i) { x.car = i; }
    public static void setrest  (Cons x, Cons y) { x.cdr = y; }
   public static Cons list(Object ... elements) {
       Cons list = null;
       for (int i = elements.length-1; i >= 0; i--) {
           list = cons(elements[i], list);
       }
       return list;
   }

    // convert a list to a string for printing
    public String toString() {
       return ( "(" + toStringb(this) ); }
    public static String toString(Cons lst) {
       return ( "(" + toStringb(lst) ); }
    private static String toStringb(Cons lst) {
       return ( (lst == null) ?  ")"
                : ( first(lst) == null ? "()" : first(lst).toString() )
                  + ((rest(lst) == null) ? ")" 
                     : " " + toStringb(rest(lst)) ) ); }

    public static int square(int x) { return x*x; }

    // ****** your code starts here ******
public static double dsquare(double x) { return x*x; }
    // iterative version
public static int sum (Cons lst) 
{
    int sum = 0;
    while (rest(lst) != null)
    {
        sum += (Integer)first(lst);
        lst = rest(lst);
    }
    sum += (Integer)first(lst);
    return sum;
}

    //gets list length
public static int length (Cons lst)
{
    return lengthb(0,lst);
}
public static int lengthb (int n, Cons lst)
{
    if (rest(lst) == null)
    {
        n++;
        return n;
    }
    else
    {
        lst = rest(lst);
        return lengthb(++n,lst);
    }
}

    // mean = (sum x[i]) / n  
public static double mean (Cons lst) 
{
    double mean = (double)sum(lst)/(double)length(lst);
    return mean;
}

    // square of the mean = mean(lst)^2  

    // mean square = (sum x[i]^2) / n  
public static double meansq (Cons lst) 
{
    Cons newlst = meansqb(null,lst);
    double meansq = (double)(sum(newlst)) / (double)length(lst);
    return meansq;
}
public static Cons meansqb (Cons newlst, Cons lst)
{
    Cons last = newlst;
    if (rest(lst) == null)
    {
        newlst = cons(square((Integer)first(lst)),last);
        return newlst;
    }
    else
    {
        newlst = cons(square((Integer)first(lst)),last);
        lst = rest(lst);
        return meansqb(newlst,lst);
    }
}

public static double variance (Cons lst) 
{
    double variance = meansq(lst) - dsquare(mean(lst));
    return variance;
}

public static double stddev (Cons lst) 
{
    double stddev = Math.sqrt(variance(lst));
    return stddev;
}

public static double sine (double x) 
{
    return sineb(x, 0, 1, 1);
}
public static double sineb (double x, double sum, double i, double j)
{
    if (i == 11)
    {
        sum += sinec (x,1,1,j);
        return sum;
    }
    else
    {
        if (i%2 == 1)
            sum += sinec (x,1,1,j);
        else
            sum += -sinec (x,1,1,j);
        j += 2;
        return sineb(x,sum,++i,j);
    }
}
public static double sinec (double x, double sum, double i, double j)
{
    if (i==j)
    {
        sum = sum * (x/i);
        return sum;
    }
    else
    { 
        sum = sum * (x/i);
        return sinec(x,sum,++i,j);
    }
}

    // public static double sineb ( ... )

public static Cons nthcdr (int n, Cons lst) 
{
    if (n>length(lst))
    {
        return null;
    }
    if (n==0)
    {
        return lst;
    }
    else
    {
        return nthcdr(--n,rest(lst));
    }
}

public static Object elt (Cons lst, int n) 
{
    return first(nthcdr(n,lst));
}

public static double interpolate (Cons lst, double x) 
{
    return interpolateb(lst,x,1);
}
public static double interpolateb (Cons lst, double x, double i)
{
    if (x < i)
    {
        return ((Integer)first(lst)) + (x-(i-1))*((double)((Integer)second(lst)-(Integer)first(lst)));
    }
    else
    {
        lst = rest(lst);
        return interpolateb(lst,x,++i);
    }
}

public static int sumtr (Cons lst)
{
    return sumtrb(0,lst);
}
public static int sumtrb (int sum, Cons lst)
{
    if (consp(first(lst)) == true)
    {
        sum = sumtrb(sum,(Cons)first(lst));
        lst = rest(lst);
        return sumtrb(sum,lst);
    }
    if (consp(first(lst)) == false && first(lst) != null)
    {
        sum += (Integer)first(lst);
        lst = rest(lst);
        return sumtrb(sum,lst);
    }
    else
    {
        return sum;
    }
}

    // use auxiliary functions as you wish.
public static Cons append (Cons x, Cons y) {
        Cons front = null;
        Cons back = null;
        Cons cell;
        if ( x == null ) return y;
        for ( ; x != null ; x = rest(x) ) {
             cell = cons(first(x), null);
             if ( front == null )
                 front = cell;
             else setrest(back, cell);
             back = cell; }
        setrest(back, y);
        return front; }   
public static Cons subseq (Cons lst, int start, int end) 
{
    return subseqb(null,lst,start,end);
}
public static Cons subseqb (Cons newlst, Cons lst, int start, int end) 
{
    Cons last = cons(first(lst),null);
    if (length(lst)==start)
    {
        return newlst;
    }
    if (length(lst)<=end)
    {
        
        newlst = append(newlst,last);
        lst=rest(lst);
        return subseqb(newlst,lst,start,end);
    }
    else
    {
        lst=rest(lst);
        return subseqb(newlst,lst,start,end);
    }
}

public static Cons posfilter (Cons lst) 
{
    Cons newlst = null;
    return posfilterb(newlst,lst);
}
public static Cons posfilterb (Cons newlst, Cons lst)
{
    Cons last = cons(first(lst),null);
    if (rest(lst) == null)
    {
        if ((Integer)first(lst) >= 0)
        {
            newlst = append(newlst,last);
        }
        return newlst;
    }
    else
    {
        if ((Integer)first(lst) >= 0)
        {
           newlst = append(newlst,last);
        }
        lst = rest(lst);
        return posfilterb(newlst,lst);
    }
}

public static Cons subset (Predicate p, Cons lst) 
{
    Cons newlst = null;
    return subsetb(newlst,p,lst);
}
public static Cons subsetb (Cons newlst, Predicate p, Cons lst) 
{
    Cons last = cons(first(lst),null);
    if (rest(lst)==null)
    {
        if (p.pred(first(lst)) == true)
        {
            newlst = append(newlst,last);
        }
        return newlst;
    }
    else
    {
        if (p.pred(first(lst)) == true)
        {
            newlst = append(newlst,last);
        }
        lst = rest(lst);
        return subsetb(newlst,p,lst);
    }
}

public static Cons mapcar (Functor f, Cons lst) 
{
    Cons newlst = null;
    return mapcarb(newlst,f,lst);
}
public static Cons mapcarb (Cons newlst, Functor f, Cons lst)
{
    Cons last = cons(f.fn((Integer)first(lst)),null);
    if (rest(lst)==null)
    {
        newlst = append(newlst,last);
        return newlst;
    }
    else
    {
        newlst = append(newlst,last);
        lst = rest(lst);
        return mapcarb(newlst,f,lst);
    }
}

public static Object some (Predicate p, Cons lst) 
{
    if (p.pred(first(lst)) == true)
    {
        return first(lst);
    }
    else
    {
        lst = rest(lst);
        return some(p,lst);
    }
}

public static boolean every (Predicate p, Cons lst) 
{
    if (first(lst)==null)
    {
        return true;
    }
    if (rest(lst)==null && p.pred(first(lst)) == true)
    {
        return true;
    }
    if (p.pred(first(lst)) == false)
    {
        return false;
    }
    else
    {
        lst = rest(lst);
        return every(p,lst);
    }
}

// Make a list of Binomial coefficients
    // binomial(2) = (1 2 1)
    public static Cons binomial(int n) 
    {
        if (n==1)
        {
            return cons(1, null);
        }
        else
        {
            Cons lst = new Cons(1, list(1));
            Cons newlst = new Cons (1,null);
            return binomialb(n,lst,newlst);
        }
    }
    public static Cons binomialb(int n, Cons lst, Cons newlst) 
    {
        if (n == 1)
        {
            return lst;
        }
        else
        {
            lst = binomialadd(lst,newlst);
            return binomialb(n-1,lst,newlst);
        }
    }
    public static Cons binomialadd (Cons lst, Cons newlst)
    {
        Integer x;
        if (first(lst) == null)
        {
            return newlst;
        }
        else
        {
            if (second(lst) == null)
                x = (Integer)first(lst);
            else
                x = (Integer)first(lst) + (Integer)second(lst);
            newlst = cons(x, newlst);
            lst = rest(lst);
            return binomialadd(lst,newlst);
        }
    }
    // ****** your code ends here ******

    public static void main( String[] args )
      { 
        Cons mylist =
            list(95, 72, 86, 70, 97, 72, 52, 88, 77, 94, 91, 79,
                 61, 77, 99, 70, 91 );
        System.out.println("mylist = " + mylist.toString());
        System.out.println("sum = " + sum(mylist));
        System.out.println("mean = " + mean(mylist));
        System.out.println("meansq = " + meansq(mylist));
        System.out.println("variance = " + variance(mylist));
        System.out.println("stddev = " + stddev(mylist)); 
        System.out.println("sine(0.5) = " + sine(0.5));  // 0.47942553860420301
        System.out.print("nthcdr 5 = ");
        System.out.println(nthcdr(5, mylist));
        System.out.print("nthcdr 18 = ");
        System.out.println(nthcdr(18, mylist));
        System.out.println("elt 5 = " + elt(mylist,5)); 
        Cons mylistb = list(0, 30, 56, 78, 96);
        System.out.println("mylistb = " + mylistb.toString());
        System.out.println("interpolate(3.4) = " + interpolate(mylistb, 3.4));
        Cons binom = binomial(12);
        System.out.println("binom = " + binom.toString());
        System.out.println("interpolate(3.4) = " + interpolate(binom, 3.4));

        Cons mylistc = list(1, list(2, 3), list(list(list(list(4)),
                                                     list(5)),
                                                6));
        System.out.println("mylistc = " + mylistc.toString());
        System.out.println("sumtr = " + sumtr(mylistc));

        Cons mylistd = list(0, 1, 2, 3, 4, 5, 6 );
        System.out.println("mylistd = " + mylistd.toString());
        System.out.println("subseq(2 5) = " + subseq(mylistd, 2, 5));

        Cons myliste = list(3, 17, -2, 0, -3, 4, -5, 12 );
        System.out.println("myliste = " + myliste.toString());
        System.out.println("posfilter = " + posfilter(myliste));

        final Predicate myp = new Predicate()
            { public boolean pred (Object x)
                { return ( (Integer) x > 3); }};

        System.out.println("subset = " + subset(myp, myliste).toString());

        final Functor myf = new Functor()
            { public Integer fn (Object x)
                { return  (Integer) x + 2; }};

        System.out.println("mapcar = " + mapcar(myf, mylistd).toString()); 

        System.out.println("some = " + some(myp, myliste).toString());

        System.out.println("every = " + every(myp, myliste));
      }

}
