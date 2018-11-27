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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExerciseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseFragment extends Fragment implements View.OnClickListener {

    ListView simpleListView;
    String from[] = {"firstLine", "secondLine"};
    String firstLine[] = {"Push ups", "Sit ups", "Pull ups"};
    String secondLine[] = {"chest and triceps", "Middle abs","Back"};
    FloatingActionButton button;
    int[] to = {R.id.eFirstLine, R.id.eSecondLine};
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_exercise, container, false);
        button = (FloatingActionButton) myView.findViewById(R.id.addEButton);
        button.setOnClickListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent k = new Intent(getActivity(), ExerciseEditActivity.class);
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
        ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
        for (int i=0;i<firstLine.length;i++)
        {
            HashMap<String,String> hashMap=new HashMap<>();//create a hashmap to store the data in key value pair
            hashMap.put(from[0],firstLine[i]);
            hashMap.put(from[1],secondLine[i]);
            arrayList.add(hashMap);//add the hashmap into arrayList
        }
        final SimpleAdapter simpleAdapter=new SimpleAdapter(getContext(),arrayList,R.layout.layout_list_exercise,from,to);//Create object and set the parameters for simpleAdapter
        simpleListView.setAdapter(simpleAdapter);
        //TODO add item transfer data
        simpleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = simpleListView.getItemAtPosition(position);
                SimpleAdapter str = (SimpleAdapter) o; //As you are using Default String Adapter
                //Toast.makeText(getBaseContext(),str.getTitle(),Toast.LENGTH_SHORT).show();
                button.hide();
                Intent k = new Intent(getActivity(), ExerciseEditActivity.class);
                view.setSelected(true);
                //startActivity(k);
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        listItems();
        super.onActivityCreated(savedInstanceState);
    }

    public void createNewExercise(){
        Intent k = new Intent(getContext(), ExerciseEditActivity.class);
        startActivity(k);
    }

    @Override
    public void onClick(View v) {
        Intent k = new Intent(getActivity(), ExerciseEditActivity.class);
        startActivity(k);
    }




}
