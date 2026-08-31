package com.example.temperocaseiro1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.temperocaseiro1.model.CentroApoio;

import java.util.List;

public class CentroApoioAdapter extends RecyclerView.Adapter<CentroApoioAdapter.CentroViewHolder> {

    private List<CentroApoio> listaCentros;
    private OnCentroClickListener listener;

    public interface OnCentroClickListener {
        void onCentroClick(CentroApoio centro);
    }

    public CentroApoioAdapter(List<CentroApoio> listaCentros,
                              OnCentroClickListener listener) {
        this.listaCentros = listaCentros;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CentroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_centro_apoio, parent, false);

        return new CentroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CentroViewHolder holder, int position) {

        CentroApoio centro = listaCentros.get(position);

        holder.textNomeCentro.setText(centro.getNome());

        holder.textLocalizacaoCentro.setText(
                centro.getCidade() + " - " + centro.getEstado()
        );

        holder.textTipoCentro.setText(centro.getTipo());

        holder.itemView.setOnClickListener(v -> {

            if (listener != null) {
                listener.onCentroClick(centro);
            }

        });
    }

    @Override
    public int getItemCount() {
        return listaCentros.size();
    }

    public static class CentroViewHolder extends RecyclerView.ViewHolder {

        TextView textNomeCentro;
        TextView textLocalizacaoCentro;
        TextView textTipoCentro;

        public CentroViewHolder(@NonNull View itemView) {
            super(itemView);

            textNomeCentro = itemView.findViewById(R.id.textNomeCentro);
            textLocalizacaoCentro = itemView.findViewById(R.id.textLocalizacaoCentro);
            textTipoCentro = itemView.findViewById(R.id.textTipoCentro);
        }
    }
}