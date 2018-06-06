package bleizing.pariwisata.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import bleizing.pariwisata.Model;
import bleizing.pariwisata.R;
import bleizing.pariwisata.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MasukkanKriteriaFragment extends Fragment {


    public MasukkanKriteriaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_masukkan_kriteria, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btnAyoMulai = (Button) getActivity().findViewById(R.id.btn_ayo_mulai);
        btnAyoMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.setChooseRecommendation(true);
                Model.setJustShowWisataList(false);
                ((MainActivity) getActivity()).changeToQuestionOneFragment();
            }
        });

        ImageView imgBack = (ImageView) getActivity().findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).goBack();
            }
        });
    }
}
