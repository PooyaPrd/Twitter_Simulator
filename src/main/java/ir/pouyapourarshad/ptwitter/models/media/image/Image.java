package ir.pouyapourarshad.ptwitter.models.media.image;

import ir.pouyapourarshad.ptwitter.models.media.File;

public class Image extends File {
    private final ImageTypes type;
    public Image(String path, ImageTypes type) {
        super(path);
        this.type = type;
    }

    public static ImageTypes getImageTypeFromName(String name){
        return ImageTypes.valueOf(name.substring(name.lastIndexOf('.') + 1).toUpperCase());
    }
}
