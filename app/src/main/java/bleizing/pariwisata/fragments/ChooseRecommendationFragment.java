package bleizing.pariwisata.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import bleizing.pariwisata.Constants;
import bleizing.pariwisata.Model;
import bleizing.pariwisata.R;
import bleizing.pariwisata.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseRecommendationFragment extends Fragment {

    private Button btn_recommendation_one;
    private Button btn_recommendation_two;
    private Button btn_recommendation_three;

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
            ((MainActivity) getActivity()).changeToInfoRecommedationFragment();
        }
    };

    private void checkAnswer() {
        int question_one = Model.getQuestionOne();
        int question_two = Model.getQuestionTwo();
        int question_three = Model.getQuestionThree();

        if (question_one == 1 && question_two == 2 && question_three == 3) {
            setRecommendationAnswer(Constants.CANDI_BOROBUDUR, Constants.CANDI_PRAMBANAN, Constants.GUNUNG_MERAPI);
        }
    }

    private void setRecommendationAnswer(String question_one, String question_two, String question_three) {
        btn_recommendation_one.setText(question_one);
        btn_recommendation_two.setText(question_two);
        btn_recommendation_three.setText(question_three);
    }
}
