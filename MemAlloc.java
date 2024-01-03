import java.util.*;

class processTimeRemaining implements Comparator<process>{
    public int compare(process p1,process p2){
        if(p1.cpuTime > p2.cpuTime){
            return 1;
        }
        else{
            return -1;
        }
    }
}

class processNeededMem implements Comparator<process>{
    public int compare(process p1,process p2){
        if(p1.neededSize > p2.neededSize){
            return 1;
        }
        else{
            return -1;
        }
    }
}

public class MemAlloc {
    public static MemoryBlockList MBL = new MemoryBlockList();
    
    public static PriorityQueue<process> RunningProcessList = 
    new PriorityQueue<>(new processTimeRemaining());

    public static PriorityQueue<process> WaitingProcessList = 
    new PriorityQueue<>(new processNeededMem());

    public static Queue<process> StartingList = 
    new LinkedList<>();

// ------------------------------Start main-----------------------------------
    public static void main(String args[]){
        initProcesses();
        int time = 1;

        System.out.println("run");
        System.out.println("==============================");

        while(!RunningProcessList.isEmpty() || !WaitingProcessList.isEmpty() || (time == 1)){
            try {
            MBL.display();
            
            process currentProcess = StartingList.poll();

            System.out.println("At time " + time++);

            updateLists();

            if(currentProcess != null){
                MemAllocate(currentProcess);
            }

            showAllProcesses();

            System.out.println("==============================");

            Thread.sleep(1000);
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }

        MBL.display();
    }
    
// ------------------------------End main-------------------------------------

    public static void initProcesses(){
        MBL.push(new MemBlock(1024, 0, 1023));
        Random r = new Random();
        for(int i = 1 ; i <= 20 ; i++){
            int id = i;
            int size = r.nextInt(256);
            int processTime = r.nextInt(20000);
            process p = new process(id, size, processTime, null);
            StartingList.add(p);
        }
    }

    static void MemAllocate(process p){
        MemBlock temp = MBL.searchBlock(p.neededSize);

// take needed size and re push the rest.
        if(temp != null && temp.size > p.neededSize){
            temp.size -= p.neededSize;

            MemBlock AddressSpace = new MemBlock(
                p.neededSize,
                temp.startAddress,
                temp.startAddress + p.neededSize - 1);

            p.addressSpace = AddressSpace;

            temp.startAddress = temp.startAddress + p.neededSize;

            MBL.push(temp);

            RunningProcessList.add(p);
        }
// no need to re push the Memory Block.
        else if(temp != null && temp.size == p.neededSize){
            p.addressSpace = temp;
            RunningProcessList.add(p);
        }
// else push in the Waiting list
        else{
            WaitingProcessList.add(p);
        }
    }

    static void showAllProcesses(){
        Stack<process> st = new Stack<>();

        System.out.println("");
        System.out.println("Processes in the Waiting list: ");
        System.out.println("");

        while(!WaitingProcessList.isEmpty()){
            WaitingProcessList.peek().displayProcess();
            st.push(WaitingProcessList.poll());
        }
        
        while(!st.isEmpty()){
            WaitingProcessList.add(st.pop());
        }

        // ==============

        System.out.println("");
        System.out.println("Processes in the Running list: ");
        System.out.println("");

        while(!RunningProcessList.isEmpty()){
            RunningProcessList.peek().displayProcess();
            st.push(RunningProcessList.poll());
        }

        while(!st.isEmpty()){
            RunningProcessList.add(st.pop());
        }
    }

    public static void updateLists(){
// running processes timeout is decreased by 1000 ms.
        Stack<process>st = new Stack<>();
        while(!RunningProcessList.isEmpty()){
            process curr = RunningProcessList.poll();
            curr.cpuTime -= 1000;
            st.push(curr);
        }
        while(!st.isEmpty()){
            RunningProcessList.add(st.pop());
        }
// Release process with cpu time < 0.
        if(!RunningProcessList.isEmpty() && 
        RunningProcessList.peek().cpuTime <= 0){
            System.out.println("Process with ID: " + 
            RunningProcessList.peek().id + " ended");
            MBL.push(RunningProcessList.poll().addressSpace);
        }
// Try to Allocate memory to the process with lowest needed size which is the peek of waiting list
        if(!WaitingProcessList.isEmpty()){
            MemAllocate(WaitingProcessList.poll());
        }
    }
}