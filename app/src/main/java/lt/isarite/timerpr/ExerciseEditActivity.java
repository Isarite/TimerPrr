package lt.isarite.timerpr;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ExerciseEditActivity extends AppCompatActivity {

    String exerciseName;
    String exerciseMuscle;
    String exerciseDescription;

    Button saveButton;

    EditText name;
    EditText muscle;
    EditText description;
    Integer id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_edit);
        saveButton = (Button) findViewById(R.id.exerciseSaveButton);

        name = (EditText) findViewById(R.id.exerciseName);
        muscle = (EditText) findViewById(R.id.exerciseMuscle);
        description = (EditText) findViewById(R.id.exerciseDesc);
        Intent intent = getIntent();
        exerciseName = intent.getStringExtra("EXERCISE_NAME");
        exerciseDescription = intent.getStringExtra("EXERCISE_DESCRIPTION");
        exerciseMuscle = intent.getStringExtra("EXERCISE_MUSCLE");
        id = intent.getIntExtra("EXERCISE_ID", -1);
        if(id!=-1) {
            name.setText(exerciseName);
            muscle.setText(exerciseMuscle);
            description.setText(exerciseDescription);
        }else{
            id=null;
        }



        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Exercise exercise = new Exercise();
                exercise.name = name.getText().toString();
                exercise.muscle = muscle.getText().toString();
                exercise.description = description.getText().toString();
                exercise.ID = id;
                WorkoutDatabaseHandler databaseHandler = new WorkoutDatabaseHandler(getApplicationContext());
                if (exercise.ID == null) {
                    databaseHandler.addExercise(exercise);
                } else {
                    databaseHandler.updateExercise(exercise);
                }
                finish();
            }
        });
    }




}
