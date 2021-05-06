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
        TextView cityState = (TextView) convertView.findViewById(R.id.providerCityState);
        TextView brands = (TextView) convertView.findViewById(R.id.vaccineBrands);

        final Provider provider = providerList.get(position);
        name.setText(provider.getName());
        address.setText(provider.getAddress());
        StringBuilder brandList = new StringBuilder();
        brandList.append("Carriers: ");
        if (provider.getBrand().contains("Unknown")){
            brandList.append("Unknown");
        } else if (provider.getBrand().size() == 1) {
            brandList.append(provider.getBrand().get(0));
        } else {
            for (int i = 0; i < provider.getBrand().size(); i++){
                if (i == provider.getBrand().size() - 1) {
                    brandList.append(provider.getBrand().get(i));
                } else {
                    brandList.append(provider.getBrand().get(i) + ", ");
                }
            }
        }
        brands.setText(brandList.toString());
        StringBuilder cityStateZip = new StringBuilder();
        cityStateZip.append(provider.getCityState());        ;
        cityStateZip.append(" ");
        cityStateZip.append(provider.getPostalCode());
        cityState.setText(cityStateZip.toString());
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
