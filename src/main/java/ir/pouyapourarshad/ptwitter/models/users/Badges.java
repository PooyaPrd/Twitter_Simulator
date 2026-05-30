package ir.pouyapourarshad.ptwitter.models.users;

public enum Badges {
    NORMAL("Normal", "assets/images/N.png"),
    BLUE("Blue", "assets/images/B.png"),
    GOLD("Gold", "assets/images/G.png");

    private final String name;
    private final String path;

    Badges(String name, String path){
        this.name = name;
        this.path = path;
    }

    public String getPath(){
        return path;
    }
}
