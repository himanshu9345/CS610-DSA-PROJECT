//HIMANSHU PATEL  cs610 2093 prp
//Priority Queue using Min Heap.
public class PriorityQueue2093 {

    private HuffChar2093[] heap;
    private  int current_heap_size;
    private  int heap_size;

    public PriorityQueue2093(int capacity){
        this.heap_size=capacity+1;
        heap=new HuffChar2093[heap_size];
        this.current_heap_size=0;
    }

    public void add(HuffChar2093 node){

        heap[++current_heap_size]=node;
        int position=current_heap_size;
        while(position!=1&&node.freq<heap[position/2].freq){
            heap[position]=heap[position/2];
            position/=2;
        }
        heap[position]=node;
    }
    public int size()
    {
        return current_heap_size;
    }

    public boolean isEmpty()
    {
        return current_heap_size == 0;
    }

    public HuffChar2093 remove(){
        int parent, child;
        HuffChar2093 item, temp;
        if (isEmpty() )
        {
            System.out.println("Heap is empty");
            return null;
        }

        item=heap[1];
        temp=heap[current_heap_size--];

        parent=1;
        child=2;
        while (child<=current_heap_size){
            if (child < current_heap_size && heap[child].freq > heap[child + 1].freq)
                child++;
            if (temp.freq <= heap[child].freq)
                break;

            heap[parent] = heap[child];
            parent = child;
            child *= 2;
        }
        heap[parent]=temp;
        return  item;
    }

}
