package bleizing.pariwisata.fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import bleizing.pariwisata.Model;
import bleizing.pariwisata.MySupportMapFragment;
import bleizing.pariwisata.NetApi;
import bleizing.pariwisata.R;
import bleizing.pariwisata.Wisata;
import bleizing.pariwisata.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailWisataFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = "DetailWisataFragment";

    private GoogleMap map;

    private Wisata wisata;

    private int wisataId;

    private ImageView imgWisataDetail;

    private TextView tvWisataTitle;
    private TextView tvWisataInfo;

    private Button btnBackHome;
    private Button btnPilihRekomendasi;

    private ProgressDialog progressDialog;

    private RequestQueue requestQueue;

    private ScrollView scrollView;

    public DetailWisataFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_wisata, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        scrollView = (ScrollView) getActivity().findViewById(R.id.scroll_view);

        MySupportMapFragment supportMapFragment;

        supportMapFragment = (MySupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps_view);
        supportMapFragment.setListener(new MySupportMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                scrollView.requestDisallowInterceptTouchEvent(true);
            }
        });

        supportMapFragment.getMapAsync(this);

        wisataId = 0;

        LinearLayout linBack = (LinearLayout) getActivity().findViewById(R.id.lin_back);

        btnBackHome = (Button) getActivity().findViewById(R.id.btn_back_home);
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).changeToMainFragment();
            }
        });

        btnPilihRekomendasi = (Button) getActivity().findViewById(R.id.btn_pilih_rekomendasi);
        btnPilihRekomendasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prosesPilihRekomendasi();
            }
        });

        ImageView imgBack = (ImageView) getActivity().findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).goBack();
            }
        });

        imgWisataDetail = (ImageView) getActivity().findViewById(R.id.img_wisata_detail);

        tvWisataTitle = (TextView) getActivity().findViewById(R.id.tv_wisata_title);
        tvWisataInfo = (TextView) getActivity().findViewById(R.id.tv_wisata_info);

        btnBackHome.setVisibility(View.GONE);
        btnPilihRekomendasi.setVisibility(View.GONE);
        linBack.setVisibility(View.GONE);

//        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps_view);
//        fragment.getMapAsync(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            wisataId = bundle.getInt("wisataId");
        }

        if (wisataId == 0) {
            prosesGetRekomendasi();
        } else {
            getWisataById();
            setData();
//            setMapData();
        }

        if (Model.isJustShowWisataList()) {
            linBack.setVisibility(View.VISIBLE);
            btnBackHome.setVisibility(View.GONE);
            btnPilihRekomendasi.setVisibility(View.GONE);
        } else {
            linBack.setVisibility(View.GONE);
        }

        if (Model.isChooseRecommendation()) {
            // Check if is ChooseRecommendation == true
            btnBackHome.setVisibility(View.GONE);
            btnPilihRekomendasi.setVisibility(View.VISIBLE);
            linBack.setVisibility(View.VISIBLE);
        } else {
            btnPilihRekomendasi.setVisibility(View.GONE);
            btnBackHome.setVisibility(View.VISIBLE);
            linBack.setVisibility(View.GONE);

            if (Model.isJustShowWisataList()) {
                linBack.setVisibility(View.VISIBLE);
                btnBackHome.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.getUiSettings().setAllGesturesEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);

        if (wisata != null) {
            setMapData();
        }
    }

    private void prosesPilihRekomendasi() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Mohon Tunggu...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        requestQueue = Volley.newRequestQueue(getContext());

        int question_one = Model.getQuestionOne();
        int question_two = Model.getQuestionTwo();
        int question_three = Model.getQuestionThree();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", "prosesKasus");
            jsonObject.put("jawaban1", question_one);
            jsonObject.put("jawaban2", question_two);
            jsonObject.put("jawaban3", question_three);
            jsonObject.put("rekomendasi_id", wisataId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "jsonObject = " + jsonObject);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NetApi.BASE_URL_INDEX, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "response = " + response);

                progressDialog.dismiss();

                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(getContext());
                }
                builder.setTitle("Berhasil!")
                        .setMessage("Anda telah berhasil merekomendasikan tempat ini. Terima kasih!")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ((MainActivity) getActivity()).changeToMainFragment();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
            }
        });

        jsonObjectRequest.setTag(TAG);
        requestQueue.add(jsonObjectRequest);
    }

    private void prosesGetRekomendasi() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Mohon Tunggu...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        requestQueue = Volley.newRequestQueue(getContext());

        int question_one = Model.getQuestionOne();
        int question_two = Model.getQuestionTwo();
        int question_three = Model.getQuestionThree();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", "prosesHitung");
            jsonObject.put("jawaban1", question_one);
            jsonObject.put("jawaban2", question_two);
            jsonObject.put("jawaban3", question_three);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "jsonObject = " + jsonObject);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NetApi.BASE_URL_INDEX, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "response = " + response);
                try {

                    String status = response.getString("status");

                    if (status.equals("success")) {
                        wisataId = response.getInt("rekomendasi_id");

                        getWisataById();
                        setMapData();
                        setData();

                        progressDialog.dismiss();
                    } else {
                        progressDialog.dismiss();
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(getContext());
                        }
                        builder.setTitle("Kesalahan!")
                                .setMessage("Mohon maaf, sistem tidak dapat merekomendasikan tempat wisata anda. Silahkan mencoba untuk memberikan rekomendasi terlebih dahulu. Terima kasih!")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ((MainActivity) getActivity()).changeToMainFragment();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
            }
        });

        jsonObjectRequest.setTag(TAG);
        requestQueue.add(jsonObjectRequest);
    }

    private void getWisataById() {
        if (Model.getWisataList() != null || Model.getWisataList().size() > 0) {
            for (Wisata w : Model.getWisataList()) {
                if (wisataId == w.getId()) {
                    wisata = w;
                    break;
                }
            }
        }
    }

    private void setData() {
        if (wisata != null) {
            String imgUrl = NetApi.BASE_URL_IMAGE + wisata.getFoto();

            Picasso.with(getContext()).load(imgUrl).fit().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imgWisataDetail);
            tvWisataTitle.setText(wisata.getIsi());
            tvWisataInfo.setText(wisata.getInfo());
        }
    }

    private void setMapData() {
        LatLng latLng = new LatLng(wisata.getLat(), wisata.getLng());
        map.addMarker(new MarkerOptions().position(latLng).title(wisata.getIsi()));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
    }
}
