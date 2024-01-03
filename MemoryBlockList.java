// Memory Block List is based linked list

class Node{
    MemBlock p;
    Node next;
    Node(MemBlock p){this.p = p;next = null;}
}

public class MemoryBlockList {
    Node head;

    public MemoryBlockList(){
        head = null;
    }

/* -------------- Start Push -------------- */

    public void push(MemBlock currMemBlock){
        Node newNode = new Node(currMemBlock);
        if(head == null){
            head = newNode;
        }
        else{
// First try to stick to make a bigger block
            Node temp = head,prev = null;
            while(temp != null){
// try stick from left
                if(temp.p.startAddress == (currMemBlock.endAddress + 1)){
                    temp.p.startAddress = currMemBlock.startAddress;
                    temp.p.size += currMemBlock.size;
// delete it and Re push the new size block memory to make a bigger block.
                    if(prev == null){
                        head = temp.next;
                    }
                    else{
                        prev.next = temp.next;
                    }
                    push(temp.p);
                    return;
                }
// try stick from right
                else if((temp.p.endAddress + 1) == currMemBlock.startAddress){
                    temp.p.endAddress = currMemBlock.endAddress;
                    temp.p.size += currMemBlock.size;
// delete it and Re push the new size block memory to make a bigger block.
                    if(prev == null){
                        head = temp.next;
                    }
                    else{
                        prev.next = temp.next;
                    }
                    push(temp.p);
                    return;
                }
                prev = temp;
                temp = temp.next;
            }

// else push to make the top => bigger size
            temp = head;
            prev = null;
            while(temp != null && currMemBlock.size < temp.p.size){
                prev = temp;
                temp = temp.next;
            }
            if(prev == null){
                newNode.next = head;
                head = newNode;
            }
            else{
                prev.next = newNode;
                newNode.next = temp;
            }
        }
    }

/* -------------- End Push -------------- */

/* -------------- Start peek -------------- */

    public MemBlock peek(){
        return this.head.p;
    }

/* -------------- End peek -------------- */

/* -------------- Start peek -------------- */

    public MemBlock pop(){
        Node curr = head;
        head = head.next;
        return curr.p;
    }

/* -------------- End peek -------------- */

/* -------------- Start isEmpty -------------- */

    public boolean isEmpt(){
        return (head == null);
    }

/* -------------- End isEmpty -------------- */

/* -------------- Start searchBlock -------------- */

    public MemBlock searchBlock(int neddedSize){
        if(isEmpt()){
            return null;
        }
        Node curr = head,prev = null;
        while(curr != null && curr.p.size < neddedSize){
            prev = curr;
            curr = curr.next;
        }
        if(curr == null){
// not found
            return null;
        }
// if found pop and return
        else if(prev == null){
            head = head.next;
            return curr.p;
        }
        else{
            prev.next = curr.next;
            return curr.p;
        }
    }

/* -------------- End searchBlock -------------- */

/* -------------- Start Display -------------- */

    public void display(){
        for(Node curr = head ; curr != null ; curr = curr.next){
            System.out.println("######################################");
            System.out.println(curr.p.size);
            System.out.println(curr.p.startAddress);
            System.out.println(curr.p.endAddress);
            System.out.println("######################################");
        }
    }

/* -------------- End Display -------------- */

}
