package client;

import com.beust.jcommander.Parameter;
import com.google.gson.annotations.Expose;

public class Args {
    @Expose
    @Parameter(names = {"-t", "--type"})
    String type;

    @Expose
    @Parameter(names = {"-k", "--key"})
    String key;

    @Expose
    @Parameter(names = {"-v", "--value"})
    String value;

    @Parameter(names = {"-in", "--input-file"})
    String fileName;

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getFileName() {
        return fileName;
    }
}
