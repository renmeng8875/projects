package v1ch14.ThreadPoolTest;

import java.util.concurrent.Callable;

public class QueryVmss implements Callable<String[]> {
	private String[] params;
	public  QueryVmss(String[] queryStr){
		this.params = queryStr;
	}
	
	 public String[] call() {
		 
		return queryResult(params);
		
	 }
	 
	 public String[] queryResult(String[] queryStr){
		 String[] resultArray = new String[2];
		 resultArray[0] = queryStr[0];
		 resultArray[0] = queryStr[1]+"1";
		 
		 return resultArray;
	 }

}
