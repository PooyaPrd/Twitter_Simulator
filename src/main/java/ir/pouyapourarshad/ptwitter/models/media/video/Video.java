package ir.pouyapourarshad.ptwitter.models.media.video;

import ir.pouyapourarshad.ptwitter.models.media.File;
import ir.pouyapourarshad.ptwitter.models.media.image.ImageTypes;

public class Video extends File {
    private VideoQualities videoQualities;
    private VideoTypes videoTypes;
    private int length; // on second.

    public Video(String path, VideoQualities videoQualities, VideoTypes videoTypes, int length) {
        super(path);
        this.videoQualities = videoQualities;
        this.videoTypes = videoTypes;
        this.length = length;
    }
}
