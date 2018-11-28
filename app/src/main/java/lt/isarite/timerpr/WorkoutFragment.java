package lt.isarite.timerpr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Activity;
import android.view.Menu;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


/**

 */
public class WorkoutFragment extends Fragment {

    ListView simpleListView;
    String from[] = {"firstLine"};
    String firstLine[] = {"Chest workout", "Ab extinction", "10 minute Jump Rope"};
    FloatingActionButton addButton, editButton, deleteButton, playButton;
    ArrayList<Workout> workouts;
    Workout selected;
    int prev = -1;

    int[] to = {R.id.eFirstLine};

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_workout, container, false);
        setButtonListeners(myView);
        return myView;

    }

    void setButtonListeners(View myView){
        addButton = (FloatingActionButton) myView.findViewById(R.id.addWButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent k = new Intent(getActivity(), WorkoutEditActivity.class);
                startActivity(k);
            }
        });

        editButton = (FloatingActionButton) myView.findViewById(R.id.editWButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent k = new Intent(getActivity(), WorkoutEditActivity.class);
                Workout workout = workouts.get(prev);
                k.putExtra("WORKOUT_NAME",workout.name);
                k.putExtra("EDIT",true);
                startActivity(k);
            }
        });

        playButton = (FloatingActionButton) myView.findViewById(R.id.playWButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent k = new Intent(getActivity(), TimerActivity.class);
                Workout workout = workouts.get(prev);
                k.putExtra("WORKOUT_NAME",workout.name);
                startActivity(k);
            }
        });

        deleteButton = (FloatingActionButton) myView.findViewById(R.id.deleteWButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WorkoutDatabaseHandler databaseHandler = new WorkoutDatabaseHandler(getActivity());
                databaseHandler.deleteWorkout(workouts.get(prev).name);
                listItems();
            }
        });

    }

    void listItems() {
        simpleListView = (ListView) getView().findViewById(R.id.workout_list);
        simpleListView.setSelector(R.drawable.selector);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        WorkoutDatabaseHandler databaseHandler = new WorkoutDatabaseHandler(getActivity());
        workouts = databaseHandler.getAllWorkouts();

        for (int i = 0; i < workouts.size(); i++) {
            HashMap<String, String> hashMap = new HashMap<>();//create a hashmap to store the data in key value pair
            hashMap.put(from[0], workouts.get(i).name);
            arrayList.add(hashMap);//add the hashmap into arrayList
        }
        final SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), arrayList, R.layout.layout_list_workout, from, to);//Create object and set the parameters for simpleAdapter
        simpleListView.setAdapter(simpleAdapter);
        //TODO add item transfer data
        simpleListView.setOnItemClickListener((new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position!=prev) {
                    Object o = simpleListView.getItemAtPosition(position);
                    //selected = exercises.get(position);

                    SimpleAdapter str = (SimpleAdapter) o; //As you are using Default String Adapter
                    //Toast.makeText(getBaseContext(),str.getTitle(),Toast.LENGTH_SHORT).show();
                    addButton.hide();
                    editButton.show();
                    playButton.show();
                    //Intent k = new Intent(getActivity(), ExerciseEditActivity.class);
                    //simpleListView.setSelection(position);
                    //startActivity(k);
                    prev = position;
                }else{
                    prev = -1;
                    view.setSelected(false);
                    simpleListView.setItemChecked(position, false);
                    simpleListView.setSelection(-1);
                    addButton.show();
                    editButton.hide();
                    playButton.hide();
                    simpleListView.requestLayout();
                }
            }
        }));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        listItems();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        prev = -1;
        simpleListView.setItemChecked(simpleListView.getSelectedItemPosition(), false);
        simpleListView.requestLayout();
        playButton.hide();
        editButton.hide();
        addButton.show();
        listItems();
        super.onResume();
    }
}
