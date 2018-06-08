package com.software.sulthan.kasirtoko;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PersonListAdapter extends ArrayAdapter<Person> {


    private static final String TAG = "PersonListAdapter";
    private Context mContext;
    int mResouce;

    public PersonListAdapter(Context context, int resouce, ArrayList<Person> objects)
    {
        super(context, resouce, objects);
        mContext = context;
        mResouce = resouce;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        //get the person information
        String name = getItem(position).getName();
        String birthday = getItem(position).getName();
        String sex = getItem(position).getName();

        //Create the person object with the information
        Person person = new Person(name, birthday, sex);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResouce, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.eNamaCust);
        TextView tvBirthday = (TextView) convertView.findViewById(R.id.eKodeCust);
        TextView tvSex = (TextView) convertView.findViewById(R.id.eAlamat);

        tvName.setText(name);
        tvBirthday.setText(birthday);
        tvSex.setText(sex);

        return convertView;
    }
}
