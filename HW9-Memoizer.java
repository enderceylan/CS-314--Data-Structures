import java.util.HashMap;
//interface Functor { Object fn(Object x); }
public class Memoizer
{
	Functor f;
	HashMap<Object, Object> map;

	public Memoizer(Functor f1)
	{
		f = f1;
		map = new HashMap<Object, Object>();
	}
	public Object call(Object x)
	{
		if (map.containsKey(x))
		{
			return map.get(x);
		}
		else
		{
			Object y = f.fn(x);
			map.put(x,y);
			return y;
		}
	}
}
