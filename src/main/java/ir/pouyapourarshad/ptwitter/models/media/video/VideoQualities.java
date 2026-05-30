package ir.pouyapourarshad.ptwitter.models.media.video;

public enum VideoQualities {
    Q1080(1080), Q720(720), Q360(360);
    private int value;
    private VideoQualities(int value){
        this.value = value;
    }
}
