/**
 * this class Cons implements a Lisp-like Cons cell
 * 
 * @author  Gordon S. Novak Jr.
 * @version 29 Nov 01; 25 Aug 08; 05 Sep 08; 08 Sep 08; 12 Sep 08; 24 Sep 08
 *          06 Oct 08; 07 Oct 08; 09 Oct 08; 27 Mar 09; 18 Mar 11; 09 Oct 13
 */

import java.util.StringTokenizer;

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
    // access functions for expression representation
    public static Object op  (Cons x) { return first(x); }
    public static Object lhs (Cons x) { return first(rest(x)); }
    public static Object rhs (Cons x) { return first(rest(rest(x))); }
    public static boolean numberp (Object x)
       { return ( (x != null) &&
                  (x instanceof Integer || x instanceof Double) ); }
    public static boolean integerp (Object x)
       { return ( (x != null) && (x instanceof Integer ) ); }
    public static boolean floatp (Object x)
       { return ( (x != null) && (x instanceof Double ) ); }
    public static boolean stringp (Object x)
       { return ( (x != null) && (x instanceof String ) ); }

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

// member returns null if requested item not found
public static Cons member (Object item, Cons lst) {
  if ( lst == null )
     return null;
   else if ( item.equals(first(lst)) )
           return lst;
         else return member(item, rest(lst)); }

public static Cons union (Cons x, Cons y) {
  if ( x == null ) return y;
  if ( member(first(x), y) != null )
       return union(rest(x), y);
  else return cons(first(x), union(rest(x), y)); }

    // combine two lists: (append '(a b) '(c d e))  =  (a b c d e)
public static Cons append (Cons x, Cons y) {
  if (x == null)
     return y;
   else return cons(first(x),
                    append(rest(x), y)); }

    // look up key in an association list
    // (assoc 'two '((one 1) (two 2) (three 3)))  =  (two 2)
public static Cons assoc(Object key, Cons lst) {
  if ( lst == null )
     return null;
  else if ( key.equals(first((Cons) first(lst))) )
      return ((Cons) first(lst));
          else return assoc(key, rest(lst)); }

    public static int square(int x) { return x*x; }
    public static int pow (int x, int n) {        // x to the power n
        if ( n <= 0 ) return 1;
        if ( (n & 1) == 0 )
            return square( pow(x, n / 2) );
        else return x * pow(x, n - 1); }

public static Object reader(String str) {
    return readerb(new StringTokenizer(str, " \t\n\r\f()'", true)); }

public static Object readerb( StringTokenizer st ) {
    if ( st.hasMoreTokens() ) {
        String nexttok = st.nextToken();
        if ( nexttok.charAt(0) == ' ' ||
             nexttok.charAt(0) == '\t' ||
             nexttok.charAt(0) == '\n' ||
             nexttok.charAt(0) == '\r' ||
             nexttok.charAt(0) == '\f' )
            return readerb(st);
        if ( nexttok.charAt(0) == '(' )
            return readerlist(st);
        if ( nexttok.charAt(0) == '\'' )
            return list("QUOTE", readerb(st));
        return readtoken(nexttok); }
    return null; }

    public static Object readtoken( String tok ) {
        if ( (tok.charAt(0) >= '0' && tok.charAt(0) <= '9') ||
             ((tok.length() > 1) &&
              (tok.charAt(0) == '+' || tok.charAt(0) == '-' ||
               tok.charAt(0) == '.') &&
              (tok.charAt(1) >= '0' && tok.charAt(1) <= '9') ) ||
             ((tok.length() > 2) &&
              (tok.charAt(0) == '+' || tok.charAt(0) == '-') &&
              (tok.charAt(1) == '.') &&
              (tok.charAt(2) >= '0' && tok.charAt(2) <= '9') )  ) {
            boolean dot = false;
            for ( int i = 0; i < tok.length(); i++ )
                if ( tok.charAt(i) == '.' ) dot = true;
            if ( dot )
                return Double.parseDouble(tok);
            else return Integer.parseInt(tok); }
        return tok; }

public static Cons readerlist( StringTokenizer st ) {
    if ( st.hasMoreTokens() ) {
        String nexttok = st.nextToken();
        if ( nexttok.charAt(0) == ' ' ||
             nexttok.charAt(0) == '\t' ||
             nexttok.charAt(0) == '\n' ||
             nexttok.charAt(0) == '\r' ||
             nexttok.charAt(0) == '\f' )
            return readerlist(st);
        if ( nexttok.charAt(0) == ')' )
            return null;
        if ( nexttok.charAt(0) == '(' ) {
            Cons temp = readerlist(st);
            return cons(temp, readerlist(st)); }
        if ( nexttok.charAt(0) == '\'' ) {
            Cons temp = list("QUOTE", readerb(st));
            return cons(temp, readerlist(st)); }
        return cons( readtoken(nexttok),
                     readerlist(st) ); }
    return null; }

    // read a list of strings, producing a list of results.
public static Cons readlist( Cons lst ) {
    if ( lst == null )
        return null;
    return cons( reader( (String) first(lst) ),
                 readlist( rest(lst) ) ); }

    // You can use these association lists if you wish.
    public static Cons engwords = list(list("+", "sum"),
                                       list("-", "difference"),
                                       list("*", "product"),
                                       list("/", "quotient"),
                                       list("expt", "power"));

    public static Cons opprec = list(list("=", 1),
                                     list("+", 5),
                                     list("-", 5),
                                     list("*", 6),
                                     list("/", 6) );

    // ****** your code starts here ******

public static Integer maxbt (Object tree) 
{
	if (consp(tree) == true)
	{
		return Math.max(maxbt(((Object)first((Cons)tree))), maxbt((Object)rest((Cons)tree)));
	}
	else if (numberp(tree) == true)
	{
		return (Integer)tree;
	}
	else
	{
		return Integer.MIN_VALUE;
	}
}

public static Cons vars (Object expr) 
{
	if (consp(expr) == true)
	{
		return union(vars(lhs((Cons)expr)), vars(rhs((Cons)expr)));
	}
	else if (numberp(expr) == false)
	{
		return cons(expr,null);
	}
	else
	{
		return null;
	}
}

public static boolean occurs (Object value, Object tree) 
{
	if (consp(tree) == true)
	{
		return occurs(value,lhs((Cons)tree)) || occurs(value,rhs((Cons)tree));
	}
	else if (value.equals(tree))
	{
		return true;
	}
	else
	{
		return false;
	}
}

public static Integer eval (Object tree) 
{
	if (consp(tree) == true)
	{
		if (op((Cons)tree).equals("+"))
		{
			return eval(lhs((Cons)tree)) + eval(rhs((Cons)tree));
		}
		else if (op((Cons)tree).equals("-"))
		{
			if (op((Cons)tree).equals("-") && rhs((Cons)tree) == null)
			{
				return -(Integer)lhs((Cons)tree);
			}
			return eval(lhs((Cons)tree)) - eval(rhs((Cons)tree));
		}
		else if (op((Cons)tree).equals("*"))
		{
			return eval(lhs((Cons)tree)) * eval(rhs((Cons)tree));
		}
		else if (op((Cons)tree).equals("/"))
		{
			return eval(lhs((Cons)tree)) / eval(rhs((Cons)tree));
		}
		else if (op((Cons)tree).equals("expt"))
		{
			return pow(eval(lhs((Cons)tree)),eval(rhs((Cons)tree)));
		}
		else
		{
			return 0;
		}
	}
	else if (numberp(tree))
	{
		return (Integer)tree;
	}
	else
	{
		return 0;
	}
}

public static Integer eval (Object tree, Cons bindings) 
{
	if (consp(tree) == true)
	{
		if (op((Cons)tree).equals("+"))
		{
			return eval(lhs((Cons)tree),bindings) + eval(rhs((Cons)tree),bindings);
		}
		else if (op((Cons)tree).equals("-"))
		{
			if (op((Cons)tree).equals("-") && rhs((Cons)tree) == null)
			{
				if (stringp(lhs((Cons)tree)))
				{
					return -(Integer)second(assoc(lhs((Cons)tree),bindings));
				}
				return -(Integer)lhs((Cons)tree);
			}
			return eval(lhs((Cons)tree),bindings) - eval(rhs((Cons)tree),bindings);
		}
		else if (op((Cons)tree).equals("*"))
		{
			return eval(lhs((Cons)tree),bindings) * eval(rhs((Cons)tree),bindings);
		}
		else if (op((Cons)tree).equals("/"))
		{
			return eval(lhs((Cons)tree),bindings) / eval(rhs((Cons)tree),bindings);
		}
		else if (op((Cons)tree).equals("expt"))
		{
			return pow(eval(lhs((Cons)tree),bindings),eval(rhs((Cons)tree),bindings));
		}
		else
		{
			return 0;
		}
	}
	else if (stringp(tree))
	{
		return (Integer)second(assoc(tree,bindings));
	}
	else if (numberp(tree))
	{
		return (Integer)tree;
	}
	else
	{
		return 0;
	}
}

public static Cons english (Object tree)
{
	return englishb(tree);
}
public static Cons englishb (Object tree) 
{
	if (consp(tree))
	{
		if (op((Cons)tree).equals("+"))
		{
			Cons x = append(list("the",second(assoc(op((Cons)tree),engwords)),"of"),english(lhs((Cons)tree)));
			return append(x,append(list("and"),english(rhs((Cons)tree))));
		}
		else if (op((Cons)tree).equals("-"))
		{
			if (op((Cons)tree).equals("-") && rhs((Cons)tree) == null)
			{
				return append(list("negative"),english(lhs((Cons)tree)));
			}
			Cons x = append(list("the",second(assoc(op((Cons)tree),engwords)),"of"),english(lhs((Cons)tree)));
			return append(x,append(list("and"),english(rhs((Cons)tree))));
		}
		else if (op((Cons)tree).equals("*"))
		{
			Cons x = append(list("the",second(assoc(op((Cons)tree),engwords)),"of"),english(lhs((Cons)tree)));
			return append(x,append(list("and"),english(rhs((Cons)tree))));
		}
		else if (op((Cons)tree).equals("/"))
		{
			Cons x = append(list("the",second(assoc(op((Cons)tree),engwords)),"of"),english(lhs((Cons)tree)));
			return append(x,append(list("and"),english(rhs((Cons)tree))));
		}
		else if (op((Cons)tree).equals("expt"))
		{
			Cons x = append(list("the",second(assoc(op((Cons)tree),engwords)),"of"),english(lhs((Cons)tree)));
			return append(x,append(list("and"),english(rhs((Cons)tree))));
		}
		return append(english(lhs((Cons)tree)),english(rhs((Cons)tree)));
	}
	else if (stringp(tree) || numberp(tree))
	{
		return list(tree);
	}
	else
	{
		return null;
	}
}

public static String tojava (Object tree) 
{
   return (tojavab(tree, 0) + ";"); 
}
public static String tojavab (Object tree, int prec) 
