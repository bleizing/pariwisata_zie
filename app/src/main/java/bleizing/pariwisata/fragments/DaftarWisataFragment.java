package bleizing.pariwisata.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bleizing.pariwisata.Model;
import bleizing.pariwisata.R;
import bleizing.pariwisata.RecyclerTouchListener;
import bleizing.pariwisata.RecyclerViewClickListener;
import bleizing.pariwisata.Wisata;
import bleizing.pariwisata.WisataAdapter;
import bleizing.pariwisata.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaftarWisataFragment extends Fragment {

    private List<Wisata> wisataList;


    public DaftarWisataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daftar_wisata, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btnAyoMulai = (Button) getActivity().findViewById(R.id.btn_ayo_mulai);
        btnAyoMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model.setChooseRecommendation(false);
                Model.setJustShowWisataList(true);
                ((MainActivity) getActivity()).changeToWisataFragment();
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
