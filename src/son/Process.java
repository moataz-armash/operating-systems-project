package son;

public class Process {
    private int pid;
    private int arrivalTime;
    private Priority priority;
    private int processTime;
    private int requiredMemory;
    private int printers;
    private int scanners;
    private int modems;
    private int cdDrives;

    // Priority enum definition
    public enum Priority {
        REAL_TIME,
        HIGH,
        LOW
    }
    
    // Status enum definition
    public enum Status {
        RUNNING,
        COMPLETED,
        ERROR
    }
    
    private Status status;

    public Process(int pid, int arrivalTime, Priority priority, int processTime, int requiredMemory, int printers, int scanners, int modems, int cdDrives) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.processTime = processTime;
        this.requiredMemory = requiredMemory;
        this.printers = printers;
        this.scanners = scanners;
        this.modems = modems;
        this.cdDrives = cdDrives;
        this.status = Status.RUNNING;
    }

    // Getters for all attributes

    public int getPid() {
        return pid;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public Priority getPriority() {
        return priority;
    }

    public int getProcessTime() {
        return processTime;
    }

    public int getRequiredMemory() {
        return requiredMemory;
    }

    public int getPrinters() {
        return printers;
    }

    public int getScanners() {
        return scanners;
    }

    public int getModems() {
        return modems;
    }

    public int getCdDrives() {
        return cdDrives;
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
