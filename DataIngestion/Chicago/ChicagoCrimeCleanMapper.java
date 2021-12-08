import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class ChicagoCrimeCleanMapper extends Mapper<LongWritable, Text, NullWritable, Text> {
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
        StringBuilder newValueBuilder = new StringBuilder();
        newValueBuilder.append(values[0].trim());

        int i;
        for(i=1; i<values.length; ++i){
            if(values[i].trim().isEmpty())break;
            if(i!=1 && i!=21){
                newValueBuilder.append(",");
                newValueBuilder.append(values[i].trim());
            }
            //Integer tmp=i;
            //System.out.println(tmp.toString()+" "+values[i]);
            //context.write(new Text(tmp.toString()),new Text(values[i]));
        }
        if(i==values.length){
            context.write(NullWritable.get(),new Text(newValueBuilder.toString()));
        }

        //StringBuilder strOutlinksBuilder = new StringBuilder();

    }


}