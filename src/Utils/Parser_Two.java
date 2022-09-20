package Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser_Two {
    public static ArrayList<ArrayList<String>> readFile(String nameOfFile) throws IOException {
        ArrayList<ArrayList<String>> FileLines = new ArrayList<ArrayList<String>>();
        BufferedReader bR = new BufferedReader(new FileReader(nameOfFile.concat(".txt")));
        String FileLine = bR.readLine();// to start the while loop and be updated inside;

        while (FileLine != null) {
            FileLines.add((toarraylist(FileLine.split(" "))));
            FileLine = bR.readLine();
        }
        bR.close();
        return FileLines;
    }

    public static ArrayList<String> toarraylist(String[] x) {// helper method array to arraylist

        ArrayList<String> result = new ArrayList<String>();

        for (int i = 0; i < x.length; i++) {
            result.add(x[i]);
        }
        return result;
    }

    public static void addToInstructionMemory(ArrayList<ArrayList<String>> Lines, Short[] instruction) {
        for (int i = 0; i < Lines.size(); i++) {
            String intNumber = "";
            switch (Lines.get(i).get(0).toUpperCase()) { //opcode
                case "ADD":
                    intNumber = "0000";
                    break;
                case "SUB":
                    intNumber = "0001";
                    break;
                case "MUL":
                    intNumber = "0010";
                    break;
                case "LDI":
                    intNumber = "0011";
                    break;
                case "BEQZ":
                    intNumber = "0100";
                    break;
                case "AND":
                    intNumber = "0101";
                    break;
                case "OR":
                    intNumber = "0110";
                    break;
                case "JR":
                    intNumber = "0111";
                    break;
                case "SLC":
                    intNumber = "1000";
                    break;
                case "SRC":
                    intNumber = "1001";
                    break;
                case "LB":
                    intNumber = "1010";
                    break;
                case "SB":
                    intNumber = "1011";
                    break;
            }

            if (!(Lines.get(i).get(1).charAt(0) >= 48 && Lines.get(i).get(1).charAt(0) <= 57)) {
                Integer registerOne = Integer.valueOf(Lines.get(i).get(1).substring(1));

                String extendedAdd = Integer.toBinaryString(registerOne);
                while (extendedAdd.length() < 6) {
                    extendedAdd = "0".concat(extendedAdd);
                }
                intNumber = intNumber.concat(extendedAdd);//first register
            } else {
                Integer registerOne = Integer.valueOf(Lines.get(i).get(1));

                String extendedAdd = Integer.toBinaryString(registerOne);
                while (extendedAdd.length() < 6) {
                    extendedAdd = "0".concat(extendedAdd);
                }
                intNumber = intNumber.concat(extendedAdd);//first register
            }
            if (!(Lines.get(i).get(2).charAt(0) >= 48 && Lines.get(i).get(2).charAt(0) <= 57)) {
                if (Lines.get(i).get(2).charAt(0) == 45) {//negative sign
                    byte registerTwo = Byte.valueOf(Lines.get(i).get(2));
                    String negativeNumberRegisterTwo = Integer.toBinaryString(0b00111111 & registerTwo);
                    intNumber = intNumber.concat(negativeNumberRegisterTwo);//second register
                    instruction[i] = Short.parseShort(intNumber, 2);
                } else {
                    Integer registerTwo = Integer.valueOf(Lines.get(i).get(2).substring(1));
                    String extendedAdd2 = Integer.toBinaryString(registerTwo);
                    while (extendedAdd2.length() < 6) {
                        extendedAdd2 = "0".concat(extendedAdd2);
                    }
                    intNumber = intNumber.concat(extendedAdd2);//second register
                    instruction[i] = Short.parseShort(intNumber, 2);
                }
            } else {
                Integer registerTwo = Integer.valueOf(Lines.get(i).get(2));
                String extendedAdd2 = Integer.toBinaryString(registerTwo);
                while (extendedAdd2.length() < 6) {
                    extendedAdd2 = "0".concat(extendedAdd2);
                }
                intNumber = intNumber.concat(extendedAdd2);//second register

                instruction[i] = //Short.parseShort(intNumber, 2);
                ((Integer) (Integer.parseInt(intNumber, 2))).shortValue();


            }
        }
    }
    
}
