package CMPE133_project.vaxtrack;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Provider> {
    List<Provider> providerList;
    private Context context;
    public CustomAdapter(@NonNull Context context, @NonNull List<Provider> providerList) {
        super(context, R.layout.activity_singleprovider, providerList);
        this.context = context;
        this.providerList = providerList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null){
            convertView = inflater.inflate(R.layout.activity_singleprovider, null);
        }
        TextView name = (TextView) convertView.findViewById(R.id.providerName);
        TextView address = (TextView) convertView.findViewById(R.id.providerAddress);
        TextView brands = (TextView) convertView.findViewById(R.id.vaccineBrands);

        final Provider provider = providerList.get(position);
        name.setText(provider.getName());
        address.setText(provider.getAddress());
        brands.setText(provider.getBrand());

        // Add Map View later.

        return convertView;
    }

    public void clear() {
        providerList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Provider> newProviderList) {
        providerList.addAll(newProviderList);
        notifyDataSetChanged();
    }

}
