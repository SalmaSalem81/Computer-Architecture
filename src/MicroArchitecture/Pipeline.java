package MicroArchitecture;

import java.util.Arrays;

import Memory.Instruction;

public class Pipeline {
    private int cycle = 0 ;
    private MicroController mc;
    private int cycles_needed;
    //private int cycles_needed_for_branch;
    private int acctualInst = 0 ;
    public Pipeline(MicroController mc){
        this.mc = mc ;
        ;
        for(int i=0; i<mc.getInstruction_Object().length;i++){
            if(mc.getInst()[i]!=(-1)){
                acctualInst++;
            }
        }
        cycles_needed= 3 + ((acctualInst-1)*1);
        
    }
    public int getCycle() {
        return cycle;
    }
    public void setCycle(int cycle) {
        this.cycle = cycle;
    }
    public int getCycles_needed() {
        return cycles_needed;
    }
    public void setCycles_needed(int cycles_needed) {
        this.cycles_needed = cycles_needed;
    }
    
    public void Driver_salma() {
    	
   	 Instruction instReference  = mc.getInstruction_Object()[0];
        short instFetch=0;
         int   index = 0 ;
        for (; cycle <this.getCycles_needed() ; cycle++){
        	  instReference  = mc.getInstruction_Object()[index];
                System.out.println("Clock Cycle: "+ cycle);
                if(instReference.getCurrent_stage()==1 ){
                    instFetch = mc.fetch();   
                    if(instFetch== -1){
                    	System.out.println("-------------------------------------");
                        continue;
                    }
                }
                else if (instReference.getCurrent_stage()==2){
                    System.out.println("-->Instruction " + (index) + " is being decoded");
                    mc.decode(instFetch);
                    int x = index +1;
                    instFetch=mc.fetch();
                    if(instFetch== -1){
                    	System.out.println("-------------------------------------");
                        continue;
                    }
                }
                if (instReference.getCurrent_stage()==3){
                   System.out.println("-->Instruction " + (index) + " is being executed");
                    mc.execute(instReference);
                    if(instReference.getOpcode()==7 || mc.isIs_branching()){
                   	 int old_index=index;
                   	 int extra_cycles=0;
                        index = mc.getRegister().getPc();
                        if (old_index >= index) {
                       	 for(int i = old_index; i>= index ; i--) {
                       		 extra_cycles++;
                       		  mc.getInstruction_Object()[i].setCurrent_stage(1);
                       		 
                       	 }
                       	 extra_cycles= extra_cycles+2;
                       	 this.setCycles_needed(this.getCycles_needed()+ extra_cycles);
                        }
                        if(mc.isIs_branching()) {
                        	
                        	this.setCycles_needed(this.getCycles_needed()+ 1);
                        }
                        continue;//ignore the rest of the instructions from the beginning of the loop 
                    }
                    index ++;
                    int y = index  ;
                    
                   if (instFetch==-1 || index==mc.getInst().length-1) {
                      System.out.println("=====Done Executing All Instructions=====");
                      System.out.println("=================================================================== \n");
                       break;
                    }
                         
                    else {
                        
                    System.out.println("-->Instruction " + y + " is being decoded");
                    mc.decode(instFetch);
                    instFetch = mc.fetch();
                    if(instFetch== -1){
                    	System.out.println("-------------------------------------");
                        continue;
                    }
                    
                    if(index >= mc.getInst().length){
                    	System.out.println("-------------------------------------");
                        break;
                    }
                    
                    }
                 }
            System.out.println("-------------------------------------");
        }
        System.out.println("Data Memory: ");
        System.out.println(Arrays.toString(mc.getDataMem()));
        System.out.println("Instruction Memory: ");
        System.out.println(Arrays.toString(mc.getInst()));
        System.out.println("General Registers :");
        System.out.println(Arrays.toString(mc.getRegister().getGeneralReg()));
        System.out.println("PC location :" + mc.getRegister().getPc());
        System.out.println(toString(mc.getRegister().getSREG()));
    }
    
  
   
     
    private static String toString(boolean [] sreg){
        return("SREG : [" + "Z: " + sreg[0] + ", S: " + sreg[1] + ", N: " + sreg[2] + ", V: " + sreg[3] + ", C: " + sreg[4] + ", " + sreg[5] + ", " + sreg[6] + ", " + sreg[7] + "]");
    }

    
}

