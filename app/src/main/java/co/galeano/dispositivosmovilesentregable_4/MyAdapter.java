package co.galeano.dispositivosmovilesentregable_4;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyView> {

    private Context context;
    private List<InfoClass> datalist;

    public MyAdapter(Context context, List<InfoClass> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle, parent, false);
        return new MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        Glide.with(context).load(datalist.get(position).getDataimage()).into(holder.reImage);
        holder.namePro.setTextContent(datalist.get(position).getDataname());
        holder.desctPro.setTextContent(datalist.get(position).getDatadesc());
        holder.categoryPro.setTextContent(datalist.get(position).getDatacate());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Image", datalist.get(holder.getAdapterPosition()).getDataimage());
                intent.putExtra("Description", datalist.get(holder.getAdapterPosition()).getDatadesc());
                intent.putExtra("Name", datalist.get(holder.getAdapterPosition()).getDataname());

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
}

class MyView extends RecyclerView.ViewHolder {

    ImageView reImage;
    Text namePro, desctPro, categoryPro;
    CardView recCard;

    public MyView(@NonNull View itemView) {
        super(itemView);
        reImage = itemView.findViewById(R.id.reImage);
        namePro = itemView.findViewById(R.id.namePro);
        desctPro = itemView.findViewById(R.id.desctPro);
        categoryPro = itemView.findViewById(R.id.categoryPro);
        recCard = itemView.findViewById(R.id.recCard);
    }
}
