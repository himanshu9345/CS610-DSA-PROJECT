//HIMANSHU PATEL  cs610 2093 prp

public class HuffChar2093 {
    Byte character;
    int freq;
    String code;
    HuffChar2093 left;
    HuffChar2093 right;

    public HuffChar2093(Byte input1,int input2){
        this.freq=input2;
        this.character=input1;
        this.left=null;
        this.right=null;
    }

}
