package com.example.trabalho3;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.ViewHolder>  {

private Cursor cursor;

public CategoriaAdapter(Cursor c) {
        this.cursor = c;
        }

private CategoriaAdapter.OnItemClickListener listener;

public void setOnItemClickListener(CategoriaAdapter.OnItemClickListener listener) { this.listener = listener; }

public interface OnItemClickListener {
    void onItemClick(View itemView, int position);
}

    public void setCursor(Cursor c){
        this.cursor = c;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoriaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View producaoView = inflater.inflate(R.layout.categoria_layout, parent, false);
        CategoriaAdapter.ViewHolder holder = new CategoriaAdapter.ViewHolder(producaoView);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaAdapter.ViewHolder holder, int position) {
        int idxID = cursor.getColumnIndexOrThrow(CurriculoContract.Categoria._ID);
        int idxTitulo = cursor.getColumnIndexOrThrow(CurriculoContract.Categoria.COLUMN_TITULO);

        cursor.moveToPosition(position);

        holder.itemID.setText(cursor.getString(idxID));
        holder.itemTitulo.setText(cursor.getString(idxTitulo));
    }

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView itemID, itemTitulo;


    public ViewHolder(@NonNull final View itemView) {
        super(itemView);
        itemID = itemView.findViewById(R.id.textIDProducao);
        itemTitulo = itemView.findViewById(R.id.textTitulo);

        itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (listener != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(itemView, position);
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        if(position !=RecyclerView.NO_POSITION){
            listener.onItemClick(v, position);
        }
    }
}


    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}
