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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import bleizing.pariwisata.Constants;
import bleizing.pariwisata.Model;
import bleizing.pariwisata.NetApi;
import bleizing.pariwisata.R;
import bleizing.pariwisata.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendationFragment extends Fragment {
    private static final String TAG = "RecommendationFragment";

    private RequestQueue requestQueue;

    private TextView tv_recommendation_name;

    private ProgressDialog progressDialog;

    public RecommendationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommendation, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        requestQueue = Volley.newRequestQueue(getContext());

        tv_recommendation_name = (TextView) getActivity().findViewById(R.id.tv_recommendation_name);

        checkRecommendation();

        Button btn_agree = (Button) getActivity().findViewById(R.id.btn_agree);
        btn_agree.setOnClickListener(onButtonClicked);
    }

    private View.OnClickListener onButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_agree :
                    ((MainActivity) getActivity()).changeToInfoRecommedationFragment();
                    break;
            }
        }
    };

    private void checkRecommendation() {
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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NetApi.BASE_URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "response = " + response);
                try {
                    String rekomendasi = response.getString("rekomendasi_isi");

                    Model.setRecommendation(rekomendasi);

                    setRecommendationName(Model.getRecommendation());

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

//        if (question_one == 1 && question_two == 2 && question_three == 3) {
//            Model.setRecommendation(Constants.CANDI_BOROBUDUR);
//        }
//
//        setRecommendationName(Model.getRecommendation());
    }

    private void setRecommendationName(String recommendationName) {
        tv_recommendation_name.setText(recommendationName);
    }
}
