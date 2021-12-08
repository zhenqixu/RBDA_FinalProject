import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ChicagoCrimeClean {
    public static void main(String[] args)
            throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: ChicagoCrimeClean <input path> <output path>");
            System.exit(-1);
        }
        Job job = Job.getInstance();
        //job.setCombinerClass(PageRankReducer.class);
        job.setNumReduceTasks(1);
        job.setJarByClass(ChicagoCrimeClean.class);
        job.setJobName("ChicagoCrimeClean");
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.setMapperClass(ChicagoCrimeCleanMapper.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
