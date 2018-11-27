package lt.isarite.timerpr;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class WorkoutEditActivity extends AppCompatActivity {

    ListView simpleListView;
    String from[] = {"firstLine", "secondLine"};
    String firstLine[] = {"Chest workout", "Ab extinction", "10 minute Jump Rope"};
    String secondLine[] = {"Chest", "Abs", "Cardio"};
    FloatingActionButton addButton, editButton, deleteButton;
    int[] to = {R.id.eFirstLine, R.id.eSecondLine};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_edit);
    }

    void setButtonListeners(View myView){
        addButton = (FloatingActionButton) myView.findViewById(R.id.addW2Button);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent k = new Intent(getApplicationContext(), ExerciseEditInsideWorkoutActivity.class);
                startActivity(k);
            }
        });

//        editButton = (FloatingActionButton) myView.findViewById(R.id.editW2Button);
//        editButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent k = new Intent(getApplicationContext(), ExerciseEditInsideWorkoutActivity.class);
//                startActivity(k);
//            }
//        });

    }
}
