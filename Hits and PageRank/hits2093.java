//HIMANSHU PATEL  cs610 2093 prp
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;

public class hits2093 {
    static int[][] adjMatrix=null;
    static double[] authority;
    static  double[] old_authority;
    static double[] hub;
    static  double[] old_hub;
    static double target_error=1;
    static double current_error=2;
    static  double hub_sum_sq=0.0;
    static  double authority_sum_sq=0.0;
    static Integer no_vertices=null;
    static Integer no_edges=null;
    static DecimalFormat decimalFormat = new DecimalFormat("0.0000000");

    static int[][] createAdjMatrix(BufferedReader file,int no_vertices,int no_edges) throws IOException {
        int[][] adjMatrix=new int[no_vertices][no_vertices];
        String items;
        while((items=file.readLine())!=null){
            String[] splitted_items=items.split(" ");
            adjMatrix[Integer.parseInt(splitted_items[0])][Integer.parseInt(splitted_items[1])]=1;
        }
        return adjMatrix;
    }
    static double[] set_hub_value(int initialvalue,int no_vertices){
        double[] values={ (1/Math.sqrt(no_vertices)),1/(no_vertices+0.0),0,1};
        double[] hub=new double[no_vertices];
        Arrays.fill(hub,values[2+initialvalue]);
        return hub;
    }
    static double[] set_authority_value(int initialvalue,int no_vertices){
        double[] values={ 1/Math.sqrt(no_vertices),1/(no_vertices+0.0),0,1};
        double[] authority=new double[no_vertices];
        Arrays.fill(authority,values[2+initialvalue]);
        return authority;
    }
    static void authority_update(){
        authority_sum_sq=0.0;
        for(int i=0;i<adjMatrix.length;i++){
            double total=0.0;
            for (int j=0;j<adjMatrix[0].length;j++){
                total+=adjMatrix[j][i]*hub[j];
            }
            authority[i]=total;
            authority_sum_sq+=authority[i]*authority[i];
        }
    }
    static void hub_update(){
        hub_sum_sq=0.0;
        for(int i=0;i<adjMatrix.length;i++) {
            double total = 0.0;
            for (int j = 0; j < adjMatrix[0].length; j++) {
                total += adjMatrix[i][j] * authority[j];
            }
            hub[i]=total;
            hub_sum_sq+=hub[i]*hub[i];
        }
    }

    static double scale_hub_authority(int iterations){
        double max=-1;
        for(int i=0;i<hub.length;i++){
            hub[i]=hub[i]/Math.sqrt(hub_sum_sq);
            authority[i]=authority[i]/Math.sqrt(authority_sum_sq);
            max=Math.max(Math.max(Math.abs(hub[i]-old_hub[i]),Math.abs(authority[i]-old_authority[i])),max);
        }
        if (iterations<=0){
            return max;
        }
        return 2;
    }

    static void print_hub_authority(int iteration,boolean final_iteration_or_not){
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
        for(int i=0;i<hub.length;i++){
            output_str+="A/H["+i+"]="+ decimalFormat.format(authority[i])+"/"+decimalFormat.format(hub[i])+" ";
            if(no_vertices>10&&final_iteration_or_not){
                System.out.println("A/H ["+i+"] = "+ decimalFormat.format(authority[i])+"/"+decimalFormat.format(hub[i])+" ");
            }
        }
        if(no_vertices<=10){
            System.out.println(output_str);}
        else if (final_iteration_or_not){
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
        Integer intialvalues=Integer.parseInt((args[1]));
        String filename=args[2];

        //checking if all the input arguments are correct.
        if (intialvalues>1 || intialvalues<-2){
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
        //reading the file to create adJ Matrix
        BufferedReader filereader=new BufferedReader(inputFile);
        String vertex_edges= filereader.readLine();
        String[] splitted_items=vertex_edges.split(" ");
        no_vertices=Integer.parseInt(splitted_items[0]);
        no_edges=Integer.parseInt(splitted_items[1]);
        adjMatrix=createAdjMatrix(filereader,no_vertices,no_edges);
        //setting initial values for hubs and authority
        hub=set_hub_value(intialvalues, no_vertices);
        authority=set_authority_value(intialvalues,no_vertices);
        if (iterations<=0){
            if(iterations==0){
                target_error=Math.pow(10.0,-5+0.0);
                iterations=-1;
            }
            else{
            target_error=Math.pow(10.0,iterations+0.0);}
            current_error=1;
        }

        if (no_vertices>10){
            target_error=Math.pow(10.0,-5+0.0);
            hub=set_hub_value(-1, no_vertices);
            authority=set_authority_value(-1,no_vertices);
            iterations=-1;
        }
        print_hub_authority(0,false);
        int current_iteration=1;
        //Running HITS Algo Steps
        while(iterations!=0&& current_error>=target_error) {
            old_authority=authority.clone();
            old_hub=hub.clone();
            authority_update();
            hub_update();
            current_error=scale_hub_authority(iterations);
            print_hub_authority(current_iteration, (current_error < target_error || iterations == 1) && no_vertices > 10);
            current_iteration += 1;
            iterations--;

        }
    }
}
