package lt.isarite.timerpr;

public class Exercise {
    Integer ID;
    String name;
    String muscle;
    String description;

    public Exercise(int id, String name, String muscle, String description){
        this.ID = id;
        this.name = name;
        this.muscle = muscle;
        this.description = description;
    }

    public Exercise(String name, String muscle, String description){
        this.ID = null;
        this.name = name;
        this.muscle = muscle;
        this.description = description;
    }

    public Exercise(){
        this.ID = null;
        this.name = null;
        this.muscle = null;
        this.description = null;
    }


}
