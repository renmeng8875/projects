package javatuning.ch6.toolscheck.scripts;
import static com.sun.btrace.BTraceUtils.*;
import com.sun.btrace.annotations.*;

public class PrintTimes 
{
	@TLS
	private static long startTime;
	
	@OnMethod(clazz="/.+/",method="/writeFile/")
	public static void startMethod(){
		startTime = timeMillis();
	}
	
	@SuppressWarnings("deprecation")
	@OnMethod(clazz="/.+/",method="/writeFile/",location=@Location(Kind.RETURN))
	public static void endMethod(){
		print(strcat(strcat(name(probeClass()),"."),probeMethod()));
		print(" [");
		print(strcat("Time taken :",str(timeMillis()-startTime)));
		print("]");
		println();
	}
	
}
