package com.example.gymcross;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GimnasiosAdapter extends RecyclerView.Adapter<GimnasiosAdapter.ViewHolder>
{
    private final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<Gimnasio> gimnasios;
    Context context;
    public GimnasiosAdapter(ArrayList<Gimnasio> _gimnasios, Context _context, RecyclerViewInterface _recyclerViewInterface)
    {
        this.recyclerViewInterface = _recyclerViewInterface;
        this.context = _context;
        this.gimnasios = _gimnasios ;
    }
    @Override
    public int getItemCount() {
        return gimnasios.size();
    }
    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public GimnasiosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.camping_item,parent,false);
        return new ViewHolder(view,recyclerViewInterface);
    }

    // vicula los datos al textview para cada item
    @Override
    public void onBindViewHolder(@NonNull GimnasiosAdapter.ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        final Gimnasio gimnasio = gimnasios.get(position);
        holder.campingName.setText(gimnasio.getNombre());
        holder.campingLugar.setText(gimnasio.getMunicipio() + " (" + gimnasio.getProvincia() + ")");
        holder.campingCorreo.setText(gimnasio.getCorreo());
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView campingName;
        TextView campingCategoria;
        TextView campingLugar;
        TextView campingCorreo;
        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            campingName = itemView.findViewById(R.id.campingName);
            campingCategoria = itemView.findViewById(R.id.campingCategoria);
            campingLugar = itemView.findViewById(R.id.campingLugar);
            campingCorreo = itemView.findViewById(R.id.campingCorreo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}



