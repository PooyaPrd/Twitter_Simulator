package ir.pouyapourarshad.ptwitter.models.report;

public class Report {

    private static int lastUid = 0;

    private int uid;
    private int reporterUid;
    private int reportedUid;
    private ReportStatus reportStatus;
    private String description;

    public Report(int reporterUid, int reportedUid, String description){
        uid = ++lastUid;
        this.reporterUid = reporterUid;
        this.reportedUid = reportedUid;
        this.description = description;
        reportStatus = ReportStatus.WAITING;
    }

//    Getters & Setters
    public int getUid() {
        return uid;
    }

    public int getReporterUid() {
        return reporterUid;
    }

    public int getReportedUid() {
        return reportedUid;
    }

    public ReportStatus getReportStatus() {
        return reportStatus;
    }

    public String getDescription() {
        return description;
    }



}
