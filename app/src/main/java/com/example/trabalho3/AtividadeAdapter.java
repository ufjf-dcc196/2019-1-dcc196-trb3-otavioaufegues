package com.example.trabalho3;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AtividadeAdapter extends RecyclerView.Adapter<AtividadeAdapter.ViewHolder>  {

    private Cursor cursor;

    public AtividadeAdapter(Cursor c) {
        this.cursor = c;
    }

    private AtividadeAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(AtividadeAdapter.OnItemClickListener listener) { this.listener = listener; }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setCursor(Cursor c){
        this.cursor = c;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AtividadeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View atividadeView = inflater.inflate(R.layout.atividade_layout, parent, false);
        AtividadeAdapter.ViewHolder holder = new AtividadeAdapter.ViewHolder(atividadeView);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull AtividadeAdapter.ViewHolder holder, int position) {
        int idxID = cursor.getColumnIndexOrThrow(CurriculoContract.Atividade._ID);
        int idxDescricao = cursor.getColumnIndexOrThrow(CurriculoContract.Atividade.COLUMN_DESCRICAO);
        int idxHoras = cursor.getColumnIndexOrThrow(CurriculoContract.Atividade.COLUMN_HORAS);


        cursor.moveToPosition(position);

        holder.itemID.setText(cursor.getString(idxID));
        holder.itemDescricao.setText(cursor.getString(idxDescricao));
        holder.itemHoras.setText(cursor.getString(idxHoras));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemID, itemDescricao,  itemHoras;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemID = itemView.findViewById(R.id.textIDAtividade);
            itemDescricao = itemView.findViewById(R.id.textAtividadeDescricao);
            itemHoras = itemView.findViewById(R.id.textAtividadeHoras);

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
