package fr.accropolis.teamdev.accropolis.controller;

import android.content.Context;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.accropolis.teamdev.accropolis.R;
import fr.accropolis.teamdev.accropolis.model.Live;

/**
 * Created by nspu on 06/03/18.
 *
 * Get the Schedule from a public google calendar
 */
public class GetSchedule extends AsyncTask<Void, Void, List<Live>> {

    GetScheduleListener mListener;
    Exception mError;
    int mNbrDay;
    Context mContext;

    /**
     *
     * @param context Application context
     * @param mListener call for success or error
     * @param nbrDay Number of day to get
     */
    public GetSchedule(Context context, GetScheduleListener mListener, int nbrDay) {
        this.mListener = mListener;
        this.mNbrDay = nbrDay;
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected List<Live> doInBackground(Void... voids) {
        try {
            return getDataFromApi();
        } catch (Exception e) {
            cancel(true);
            mError = e;
            return null;
        }
    }

    /**
     * Fetch a list of the next nbDay events from the primary calendar.
     *
     * @return List of Live describing returned events.
     * @throws IOException
     */
    private List<Live> getDataFromApi() throws IOException {
        List<Live> eventLives = new ArrayList<>();
        List<Event> items = initEvent().getItems();

        for (Event event : items) {
            Live live = new Live(event.getLocation(),
                    event.getSummary(),
                    event.getDescription(),
                    toCalendar(event.getStart().getDateTime())
            );
            eventLives.add(live);
        }
        return eventLives;
    }

    /**
     * Date time to calendar object
     * @param date
     * @return
     */
    public static Calendar toCalendar(DateTime date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(date.getValue()));
        return cal;
    }

    /**
     * Initialise the service for get the calendar
     * @return
     */
    private com.google.api.services.calendar.Calendar initService() {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        return new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, null)
                .setApplicationName("Accropolis agenda")
                .build();
    }

    /**
     * init the list of event.
     * @return
     * @throws IOException
     */
    private Events initEvent() throws IOException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        DateTime now = new DateTime(calendar.getTime());

        com.google.api.services.calendar.Calendar.Events.List list = initService().events().list("uf6r98daui5pi268c1b3s3umdg@group.calendar.google.com")
                .setKey(mContext.getResources().getString(R.string.google_api_key))
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true);

        if(mNbrDay>0){
            calendar.add(Calendar.HOUR_OF_DAY, mNbrDay*24);
            list.setTimeMax(new DateTime(calendar.getTime()));
        }

        return list.execute();
    }

    @Override
    protected void onPostExecute(List<Live> liveList) {
        if (liveList == null) {
            mListener.onError(null);
        }else{
            mListener.onSucces(liveList);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        mListener.onError(mError);
    }

    public interface GetScheduleListener {
        void onSucces(List<Live> liveList);

        void onError(Exception e);
    }
}

