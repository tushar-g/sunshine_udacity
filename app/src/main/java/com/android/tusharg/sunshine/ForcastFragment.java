package com.android.tusharg.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.tusharg.sunshine.model.DayInfo;
import com.android.tusharg.sunshine.model.WeatherData;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForcastFragment extends Fragment {

    @Bind(R.id.listview_forecast)
    ListView listView_forecast;

    private ArrayAdapter<String> mAdapter;
    private WeatherData weatherData;
    SharedPreferences pref;
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";

    String[] forcastArray;

    public String getURL() {

        final String location = pref.getString(getActivity().getString(R.string.location_pref),
                getActivity().getString(R.string.location_pref_default_val));
        final String units = pref.getString(getActivity().getString(R.string.temp_pref), getActivity().getString(R.string.temp_pref_default_val));

        HashMap<String, String> apiQuery = new HashMap<String, String>() {
            {
                put("q", location);
                put("mode", "json");
                put("units", units);
                put("cnt", "7");
                put("APPID", BuildConfig.OPEN_WEATHER_MAP_API_KEY);
            }
        };

        Uri.Builder uri = Uri.parse(BASE_URL).buildUpon();
        for (String key : apiQuery.keySet()) {
            uri = uri.appendQueryParameter(key, apiQuery.get(key));
        }
        Log.d("ForcastFragment", uri.build().toString());

        return uri.build().toString();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This indicates that we need callbacks for menu methods for this fragment.
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        ButterKnife.bind(this, view);

        ArrayList<String> weekForcast;
        if (forcastArray == null) {
            weekForcast = new ArrayList<>();
        } else {
            weekForcast = new ArrayList<>(Arrays.asList(forcastArray));
        }
        if (weatherData == null) {
            Log.d("", "WeatherData is null, running async task");
            (new FetchWeatherTask()).execute(getURL());
        }
        mAdapter = new ArrayAdapter<>(getActivity(), R.layout.list_item_forcast, R.id.tv_listItemForecast, weekForcast);
        listView_forecast.setAdapter(mAdapter);
        listView_forecast.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity() , weekForcast.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), WeatherDetailActivity.class);
                intent.putExtra(AppConstants.INTENT_WEATHER_DETAIL_SEND_WEATHERDATA, forcastArray[position]);
                startActivity(intent);
            }
        });
        return view;
    }
/*
    @OnItemClick(R.id.listview_forecast)
    protected weatherInfoClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(),);
    }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.forcastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {

            (new FetchWeatherTask()).execute(getURL());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * AsyncTask to call openWeatherMap API.
     */
    public class FetchWeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return HttpManager.getRequest(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String units = pref.getString(getActivity().getString(R.string.temp_pref), getActivity().getString(R.string.temp_pref_default_val));
            Gson gson = new Gson();
            weatherData = gson.fromJson(s, WeatherData.class);

            Time dayTime = new Time();
            dayTime.setToNow();

            // we start at the day returned by local time. Otherwise this is a mess.
            int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

            // now we work exclusively in UTC
            dayTime = new Time();

            forcastArray = new String[weatherData.getCnt()];
            int i = 0;
            for (DayInfo d : weatherData.getList()) {
                long dateTime;
                // Cheating to convert this to UTC time, which is what we want anyhow
                dateTime = dayTime.setJulianDay(julianStartDay + i);
                String day = getReadableDateString(dateTime);
                String description = d.getWeather().get(0).getDescription();
                double high = d.getTemp().getMax();
                double low = d.getTemp().getMax();
                String highAndLow = formatHighLows(high, low, units);
                forcastArray[i++] = day + " - " + description + " - " + highAndLow;
            }
            mAdapter.clear();
            mAdapter.addAll(new ArrayList<String>(Arrays.asList(forcastArray)));
        }

    }

    private String getReadableDateString(long time) {
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
        return shortenedDateFormat.format(time);
    }

    private String formatHighLows(double high, double low, String units) {
        // For presentation, assume the user doesn't care about tenths of a degree.
        long roundedHigh, roundedLow;
        String highLowStr;
        if (units.equals(getActivity().getString(R.string.temp_pref_default_val))) {
            roundedLow = Math.round(low);
            roundedHigh = Math.round(high);
            highLowStr = roundedHigh + "˚C/" + roundedLow + "˚C";
        } else {
            roundedLow = Math.round(low) * 9 / 5 + 32;
            roundedHigh = Math.round(high) * 9 / 5 + 32;
            highLowStr = roundedHigh + "˚F/" + roundedLow + "˚F";
        }
        return highLowStr;
    }

}
