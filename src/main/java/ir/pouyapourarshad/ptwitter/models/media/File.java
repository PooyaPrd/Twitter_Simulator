package ir.pouyapourarshad.ptwitter.models.media;

public abstract class File{
    String path;
    protected File(String path){
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }
    public String getPathUrl(){
        if (path == null || path.isBlank()){
            return null;
        }
        return new java.io.File(path).toURI().toString();
    }
}
