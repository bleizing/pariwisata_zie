package bleizing.pariwisata.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

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

        LinearLayout linMauKemana = (LinearLayout) getActivity().findViewById(R.id.lin_mau_kemana);
        linMauKemana.setOnClickListener(onButtonClicked);

        LinearLayout linMasukkanKriteria = (LinearLayout) getActivity().findViewById(R.id.lin_kriteria);
        linMasukkanKriteria.setOnClickListener(onButtonClicked);

        LinearLayout linListWisata = (LinearLayout) getActivity().findViewById(R.id.lin_list_wisata);
        linListWisata.setOnClickListener(onButtonClicked);
    }

    private View.OnClickListener onButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.lin_mau_kemana :
//                    Model.setChooseRecommendation(true);
                    ((MainActivity) getActivity()).changeToMauKemanaFragment();
                    break;
                case R.id.lin_kriteria :
//                    Model.setChooseRecommendation(false);
                    ((MainActivity) getActivity()).changeToMasukkanKriteriaFragment();
                    break;
                case R.id.lin_list_wisata :
                    ((MainActivity) getActivity()).changeToDaftarWisataFragment();
                    break;
            }
        }
    };
}