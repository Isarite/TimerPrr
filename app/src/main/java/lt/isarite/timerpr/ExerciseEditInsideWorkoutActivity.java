package lt.isarite.timerpr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ExerciseEditInsideWorkoutActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button saveButton;
    Link link;
    int ID;

    Spinner exerciseSpinner;
    EditText linkDuration;
    Spinner linkType;
    EditText linkSets;
    EditText linkReps;
    EditText linkRestIn;
    EditText linkRestAfter;


    ArrayList<Exercise> exercises;

    String workoutName;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_edit_inside_workout);
        saveButton = (Button) findViewById(R.id.linkSave);




        linkDuration= findViewById(R.id.linkDuration);
        linkSets= findViewById(R.id.linkSets);
        linkReps= findViewById(R.id.linkReps);
        linkRestIn= findViewById(R.id.linkRestIn);
        linkRestAfter= findViewById(R.id.linkRestAfter);

        Intent intent = getIntent();
        WorkoutDatabaseHandler databaseHandler = new WorkoutDatabaseHandler(getApplicationContext());
        ID = intent.getIntExtra("ID", -1);
        workoutName = intent.getStringExtra("WORKOUT_NAME");

        if(ID!=-1){
            link = databaseHandler.getLink(ID);
            //exerciseSpinner.setSelection(0);
            linkDuration.setText(Float.toString(link.duration));
            //linkType;
            linkSets.setText(Integer.toString(link.sets));
            linkReps.setText(Integer.toString(link.reps));
            linkRestIn.setText(Double.toString(link.restIn));
            linkRestAfter.setText(Double.toString(link.restAfter));


        }else{
            link = new Link();
        }
        exerciseSpinner();
        typeSpinner();

//        exerciseName = intent.getStringExtra("EXERCISE_NAME");
//        exerciseDescription = intent.getStringExtra("EXERCISE_DESCRIPTION");
//        exerciseMuscle = intent.getStringExtra("EXERCISE_MUSCLE");
//        id = intent.getIntExtra("EXERCISE_ID", -1);
//        if(id!=-1) {
//            name.setText(exerciseName);
//            muscle.setText(exerciseMuscle);
//            description.setText(exerciseDescription);
//        }else{
//            id=null;
//        }



        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                link.duration = Float.parseFloat(linkDuration.getText().toString());
                link.sets = Integer.parseInt(linkSets.getText().toString());
                link.reps = Integer.parseInt(linkReps.getText().toString());
                link.restIn = Float.parseFloat(linkRestIn.getText().toString());
                link.restAfter = Float.parseFloat(linkRestAfter.getText().toString());
                link.FK_WORKOUT = workoutName;





                WorkoutDatabaseHandler databaseHandler = new WorkoutDatabaseHandler(getApplicationContext());
                if (link.ID == null) {
                    databaseHandler.addLink(link);
                } else {
                    databaseHandler.updateLink(link);
                }
                killActivity();
                killActivity();
            }
        });
    }

    private void killActivity() {
        finish();
    }

    void exerciseSpinner(){
       exerciseSpinner = findViewById(R.id.exerciseSpinner);

        // Spinner click listener
        exerciseSpinner.setOnItemSelectedListener(this);
        WorkoutDatabaseHandler databaseHandler = new WorkoutDatabaseHandler(getApplicationContext());
        exercises = databaseHandler.getAllExercises();
        int selection = 0;
        // Spinner Drop down elements
        ArrayList<String> categories = new ArrayList<String>();
        for(int i = 0; i < exercises.size(); i++){
            String name = exercises.get(i).name;
            if(link.FK_EXERCISE!=null&&link.FK_EXERCISE==exercises.get(i).ID){
                selection = i;
            }
            categories.add(name);
        }
        exerciseSpinner.setSelection(selection);


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        exerciseSpinner.setAdapter(dataAdapter);
    }

    void typeSpinner() {
        linkType = findViewById(R.id.linkType);

        // Spinner click listener
        linkType.setOnItemSelectedListener(this);


        // Spinner Drop down elements
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("timed");
        categories.add("reps");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        linkType.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.linkType:
                link.type = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + link.type, Toast.LENGTH_LONG).show();
                if(link.type.equals("timed")){
                    linkReps.setVisibility(View.GONE);
                    linkDuration.setVisibility(View.VISIBLE);
                    linkReps.setText("0");
                }else{
                    linkReps.setVisibility(View.VISIBLE);
                    linkDuration.setVisibility(View.GONE);
                    linkDuration.setText("0");
                }
                break;
            case R.id.exerciseSpinner:
                link.FK_EXERCISE = exercises.get(position).ID;
                Toast.makeText(parent.getContext(), "Selected: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
