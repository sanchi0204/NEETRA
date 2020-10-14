package com.example.safetapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.safetapp.R;

import pl.droidsonroids.gif.GifImageView;

public class SlideAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public SlideAdapter(Context context) {
        this.context = context;
    }

    //Arrays for content

    public int[] images=
            {
                    R.drawable.report_crime_online,
                    R.drawable.reported_cases,
                    R.drawable.track_loc_safety,
                    R.drawable.monitoring_police,
                    R.drawable.alarm,
                    R.drawable.notif_gif
            };

    public int[] textMain=
            {
                     R.string.report_crime_online_m,
                    R.string.report_cases_m,
                    R.string.track_location_m,
                    R.string.monitoring_m,
                    R.string.alarm_m,
                    R.string.notifications_location
            };

    public int[] textSub=
            {
                    R.string.report_crime_online_s,
                    R.string.report_cases_s,
                    R.string.track_location_s,
                    R.string.monitoring_s,
                    R.string.alarm_s,
                    R.string.notif_location_text
            };


    @Override
    public int getCount() {
        return textMain.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view== (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        GifImageView gifImageView = view.findViewById(R.id.gif_view);
        TextView text_main = view.findViewById(R.id.text_main);
        TextView text_sub = view.findViewById(R.id.text_sub);

        gifImageView.setImageResource(images[position]);
        text_main.setText(textMain[position]);
        text_sub.setText(textSub[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
