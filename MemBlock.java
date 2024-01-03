
public class MemBlock{
    public int size;
    public int startAddress;
    public int endAddress;

    MemBlock(int sz,int stAd,int enAd){
        this.size = sz;
        this.startAddress = stAd;
        this.endAddress = enAd;
    }
}