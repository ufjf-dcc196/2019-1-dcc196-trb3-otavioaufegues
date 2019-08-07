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
        void onItemClick(View candidatoView, int position);
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
        holder.itemNome.setText(cursor.getString(idxNome));
        holder.itemDtNasc.setText(cursor.getString(idxNascimento));
        holder.itemTelefone.setText(cursor.getString(idxTelefone));
        holder.itemPerfil.setText(cursor.getString(idxPerfil));
        holder.itemEmail.setText(cursor.getString(idxEmail));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemID, itemNome, itemDtNasc, itemPerfil, itemTelefone, itemEmail;


        public ViewHolder(@NonNull final View candidatoView) {
            super(candidatoView);
            itemID = candidatoView.findViewById(R.id.textViewId);
            itemNome = candidatoView.findViewById(R.id.editNovoNome);
            itemDtNasc = candidatoView.findViewById(R.id.textViewDtNasc);
            itemPerfil = candidatoView.findViewById(R.id.textViewPerfil);
            itemTelefone = candidatoView.findViewById(R.id.textViewTelefone);
            itemEmail = candidatoView.findViewById(R.id.textViewEmail);

            candidatoView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(candidatoView, position);
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
