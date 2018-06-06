package bleizing.pariwisata;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.ViewHolder> {

    private List<Wisata> wisataList;

    private Context context;

    public WisataAdapter(Context context, List<Wisata> wisataList) {
        this.context = context;
        this.wisataList = wisataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_wisata, parent, false);

        return new WisataAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Wisata wisata = wisataList.get(position);

        String imgUrl = NetApi.BASE_URL_IMAGE + wisata.getFoto();

        holder.tvTitle.setText(wisata.getIsi());
        Picasso.with(context).load(imgUrl).fit().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.imgWisata);
    }

    @Override
    public int getItemCount() {
        return wisataList.size();
    }

    public List<Wisata> getWisataList() {
        return wisataList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgWisata;
        private TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            imgWisata = (ImageView) itemView.findViewById(R.id.img_wisata);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title_wisata);
        }
    }
}
