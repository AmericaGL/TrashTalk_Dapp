package vic.sample.fireapp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


public class Tab2Fragment extends Fragment {
    private static final String TAG = "Tab2Fragment";

    private ListView mListView;
    private TextView xemView;
    private TextView addyview;
    private TextView balview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_layout,container,false);
//        mListView = (ListView) view.findViewById(R.id.listView);
//
//        ArrayList<Card> list = new ArrayList<>();
//
//        list.add(new Card("drawable://" + R.drawable.aluminum_can, "Plain Aluminum Can"));
//        list.add(new Card("drawable://" + R.drawable.aquafina_water_bottle, "Aquafina Water Bottle"));
//        list.add(new Card("drawable://" + R.drawable.crushed_can, "Crushed Can"));
//        list.add(new Card("drawable://" + R.drawable.pacifico, "Pacifico Can"));
//        list.add(new Card("drawable://" + R.drawable.plain_water_bottle, "Plain Water Bottle"));
//        list.add(new Card("drawable://" + R.drawable.smart_water, "Smart Water Bottle"));
//
//        CustomListAdapter adapter = new CustomListAdapter(getActivity(), R.layout.card_layout_main, list);
//        mListView.setAdapter(adapter);

        xemView = view.findViewById(R.id.balance);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        String apidata = sharedPref.getString("strBalance", "");

        xemView.setText(apidata);

       // tv.setText(name);




        return view;
    }
}


