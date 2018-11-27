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

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkoutFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkoutFragment extends Fragment {

    ListView simpleListView;
    String from[] = {"firstLine", "secondLine"};
    String firstLine[] = {"Chest workout", "Ab extinction", "10 minute Jump Rope"};
    String secondLine[] = {"Chest", "Abs", "Cardio"};
    FloatingActionButton addButton, editButton, deleteButton, playButton;
    int[] to = {R.id.eFirstLine, R.id.eSecondLine};

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //listWorkouts();

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
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent k = new Intent(getActivity(), WorkoutEditActivity.class);
                startActivity(k);
            }
        });

        playButton = (FloatingActionButton) myView.findViewById(R.id.playWButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent k = new Intent(getActivity(), TimerActivity.class);
                startActivity(k);
            }
        });

    }

    void listItems() {
        simpleListView = (ListView) getView().findViewById(R.id.workout_list);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        for (int i = 0; i < firstLine.length; i++) {
            HashMap<String, String> hashMap = new HashMap<>();//create a hashmap to store the data in key value pair
            hashMap.put(from[0], firstLine[i]);
            hashMap.put(from[1], secondLine[i]);
            arrayList.add(hashMap);//add the hashmap into arrayList
        }
        final SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), arrayList, R.layout.layout_list_exercise, from, to);//Create object and set the parameters for simpleAdapter
        simpleListView.setAdapter(simpleAdapter);
        //TODO add item transfer data
        simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = simpleListView.getItemAtPosition(position);
                SimpleAdapter str = (SimpleAdapter) o; //As you are using Default String Adapter
                addButton.hide();
                editButton.show();
                playButton.show();
                Intent k = new Intent(getActivity(), ExerciseEditActivity.class);
                view.setSelected(true);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        listItems();
        super.onActivityCreated(savedInstanceState);
    }
}
