//HIMANSHU PATEL  cs610 2093 prp
//class to read given byte array into bit.
public class BitReader2093 {

    byte[] input;
    int byte_array_length;
    int current_index;
    int current_bit;
    int length_last_bit;

    BitReader2093(byte[] input){
        this.input=input;
        this.byte_array_length=input.length;
        this.current_index=0;
        this.current_bit=7;

        if (input[byte_array_length-1]==8){
            length_last_bit=7;
        }
        else{
            length_last_bit=input[byte_array_length-1]-1;
        }
    }

    int nextBit(){
        if (current_index>=byte_array_length-2){
           if (current_index==byte_array_length-1){
               return -1;
           }
           if(length_last_bit==0){
                int ans=(input[current_index] & (1 << length_last_bit));
                   current_index+=1;
                   return ans;
           }else {
                   int ans=(input[current_index] & (1 << length_last_bit));
               length_last_bit-=1;
               return ans;
           }
        }
        if(current_bit==0){
            int ans=(input[current_index] & (1 << current_bit));
            current_bit=7;
            current_index+=1;
            return ans;
        }else {
            int ans=(input[current_index] & (1 << current_bit));
            current_bit-=1;
            return ans;
        }
    }



}
