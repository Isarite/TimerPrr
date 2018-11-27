package lt.isarite.timerpr;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class TimerActivity extends AppCompatActivity {

    ProgressBar circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        //controlCircle();
    }



    void controlCircle(){
        circle = (ProgressBar) findViewById(R.id.progressCircle);
        circle.incrementProgressBy(1);
        circle.setProgress(0);
        circle.setMax(100);
        for(int i = 0; i < 100; i++){
            circle.setProgress(i);
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
