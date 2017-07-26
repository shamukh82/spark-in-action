/**
 * Created by smukherjee on 7/21/17.
 */
public class Runner {
    private static Runner ourInstance = new Runner();

    public static Runner getInstance() {
        return ourInstance;
    }

    private Runner() {
    }
}
