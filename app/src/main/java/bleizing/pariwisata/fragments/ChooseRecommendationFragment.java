package bleizing.pariwisata.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import bleizing.pariwisata.Constants;
import bleizing.pariwisata.Model;
import bleizing.pariwisata.NetApi;
import bleizing.pariwisata.R;
import bleizing.pariwisata.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseRecommendationFragment extends Fragment {
    private static final String TAG = "ChooseRecommendation";

    private RequestQueue requestQueue;

    private Button btn_recommendation_one;
    private Button btn_recommendation_two;
    private Button btn_recommendation_three;

    private ProgressDialog progressDialog;

    public ChooseRecommendationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_recommendation, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        requestQueue = Volley.newRequestQueue(getContext());

        btn_recommendation_one = (Button) getActivity().findViewById(R.id.btn_reccomendation_one);
        btn_recommendation_two = (Button) getActivity().findViewById(R.id.btn_reccomendation_two);
        btn_recommendation_three = (Button) getActivity().findViewById(R.id.btn_reccomendation_three);

        checkAnswer();

        btn_recommendation_one.setOnClickListener(onButtonClicked);
        btn_recommendation_two.setOnClickListener(onButtonClicked);
        btn_recommendation_three.setOnClickListener(onButtonClicked);
    }

    private View.OnClickListener onButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_reccomendation_one :
                    Model.setRecommendation(btn_recommendation_one.getText().toString());
                    break;
                case R.id.btn_reccomendation_two :
                    Model.setRecommendation(btn_recommendation_two.getText().toString());
                    break;
                case R.id.btn_reccomendation_three :
                    Model.setRecommendation(btn_recommendation_three.getText().toString());
            }

            int question_one = Model.getQuestionOne();
            int question_two = Model.getQuestionTwo();
            int question_three = Model.getQuestionThree();

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("type", "prosesKasus");
                jsonObject.put("jawaban1", question_one);
                jsonObject.put("jawaban2", question_two);
                jsonObject.put("jawaban3", question_three);
                jsonObject.put("rekomendasi_isi", Model.getRecommendation());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d(TAG, "jsonObject = " + jsonObject);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NetApi.BASE_URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, "response = " + response);

                    ((MainActivity) getActivity()).changeToInfoRecommedationFragment();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            jsonObjectRequest.setTag(TAG);
            requestQueue.add(jsonObjectRequest);
        }
    };

    private void checkAnswer() {
//        int question_one = Model.getQuestionOne();
//        int question_two = Model.getQuestionTwo();
//        int question_three = Model.getQuestionThree();

//        if (question_one == 1 && question_two == 2 && question_three == 3) {
//            setRecommendationAnswer(Constants.CANDI_BOROBUDUR, Constants.CANDI_PRAMBANAN, Constants.GUNUNG_MERAPI);
//        }

        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", "getRekomendasi");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "jsonObject = " + jsonObject);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NetApi.BASE_URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "response = " + response);
                try {
                    JSONArray list = response.getJSONArray("list");

                    ArrayList<String> rekomendasi_list = new ArrayList<>();
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject jsonObject1 = list.getJSONObject(i);

                        rekomendasi_list.add(jsonObject1.getString("rekomendasi_isi"));
                    }
                    String rekomendasi_isi1 = rekomendasi_list.get(0);
                    String rekomendasi_isi2 = rekomendasi_list.get(1);
                    String rekomendasi_isi3 = rekomendasi_list.get(2);
//
                    setRecommendationAnswer(rekomendasi_isi1, rekomendasi_isi2, rekomendasi_isi3);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        jsonObjectRequest.setTag(TAG);
        requestQueue.add(jsonObjectRequest);
    }

    private void setRecommendationAnswer(String question_one, String question_two, String question_three) {
        btn_recommendation_one.setText(question_one);
        btn_recommendation_two.setText(question_two);
        btn_recommendation_three.setText(question_three);
    }
}
