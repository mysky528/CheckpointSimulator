//granularity: attempt+superstep
public class IterationUnit {
    private int attepmt = 0; //job attempt, increment when a job is restarted
    private int superstep = 0;
    private long killTime = 0; //this superstep is killed or not
    private long checkpointStart = 0;
    private long checkpointEnd = 0;
    private long computeStart = 0;
    private long computeEnd = 0;
    private long recoveryOverheadStart = 0;
    private long recoveryOverheadEnd = 0;

    @Override
    public String toString() {
        return "simple.IterationUnit{" +
                "attepmt=" + attepmt +
                ", superstep=" + superstep +
                ", killTime=" + killTime +
                ", checkpointStart=" + checkpointStart +
                ", checkpointEnd=" + checkpointEnd +
                ", checkpointDuration=" + (checkpointEnd - checkpointStart) +

                ", computeStart=" + computeStart +
                ", computeEnd=" + computeEnd +
                ", computeDuration=" + (computeEnd - computeStart) +

                ", recoveryOverheadStart=" + recoveryOverheadStart +
                ", recoveryOverheadEnd=" + recoveryOverheadEnd +
                ", recoveryOverheadDuration=" + (recoveryOverheadEnd - recoveryOverheadStart) +

                '}';
    }

    public String toCsvString() {
        return attepmt +
                "," + superstep +
                "," + killTime +
                "," + checkpointStart +
                "," + checkpointEnd +
                "," + (checkpointEnd - checkpointStart) +
                "," + computeStart +
                "," + computeEnd +
                "," + (computeEnd - computeStart) +
                "," + recoveryOverheadStart +
                "," + recoveryOverheadEnd +
                "," + (recoveryOverheadEnd - recoveryOverheadStart)
                ;
    }

    public int getAttepmt() {
        return attepmt;
    }

    public void setAttepmt(int attepmt) {
        this.attepmt = attepmt;
    }

    public int getSuperstep() {
        return superstep;
    }

    public void setSuperstep(int superstep) {
        this.superstep = superstep;
    }


    public long getKillTime() {
        return killTime;
    }

    public void setKillTime(long killTime) {
        this.killTime = killTime;
    }

    public long getCheckpointStart() {
        return checkpointStart;
    }

    public void setCheckpointStart(long checkpointStart) {
        this.checkpointStart = checkpointStart;
    }

    public long getCheckpointEnd() {
        return checkpointEnd;
    }

    public void setCheckpointEnd(long checkpointEnd) {
        this.checkpointEnd = checkpointEnd;
    }

    public long getComputeStart() {
        return computeStart;
    }

    public void setComputeStart(long computeStart) {
        this.computeStart = computeStart;
    }

    public long getComputeEnd() {
        return computeEnd;
    }

    public void setComputeEnd(long computeEnd) {
        this.computeEnd = computeEnd;
    }

    public long getRecoveryOverheadStart() {
        return recoveryOverheadStart;
    }

    public void setRecoveryOverheadStart(long recoveryOverheadStart) {
        this.recoveryOverheadStart = recoveryOverheadStart;
    }

    public long getRecoveryOverheadEnd() {
        return recoveryOverheadEnd;
    }

    public void setRecoveryOverheadEnd(long recoveryOverheadEnd) {
        this.recoveryOverheadEnd = recoveryOverheadEnd;
    }
}
