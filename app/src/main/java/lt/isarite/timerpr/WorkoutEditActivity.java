package lt.isarite.timerpr;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class WorkoutEditActivity extends AppCompatActivity {

    ListView simpleListView;
    String from[] = {"firstLine", "secondLine"};
    String firstLine[] = {"Chest workout", "Ab extinction", "10 minute Jump Rope"};
    String secondLine[] = {"Chest", "Abs", "Cardio"};
    String workoutName;
    boolean edit = false;
    EditText name;
    int prev = -1;
    ArrayList<Link>  links;
    FloatingActionButton addButton, editButton, saveButton, deleteButton;
    TextView warning;
    int[] to = {R.id.eFirstLine, R.id.eSecondLine};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name = (EditText) findViewById(R.id.workoutName);
        Intent intent = getIntent();
        workoutName = intent.getStringExtra("WORKOUT_NAME");
        edit = intent.getBooleanExtra("EDIT", false);

        setContentView(R.layout.activity_workout_edit);
        setButtonListeners();
        listItems();
    }

    void setButtonListeners(){
        addButton = (FloatingActionButton) findViewById(R.id.addW2Button);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent k = new Intent(getApplicationContext(), ExerciseEditInsideWorkoutActivity.class);
                name = findViewById(R.id.workoutName);
                workoutName = name.getText().toString();
                if(workoutName!=null) {
                    warning = findViewById(R.id.workoutWarning);
                    warning.setText("");

                    k.putExtra("WORKOUT_NAME", workoutName);
                    startActivity(k);
                }else{
                    warning = findViewById(R.id.workoutWarning);
                    warning.setText("Please write in name first");
                }
            }
        });

        editButton = (FloatingActionButton) findViewById(R.id.editW2Button);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent k = new Intent(getApplicationContext(), ExerciseEditInsideWorkoutActivity.class);
                Link link = links.get(prev);
                name = findViewById(R.id.workoutName);
                workoutName = name.getText().toString();
                if(workoutName!=null) {
                    warning = findViewById(R.id.workoutWarning);
                    warning.setText("");
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

                    k.putExtra("WORKOUT_NAME", workoutName);
                    k.putExtra("ID", link.ID);




                    startActivity(k);
                }else{
                    warning = findViewById(R.id.workoutWarning);
                    warning.setText("Please write in name first");
                }
            }
        });

        saveButton = (FloatingActionButton) findViewById(R.id.saveW2Button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                workoutName = name.getText().toString();
                if(workoutName!=null) {
                    name.setText(workoutName);
                    Workout workout = new Workout();
                    workout.name = workoutName;
                    WorkoutDatabaseHandler databaseHandler = new WorkoutDatabaseHandler(getApplicationContext());
                    if(!edit){
                        databaseHandler.addWorkout(workout);
                        saveButton.hide();
                        name.setKeyListener(null);
                        finish();
                    }
                }else{
                    warning = findViewById(R.id.workoutWarning);
                    warning.setText("Please set the name of the workout");
                }
            }
        });

        deleteButton = (FloatingActionButton) findViewById(R.id.deleteW2Button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WorkoutDatabaseHandler databaseHandler = new WorkoutDatabaseHandler(getApplicationContext());
                databaseHandler.deleteLink(links.get(prev).ID);
                listItems();
            }
        });






    }

    void listItems() {
        name = findViewById(R.id.workoutName);
        if(edit){
            saveButton = findViewById(R.id.saveW2Button);
            saveButton.hide();
            name.setKeyListener(null);
            name.setText(workoutName);
        }else{
            saveButton.show();
        }
        name.setText(workoutName);
        simpleListView = (ListView) findViewById(R.id.linkList);
        simpleListView.setSelector(R.drawable.selector);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        WorkoutDatabaseHandler databaseHandler = new WorkoutDatabaseHandler(getApplicationContext());
        if(workoutName!=null) {
            links = databaseHandler.getWorkoutLinks(workoutName);


            for (int i = 0; i < links.size(); i++) {
                HashMap<String, String> hashMap = new HashMap<>();//create a hashmap to store the data in key value pair
                Exercise exercise = databaseHandler.getExercise(links.get(i).FK_EXERCISE);
                Link link = links.get(i);
                hashMap.put(from[0], exercise.name);
                if (!link.type.equals("timed")) {
                    hashMap.put(from[1], String.format(Locale.UK,"%dx%d     rest in between %.2f,    rest after %.2f", link.sets, link.reps, link.restIn, link.restAfter));
                } else {
                    hashMap.put(from[1], String.format(Locale.UK,"%dx%.2f sec    rest in between %.2f,    rest after %.2f", link.sets, link.duration, link.restIn, link.restAfter));
                }
                arrayList.add(hashMap);//add the hashmap into arrayList
            }

            final SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), arrayList, R.layout.layout_list_chosen_exercise, from, to);//Create object and set the parameters for simpleAdapter
            simpleListView.setAdapter(simpleAdapter);
            //TODO add item transfer data
            simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position != prev) {
                        Object o = simpleListView.getItemAtPosition(position);
                        //selected = exercises.get(position);

                        SimpleAdapter str = (SimpleAdapter) o; //As you are using Default String Adapter
                        //Toast.makeText(getBaseContext(),str.getTitle(),Toast.LENGTH_SHORT).show();
                        addButton.hide();
                        editButton.show();
                        //Intent k = new Intent(getActivity(), ExerciseEditActivity.class);
                        view.setSelected(true);
                        //startActivity(k);
                        prev = position;
                    } else {
                        prev = -1;
                        view.setSelected(false);
                        addButton.show();
                        editButton.hide();
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
        prev = -1;
        listItems();
        super.onResume();
    }
}
