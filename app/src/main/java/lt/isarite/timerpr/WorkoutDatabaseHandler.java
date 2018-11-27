package lt.isarite.timerpr;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WorkoutDatabaseHandler extends SQLiteOpenHelper {

    String EXERCISE_TABLE_NAME = "Exercise";
    String LINK_TABLE_NAME = "Link";
    String WORKOUT_TABLE_NAME = "Workout";

    String EXERCISE_ID = "id";
    String EXERCISE_NAME = "name";
    String EXERCISE_MUSCLE = "muscle";
    String EXERCISE_DESC = "desc";

    String LINK_ID = "id";
    String LINK_NUMBER = "number";
    String LINK_DURATION = "duration";
    String LINK_TYPE = "type";
    String LINK_RESTIN = "restin";
    String LINK_RESTAFTER = "restafter";
    String LINK_SETS = "sets";
    String LINK_REPS = "reps";
    String LINK_FK_EXERCISE = "fk_ExerciseId";
    String LINK_FK_WORKOUT = "fk_WorkoutName";

    String WORKOUT_NAME = "name";










    public WorkoutDatabaseHandler(){

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query2 = "CREATE TABLE " + SCORES_TABLE_NAME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_NAME + " TEXT," +
                KEY_SCORE + " INTEGER" +
                ")";
        String query = "" +
                "CREATE TABLE " +"Exercise" +
                "(" +
                "id integer NOT NULL," +
                "name TEXT NOT NULL," +
                "muscle TEXT," +
                "description TEXT," +
                "PRIMARY KEY(id)" +
                ");" +
                "CREATE TABLE Workout" +
                "(" +
                "name TEXT NOT NULL," +
                "PRIMARY KEY(name)" +
                ");" +
                "CREATE TABLE Link" +
                "(" +
                "number integer NOT NULL," +
                "duration real," +
                "type TEXT NOT NULL," +
                "restin real," +
                "restafter real," +
                "sets integer NOT NULL," +
                "reps integer NOT NULL," +
                "id integer NOT NULL," +
                "fk_ExerciseId integer," +
                "fk_WorkoutName TEXT NOT NULL," +
                "PRIMARY KEY(id)," +
                "FOREIGN KEY(fk_ExerciseId) REFERENCES Exercise (id)," +
                "FOREIGN KEY(fk_WorkoutName) REFERENCES Workout (name)" +
                ");";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
