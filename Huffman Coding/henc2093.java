//HIMANSHU PATEL  cs610 2093 prp

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

public class henc2093 {
    static int [] character_count=new int[256];
    static byte [] data;
    static HuffChar2093 root;
    static String[] huffman_codes=new String[256];
    static String[] arguments;
    static  int space_req=0;

    //function to read input file in byte array
    static  void reading_file(String[] args) throws FileNotFoundException {
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

    //building the huffman tree.
    static HuffChar2093 build_huffman_tree(int[] freq_count){
        PriorityQueue2093 huff_priority_queue=new PriorityQueue2093(freq_count.length);
        for(int i=0;i<freq_count.length;i++){
            if (freq_count[i]>0){
                huff_priority_queue.add(new HuffChar2093((byte)(i-128),freq_count[i]));
            }
        }

        while(huff_priority_queue.size()>1){
            HuffChar2093 char1=huff_priority_queue.remove();
            HuffChar2093 char2=huff_priority_queue.remove();
            HuffChar2093 root=new HuffChar2093(null,char1.freq+char2.freq);
            root.left=char2;
            root.right=char1;
            huff_priority_queue.add(root);
        }
        return huff_priority_queue.remove();
    }

    //assign huffman codes
    static void generate_huffman_code(HuffChar2093 root,String code){
        if(root==null){
            return;
        }
        generate_huffman_code(root.left,code+"0");
        generate_huffman_code(root.right,code+"1");
        root.code=code;
        if(root.character!=null){
        huffman_codes[128+root.character]=root.code;
        }
    }

    //encoding file saving it as filename.huf adding payload of frequency table.
    static void encodefile() throws IOException {
        String temp="";
        for(int i=0;i<character_count.length;i++){
            if(character_count[i]>0){
                space_req+=character_count[i]*huffman_codes[i].length();
                temp+=Integer.toString(i-128)+"*"+Integer.toString(character_count[i])+" ";
            }
        }
        byte[] gg=new byte[temp.getBytes().length+3];
        System.arraycopy(temp.getBytes(),0,gg,0,gg.length-3);

        try (FileOutputStream fos = new FileOutputStream(arguments[0]+".huf")) {
            fos.write(gg);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        OutputStream os = new FileOutputStream(arguments[0]+".huf",true);
        DataOutputStream out=new DataOutputStream(os);

        int data_count=0;
        int count=0;
        String byte_data_length="";
        byte[] byte_array_dump=new byte[(int)(Math.ceil((space_req/8.0)))+1];
        while (data_count<data.length){
            byte_data_length+=huffman_codes[128+data[data_count]];
            while (byte_data_length.length()>8){
                byte newByte=(byte)Integer.parseInt(byte_data_length.subSequence(0,8).toString(),2);;
                byte_array_dump[count]=newByte;
                byte_data_length=byte_data_length.substring(8);
                count++;
            }
            data_count++;
        }
        byte newByte=Byte.parseByte(byte_data_length, 2);
        byte_array_dump[count]=newByte;
        count++;

        System.out.println("Number of bytes of the compressed file "+(count+1+gg.length));
        System.out.println("Bits require in encoding "+space_req);

        if (byte_data_length.length()==8){
             newByte=8;
            byte_array_dump[count]=newByte;
        }
        else {
            newByte=(byte) byte_data_length.length();
            byte_array_dump[count]=newByte;
        }
        out.write(byte_array_dump);
        out.close();
        os.close();
    }


    public static void main(String[] args) throws IOException {
        Instant start = Instant.now();
        if (args.length!=1){
            System.out.println("Wrong Input arguments");
            return;
        }
        arguments=args;
        reading_file(args);
        Arrays.fill(character_count,0);

        for(int i=0;i<data.length;i++){
            character_count[128+data[i]]++;
        }
        root=build_huffman_tree(character_count);
        generate_huffman_code(root,"");
        encodefile();
        long timeElapsed = Duration.between(start,  Instant.now()).toMillis();
//        System.out.println(timeElapsed);

    }

}
