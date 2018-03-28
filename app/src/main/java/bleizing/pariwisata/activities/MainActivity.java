package bleizing.pariwisata.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import bleizing.pariwisata.Constants;
import bleizing.pariwisata.R;
import bleizing.pariwisata.fragments.ChooseRecommendationFragment;
import bleizing.pariwisata.fragments.InfoRecommendationFragment;
import bleizing.pariwisata.fragments.MainFragment;
import bleizing.pariwisata.fragments.QuestionOneFragment;
import bleizing.pariwisata.fragments.QuestionThreeFragment;
import bleizing.pariwisata.fragments.QuestionTwoFragment;
import bleizing.pariwisata.fragments.RecommendationFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            MainFragment fragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragment, Constants.FRAGMENT_MAIN_TAG).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goBack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            if (fm.getBackStackEntryCount() == 1) {
                setBack(false);
            }
            fm.popBackStack();
        } else {
            finish();
        }
    }

    private void setBack(boolean set) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(set);
    }

    public void changeToQuestionOneFragment() {
        setBack(true);

        QuestionOneFragment fragment = new QuestionOneFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, Constants.FRAGMENT_QUESTION_ONE_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToQuestionTwoFragment() {
        QuestionTwoFragment fragment = new QuestionTwoFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, Constants.FRAGMENT_QUESTION_TWO_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToQuestionThreeFragment() {
        QuestionThreeFragment fragment = new QuestionThreeFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, Constants.FRAGMENT_QUESTION_THREE_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToChooseRecommendationFragment() {
        ChooseRecommendationFragment fragment = new ChooseRecommendationFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, Constants.FRAGMENT_CHOOSE_RECOMMENDATION_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToRecommedationFragment() {
        RecommendationFragment fragment = new RecommendationFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, Constants.FRAGMENT_RECOMMENDATION_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToInfoRecommedationFragment() {
        clearBackstack();

        InfoRecommendationFragment fragment = new InfoRecommendationFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, Constants.FRAGMENT_INFO_RECOMMENDATION_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void clearBackstack() {

        FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(
                0);
        getSupportFragmentManager().popBackStack(entry.getId(),
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager().executePendingTransactions();

    }
}
