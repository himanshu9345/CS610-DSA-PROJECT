//HIMANSHU PATEL  cs610 2093 prp

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

public class hdec2093 {

    static int[] character_count=new int[256];
    static byte[] data;
    static HuffChar2093 root;
    static int space_req;


    //reading the encoded file
    static  void reading_file(String[] args) throws IOException {
        File file = new File(args[0]);
        FileInputStream fis = new FileInputStream(file);
        data= new byte[(int)file.length()];
        try {
            fis.read(data);
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // get the frequency count from encoded file(given file)
    static int get_character_frequency(){
        int null_count_index=0;
        for (int i=0;i<data.length-3;i++){
            if (data[i]==0 &&(data[i+1]==0 && data[i+2]==0)){
                null_count_index=i;
                break;
            }
        }
        byte [] freq_info= Arrays.copyOfRange(data,0,null_count_index);
        String frequency_string_encoded_file=new String(freq_info);
        String[] characters_value=frequency_string_encoded_file.split(" ");
        for (int i=0;i<characters_value.length;i++){
            String[] character_freq=characters_value[i].split("\\*");
            character_count[128+Integer.parseInt(character_freq[0])]=Integer.parseInt(character_freq[1]);
        }
        return null_count_index+3;
    }

    //building the huffman tree.
    static HuffChar2093 build_huffman_tree(int[] freq_count){
       PriorityQueue2093 huff_priority_queue=new PriorityQueue2093(freq_count.length);
        for(int i=0;i<freq_count.length;i++){
            if (freq_count[i]>0){
//                System.out.println(freq_count[i]);
                space_req+=freq_count[i];
                huff_priority_queue.add(new HuffChar2093((byte) (i -128),freq_count[i]));
            }
        }

        while(huff_priority_queue.size()>1){
            HuffChar2093 char1=huff_priority_queue.remove();
            HuffChar2093 char2=huff_priority_queue.remove();
            HuffChar2093 root=new HuffChar2093((byte)0,char1.freq+char2.freq);
            root.left=char2;
            root.right=char1;
            huff_priority_queue.add(root);
        }
        return huff_priority_queue.remove();
    }

    //function to check whether given node is leaf or not
    static boolean check_leaf(HuffChar2093 root){
        if (root.left==null && root.right==null){
            return true;
        }
        return false;
    }

    public static void main(String [] args) throws IOException {
        Instant start = Instant.now();

        if (args.length!=1){
            System.out.println("Wrong Input arguments");
            return;
        }
        reading_file(args);
        int starting_position_of_data=get_character_frequency();
        root=build_huffman_tree(character_count);
        int bit=0;
        HuffChar2093 root1=root;
        String given_file=args[0];
        String file_name=given_file.split(".huf")[0];
        OutputStream os = new FileOutputStream(file_name);
        DataOutputStream out=new DataOutputStream(os);
        int no_character=0;
        byte[] dump_array=new byte[space_req];
        BitReader2093 br=new BitReader2093(Arrays.copyOfRange(data,starting_position_of_data,data.length));
        while ((bit=br.nextBit())!=-1){
            if (bit==0){
                root1=root1.left;
            }
            else{
                root1=root1.right;
            }
            if (check_leaf(root1)){
                dump_array[no_character]=root1.character;
                no_character++;
                root1=root;
            }
        }
        out.write(dump_array);
        System.out.println("File size after decoding "+space_req);
//        long timeElapsed = Duration.between(start,  Instant.now()).toMillis();
//        System.out.println(timeElapsed);


    }

}
