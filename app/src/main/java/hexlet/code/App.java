package hexlet.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

import static hexlet.code.Differ.generate;

@Command(
        name = "app",
        mixinStandardHelpOptions = true,
        version = "app 1.0",
        description = "Compares two configuration files and shows a difference.",
        customSynopsis = "app [-hV] [-f=format] filepath1 filepath2"
)
public class App implements Callable<Integer> {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);
    @Option(
            names = {"-f", "--format"},
            description = "output format [default: stylish]",
            paramLabel = "format"
    )
    private String format = "stylish";

    @Parameters(paramLabel = "filepath1", description = "path to first file")
    private String filepath1;

    @Parameters(paramLabel = "filepath2", description = "path to second file")
    private String filepath2;


    public static void main(String[] args) {
        new CommandLine(new App()).execute(args);
    }

    @Override
    public Integer call() {
        try {
            String resultByDiffer = generate(filepath1, filepath2, format);
            LOG.info(resultByDiffer);
            return 0;
        } catch (Exception e) {
            LOG.error("Error: {}", e.getMessage());
            return 1;
        }
    }
}
