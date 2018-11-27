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
    String EXERCISE_DESC = "description";

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
        String exercise = "" +
                "CREATE TABLE " +EXERCISE_TABLE_NAME +
                "(" +
                EXERCISE_ID + " integer NOT NULL," +
                EXERCISE_NAME + " TEXT NOT NULL," +
                EXERCISE_MUSCLE + " TEXT," +
                EXERCISE_DESC+" TEXT," +
                "PRIMARY KEY("+EXERCISE_ID+")" +
                ");";
        String workout = "" +
                "CREATE TABLE "+WORKOUT_TABLE_NAME +
                "(" +
                WORKOUT_NAME + " TEXT NOT NULL," +
                "PRIMARY KEY(" + WORKOUT_NAME + ")" +
                ");";
        String link = "" +
        "CREATE TABLE "+LINK_TABLE_NAME+
                "(" +
                LINK_NUMBER+ " integer NOT NULL," +
                LINK_DURATION+" real," +
                LINK_TYPE+" TEXT NOT NULL," +
                LINK_RESTIN+" real," +
                LINK_RESTAFTER+" real," +
                LINK_SETS+" integer NOT NULL," +
                LINK_REPS+" integer NOT NULL," +
                LINK_ID+" integer NOT NULL," +
                LINK_FK_EXERCISE+" integer," +
                LINK_FK_WORKOUT+" TEXT NOT NULL," +
                "PRIMARY KEY("+LINK_ID+")," +
                "FOREIGN KEY("+LINK_FK_EXERCISE+") REFERENCES "+EXERCISE_TABLE_NAME+" ("+EXERCISE_ID+")," +
                "FOREIGN KEY("+LINK_FK_WORKOUT+") REFERENCES "+WORKOUT_TABLE_NAME+" ("+WORKOUT_NAME+")" +
                ");";


        db.execSQL(exercise);
        db.execSQL(workout);
        db.execSQL(link);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
