package com.example.trabalho3;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CandidatoAdapter extends RecyclerView.Adapter<CandidatoAdapter.ViewHolder>  {

    private Cursor cursor;

    public CandidatoAdapter(Cursor c) {
        this.cursor = c;
    }

    private CandidatoAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(CandidatoAdapter.OnItemClickListener listener) { this.listener = listener; }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setCursor(Cursor c){
        this.cursor = c;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CandidatoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View candidatoView = inflater.inflate(R.layout.candidato_layout, parent, false);
        CandidatoAdapter.ViewHolder holder = new CandidatoAdapter.ViewHolder(candidatoView);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull CandidatoAdapter.ViewHolder holder, int position) {
        int idxID = cursor.getColumnIndexOrThrow(CurriculoContract.Candidato._ID);
        int idxNome = cursor.getColumnIndexOrThrow(CurriculoContract.Candidato.COLUMN_NOME);
        int idxNascimento = cursor.getColumnIndexOrThrow(CurriculoContract.Candidato.COLUMN_NASCIMENTO);
        int idxTelefone = cursor.getColumnIndexOrThrow(CurriculoContract.Candidato.COLUMN_TELEFONE);
        int idxPerfil = cursor.getColumnIndexOrThrow(CurriculoContract.Candidato.COLUMN_PERFIL);
        int idxEmail = cursor.getColumnIndexOrThrow(CurriculoContract.Candidato.COLUMN_EMAIL);

        cursor.moveToPosition(position);

        holder.itemID.setText(cursor.getString(idxID));
        holder.itemTitulo.setText(cursor.getString(idxNome));
        holder.itemDescricao.setText(cursor.getString(idxNascimento));
        holder.itemInicio.setText(cursor.getString(idxTelefone));
        holder.itemFim.setText(cursor.getString(idxPerfil));
        holder.itemCategoria.setText(cursor.getString(idxEmail));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemID, itemTitulo, itemDescricao, itemInicio, itemFim, itemCategoria;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemID = itemView.findViewById(R.id.textIDProducao);
            itemTitulo = itemView.findViewById(R.id.textTitulo);
            itemDescricao = itemView.findViewById(R.id.textDescricao);
            itemCategoria = itemView.findViewById(R.id.textCategoriaProducao);

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
