package son;
public class RR {
    public static void execute(Process process, Dispatcher dispatcher, int timeQuantum) {
        allocateResources(process, dispatcher);
        executeProcess(process, timeQuantum);
        releaseResources(process, dispatcher);
    }

    private static void allocateResources(Process process, Dispatcher dispatcher) {
        // Implementation omitted for brevity
    }

    private static void executeProcess(Process process, int timeQuantum) {
        // Implementation omitted for brevity
    }

    private static void releaseResources(Process process, Dispatcher dispatcher) {
        // Implementation omitted for brevity
    }
}
