package dsa.map;
public class SimpleHashMap {

	private Object[] table;

	public SimpleHashMap() {
		table = new Object[10];
	}

	public Object get(Object key) {
		int index = indexFor(hash(key.hashCode()), 10);
		return table[index];
	}

	public void put(Object key, Object value) {
		int index = indexFor(hash(key.hashCode()), 10);
		table[index] = value;
	}

	/**
	 * 通过hash code 和table的length得到对应的数组下标 
	 * @param h
	 * @param length
	 * @return
	 */
	static int indexFor(int h, int length) {
		return h & (length - 1);
	}

	/**
	 * 通过一定算法计算出新的hash值 
	 * @param h
	 * @return
	 */
	static int hash(int h) {
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}
	
	
	public static void main(String[] args){
		SimpleHashMap hashMap = new SimpleHashMap();
		hashMap.put("key", "value");
		System.out.println(hashMap.get("key"));
	}
}