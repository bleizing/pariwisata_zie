package bleizing.pariwisata.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import bleizing.pariwisata.Model;
import bleizing.pariwisata.R;
import bleizing.pariwisata.activities.MainActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btn_kriteria = (Button) getActivity().findViewById(R.id.btn_kriteria);
        btn_kriteria.setOnClickListener(onButtonClicked);

        Button btn_kemana = (Button) getActivity().findViewById(R.id.btn_kemana);
        btn_kemana.setOnClickListener(onButtonClicked);
    }

    private View.OnClickListener onButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_kriteria :
                    Model.setChooseRecommendation(true);
                    break;
                case R.id.btn_kemana :
                    Model.setChooseRecommendation(false);
                    break;
            }
            ((MainActivity) getActivity()).changeToQuestionOneFragment();
        }
    };
}
