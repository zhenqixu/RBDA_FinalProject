import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class ChicagoCrimeProfileMapper extends Mapper<LongWritable, Text, Text, Text> {
    private static final int MISSING = 9999;
    //private Text textValue = new Text();
    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String str = value.toString();
        System.out.println(str);
        String delimiter = ",";
        //String[] values = str.split(delimiter);
        String values[] = str.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)",-1);
        for(int i=1; i<values.length; ++i){
            if(values[i].trim().isEmpty())continue;
            Integer tmp=i;
            System.out.println(tmp.toString()+" "+values[i]);
            context.write(new Text(tmp.toString()),new Text(values[i]));
        }


        //StringBuilder strOutlinksBuilder = new StringBuilder();

    }


}