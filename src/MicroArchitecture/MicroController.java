package MicroArchitecture;
import java.util.*;
import Memory.Instruction;

//import Memory.Data;
//Class that does the Fetching, decoding and executing  


public class MicroController {
    private  Short [] inst;
    private static Instruction []  instruction_Object;
    private byte [] dataMem;
    private static Register register;
    private boolean is_branching = false;
    

    public MicroController(Short [] inst){
        this.setInst(inst);
        this.instruction_Object = new Instruction[inst.length];
        
        for(int i=0; i<inst.length;i++){
            Instruction tmp =  new Instruction(inst[i]);
            this.instruction_Object[i] = tmp;
        }
        setDataMem(new byte [2048]);
        Arrays.fill(dataMem,(byte) 0);
        setRegister(new Register());

    }
    public boolean isIs_branching() {
        return is_branching;
    }
    public void setIs_branching(boolean is_branching) {
        this.is_branching = is_branching;
    }
    public Instruction [] getInstruction_Object() {
        return instruction_Object;
    }
    public static void setInstruction_Object(Instruction [] instruction_Object) {
        MicroController.instruction_Object = instruction_Object;
    }
    public Register getRegister() {
        return register;
    }
    public void setRegister(Register register) {
        this.register = register;
    }
    public byte [] getDataMem() {
        return dataMem;
    }
    public void setDataMem(byte [] dataMem) {
        this.dataMem = dataMem;
    }
    public Short [] getInst() {
        return inst;
    }
    public void setInst(Short [] inst) {
        this.inst = inst;
    }
    public short fetch(){        
        short x = register.getPc();
        
        if(x== inst.length || inst[x]== -1){
            System.out.println("-->No more instructions to fetch");
            return -1 ;
          }
          else{
            register.setPc((short) (x+1));
          }
        System.out.println("-->Instruction "+(x)+ " is being fetched");
        instruction_Object[x].setCurrent_stage(2);
        return(inst[x]);
       }
    public void decode(short inst){
        Instruction instF = new Instruction(inst);
        short temp  =  instF.getInst();
       Short opcode = (short) (temp & 0B1111000000000000);
        opcode = (short)(opcode >>> 12);
        instF.setOpcode(opcode);
        short r1  = (short) ((temp & 0B0000111111000000)>>6);
        instF.setR1(r1);
        instF.setR1_data(register.getGeneralReg()[(byte)r1]);

        instF.setR2((byte) (temp & 0B0000000000111111));
        byte r2 = (byte) (temp & 0B0000000000111111);//8 bits 
        instF.setR2_data(register.getGeneralReg()[(byte)r2]);
        byte imm = r2;
        if(imm >= 32){
            imm = (byte)(imm- 64);
        }
        instF.setImmeadiate_data(imm);
        instF.setR2_data(register.getGeneralReg()[r2]);
        instF.setCurrent_stage(3);
        instF.setImmeadiate_data(imm);

        
        instruction_Object[(register.getPc()-1)] = instF;
    }

    public void execute(Instruction instD){
        Short opcode = instD.getOpcode();
        setIs_branching(false);
        byte [] tempReg =register.getGeneralReg();
        int r11,r22 = 0;
        byte r1 = instD.getR1_data();
        byte r2 = instD.getR2_data();
        byte imm = instD.getImmeadiate_data();
        int res  = 0 ;
        byte res1 = 0;
        String opcode_name = Integer.toBinaryString((int)opcode);        
        if(opcode_name.length()<32){
            int n = 32-opcode_name.length();
            for(int i=0;i<n;i++){
                opcode_name = "0"+opcode_name;
            }
        }
        
        String op_func= opcode_name.substring(32-4,32);
        System.out.println("     op_func: "+op_func);            
        
        switch(op_func){//update flags 
            case "0000" : res1 = (byte) (r1+r2);
                     checkOverflowAdd(r1,r2,res1);
                     int temp1 = r1 & 0x000000FF;
                     int temp2 = r2 & 0x000000FF;
                     res = temp1+temp2;
                     r1 = res1;
                     //getCarry(r1,res);
                     tempReg[instD.getR1()] = r1;
                     register.setGeneralReg(tempReg);
                     checkZero(r1);
                     checkNegative(r1);
                     checkSign();
                     checkCarry(res);
                     System.out.println("     Executed Function ADD on R"+instD.getR1()+" and R"+instD.getR2()+" and result is "+res1+ " Saved in R"+instD.getR1());
                    break ;
            case "0001" :res1 =  (byte)(r1-r2) ;
                    checkOverflowSub(r1, r2, res);
                    r1 -= r2 ;
                    tempReg[instD.getR1()] = r1;
                    register.setGeneralReg(tempReg);
                    checkZero(r1);
                    checkNegative(r1);
                    checkSign();
                    System.out.println("     Executed Function SUB on R"+instD.getR1()+" and R"+instD.getR2()+" and result is "+res1+ " Saved in R"+instD.getR1());
                    break ;
            case "0010" :r1 *= r2 ;
                    tempReg[instD.getR1()] = r1;
                    register.setGeneralReg(tempReg);
                    checkZero(r1);
                    checkNegative(r1);
                    System.out.println("     Executed Function MUL on R"+instD.getR1()+" and R"+instD.getR2()+" and result is "+r1+ " Saved in R"+instD.getR1());
                    break ;
            case "0011" :r1 = imm;
                   tempReg[instD.getR1()] = r1;
                   register.setGeneralReg(tempReg);
                   System.out.println("     Executed Function LDI on R"+instD.getR1()+": loading value "+imm+ " in R"+instD.getR1());
                   break ;
                    
            case "0100" :if(r1==0){
                         int x = register.getPc();
                         x =  (x+imm)-1;
                         register.setPc((short) x);
                         setIs_branching(true);
                         System.out.println("     Executed Function BEQZ on R"+instD.getR1()+" Branching to instruction at location " + x );
                }
                else{
                    System.out.println("     Executed Function BEQZ on R"+instD.getR1()+" NOT Branching to instruction at location " + register.getPc()+1+imm );
                }
                break;
            case "0101" :r1 = (byte)  (r1 & r2) ;
                    tempReg[instD.getR1()] = r1;
                    register.setGeneralReg(tempReg);
                    checkZero(r1);
                    checkNegative(r1);
                    System.out.println("     Executed Function AND on R "+instD.getR1()+" and R"+instD.getR2()+" and result is "+r1+ "Saved in R"+instD.getR1());
                    break ;
                    
            case "0110" :r1 = (byte) (r1 | r2) ;
                    tempReg[instD.getR1()] = r1;
                    register.setGeneralReg(tempReg);
                    checkZero(r1);
                    checkNegative(r1);
                    System.out.println("     Executed Function OR on R "+instD.getR1()+" and R"+instD.getR2()+" and result is "+r1+ "Saved in R"+instD.getR1());

                    break ;

            case "0111" :r11= (short) r1;
                         r22 = (short) r2 ;
                         r11 = (short) (r11 << 8) ;
                         r22 = (short) (0b0000000011111111 & r22);
                         register.setPc((short)((r11|r22)));
                         System.out.println("     Executed Function JR on R"+instD.getR1()+" & R"+instD.getR2()+" Jumping to instruction at location: " + register.getPc() );
                         break;
            
            case "1000" : r1 =(byte) (Integer.rotateLeft((int)r1, (int)imm));
                    checkZero(r1);
                    checkNegative(r1);
                    tempReg[instD.getR1()] = r1;
                    register.setGeneralReg(tempReg);
                    System.out.println("     Executed Function SLC on R"+instD.getR1()+" circular shifting left R"+instD.getR1()+ imm+ " and result is "+r1+ " Saved in R"+instD.getR1());
                    break;
            case "1001" :r1 = (byte) Integer.rotateRight((int)r1,(int) imm);
                    checkZero(r1);
                    checkNegative(r1);
                    tempReg[instD.getR1()] = r1;
                    register.setGeneralReg(tempReg);
                    System.out.println("     Executed Function SRC on R"+instD.getR1()+" circular shifting right R"+instD.getR1()+ imm+ " and result is "+r1+ " Saved in R"+instD.getR1());

                    break;
            case "1010" :r1 = dataMem[instD.getR2()];
                     tempReg[instD.getR1()] = r1;
                     register.setGeneralReg(tempReg);
                     System.out.println("     Executed Function LB on R "+instD.getR1()+" and immeadiate value "+imm+", loading value in location: "+imm+ " which is : " +r1+" from memory in R"+instD.getR1());

                     break ;
            case "1011":dataMem[instD.getR2()] = r1;
                       System.out.println("     Executed Function SB on R"+instD.getR1()+" and immeadiate value "+imm+", loading value in R"+instD.getR1()+" which is : "+r1+" in location: "+(imm)+" in Memory");
                        break ;
            default : System.out.println("=====INVALID OPERATION CANNOT EXECUTE=====");
                     break ;
        }
        instD.setCurrent_stage(4);
        System.out.println("     "+ toString(register.getSREG()));
        
}

private static void checkCarry( int res) {
    int mask = 0b00000000000000000000000100000000;
    boolean flag = false ;
    boolean [] temp =  register.getSREG();
    if ((res & mask) == mask){
        flag  = true ;
       }
       temp[4] = flag ;
        register.setSREG(temp);
    }
private static void checkSign() {
    boolean [] temp =  register.getSREG();
    temp[1]=  temp[2]^temp[3];
    }
private static void checkOverflowSub(byte r1, byte r2, int res) {
    boolean [] temp =  register.getSREG();
    if ((r2>0 && res>0 && r1<0)|| (r2<0 && res<0 && r1>0)){
        temp[4]=true;
    }
    else temp[4] =  false ;
    register.setSREG(temp);
    }
private static void checkOverflowAdd(byte r1,byte r2, byte res){
    boolean [] temp =  register.getSREG();
        if ((r1<0 && r2<0 && res>0) || (r1>0 && r2>0 && res<0) ){
            temp[3] = true ;

        }
        else temp[3] = false;
        register.setSREG(temp);

    }
private static void checkZero(byte r1) {
    boolean [] temp = register.getSREG();
        if (r1 ==  0){
            temp[0] = true ;
            register.setSREG(temp);
        }
       else {
        temp[0] = false ;
        register.setSREG(temp);
       }
    }
private static void checkNegative(byte r1) {
        boolean [] temp =  register.getSREG();
        if (r1 < 0 ){
            temp[2] = true ;
            register.setSREG(temp);
        }
        else {
            temp[2] = false ;
            register.setSREG(temp);
        }
    }
/*private static String invertDigits(String binaryInt) {
        String result = binaryInt;
        result = result.replace("0", "j"); //temp replace 0s
        result = result.replace("1", "0"); //replace 1s with 0s
        result = result.replace("j", "1"); //put the 1s back in
        return result;
    }*/
private static String toString(boolean [] sreg){
        return("SREG : [" + "Z: " + sreg[0] + ", S: " + sreg[1] + ", N: " + sreg[2] + ", V: " + sreg[3] + ", C: " + sreg[4] + ", " + sreg[5] + ", " + sreg[6] + ", " + sreg[7] + "]");
    }
}



    

