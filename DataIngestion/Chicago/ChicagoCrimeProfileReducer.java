import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.util.*;
import java.util.Map.Entry;

import java.io.IOException;

public class ChicagoCrimeProfileReducer extends Reducer<Text, Text, Text, Text> {
    @Override public void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

        Map<String, Integer> map = new HashMap<String, Integer>();
        Integer count=0;
        String[] ColumnNames={"ID","Case Number","Date","Block","IUCR","Primary Type",
                "Description","Location Description","Arrest","Domestic",
                "Beat","District","Ward","Community Area","FBI Code","X Coordinate",
                "Y Coordinate","Year","Updated On","Latitude","Longitude","Location"};
        for (Text value : values) {


            map.put(value.toString().trim(),map.getOrDefault(value.toString().trim(), 0) + 1);

            count++;
        }

        Set<Entry<String, Integer>> set = map.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(
                set);

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

            @Override
            public int compare(Entry<String, Integer> o1,
                               Entry<String, Integer> o2) {

                return o2.getValue().compareTo(o1.getValue());
            }

        });
        StringBuilder output = new StringBuilder();

        //System.out.println(Integer.valueOf(key.toString()));
        output.append(ColumnNames[Integer.valueOf(key.toString())]);
        output.append("\n");
        output.append("valid data count: ");
        output.append(count.toString());
        output.append("\n");

        List<Entry<String, Integer>> ListTopTen;
        // || key.toString().equals("7") || key.toString().equals("6") || key.toString().equals("4")
        if(list.size()<10){
            ListTopTen=list;
        }
        else{
            ListTopTen=list.subList(0,10);
        }

        for(Entry<String, Integer> e : ListTopTen){
            output.append(e.toString());
            output.append("\n");
        }

        System.out.println("key\n " + key + " output\n " + output );

        context.write(key, new Text(output.toString()));
    }
}