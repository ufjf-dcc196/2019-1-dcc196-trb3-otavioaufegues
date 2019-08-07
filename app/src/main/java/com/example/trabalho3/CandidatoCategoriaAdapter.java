package com.example.trabalho3;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;


public class CandidatoCategoriaAdapter extends RecyclerView.Adapter<CandidatoCategoriaAdapter.ViewHolder>   {
    private Cursor cursor;
    private CandidatoCategoriaAdapter.OnItemClickListener listener;

    public CandidatoCategoriaAdapter(Cursor c) {
        this.cursor = c;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setCursor(Cursor c){
        this.cursor = c;
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(CandidatoCategoriaAdapter.OnItemClickListener listener) { this.listener = listener;}

    @NonNull
    @Override
    public CandidatoCategoriaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.candidato_categoria_layout, viewGroup, false);
        CandidatoCategoriaAdapter.ViewHolder viewHolder = new CandidatoCategoriaAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CandidatoCategoriaAdapter.ViewHolder viewHolder, int i) {
        int idxNome = cursor.getColumnIndexOrThrow(CurriculoContract.Candidato.COLUMN_NOME);
        int idxHoras = cursor.getColumnIndexOrThrow("horastotais");

        cursor.moveToPosition(i);

        viewHolder.itemNome.setText(cursor.getString(idxNome));
        viewHolder.itemHoras.setText(cursor.getString(idxHoras));

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemNome, itemHoras;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemNome = itemView.findViewById(R.id.textViewListaNome);
            itemHoras = itemView.findViewById(R.id.textViewListaHoras);


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
}
