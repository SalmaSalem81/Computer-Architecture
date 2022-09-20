package MicroArchitecture;

public class Register {
    private  byte []  generalReg;
    private  boolean [] SREG ;
    private short PC;

    public Register (){
        setGeneralReg(new byte[64]);
        setSREG(new  boolean [8]);
        for (int i = SREG.length-1;i<=5;i--){
            SREG[i] = false;
        };
        PC=0 ;

        
    }

    public boolean [] getSREG() {
        return SREG;
    }

    public void setSREG(boolean [] sREG) {
        this.SREG = sREG;
    }

    public short getPc() {
        return PC;
    }

    public void setPc(short pc) {
        this.PC = pc;
    }
    
    public byte [] getGeneralReg() {
        return generalReg;
    }

    public void setGeneralReg(byte [] generalReg) {
        this.generalReg = generalReg;
    }



    
}

