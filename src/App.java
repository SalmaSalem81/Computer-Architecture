
import java.util.ArrayList;
import java.util.Arrays;
import MicroArchitecture.MicroController;
import MicroArchitecture.Pipeline;
import Utils.Parser;
//main class to call the CA 
public class App {
     public static void main(String[] args) throws Exception {
        Parser q = new Parser();
        ArrayList<String> my = q.parse("File.txt");
        ArrayList<Short> j =  q.FromStringToShort(my);
        System.out.println("Parsed Instructions : "+ j);
        Short [] v = new Short[1024];
        Arrays.fill(v,(short) (-1));
        for (int i = 0 ; i < j.size(); i++) {
            v[i] = j.get(i);
        } 
        MicroController mc = new MicroController(v);
        Pipeline p = new Pipeline(mc);
        System.out.println("==========STARTING TO RUN========== \n");
        p.Driver_salma();
      
    }
}
