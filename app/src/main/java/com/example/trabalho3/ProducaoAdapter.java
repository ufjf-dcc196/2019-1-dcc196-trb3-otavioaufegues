package com.example.trabalho3;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProducaoAdapter extends RecyclerView.Adapter<ProducaoAdapter.ViewHolder>  {
    CurriculoDBHelper dbHelper;
    private Cursor cursor;

    public ProducaoAdapter(Cursor c) {
        this.cursor = c;
    }

    private ProducaoAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(ProducaoAdapter.OnItemClickListener listener) { this.listener = listener; }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setCursor(Cursor c){
        this.cursor = c;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProducaoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        dbHelper = new CurriculoDBHelper(context);

        View producaoView = inflater.inflate(R.layout.producao_layout, parent, false);
        ViewHolder holder = new ViewHolder(producaoView);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ProducaoAdapter.ViewHolder holder, int position) {

        int idxID = cursor.getColumnIndexOrThrow(CurriculoContract.Producao._ID);
        int idxTitulo = cursor.getColumnIndexOrThrow(CurriculoContract.Producao.COLUMN_TITULO);
        int idxDescricao = cursor.getColumnIndexOrThrow(CurriculoContract.Producao.COLUMN_DESCRICAO);
        int idxCategoria = cursor.getColumnIndexOrThrow(CurriculoContract.Producao.COLUMN_CATEGORIA);
        int idxDtInicio = cursor.getColumnIndexOrThrow(CurriculoContract.Producao.COLUMN_INICIO);

        cursor.moveToPosition(position);

        holder.itemID.setText(cursor.getString(idxID));
        holder.itemTitulo.setText(cursor.getString(idxTitulo));
        holder.itemDescricao.setText(cursor.getString(idxDescricao));
        holder.itemCategoria.setText(dbHelper.getCategoria(cursor.getString(idxCategoria)));
        holder.itemInicio.setText(cursor.getString(idxDtInicio));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemID, itemTitulo, itemDescricao, itemInicio, itemCategoria;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemID = itemView.findViewById(R.id.textIDProducao);
            itemTitulo = itemView.findViewById(R.id.textTitulo);
            itemDescricao = itemView.findViewById(R.id.textDescricao);
            itemCategoria = itemView.findViewById(R.id.textCategoriaProducao);
            itemInicio = itemView.findViewById(R.id.textDtInicio);

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
