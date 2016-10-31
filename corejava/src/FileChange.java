import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 */

/**      
 * 项目名称：corejava   
 * 类名称：FileChange   
 * 类描述：   
 * 创建人：wuwh   
 * 创建时间：2013-1-12 下午07:37:51   
 * 修改人：Administrator   
 * 修改时间：2013-1-12 下午07:37:51   
 * 修改备注：   
 * @version  1.0     
 */

public class FileChange {
	public static void main(String[] args) {
		 
        try {

        // read file content from file

        StringBuffer sb= new StringBuffer("");

        

        FileReader reader = new FileReader("E:/Workspaces/corejava/src/Weapon.text"); 

        BufferedReader br = new BufferedReader(reader);

        

        String str = null; 

        

        while((str = br.readLine()) != null) {
        	  if(str.indexOf(";")>0){
        		  str = str.substring(0,str.indexOf(";"));
        	  } 
              if(str.startsWith("Add_level=")){
            	  str="Add_level=2";
              } 
              if(str.startsWith("Cost=")){
            	  str="Cost=100";
              }
              if(str.startsWith("Reload=")){
            	  str="Reload=10 20";
              }
              if(str.startsWith("Money=")){
            	  str="Money=10000";
              }
              if(str.startsWith("Health=")){
            	  str="Health=100";
              }
              if(str.startsWith("Strength=")){
            	  str="Strength=300";
              }
              sb.append(str+"/n");

              

              System.out.println(str);

        } 

        

        br.close();

        reader.close();

        

       

  }

  catch(FileNotFoundException e) {

              e.printStackTrace();

        }

        catch(IOException e) {

              e.printStackTrace();

        }

  }


}
