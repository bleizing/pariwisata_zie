package bleizing.pariwisata.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bleizing.pariwisata.Model;
import bleizing.pariwisata.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoRecommendationFragment extends Fragment {


    public InfoRecommendationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_recommendation, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView tv_header_info = (TextView) getActivity().findViewById(R.id.tv_header_info);
        tv_header_info.setText(Model.getRecommendation());
    }
}
