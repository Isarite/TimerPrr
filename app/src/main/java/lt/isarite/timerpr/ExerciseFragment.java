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
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;



public class ExerciseFragment extends Fragment implements View.OnClickListener {

    ListView simpleListView;
    String from[] = {"firstLine", "secondLine"};
    String firstLine[] = {"Push ups", "Sit ups", "Pull ups"};
    String secondLine[] = {"chest and triceps", "Middle abs","Back"};
    Exercise selected;
    int prev = -1;
    ArrayList<Exercise> exercises;
    FloatingActionButton addButton, editButton, deleteButton;
    int[] to = {R.id.eFirstLine, R.id.eSecondLine};
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_exercise, container, false);
        addButton = (FloatingActionButton) myView.findViewById(R.id.addEButton);
        addButton.setOnClickListener(this);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent k = new Intent(getActivity(), ExerciseEditActivity.class);
                startActivity(k);
            }
        });

        deleteButton = (FloatingActionButton) myView.findViewById(R.id.deleteEButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                WorkoutDatabaseHandler databaseHandler = new WorkoutDatabaseHandler(getActivity());
                databaseHandler.deleteExercise(exercises.get(prev).ID);
                listItems();
            }
        });

        editButton = (FloatingActionButton) myView.findViewById(R.id.editEButton);
        editButton.setOnClickListener(this);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent k = new Intent(getActivity(), ExerciseEditActivity.class);
                Exercise exercise = exercises.get(prev);
                k.putExtra("EXERCISE_NAME",exercise.name);
                k.putExtra("EXERCISE_ID", exercise.ID);
                k.putExtra("EXERCISE_MUSCLE", exercise.muscle);
                k.putExtra("EXERCISE_DESCRIPTION", exercise.description);


                startActivity(k);
            }
        });
        return myView;






    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    void listItems(){
        simpleListView=(ListView)getView().findViewById(R.id.exercise_list);
        simpleListView.setSelector(R.drawable.selector);
        ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
        WorkoutDatabaseHandler databaseHandler = new WorkoutDatabaseHandler(getActivity());
        exercises = databaseHandler.getAllExercises();
        for (int i=0;i<exercises.size();i++)
        {
            HashMap<String,String> hashMap=new HashMap<>();//create a hashmap to store the data in key value pair
            //hashMap.put(from[0],firstLine[i]);
            //hashMap.put(from[1],secondLine[i]);
            hashMap.put(from[0],exercises.get(i).name);
            hashMap.put(from[1],exercises.get(i).muscle);
            arrayList.add(hashMap);//add the hashmap into arrayList
        }
        final SimpleAdapter simpleAdapter=new SimpleAdapter(getContext(),arrayList,R.layout.layout_list_exercise,from,to);//Create object and set the parameters for simpleAdapter
        simpleListView.setAdapter(simpleAdapter);
        //TODO add item transfer data
        simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position!=prev) {
                    Object o = simpleListView.getItemAtPosition(position);
                    //selected = exercises.get(position);

                    SimpleAdapter str = (SimpleAdapter) o; //As you are using Default String Adapter
                    //Toast.makeText(getBaseContext(),str.getTitle(),Toast.LENGTH_SHORT).show();
                    addButton.hide();
                    editButton.show();
                    deleteButton.show();
                    //Intent k = new Intent(getActivity(), ExerciseEditActivity.class);
                    view.setSelected(true);
                    //startActivity(k);
                    prev = position;
                }else{
                    prev = -1;
                    view.setSelected(false);
                    addButton.show();
                    editButton.hide();
                    deleteButton.hide();
                }
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        listItems();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        prev = -1;
        deleteButton.hide();
        editButton.hide();
        addButton.show();
        listItems();
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        Intent k = new Intent(getActivity(), ExerciseEditActivity.class);
        startActivity(k);
    }




}
