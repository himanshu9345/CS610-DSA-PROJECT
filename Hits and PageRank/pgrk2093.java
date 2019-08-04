//HIMANSHU PATEL  cs610 2093 prp
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class pgrk2093 {
    static double constant_d=0.85;
    static double [] pgrk_array;
    static double target_error=1;
    static double current_error=2;
    static double[] temp_pgrk_array;
    static Integer no_vertices=null;
    static Integer no_edges=null;
    static ArrayList<ArrayList<Integer>> adjList1;
    static DecimalFormat decimalFormat = new DecimalFormat("0.0000000");

    static void createAdjList(BufferedReader file, int no_vertices, int no_edges) throws IOException {
        String items;
        adjList1=new ArrayList<>();
        for (int i=0;i<no_vertices;i++){
            adjList1.add(new ArrayList<Integer>());
        }
        while((items=file.readLine())!=null) {
            String[] splitted_items = items.split(" ");
            adjList1.get(Integer.parseInt(splitted_items[0])).add(Integer.parseInt(splitted_items[1]));
        }
    }

    static void initialize_pageant(int no_vertices, int initialvalue){
        double[] values={ (1/Math.sqrt(no_vertices)),1/(no_vertices+0.0),0,1};
        pgrk_array=new double[no_vertices];
        Arrays.fill(pgrk_array,values[2+initialvalue]);
    }
    
    static  double calculate_page_rank(int no_vertices,int iterations){
        for (int i=0;i<no_vertices;i++){
            for(int j=0;j<adjList1.get(i).size();j++){
                temp_pgrk_array[adjList1.get(i).get(j)]+=pgrk_array[i]/(adjList1.get(i).size()+0.0);
            }
        }
        double max_error=-1;
        for (int i=0;i<no_vertices;i++){
            temp_pgrk_array[i]=constant_d*temp_pgrk_array[i]+((1-constant_d)/no_vertices);
            if(Math.abs(temp_pgrk_array[i]-pgrk_array[i])>max_error){
                max_error=Math.abs(temp_pgrk_array[i]-pgrk_array[i]);
            }
        }
        if (iterations<=0){
            return max_error;
        }
        return 2;
    }

    static void print_page_rank(int iteration,boolean final_iteration_or_not){
        String output_str="";
        if (iteration==0) {
            output_str += "Base : " + iteration + " :";

        }
        else {
            output_str += "Iter : " + iteration + " :";
            if(no_vertices>10&&final_iteration_or_not){
                System.out.println("Iter : " + iteration );
            }
        }
        for(int i=0;i<pgrk_array.length;i++){
            output_str+="P["+i+"]="+ decimalFormat.format(pgrk_array[i])+" ";
            if(no_vertices>10&&final_iteration_or_not){
                System.out.println("P ["+i+"] = "+ decimalFormat.format(pgrk_array[i])+" ");
            }
        }
        if(no_vertices<=10){
        System.out.println(output_str);}
        else if(final_iteration_or_not) {
            System.out.println();
        }
    }
    public static void main(String[] args) throws IOException {
        FileReader inputFile=null;
        if(args.length!=3){
            System.out.println("Wrong Input arguments");
            return;
        }
        //Parsing the inputs
        Integer iterations=Integer.parseInt(args[0]);
        Integer initialvalues=Integer.parseInt((args[1]));
        String filename=args[2];
        //checking if all the input arguments are correct.
        if (initialvalues>1 || initialvalues<-2){
            System.out.println("initial value is not one of values(-2,-1,0,1)");
            return;
        }
        try
        {
            inputFile=new FileReader(filename);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File name:"+filename+" not found");
        }
        BufferedReader filereader=new BufferedReader(inputFile);
        String vertex_edges= filereader.readLine();
        String[] splitted_items=vertex_edges.split(" ");
        no_vertices=Integer.parseInt(splitted_items[0]);
        no_edges=Integer.parseInt(splitted_items[1]);
        createAdjList(filereader,no_vertices,no_edges);
        initialize_pageant(no_vertices,initialvalues);
        if (iterations<=0){
            if (iterations==0){
                target_error=Math.pow(10.0,-5.0);
                iterations=-1;
            }
            else{
            target_error=Math.pow(10.0,iterations+0.0);}
            current_error=1;
        }
        if (no_vertices>10){
            target_error=Math.pow(10.0,-5+0.0);
            iterations=-1;
            initialize_pageant(no_vertices,-1);

        }
        temp_pgrk_array=new double[no_vertices];
        print_page_rank(0,false);
        int current_iteration=1;
        while(iterations!=0 && current_error>=target_error){
            Arrays.fill(temp_pgrk_array,0.0);
            current_error=calculate_page_rank(no_vertices,iterations);
            pgrk_array=temp_pgrk_array.clone();
            print_page_rank(current_iteration,(current_error <target_error || iterations == 1) && no_vertices > 10);
            current_iteration++;
            iterations--;
        }
    }
}
