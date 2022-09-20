package Memory;

public class Data {
    private byte [] dataMem ;

    public Data (){
        setDataMem(new byte [2048]);

    }

    public byte [] getDataMem() {
        return dataMem;
    }

    public void setDataMem(byte [] dataMem) {
        this.dataMem = dataMem;
    }

  
    
}
