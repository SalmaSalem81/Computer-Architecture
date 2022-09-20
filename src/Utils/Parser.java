package Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

public class Parser {
    //get the instruction from text file 
    //translate instruction to 1 0 
        //pass intruction array to microController
        private static final Set<String> operators = Set.of(
            "ADD",
            "SUB",
            "MUL",
            "LDI",
            "BEQZ",
            "AND",
            "OR",
            "JR",
            "SLC",
            "SRC",
            "LB",
            "SB"
            );

    public Parser() {
        
    }
        public ArrayList<String> parse(String path) {
            ArrayList<String> ars = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new java.io.FileReader("File.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split(" ");
                    if (!operators.contains(words[0].toUpperCase())) {
                        System.out.println("Invalid Operation");
                        break;
                    }
                ars.add(line);
             }
                System.out.println("Instrcutions Array : "+ ars);
            } catch (IOException e) {
                System.out.println("---------- Error in Parsing File!! -------------");
            }
            return ars;
        }

        public ArrayList<Short> FromStringToShort(ArrayList<String> code){
            ArrayList<Short> ars = new ArrayList<>();
            String tmp = "";
            String R1 = "";
            String R2= ""; 
            Short myStr=0;       
            for(String s : code){
                String[] words = s.split(" ");
                if(words[0].toUpperCase().equals("ADD")){
                    tmp = "0000";
                    R1=words[1];
                    char c  = R1.charAt(1);
                    int i = Character.getNumericValue(c);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i);
                    R2 = words[2];
                    char c1  = R2.charAt(1);
                    int i1 = Character.getNumericValue(c1);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i1).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i1);
                    myStr = Short.parseShort(tmp,2) ;
                    
                }
                else if(words[0].toUpperCase().equals("SUB")){
                    tmp = "0001";
                    R1=words[1];
                    char c  = R1.charAt(1);
                    int i = Character.getNumericValue(c);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i);
                    R2 = words[2];
                    char c1  = R2.charAt(1);
                    int i1 = Character.getNumericValue(c1);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i1).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i1);
                    myStr = Short.parseShort(tmp,2) ;

                }
                else if(words[0].toUpperCase().equals("MUL")){
                    tmp = "00010";
                    R1=words[1];
                    char c  = R1.charAt(1);
                    int i = Character.getNumericValue(c);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i);
                    R2 = words[2];
                    char c1  = R2.charAt(1);
                    int i1 = Character.getNumericValue(c1);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i1).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i1);
                    myStr = Short.parseShort(tmp,2) ;
                }
                else if(words[0].toUpperCase().equals("LDI")){
                    tmp = "0011";
                    R1=words[1];
                    char c  = R1.charAt(1);
                    int i = Character.getNumericValue(c);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i);
                    R2 = words[2];
                    //char c1  = R2.charAt(1);
                    int i1 = Integer.parseInt(R2);
                    if(6-Integer.toBinaryString(i1).length()!=6){
                    for(int j = 0 ; j<6-Integer.toBinaryString(i1).length();j++){
                        tmp += "0";
                    }
                    }
                    String n = Integer.toBinaryString(i1);
                    if(n.length()>=32){
                        tmp+=n.substring(32-6,32);
                    }
                    else{
                        tmp += Integer.toBinaryString(i1);
                    }
                   
                    myStr = ((Integer) (Integer.parseInt(tmp, 2))).shortValue()  ;
                }
                else if(words[0].toUpperCase().equals("BEQZ")){
                    tmp = "0100";
                    R1=words[1];
                    char c  = R1.charAt(1);
                    int i = Character.getNumericValue(c);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i);
                    R2 = words[2];
                    //char c1  = R2.charAt(1);
                    int i1 = Integer.parseInt(R2);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i1).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i1);
                    myStr = ((Integer) (Integer.parseInt(tmp, 2))).shortValue()  ;
                }
                else if(words[0].toUpperCase().equals("AND")){
                    tmp = "0101";
                    R1=words[1];
                    char c  = R1.charAt(1);
                    int i = Character.getNumericValue(c);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i);
                    R2 = words[2];
                    char c1  = R2.charAt(1);
                    int i1 = Character.getNumericValue(c1);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i1).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i1);
                    myStr = ((Integer) (Integer.parseInt(tmp, 2))).shortValue()  ;
                }
                else if(words[0].toUpperCase().equals("OR")){
                    tmp = "0110";
                    R1=words[1];
                    char c  = R1.charAt(1);
                    int i = Character.getNumericValue(c);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i);
                    R2 = words[2];
                    char c1  = R2.charAt(1);
                    int i1 = Character.getNumericValue(c1);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i1).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i1);
                    myStr = ((Integer) (Integer.parseInt(tmp, 2))).shortValue()  ;
                }
                else if(words[0].toUpperCase().equals("JR")){
                    tmp = "0111";
                    R1=words[1];
                    char c  = R1.charAt(1);
                    int i = Character.getNumericValue(c);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i);
                    R2 = words[2];
                    char c1  = R2.charAt(1);
                    int i1 = Character.getNumericValue(c1);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i1).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i1);
                    myStr = ((Integer) (Integer.parseInt(tmp, 2))).shortValue()  ;
                }
                else if(words[0].toUpperCase().equals("SLC")){
                    tmp = "1000";
                    R1=words[1];
                    char c  = R1.charAt(1);
                    int i = Character.getNumericValue(c);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i);
                    R2 = words[2];
                    //char c1  = R2.charAt(1);
                    int i1 = Integer.parseInt(R2);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i1).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i1);
                    myStr = ((Integer) (Integer.parseInt(tmp, 2))).shortValue() ;
                }
                else if(words[0].toUpperCase().equals("SRC")){
                    tmp = "1001";
                    R1=words[1];
                    char c  = R1.charAt(1);
                    int i = Character.getNumericValue(c);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i);
                    R2 = words[2];
                    //char c1  = R2.charAt(1);
                    int i1 = Integer.parseInt(R2);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i1).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i1);
                    myStr = ((Integer) (Integer.parseInt(tmp, 2))).shortValue() ;
                }
                else if(words[0].toUpperCase().equals("LB")){
                    tmp = "1010";
                    R1=words[1];
                    char c  = R1.charAt(1);
                    int i = Character.getNumericValue(c);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i);
                    R2 = words[2];
                    //char c1  = R2.charAt(1);
                    int i1 = Integer.parseInt(R2);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i1).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i1);
                    myStr = ((Integer) (Integer.parseInt(tmp, 2))).shortValue()  ;
                }
                else if(words[0].toUpperCase().equals("SB")){
                    tmp = "1011";
                    R1=words[1];
                    char c  = R1.charAt(1);
                    int i = Character.getNumericValue(c);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i);
                    R2 = words[2];
                    //char c1  = R2.charAt(1);
                    int i1 = Integer.parseInt(R2);
                    for(int j = 0 ; j<6-Integer.toBinaryString(i1).length();j++){
                        tmp += "0";
                    }
                    tmp += Integer.toBinaryString(i1);
                   // myStr = Short.parseShort(tmp,2) ;
                    myStr= ((Integer) (Integer.parseInt(tmp, 2))).shortValue();
                }
                else{
                    System.out.println("-------Invalid Operation-------");
                }
                ars.add(myStr);
            }
            return ars;

        }

       }
    