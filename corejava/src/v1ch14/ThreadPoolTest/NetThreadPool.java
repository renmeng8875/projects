package v1ch14.ThreadPoolTest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetThreadPool {
	public static void main(String[] args)  {
		ExecutorService pool = Executors.newCachedThreadPool();
        Map<String,String> daoMap = new HashMap<String, String>();
        daoMap.put("13012345678","0");
        daoMap.put("13697482714","0");
	    
	}


	
}