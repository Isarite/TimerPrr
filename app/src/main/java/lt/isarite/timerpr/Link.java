package lt.isarite.timerpr;

public class Link {


    Integer ID;
    int number;
    float duration;
    String type;
    double restIn;
    double restAfter;
    int sets;
    int reps;
    Integer FK_EXERCISE;
    String FK_WORKOUT;

    public Link(int id, int number, float duration, String type, double restIn,
                double restAfter, int sets, int reps, int FK_EXERCISE, String FK_WORKOUT){
        this.ID = id;
        this.number = number;
        this.duration = duration;
        this.type = type;
        this.restIn = restIn;
        this.restAfter = restAfter;
        this.sets = sets;
        this.reps = reps;
        this.FK_EXERCISE = FK_EXERCISE;
        this.FK_WORKOUT = FK_WORKOUT;

    }

    public Link(int number, float duration, String type, double restIn,
                double restAfter, int sets, int reps, int FK_EXERCISE, String FK_WORKOUT){
        this.ID = null;
        this.number = number;
        this.duration = duration;
        this.type = type;
        this.restIn = restIn;
        this.restAfter = restAfter;
        this.sets = sets;
        this.reps = reps;
        this.FK_EXERCISE = FK_EXERCISE;
        this.FK_WORKOUT = FK_WORKOUT;
    }

    public Link(){
        this.ID = null;
        this.number = 0;
        this.duration = 0;
        this.type = null;
        this.restIn = 0;
        this.restAfter = 0;
        this.sets = 0;
        this.reps = 0;
        this.FK_EXERCISE = null;
        this.FK_WORKOUT = null;
    }


}
