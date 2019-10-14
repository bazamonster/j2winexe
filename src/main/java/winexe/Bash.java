package winexe;

import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.util.List;

public class Bash {

    private final ProcessBuilder processBuilder = new ProcessBuilder();

    public List<String> execCommand(String[] command, String encoding) {
        try {
            return IOUtils
                    .readLines(
                            processBuilder
                                    .command(command)
                                    .start()
                                    .getInputStream(), encoding
                    );
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public List<String> execCommand(String command, String encoding){
        try {
            return IOUtils
                    .readLines(
                            Runtime
                                    .getRuntime()
                                    .exec(command)
                                    .getInputStream(), encoding
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
