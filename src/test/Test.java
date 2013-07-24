package test;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.CacheManager;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.ArrayUtils;

import com.fzw.utils.LogUtils;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.legaldaily.estension.ecard.model.law.LawCategory;

public class Test {
//	enum day{
//		sun,mon
//	}
//	static{
//
//		try {
//			XMLConfiguration xmlConfiguration = new XMLConfiguration("extensions.xml");
//			List extensionList =  xmlConfiguration.configurationsAt("extension");
//			for (Object object : extensionList) {
//				HierarchicalConfiguration extension = (HierarchicalConfiguration) object;
//				String name = extension.getString("name");
//				String init = extension.getString("init");
//				System.out.println(name+"  "+init);
//			}
//		} catch (ConfigurationException e) {
//			LogUtils.error("no ecommands.xml found");
//			System.exit(0);
//		}
//		
//	}
	public static void main(String[] args) {
//		System.out.println(day.mon.name());
//
//		System.out.println(ArrayUtils.contains(new String[]{"wait","publish","a"}, null));
		
//		CacheManager manager1 = new CacheManager();
//		CacheManager manager2 = new CacheManager();
//		manager1.addCache("abc");
//		manager2.getCache("abc");
//		System.out.println(manager1.getCache("abc"));
//		System.out.println(manager2.getCache("abc"));
		// String
		// aString="0101101010001010110101011001101011010100010101101010110011010110101000101011010101100110101110110101010101010101011111111000111111";
		// String
		// bString="1101101010001010110101011001101011010100010101101010110011010110101000101011010101100110101110110101010101010101011111111000111110";
		// BigInt bigInt = new BigInt(1);
		// bigInt.
		// byte [] b = bString.getBytes();
		// System.out.println(Integer.valueOf(""+'1', 2)
		// &Integer.valueOf(""+'1', 2));
		// for (int i = 0; i < b.length; i++) {
		// System.out.print(a[i] & b[i]);
		// }
		
		//-------------------------------
//		Gson gson = new GsonBuilder().registerTypeAdapter(Object.class,
//				new NaturalDeserializer()).create();
//		try {
//
//			Map map = gson.fromJson(
//					FileUtils.readFileToString(new File("/tmp/11.txt")),
//					HashMap.class);
//			System.out.println(map.keySet().iterator().next());
//		} catch (JsonSyntaxException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		//-------------------------------
//		List<String> strings = new ArrayList<String>();
//		strings.add("5");
//		strings.add("12");
//		strings.add("88");
//		strings.add("44");
//		strings.add("3355");
//		strings.add("5");
////		Object object = Collections.max(strings, Comparators.STRING_COMPARATOR_DESC_INTEGER);
////		System.out.println(object);
//		System.out.println(strings.size());
//		com.fzw.utils.ListUtils.subList(strings, 0, 2);
//		System.out.println(strings.size());
//		int id[] = new int[]{1,3,5,76,2};
//		id = ArrayUtils.removeElement(id, 1);
//		System.out.println(StringUtils.join(ArrayUtils.toObject(id),","));
		
//		Calendar calendar = Calendar.getInstance();
//		System.out.println(calendar.getTime());
//		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-1);
//		System.out.println(calendar.getTime());
		
		LawCategory lawCategory = new LawCategory();
		System.out.println( lawCategory.getCatId());
	}

	private static <T> T[] GetLongAction(List<T> list) {
		T t = null;
		Object[] object = list.toArray();
		Object[] oo = new Object[list.size()];

		for (int i = 0; i < object.length; i++) {
			oo[i] = (T) object[i];
		}
		return (T[]) oo;
	}

	public static void mianTest() {

		new ArrayList<String>().addAll(null);
		ListUtils.union(new ArrayList<String>(), new ArrayList<String>());
		System.out.println(new Date(0));
		Gson gson = new GsonBuilder().addSerializationExclusionStrategy(
				new ExclusionStrategy() {

					@Override
					public boolean shouldSkipField(FieldAttributes f) {
						// System.out.println("field--"+f.getDeclaredClass().getName());
						System.out.println(f.getName() + "--"
								+ f.getDeclaringClass().getName());
						// if(f.getDeclaredClass().getName().equals("test.B"))
						// return true;
						return false;
					}

					@Override
					public boolean shouldSkipClass(Class<?> clazz) {
						// if(clazz.getSimpleName().equals("B"))
						// //System.out.println("clazz--"+clazz.getSimpleName());
						// return true;
						return false;
					}
				}).create();
		A a = new A();
		a.t = "a";
		B b = new B();
		b.tt = "b";
		a.b = b;
		// b.a = a;
		System.out.println(gson.toJson(a));

		ComparatorChain cc = new ComparatorChain();
		cc.addComparator(new Comparator<Ct>() {

			@Override
			public int compare(Ct o1, Ct o2) {
				if (o1.getA() > o2.getA()) {
					return -1;
				} else if (o1.getA() == o2.getA()) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		cc.addComparator(new Comparator<Ct>() {

			@Override
			public int compare(Ct o1, Ct o2) {
				if (o1.getB() > o2.getB()) {
					return -1;
				} else if (o1.getB() == o2.getB()) {
					return 0;
				} else {
					return 1;
				}
			}
		});
		Ct ct1 = new Ct();
		ct1.setA(1);
		ct1.setB(5);

		Ct ct2 = new Ct();
		ct2.setA(1);
		ct2.setB(3);

		Ct ct3 = new Ct();
		ct3.setA(2);
		ct3.setB(4);
		Ct[] cccCts = new Ct[] { ct1, ct2, ct3 };
		Arrays.sort(cccCts, cc);
		for (Ct string : cccCts) {
			System.out.println(string);
		}

	}
}

class A {
	String t;
	B b;

	public String getT() {
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public B getA() {
		return b;
	}

	public void setA(B a) {
		this.b = a;
	}
}

class B {
	String tt;
	A a;

	public String getTt() {
		return tt;
	}

	public void setTt(String tt) {
		this.tt = tt;
	}

	public A getA() {
		return a;
	}

	public void setA(A a) {
		this.a = a;
	}
}

class Ct {
	int a, b;

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	@Override
	public String toString() {
		return a + "--" + b;
	}
}

abstract class C {
	protected static Object object = null;

	protected void doo() {
		System.out.println("cccc");
	}
}

class D extends C {
	private int sid;

	@Override
	protected void doo() {
		System.out.println("ddd");
		super.doo();
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}
}

class NaturalDeserializer implements JsonDeserializer<Object> {
	public Object deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) {
		if (json.isJsonNull())
			return null;
		else if (json.isJsonPrimitive())
			return handlePrimitive(json.getAsJsonPrimitive());
		else if (json.isJsonArray())
			return handleArray(json.getAsJsonArray(), context);
		else
			return handleObject(json.getAsJsonObject(), context);
	}

	private Object handlePrimitive(JsonPrimitive json) {
		if (json.isBoolean())
			return json.getAsBoolean();
		else if (json.isString())
			return json.getAsString();
		else {
			BigDecimal bigDec = json.getAsBigDecimal();
			// Find out if it is an int type
			try {
				bigDec.toBigIntegerExact();
				try {
					return bigDec.intValueExact();
				} catch (ArithmeticException e) {
				}
				return bigDec.longValue();
			} catch (ArithmeticException e) {
			}
			// Just return it as a double
			return bigDec.doubleValue();
		}
	}

	private Object handleArray(JsonArray json,
			JsonDeserializationContext context) {
		Object[] array = new Object[json.size()];
		for (int i = 0; i < array.length; i++)
			array[i] = context.deserialize(json.get(i), Object.class);
		return array;
	}

	private Object handleObject(JsonObject json,
			JsonDeserializationContext context) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Map.Entry<String, JsonElement> entry : json.entrySet())
			map.put(entry.getKey(),
					context.deserialize(entry.getValue(), Object.class));
		return map;
	}
}