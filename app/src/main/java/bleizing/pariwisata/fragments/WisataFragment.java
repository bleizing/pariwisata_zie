package bleizing.pariwisata.fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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
public class WisataFragment extends Fragment {

    private List<Wisata> wisataList;

    public WisataFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wisata, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        wisataList = Model.getWisataList();

        if (wisataList != null) {
            RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.wisata_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemViewCacheSize(20);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            final WisataAdapter adapter = new WisataAdapter(getContext(), wisataList);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Wisata wisata = adapter.getWisataList().get(position);

//                Toast.makeText(getContext(), wisata.getIsi(), Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).changeToDetailWisataFragment(wisata.getId());
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
            recyclerView.setAdapter(adapter);
        } else {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(getContext());
            }
            builder.setTitle("Kesalahan!")
                    .setMessage("Mohon maaf, daftar tempat wisata tidak tersedia. Terima kasih!")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ((MainActivity) getActivity()).changeToMainFragment();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }

        ImageView imgBack = (ImageView) getActivity().findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).goBack();
            }
        });
    }
}
