package lt.isarite.timerpr;

import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dinuscxj.progressbar.CircleProgressBar;

import java.util.ArrayList;

public class TimerActivity extends AppCompatActivity {

    CircleProgressBar circle;
    ArrayList<Link> links;
    ArrayList<Exercise> exercises;
    Button b;
    int number = 0;
    int set = 0;
    boolean rest = false;
    TextView exercise;
    long startMil;
    int progress = 0;





    TextView timerTextView;
    long startTime = 0;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {


        @Override
        public void run() {
            long millis = startTime - System.currentTimeMillis();
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            if(millis<=0){
                timerHandler.removeCallbacks(timerRunnable);
                rest = !rest;
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                setUpNext();
                return;
            }

            timerHandler.removeCallbacks(timerRunnable);

            timerTextView.setText(String.format("%d:%02d", minutes, seconds));
            Handler progressBarHandler = new Handler();
            progress = (int)(startMil/1000)*(seconds + minutes*10);
            progressBarHandler .post(new Runnable() {
                public void run() {
                    circle.setMax(100);
                    circle.setProgress(progress);
                }
            });

            timerHandler.postDelayed(this, 500);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        circle = findViewById(R.id.progressCircle);
        circle.setMax(100);
        circle.setProgress(0);
        Intent intent = getIntent();
        String workoutName = intent.getStringExtra("WORKOUT_NAME");
        WorkoutDatabaseHandler databaseHandler = new WorkoutDatabaseHandler(getApplicationContext());
        exercise = findViewById(R.id.timerExercise);


        timerTextView = (TextView) findViewById(R.id.timer);
        links = databaseHandler.getWorkoutLinks(workoutName);
        exercises = new ArrayList<>();
        for(int i = 0; i < links.size(); i ++){
            exercises.add(databaseHandler.getExercise(links.get(i).FK_EXERCISE));
        }

        b =  findViewById(R.id.timerButton);
        b.setText("start");
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (b.getText().equals("stop")) {
                    timerHandler.removeCallbacks(timerRunnable);
                    finish();
                    b.setText("start");
                } else if(b.getText().equals("next")) {
                    rest = !rest;
                    setUpNext();
                }else if(b.getText().equals("exit")) {
                    killActivity();
                }else {
                    exercise.setVisibility(View.VISIBLE);
                    setUpNext();
                    //b.setText("stop");
                }
            }
        });


    }

    void killActivity(){
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        b = (Button)findViewById(R.id.timerButton);
        b.setText("start");
    }

    void setUpNext(){
        if(rest) {
            set++;
        }
        b = findViewById(R.id.timerButton);
        if(set!=links.get(number).sets){
            if(rest){
                startMil = (long)links.get(number).restIn*1000;
                startTime = startMil+System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);
                exercise = findViewById(R.id.timerExercise);
                exercise.setText("Rest");
                b.setText("stop");
            }else{
                if(links.get(number).type.equals("timed")){
                    exercise = findViewById(R.id.timerExercise);
                    exercise.setText(exercises.get(number).name);
                    startMil = (long)links.get(number).duration*1000;
                    startTime = startMil+System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    setButtonText("stop");
                }
                if(links.get(number).type.equals("reps")){
                    exercise = findViewById(R.id.timerExercise);
                    exercise.setText(exercises.get(number).name);
                    timerTextView = findViewById(R.id.timer);
                    b = findViewById(R.id.timerButton);
                    setButtonText("next");

                    timerTextView.setText(String.format("%d reps", links.get(number).reps));
                }
            }


        }else {
            if(number>=links.size()-1){
                exercise.setVisibility(View.INVISIBLE);
                TextView congrats = findViewById(R.id.congrats);
                congrats.setText("Congratuliations! You've completed your workout.");
                congrats.setVisibility(View.VISIBLE);
                circle.setVisibility(View.INVISIBLE);
                timerTextView.setVisibility((View.INVISIBLE));
                setButtonText("exit");

            }else {
                startMil = (long)links.get(number).restAfter*1000;
                startTime = startMil + System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);
                exercise.setText("Rest");
                setButtonText("stop");
                number++;
                return;
            }
        }



    }

    void setButtonText(String string){
        b.setText(string);
    }
}
