package bleizing.pariwisata.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import bleizing.pariwisata.Model;
import bleizing.pariwisata.R;
import bleizing.pariwisata.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionThreeFragment extends Fragment {


    public QuestionThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question_three, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView tv_answer_one = (TextView) getActivity().findViewById(R.id.tv_answer_one);
        TextView tv_answer_two = (TextView) getActivity().findViewById(R.id.tv_answer_two);
        TextView tv_answer_three = (TextView) getActivity().findViewById(R.id.tv_answer_three);

        tv_answer_one.setOnClickListener(onClickListener);
        tv_answer_two.setOnClickListener(onClickListener);
        tv_answer_three.setOnClickListener(onClickListener);

        ImageView imgBack = (ImageView) getActivity().findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).goBack();
            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_answer_one :
                    Model.setQuestionThree(1);
                    break;
                case R.id.tv_answer_two :
                    Model.setQuestionThree(2);
                    break;
                case R.id.tv_answer_three :
                    Model.setQuestionThree(3);
                    break;
            }

            if (Model.isChooseRecommendation()) {
                ((MainActivity) getActivity()).changeToWisataFragment();
            } else {
                ((MainActivity) getActivity()).changeToDetailWisataFragment(0);
            }
        }
    };
}
