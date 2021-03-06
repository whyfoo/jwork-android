package com.haidarh.jwork_android.ui.finished;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.haidarh.jwork_android.R;
import com.haidarh.jwork_android.classes.Invoice;
import com.haidarh.jwork_android.request.InvoiceStatusRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Recycler View adapter untuk finished job, menampilkan data dari kelas invoice yang telah diambil di finished job activity.
 *
 *  @author Haidar Hanif
 */
public class FinishedJobAdapter extends RecyclerView.Adapter<FinishedJobAdapter.ViewHolder> {


    private ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();
    private Context context;

    /**
     * Instantiates a new Finished job adapter.
     *
     * @param context     the context
     * @param invoiceList the invoice list
     */
    FinishedJobAdapter(Context context, ArrayList<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
        this.context = context;
    }

    /**
     * The type View holder.
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         * The Title main.
         */
        TextView titleMain;
        /**
         * The Tv jobseeker name.
         */
        TextView tvJobseekerName;
        /**
         * The Tv invoice date.
         */
        TextView tvInvoiceDate;
        /**
         * The Tv payment type.
         */
        TextView tvPaymentType;
        /**
         * The Tv job name.
         */
        TextView tvJobName;
        /**
         * The Tv total fee.
         */
        TextView tvTotalFee;
        /**
         * The Tv fee.
         */
        TextView tvFee;
        /**
         * The Tv invoice status.
         */
        TextView tvInvoiceStatus;
        /**
         * The Title ref code.
         */
        TextView titleRefCode;
        /**
         * The Tv ref code.
         */
        TextView tvRefCode;
        /**
         * The Btn cancel.
         */
        Button btnCancel;
        /**
         * The Btn finish.
         */
        Button btnFinish;
        /**
         * The Progress bar.
         */
        ProgressBar progressBar;


        /**
         * Instantiates a new View holder.
         *
         * @param v the v
         */
        ViewHolder(View v) {

            super(v);

            titleMain = v.findViewById(R.id.title_main);
            tvJobseekerName = v.findViewById(R.id.tv_jobseeker_name);
            tvInvoiceDate = v.findViewById(R.id.tv_invoice_date);
            tvPaymentType = v.findViewById(R.id.tv_payment_type);
            tvJobName = v.findViewById(R.id.tv_job_name);
            tvTotalFee = v.findViewById(R.id.tv_totalfee);
            tvFee = v.findViewById(R.id.tv_fee);
            tvInvoiceStatus = v.findViewById(R.id.tv_invoice_status);
            titleRefCode = v.findViewById(R.id.title_refcode);
            tvRefCode = v.findViewById(R.id.tv_refcode);
            btnCancel = v.findViewById(R.id.btn_cancel);
            btnFinish = v.findViewById(R.id.btn_finish);
        }
    }

    @NonNull
    @Override
    public FinishedJobAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleMain.setText(context.getResources().getString(R.string.invoice_id, invoiceList.get(position).getId()));
        holder.tvJobName.setText(invoiceList.get(position).getJobs());
        holder.tvFee.setText(invoiceList.get(position).getFee());
        holder.tvJobseekerName.setText(invoiceList.get(position).getJobseeker());
        holder.tvInvoiceDate.setText(invoiceList.get(position).getDate().substring(0,10));
        holder.tvPaymentType.setText(invoiceList.get(position).getPaymentType());
        holder.tvInvoiceStatus.setText(invoiceList.get(position).getInvoiceStatus());
        holder.tvTotalFee.setText(invoiceList.get(position).getTotalFee());

        if (invoiceList.get(position).getPaymentType().equals("BankPayment") || invoiceList.get(position).getRefferalCode().equals("")) {
            holder.titleRefCode.setVisibility(View.INVISIBLE);
            holder.tvRefCode.setVisibility(View.INVISIBLE);
        } else {
            holder.titleRefCode.setVisibility(View.VISIBLE);
            holder.tvRefCode.setVisibility(View.VISIBLE);
            String refCode = String.format(invoiceList.get(position).getRefferalCode() + " (+%s)", invoiceList.get(position).getBonus());
            holder.tvRefCode.setText(refCode);
        }

        if (invoiceList.get(position).getInvoiceStatus().equals("OnGoing")) {
            holder.btnCancel.setEnabled(true);
            holder.btnFinish.setEnabled(true);
        } else {
            holder.btnCancel.setEnabled(false);
            holder.btnFinish.setEnabled(false);
        }

        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            ((Activity) v.getContext()).finish();
                        }
                        catch (JSONException e) {
                            Toast.makeText(context, "Cancel Job Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                Toast.makeText(context, "Job Has Been Canceled",
                        Toast.LENGTH_SHORT).show();

                InvoiceStatusRequest cancelRequest = new InvoiceStatusRequest(invoiceList.get(position).getId(), "Cancelled", responseListener);
                RequestQueue queue = Volley.newRequestQueue(v.getContext());
                queue.add(cancelRequest);

                ((Activity) v.getContext()).finish();
            }
        });

        holder.btnFinish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            ((Activity) v.getContext()).finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Toast.makeText(context, "Job Has Been Finished",
                        Toast.LENGTH_SHORT).show();
                InvoiceStatusRequest finishedRequest = new InvoiceStatusRequest(invoiceList.get(position).getId(), "Finished", responseListener);
                RequestQueue queue = Volley.newRequestQueue(v.getContext());
                queue.add(finishedRequest);

                ((Activity) v.getContext()).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return invoiceList.size();
    }
}
