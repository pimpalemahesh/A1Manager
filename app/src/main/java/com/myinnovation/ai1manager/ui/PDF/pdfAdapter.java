package com.myinnovation.ai1manager.ui.PDF;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myinnovation.ai1manager.Activities.PDFViewActivity;
import com.myinnovation.ai1manager.R;
import com.myinnovation.ai1manager.databinding.SinglePdfLayoutBinding;

import java.io.File;
import java.util.List;

public class pdfAdapter extends RecyclerView.Adapter<pdfAdapter.pdfViewHolder>{

    private List<File> pdfFiles;
    private Context context;

    public pdfAdapter(List<File> pdfFiles, Context context) {
        this.pdfFiles = pdfFiles;
        this.context = context;
    }

    @NonNull
    @Override
    public pdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new pdfViewHolder(LayoutInflater.from(context).inflate(R.layout.single_pdf_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull pdfViewHolder holder, int position) {
        File f = pdfFiles.get(position);
        holder.binding.pdfName.setText(pdfFiles.get(position).getName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context.getApplicationContext(), PDFViewActivity.class);
            intent.putExtra("path", f.getAbsolutePath());
            intent.putExtra("name", f.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return pdfFiles.size();
    }

    public static class pdfViewHolder extends RecyclerView.ViewHolder{
        SinglePdfLayoutBinding binding;
        public pdfViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SinglePdfLayoutBinding.bind(itemView);
        }
    }
}
