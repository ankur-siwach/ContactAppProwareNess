package test.ankur.com.prowarenessapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import test.ankur.com.prowarenessapp.R;
import test.ankur.com.prowarenessapp.models.Result;

/**
 * Created by ankur.siwach on 5/27/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ContactViewHolder>{

    private List<Result> contactList;
    private Context mContext;
    private OnUpdateListener listener;

    public HomeAdapter(Context context, List<Result> list) {
        this.mContext = context;
        this.contactList = list;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder, final int position) {

        final Result result = (Result) contactList.get(position);

        contactViewHolder.mContactName.setText(result.getName());
        contactViewHolder.mContactNumber.setText(result.getUid());

        contactViewHolder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUpdate(position);
            }
        });
    }


    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView mContactName;
        protected TextView mContactNumber;
        protected ImageView mDeleteButton;

        public ContactViewHolder(View v) {
            super(v);
            mContactName =  (TextView) v.findViewById(R.id.contact_name);
            mContactNumber = (TextView) v.findViewById(R.id.contact_number);
            mDeleteButton = (ImageView) v.findViewById(R.id.btnRemoveItem);
        }
    }

    public interface OnUpdateListener {
        void onUpdate(int position);
    }

    public void setOnUpdateListner(OnUpdateListener listener) {
        this.listener = listener;
    }

}
