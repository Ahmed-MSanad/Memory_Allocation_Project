public class process {
    public int id; // should be assigned automatically
    public int neededSize;
    public int cpuTime;
    public MemBlock addressSpace;

    public process(int id,int neededSize,int cpuTime,
    MemBlock addressSpace){
        this.id = id;
        this.neededSize = neededSize;
        this.cpuTime = cpuTime;
        this.addressSpace = addressSpace;
    }

    public String toString(){
        String retStr = "";

        retStr = retStr.concat(Integer.toString(this.id));

        retStr = retStr.concat(" ");

        retStr = retStr.concat(Integer.toString(this.neededSize));

        return retStr;
    }

    public void displayProcess(){
        System.out.println("Process ID\t:\tneeded Size\t:\tcpu Time\t:\tStart Address\t:\tEnd Address");
        System.out.print(this.id+"\t\t:\t\t"+this.neededSize+"\t\t:\t\t"+this.cpuTime);
        if(addressSpace != null){
            System.out.println("\t\t:\t\t"+this.addressSpace.startAddress+"\t\t:\t\t"+this.addressSpace.endAddress);
        }
        else{
            System.out.println("\t\t:\t\tNull\t\t:\t\tNull");
        }
    }
}