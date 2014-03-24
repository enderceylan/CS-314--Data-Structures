// libtest.java      GSN    03 Oct 08; 21 Feb 12
// 

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

interface Functor { Object fn(Object x); }

interface Predicate { boolean pred(Object x); }

public class libtest 
{

    // ****** your code starts here ******


public static Integer sumlist(LinkedList<Integer> lst) 
{
	LinkedList<Integer> lst1 = new LinkedList<Integer>(lst);
	return sumlistb(lst1,0);
}
public static Integer sumlistb(LinkedList<Integer> lst, Integer sum) 
{
	if (lst.getFirst().equals(lst.getLast()))
	{
		sum += lst.getFirst();
		return sum;
	}
	else
	{
		sum += lst.getFirst();
		lst.removeFirst();
		return sumlistb(lst,sum);
	}
}

public static Integer sumarrlist(ArrayList<Integer> lst) 
{
	ArrayList<Integer> lst1 = new ArrayList<Integer>(lst);
	return sumarrlistb(lst1,0);
}
public static Integer sumarrlistb(ArrayList<Integer> lst, Integer sum) 
{
	if (lst.get(0).equals(lst.get(lst.size()-1)))
	{
		sum += lst.get(0);
		return sum;
	}
	else
	{
		sum += lst.get(0);
		lst.remove(0);
		return sumarrlistb(lst,sum);
	}
}

public static LinkedList<Object> subset (Predicate p, LinkedList<Object> lst) 
{
	LinkedList<Object> lst1 = new LinkedList<Object>(lst);
	LinkedList<Object> newlst = new LinkedList<Object>();
	return subsetb(p,lst1,newlst);
}
public static LinkedList<Object> subsetb (Predicate p, LinkedList<Object> lst, LinkedList<Object> newlst) 
{
	ListIterator<Object> itr = lst.listIterator();
	while (itr.hasNext())
	{
		Object obj = itr.next();
		if (p.pred(obj) == true)
		{
			newlst.add(obj);
		}
		itr.remove();
	}
	return newlst;
}

public static LinkedList<Object> dsubset (Predicate p, LinkedList<Object> lst) 
{
	ListIterator<Object> itr = lst.listIterator();
	while (itr.hasNext())
	{
		Object obj = itr.next();
		if (p.pred(obj) == false)
		{
			itr.remove();
		}
	}
	return lst;
}

public static Object some (Predicate p, LinkedList<Object> lst) 
{
	LinkedList<Object> lst1 = new LinkedList<Object>(lst);
	return someb(p,lst1);
}
public static Object someb (Predicate p, LinkedList<Object> lst) 
{
	ListIterator<Object> itr = lst.listIterator();
	while (itr.hasNext())
	{
		Object obj = itr.next();
		if (p.pred(obj) == true)
		{
			return obj;
		}
		itr.remove();
	}
	return null;
}

public static LinkedList<Object> mapcar (Functor f, LinkedList<Object> lst) 
{
	LinkedList<Object> lst1 = new LinkedList<Object>(lst);
	LinkedList<Object> lst2 = new LinkedList<Object>();
	return mapcarb(f,lst1,lst2);
}
public static LinkedList<Object> mapcarb (Functor f, LinkedList<Object> lst, LinkedList<Object> newlst) 
{
	ListIterator<Object> itr = lst.listIterator();
	while (itr.hasNext())
	{
		Object obj = itr.next();
		newlst.add(f.fn(obj));
		itr.remove();
	}
	return newlst;
}

public static LinkedList<Object> merge (LinkedList<Object> lsta, LinkedList<Object> lstb) 
{
	LinkedList<Object> lst1 = new LinkedList<Object>(lsta);
	LinkedList<Object> lst2 = new LinkedList<Object>(lstb);
	LinkedList<Object> lstc = new LinkedList<Object>();
	lstc.add(new Integer(0));
	
	return mergeb(lst1,lst2,lstc);
}
public static LinkedList<Object> mergeb (LinkedList<Object> lsta, LinkedList<Object> lstb, LinkedList<Object> lstc) 
{
	if (lsta.size() == 0 || lstb.size() == 0)
	{
		if (lsta.size() != 0)
		{
			lstc.add(lsta.getFirst());
		}
		if (lstb.size() != 0)
		{
			lstc.add(lstb.getFirst());
		}
		lstc.removeFirst();
		return lstc;
	}
	else
	{
		if (((Comparable<Object>)lsta.getFirst()).compareTo((Comparable<Object>)lstb.getFirst()) < 0)
		{
			lstc.add(lsta.getFirst());
			lsta.removeFirst();
			return mergeb(lsta, lstb, lstc);
		}
			else
		{
			lstc.add(lstb.getFirst());
			lstb.removeFirst();
			return mergeb(lsta, lstb, lstc);
		}
	}
}

public static LinkedList<Object> sort (LinkedList<Object> lst) 
{
	LinkedList<Object> lst1 = new LinkedList<Object>();
	LinkedList<Object> lst2 = new LinkedList<Object>();
	ListIterator<Object> itr = lst.listIterator();
	int i = 0;
	int size = lst.size();
	double mid = lst.size()/2;
	if (size == 1)
	{
		return lst;
	}
	while (itr.hasNext())
	{
		Object obj;
		for (i = 0; i < Math.floor(mid); i++)
		{
			obj = itr.next();
			lst1.add(obj);
		}
		for (i = (int)Math.floor(mid); i < size; i++)
		{
			obj = itr.next();
			lst2.add(obj);
		}
		return merge(sort(lst1), sort(lst2));
	}
	return lst;
}

public static LinkedList<Object> intersection (LinkedList<Object> lsta, LinkedList<Object> lstb) 
{
	LinkedList<Object> lst1 = new LinkedList<Object>(sort(lsta));
	LinkedList<Object> lst2 = new LinkedList<Object>(sort(lstb));
	LinkedList<Object> lst3 = new LinkedList<Object>();
	return intersectionb (lst1,lst2,lst3);
}
public static LinkedList<Object> intersectionb (LinkedList<Object> lsta, LinkedList<Object> lstb, LinkedList<Object> lstc) 
{
	int sizea = lsta.size();
	int sizeb = lstb.size();
	if (sizea == 0 || sizeb == 0)
	{
		return lstc;
	}
	else
	{
		if (((Comparable<Object>)lsta.getFirst()).equals((Comparable<Object>)lstb.getFirst()))
		{
			lstc.add(lsta.getFirst());
			lstb.removeFirst();
			return intersectionb(lsta, lstb, lstc);
		}
			else
		{
			lsta.removeFirst();
			return intersectionb(lsta, lstb, lstc);
		}
	}
}

public static LinkedList<Object> reverse (LinkedList<Object> lst) 
{
	LinkedList<Object> newlst = new LinkedList<Object>();
	newlst.add(new Integer(0));
	return reverseb(lst,newlst);
}
public static LinkedList<Object> reverseb (LinkedList<Object> lst, LinkedList<Object> newlst) 
{
	if (lst.size() == 0)
	{
		newlst.removeFirst();
		return newlst;
	}
	else
	{
		newlst.add(lst.getLast());
		lst.removeLast();
		return reverseb(lst,newlst);
	}
}

    // ****** your code ends here ******

    public static void main(String args[]) {
        LinkedList<Integer> lst = new LinkedList<Integer>();
        lst.add(new Integer(3));
        lst.add(new Integer(17));
        lst.add(new Integer(2));
        lst.add(new Integer(5));
        System.out.println("lst = " + lst);
        System.out.println("sum = " + sumlist(lst));

        ArrayList<Integer> lstb = new ArrayList<Integer>();
        lstb.add(new Integer(3));
        lstb.add(new Integer(17));
        lstb.add(new Integer(2));
        lstb.add(new Integer(5));
        System.out.println("lstb = " + lstb);
        System.out.println("sum = " + sumarrlist(lstb));

        final Predicate myp = new Predicate()
            { public boolean pred (Object x)
                { return ( (Integer) x > 3); }};

        LinkedList<Object> lstc = new LinkedList<Object>();
        lstc.add(new Integer(3));
        lstc.add(new Integer(17));
        lstc.add(new Integer(2));
        lstc.add(new Integer(5));
        System.out.println("lstc = " + lstc);
        System.out.println("subset = " + subset(myp, lstc));

        System.out.println("lstc     = " + lstc);
        System.out.println("dsubset  = " + dsubset(myp, lstc));
        System.out.println("now lstc = " + lstc);

        LinkedList<Object> lstd = new LinkedList<Object>();
        lstd.add(new Integer(3));
        lstd.add(new Integer(17));
        lstd.add(new Integer(2));
        lstd.add(new Integer(5));
        System.out.println("lstd = " + lstd);
        System.out.println("some = " + some(myp, lstd));

        final Functor myf = new Functor()
            { public Integer fn (Object x)
