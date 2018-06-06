package bleizing.pariwisata.activities;

import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

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
import java.util.List;

import bleizing.pariwisata.Constants;
import bleizing.pariwisata.Model;
import bleizing.pariwisata.NetApi;
import bleizing.pariwisata.R;
import bleizing.pariwisata.Wisata;
import bleizing.pariwisata.fragments.DaftarWisataFragment;
import bleizing.pariwisata.fragments.DetailWisataFragment;
import bleizing.pariwisata.fragments.MainFragment;
import bleizing.pariwisata.fragments.MasukkanKriteriaFragment;
import bleizing.pariwisata.fragments.MauKemanaFragment;
import bleizing.pariwisata.fragments.QuestionOneFragment;
import bleizing.pariwisata.fragments.QuestionThreeFragment;
import bleizing.pariwisata.fragments.QuestionTwoFragment;
import bleizing.pariwisata.fragments.WisataFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private List<Wisata> wisataList;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wisataList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);

        getWisata();

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

    public void goBack() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
//            if (fm.getBackStackEntryCount() == 1) {
//                setBack(false);
//            }
            fm.popBackStack();
        } else {
            finish();
        }
    }

    public void changeToMauKemanaFragment() {
//        setBack(true);

        MauKemanaFragment fragment = new MauKemanaFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, Constants.FRAGMENT_MAU_KEMANA_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToMasukkanKriteriaFragment() {
//        setBack(true);

        MasukkanKriteriaFragment fragment = new MasukkanKriteriaFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, Constants.FRAGMENT_MASUKKAN_KRITERIA_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToDaftarWisataFragment() {
//        setBack(true);

        DaftarWisataFragment fragment = new DaftarWisataFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, Constants.FRAGMENT_DAFTAR_WISATA_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToWisataFragment() {
//        setBack(true);

        WisataFragment fragment = new WisataFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, Constants.FRAGMENT_WISATA_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeToQuestionOneFragment() {
//        setBack(true);

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

    public void changeToDetailWisataFragment(int wisataId) {
//        clearBackstack();

        DetailWisataFragment fragment = new DetailWisataFragment();
        if (wisataId != 0) {
            Bundle bundle = new Bundle();
            bundle.putInt("wisataId", wisataId);
            fragment.setArguments(bundle);
        } else {
            clearBackstack();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, Constants.FRAGMENT_DETAIL_WISATA_TAG);
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

    public void changeToMainFragment() {
        clearBackstack();

        MainFragment fragment = new MainFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, Constants.FRAGMENT_MAIN_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void getWisata() {
        if (wisataList == null) {
            wisataList = new ArrayList<>();
        }

//        Wisata wisata;
//        for (int i = 0; i < 10; i++) {
//            wisata = new Wisata(i, "Wisata " + i, "", "", "", -6.17637, 106.87230);
//            wisataList.add(wisata);
//        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("type", "getRekomendasi");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "jsonObject = " + jsonObject);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, NetApi.BASE_URL_INDEX, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "response = " + response);
                try {
                    JSONArray list = response.getJSONArray("list");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject jsonObject1 = list.getJSONObject(i);

                        int rekomendasi_id = jsonObject1.getInt("rekomendasi_id");
                        String rekomendasi_isi = jsonObject1.getString("rekomendasi_isi");
                        String rekomendasi_foto = jsonObject1.getString("rekomendasi_foto");
                        String rekomendasi_info = jsonObject1.getString("rekomendasi_info");
                        String rekomendasi_kontak = jsonObject1.getString("rekomendasi_kontak");
                        Double rekomendasi_lat = jsonObject1.getDouble("rekomendasi_lat");
                        Double rekomendasi_long = jsonObject1.getDouble("rekomendasi_long");

                        Wisata wisata = new Wisata(rekomendasi_id, rekomendasi_isi, rekomendasi_foto, rekomendasi_info, rekomendasi_kontak, rekomendasi_lat, rekomendasi_long);
                        wisataList.add(wisata);
                    }

                    Model.setWisataList(wisataList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        jsonObjectRequest.setTag(TAG);
        requestQueue.add(jsonObjectRequest);
    }
}
