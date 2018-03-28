package bleizing.pariwisata.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import bleizing.pariwisata.Constants;
import bleizing.pariwisata.Model;
import bleizing.pariwisata.R;
import bleizing.pariwisata.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendationFragment extends Fragment {

    private TextView tv_recommendation_name;

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

        if (question_one == 1 && question_two == 2 && question_three == 3) {
            Model.setRecommendation(Constants.CANDI_BOROBUDUR);
        }

        setRecommendationName(Model.getRecommendation());
    }

    private void setRecommendationName(String recommendationName) {
        tv_recommendation_name.setText(recommendationName);
    }
}
