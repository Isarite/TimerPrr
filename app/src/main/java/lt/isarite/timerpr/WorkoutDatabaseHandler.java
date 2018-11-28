package lt.isarite.timerpr;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import java.util.ArrayList;

public class WorkoutDatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "workouts.db";


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



    public WorkoutDatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String query = "DROP TABLE IF EXISTS " + WORKOUT_TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
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


//-------------------------------------Exercises
    public long addExercise(Exercise exercise){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EXERCISE_ID, exercise.ID);
        cv.put(EXERCISE_DESC, exercise.description);
        cv.put(EXERCISE_MUSCLE, exercise.muscle);
        cv.put(EXERCISE_NAME, exercise.name);
        long id = db.insert(EXERCISE_TABLE_NAME, null, cv);
        return id;
    }

    public Exercise getExercise(int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;

        cursor = db.query(EXERCISE_TABLE_NAME, new String[] {EXERCISE_ID,EXERCISE_NAME,EXERCISE_MUSCLE,EXERCISE_DESC}, EXERCISE_ID + "=?",
                new String[] { Integer.toString(id) }, null, null, null);

        Exercise exercise = new Exercise();
        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                exercise = new Exercise();
                exercise.ID = cursor.getInt(cursor.getColumnIndex(EXERCISE_ID));
                exercise.description = cursor.getString(cursor.getColumnIndex(EXERCISE_DESC));
                exercise.muscle = cursor.getString(cursor.getColumnIndex(EXERCISE_MUSCLE));
                exercise.name = cursor.getString(cursor.getColumnIndex(EXERCISE_NAME));
            }
        }

        cursor.close();
        db.close();

        return exercise;
    }


    public ArrayList<Exercise> getAllExercises(){
        ArrayList<Exercise> exercises = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + EXERCISE_TABLE_NAME + " ORDER BY " + EXERCISE_ID + " ASC";

        Cursor cursor = db.rawQuery(query, null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    Exercise exercise = new Exercise();

                    exercise.ID = cursor.getInt(cursor.getColumnIndex(EXERCISE_ID));
                    exercise.description = cursor.getString(cursor.getColumnIndex(EXERCISE_DESC));
                    exercise.muscle = cursor.getString(cursor.getColumnIndex(EXERCISE_MUSCLE));
                    exercise.name = cursor.getString(cursor.getColumnIndex(EXERCISE_NAME));

                    exercises.add(exercise);
                }while(cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();

        return exercises;
    }

    public void deleteExercise(int id){

        SQLiteDatabase db = getWritableDatabase();
        db.delete(EXERCISE_TABLE_NAME, EXERCISE_ID + "=?", new String[]{Integer.toString(id)});
        db.delete(LINK_TABLE_NAME, LINK_FK_EXERCISE + "=?", new String[]{Integer.toString((id))});
        db.close();
    }

    public void updateExercise(Exercise exercise) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(EXERCISE_DESC, exercise.description);
        cv.put(EXERCISE_MUSCLE, exercise.muscle);
        cv.put(EXERCISE_NAME, exercise.name);

        db.update(EXERCISE_TABLE_NAME, cv, EXERCISE_ID + "=?", new String[] { Integer.toString(exercise.ID) });

        db.close();
    }


    //--------------------------------------------------------------Workouts

    public long addWorkout(Workout workout){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(WORKOUT_NAME, workout.name);
        long id = db.insert(WORKOUT_TABLE_NAME, null, cv);
        return id;
    }

    public ArrayList<Link> getWorkoutLinks(String name){
        ArrayList<Link> links = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;

        cursor = db.query(LINK_TABLE_NAME, new String[] {LINK_ID, LINK_NUMBER, LINK_DURATION, LINK_TYPE, LINK_RESTIN,
                        LINK_RESTAFTER, LINK_SETS, LINK_REPS, LINK_FK_EXERCISE, LINK_FK_WORKOUT}, LINK_FK_WORKOUT + "=?",
                new String[] { name }, null, null, null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    Link link = new Link();

                    link.ID = cursor.getInt(cursor.getColumnIndex(LINK_ID));
                    link.number = cursor.getInt(cursor.getColumnIndex(LINK_NUMBER));
                    link.duration = cursor.getFloat(cursor.getColumnIndex(LINK_DURATION));
                    link.type = cursor.getString(cursor.getColumnIndex(LINK_TYPE));
                    link.restIn = cursor.getFloat(cursor.getColumnIndex(LINK_RESTIN));
                    link.restAfter = cursor.getFloat(cursor.getColumnIndex(LINK_RESTAFTER));
                    link.sets = cursor.getInt(cursor.getColumnIndex(LINK_SETS));
                    link.reps = cursor.getInt(cursor.getColumnIndex(LINK_REPS));
                    link.FK_EXERCISE = cursor.getInt(cursor.getColumnIndex(LINK_FK_EXERCISE));
                    link.FK_WORKOUT = cursor.getString(cursor.getColumnIndex(LINK_FK_WORKOUT));




                    links.add(link);
                }while(cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();

        return links;
    }


    public ArrayList<Workout> getAllWorkouts(){
        ArrayList<Workout> workouts = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + WORKOUT_TABLE_NAME + " ORDER BY " + WORKOUT_NAME + " ASC";

        Cursor cursor = db.rawQuery(query, null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    Workout workout = new Workout();

                    workout.name = cursor.getString(cursor.getColumnIndex(WORKOUT_NAME));

                    workouts.add(workout);
                }while(cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();

        return workouts;
    }

    public void deleteWorkout(String name){

        SQLiteDatabase db = getWritableDatabase();
        db.delete(WORKOUT_TABLE_NAME, WORKOUT_NAME + "=?", new String[]{name});
        db.delete(LINK_TABLE_NAME, LINK_FK_WORKOUT + "=?", new String[]{name});
        db.close();
    }


    //---------------------------------------------------Links

    public long addLink(Link link){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LINK_DURATION, link.duration);
        cv.put(LINK_FK_EXERCISE, link.FK_EXERCISE);
        cv.put(LINK_FK_WORKOUT, link.FK_WORKOUT);
        cv.put(LINK_NUMBER, link.number);
        cv.put(LINK_REPS, link.reps);
        cv.put(LINK_SETS, link.sets);
        cv.put(LINK_RESTIN, link.restIn);
        cv.put(LINK_RESTAFTER, link.restAfter);
        cv.put(LINK_TYPE, link.type);
        long id = db.insert(LINK_TABLE_NAME, null, cv);
        return id;
    }

    public Link getLink(int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = null;

        cursor = db.query(LINK_TABLE_NAME, new String[] {LINK_ID, LINK_NUMBER, LINK_DURATION, LINK_TYPE, LINK_RESTIN,
                        LINK_RESTAFTER, LINK_SETS, LINK_REPS, LINK_FK_EXERCISE, LINK_FK_WORKOUT}, LINK_ID + "=?",
                new String[] { Integer.toString(id) }, null, null, null);

        Link link = new Link();
        if(cursor != null)
        {
            if(cursor.moveToFirst())
            {
                link.ID = cursor.getInt(cursor.getColumnIndex(LINK_ID));
                link.number = cursor.getInt(cursor.getColumnIndex(LINK_NUMBER));
                link.duration = cursor.getFloat(cursor.getColumnIndex(LINK_DURATION));
                link.type = cursor.getString(cursor.getColumnIndex(LINK_TYPE));
                link.restIn = cursor.getFloat(cursor.getColumnIndex(LINK_RESTIN));
                link.restAfter = cursor.getFloat(cursor.getColumnIndex(LINK_RESTAFTER));
                link.sets = cursor.getInt(cursor.getColumnIndex(LINK_SETS));
                link.reps = cursor.getInt(cursor.getColumnIndex(LINK_REPS));
                link.FK_EXERCISE = cursor.getInt(cursor.getColumnIndex(LINK_FK_EXERCISE));
                link.FK_WORKOUT = cursor.getString(cursor.getColumnIndex(LINK_FK_WORKOUT));
            }
        }

        cursor.close();
        db.close();

        return link;
    }





    public void deleteLink(int id){

        SQLiteDatabase db = getWritableDatabase();
        db.delete(LINK_TABLE_NAME, LINK_ID + "=?", new String[]{Integer.toString(id)});
        db.close();
    }

    public void updateLink(Link link)
    {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(LINK_DURATION, link.duration);
        cv.put(LINK_FK_EXERCISE, link.FK_EXERCISE);
        cv.put(LINK_FK_WORKOUT, link.FK_WORKOUT);
        cv.put(LINK_NUMBER, link.number);
        cv.put(LINK_REPS, link.reps);
        cv.put(LINK_SETS, link.sets);
        cv.put(LINK_RESTIN, link.restIn);
        cv.put(LINK_RESTAFTER, link.restAfter);
        cv.put(LINK_TYPE, link.type);
        db.update(LINK_TABLE_NAME, cv, LINK_ID + "=?", new String[] { Integer.toString(link.ID) });
        db.close();
    }



}
