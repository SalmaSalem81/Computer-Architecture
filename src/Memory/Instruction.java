package Memory;

public class Instruction {
    //when putting instruction make sure its short and has 0B
    private short inst ;
    private short opcode = 0 ;
    private short r1 = 0 ;
    private short r2 = 0 ;
    private InstructionType type;
    private byte r1_data;
    private byte r2_data;
    private byte immeadiate_data;
    private int current_stage = 1; 
    public Instruction (short inst){//el raqam ely men el parser
        this.inst = inst ;
        opcode = 0 ;
        r1 = 0 ;
        r2 = 0 ;
       // immeadiate = 0 ;
    }

    public int getCurrent_stage() {
        return current_stage;
    }

    public void setCurrent_stage(int current_stage) {
        this.current_stage = current_stage;
    }

    public byte getImmeadiate_data() {
        return immeadiate_data;
    }

    public void setImmeadiate_data(byte immeadiate_data) {
        this.immeadiate_data = immeadiate_data;
    }

    public byte getR1_data() {
        return r1_data;
    }

    public void setR1_data(byte r1_data) {
        this.r1_data = r1_data;
    }

    public byte getR2_data() {
        return r2_data;
    }

    public void setR2_data(byte r2_data) {
        this.r2_data = r2_data;
    }

    public InstructionType getType() {
        return type;
    }

    public void setType(InstructionType type) {
        this.type = type;
    }

    public short getInst() {
        return inst;
    }

    public void setInst(short inst) {
        this.inst = inst;
    }

    public short getR1() {
        return r1;
    }

    public void setR1(short r1) {
        this.r1 = r1;
    }

    public Short getOpcode() {
        return opcode;
    }

    public void setOpcode(Short opcode2) {
        this.opcode = opcode2;
    }

    public short getR2() {
        return r2;
    }

    public void setR2(short r2) {
        this.r2 = r2;
    }

   /* public short getImmeadiate() {
        return immeadiate;
    }

    public void setImmeadiate(short immeadiate) {
        this.immeadiate = immeadiate;
    }*/


    
}
