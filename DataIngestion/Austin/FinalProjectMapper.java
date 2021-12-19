import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class FinalProjectMapper
    extends Mapper<LongWritable, Text, NullWritable, Text> {
  
  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
    
    // get the string array, split by comma
    String[] splitStr = value.toString().trim().split(",");
    
    // take the date of the crime occurrence 
    String crimeDate = splitStr[5];

    // take the time of the crime occurrence 
    String crimeTime = splitStr[6];

    // take the hour of the crime occurence
    String hour;
    if (crimeTime.length() == 4) {
      hour = crimeTime.substring(0, 2);
    }
    else if (crimeTime.length() == 3) {
      hour = crimeTime.substring(0, 1);
    }
    else if (crimeTime.length() == 1) {
      hour = crimeTime;
    }
    else {
      hour = "";
    }

    // take the month, day, year of the crime occurence
    String month = crimeDate.substring(0, 2);
    String day = crimeDate.substring(3, 5);
    String year = crimeDate.substring(6, 10);
    
    // write out a string of "crimeDate,crimeTime, month, day, year"
    String finalOutputString = crimeDate + "," + crimeTime + "," + month + "," + day + "," + year + "," + hour;
    context.write(NullWritable.get(), new Text(finalOutputString));
  }
}
