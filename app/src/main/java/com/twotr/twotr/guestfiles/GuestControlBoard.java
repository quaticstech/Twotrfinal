package com.twotr.twotr.guestfiles;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.github.thunder413.datetimeutils.DateTimeUnits;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.picasso.Picasso;
import com.twotr.twotr.R;
import com.twotr.twotr.globalpackfiles.Global_url_twotr;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class GuestControlBoard extends AppCompatActivity implements AAH_FabulousFragment.Callbacks, AAH_FabulousFragment.AnimationListener{
ListView listViewguest;
    AVLoadingIndicatorView avi;
String schedule_list_url;
String searchque="a";
    SharedPreferences Shared_user_details;
    String Stoken,Sid;
    String[] aryGrade = {
    };
   Schedule_class tutorpageadapter;
    EditText ETsubject_name;
    MyFabFragment dialogFrag;
    FloatingActionButton  fab2;
    GuestData mData;
    SwipyRefreshLayout swipyRefreshLayout;
    ProgressBar footer;
    private ArrayMap<String, List<String>> applied_filters = new ArrayMap<>();
    List<GuestFilters> mList = new ArrayList<>();
int amountmax=1000;
String typeofstu="";
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_control_board);
        bottomBar =  findViewById(R.id.bottomBar);
        listViewguest=findViewById(R.id.guset_subfil);
        ETsubject_name=findViewById(R.id.subject_name);
      //  mSwipeRefreshLayout =  findViewById(R.id.activity_main_swipe_refresh_layout);
        swipyRefreshLayout=findViewById(R.id.swipyrefreshlayout);
        avi=findViewById(R.id.avi);
        avi.hide();
        fab2 =  findViewById(R.id.fab2);
        mData = guestfilterrows.getguest();

        mList.addAll(mData.getAllMovies());
        Shared_user_details=getSharedPreferences("user_detail_mode",0);
        Stoken=  Shared_user_details.getString("token", null);
        Sid=  Shared_user_details.getString("id", null);
        schedule_list_url = Global_url_twotr.Guest_list_api+searchque+"&page=1&size=10" ;
        new ScheduleAsyncList().execute(schedule_list_url);
//
        bottomBar.setDefaultTab(R.id.tab_dashboard);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                if (tabId == R.id.tab_schedules) {
                    new SweetAlertDialog(GuestControlBoard.this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("Signin Required!")
                            .setContentText("Sorry! We are not able to show this Content.Authentication is required! So you need to login your Twotr or Social Accounts")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();
                }
                else if (tabId == R.id.tab_create) {
                    new SweetAlertDialog(GuestControlBoard.this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("Signin Required!")
                            .setContentText("Sorry! We are not able to show this Content.Authentication is required! So you need to login your Twotr or Social Accounts")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();
                }
                else if (tabId == R.id.tab_dashboard) {
//                    selectedFragment = StudentDashboard.newInstance();
                }

                else if (tabId == R.id.tab_settings) {
                    new SweetAlertDialog(GuestControlBoard.this, SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("Signin Required!")

                            .setContentText("Sorry! We are not able to show this Content.Authentication is required! So you need to login your Twotr or Social Accounts")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();
                }

            }
        });

        ETsubject_name.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
//                    String getsearch = mEdit.toString();
//                    String URLLL = GlobalUrls.users_allcatsearchtwo + "?district_place=" + homeloca + "&searchitem=" + getsearch;
//                    new kilomilo().execute(URLLL);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchque = s.toString();
                schedule_list_url = Global_url_twotr.Guest_list_api+searchque+"&page=1&size=10" ;
                new ScheduleAsyncList().execute(schedule_list_url);
            }

        });

        dialogFrag = MyFabFragment.newInstance();
        dialogFrag.setParentFab(fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFrag.show(getSupportFragmentManager(), dialogFrag.getTag());
            }
        });


         footer = new ProgressBar(this);
        listViewguest.addFooterView(footer);
//        listViewguest.setOnScrollListener(scrollListener);

        listViewguest.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                schedule_list_url = Global_url_twotr.Guest_list_api+searchque+"&page="+page+"&size=10" ;
                new Newpageloading().execute(schedule_list_url);



            }

        });

        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                schedule_list_url = Global_url_twotr.Guest_list_api+searchque+"&page=1&size=10" ;
                       new ScheduleAsyncList().execute(schedule_list_url);
                       swipyRefreshLayout.setRefreshing(false);
            }
        });

    }



    public class Schedule_class extends ArrayAdapter {
        public List<Guest_list_parce> ScheduleModeList;
        public int resource;
        Context context;
        public LayoutInflater inflater;

        Schedule_class(Context context, int resource, List<Guest_list_parce> objects) {
            super(context, resource, objects);
            ScheduleModeList = objects;
            this.context = context;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }


        @SuppressLint("SetTextI18n")
        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(resource, null);
                holder = new ViewHolder();
                holder.TVsubjectname = convertView.findViewById(R.id.tutor_subject_name);
                holder.TVtuttorname=convertView.findViewById(R.id.tutor_sub_name);
                holder.TVtimetotal=convertView.findViewById(R.id.tutor_subject_time);
                holder.TVprice=convertView.findViewById(R.id.tutor_subject_price);
holder.newimage=convertView.findViewById(R.id.tutor_subject_image);
                convertView.setTag(holder);
            }//ino
            else {
                holder = (ViewHolder) convertView.getTag();
            }
           Guest_list_parce supl = ScheduleModeList.get(position);
            holder.TVtuttorname.setText(supl.getCreatedByName());
            String type_group=supl.getType();
            if (type_group.equals("oneonone"))
            {
                type_group="1 on 1";
            }
            holder.TVsubjectname.setText(supl.getSubject()+"  "+ type_group);

//            holder.TVtypemenbers.setText(type_group);
//            holder.TVschedule_des.setText(supl.getDescription());
            holder.TVprice.setText(supl.getPrice()+" KD");
            String Scompletestart=supl.getStart();
            String Scompleteend=supl.getEnd();

            String startdate=Scompletestart.substring(0,10);
            String starttime=Scompletestart.substring(11,19);
            String enddate=Scompleteend.substring(0,10);
            String endtime=Scompleteend.substring(11,19);
            String time_sched=Scompletestart.substring(11,16);
            String datestart=startdate+ " "+starttime;
            String dateend=enddate+ " "+endtime;
            int diff = DateTimeUtils.getDateDiff(datestart,dateend, DateTimeUnits.HOURS);
            diff=Math.abs(diff);
            String monthformating=DateTimeUtils.formatWithPattern(startdate, "EEE, MMM dd");
            String completedate;
if (diff>1)
{
     completedate=diff+" hours - "+time_sched+" | "+monthformating;

}
else
{
     completedate=diff+" hour - "+time_sched+" | "+monthformating;

}
holder.TVtimetotal.setText(completedate);



                Picasso
                        .with(context)
                        .load(Global_url_twotr.Image_Base_url+supl.getUrl())
                        .fit()
                        .error(getResources().getDrawable(R.drawable.profile_image_tutor))
                        .centerCrop()
                        .into( holder.newimage);


            return convertView;
        }

        class ViewHolder {
            public TextView TVsubjectname;
            public TextView TVprice;
            public TextView TVtimetotal;
            public TextView TVtuttorname;

public CircleImageView newimage;
            //  private TextView TVstart_time;


        }
    }


    @SuppressLint("StaticFieldLeak")
    public class ScheduleAsyncList extends AsyncTask<String, String, List<Guest_list_parce>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected List<Guest_list_parce> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                DataOutputStream printout;
DataInputStream inputStream;
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput (true);
                connection.setDoOutput (true);
                connection.setUseCaches (false);
                connection.setRequestProperty("content-type","application/json");
                connection.setRequestProperty("x-tutor-app-id","tutor-app-android");
                connection.setRequestMethod("POST");
                connection.connect();
                JSONArray array2=new JSONArray(aryGrade);
                JSONObject price=new JSONObject();
                JSONObject auth=new JSONObject();
                auth.put("grades",array2);
                auth.put("isTutorOnly","false");
                auth.put("type", typeofstu);
                price.put("min",0);
                price.put("max",amountmax);
                auth.put("price",price);
                auth.put("ratings", "0");
                auth.put("timezone", "Asia/Kuwait");


                printout = new DataOutputStream(connection.getOutputStream ());
                printout.writeBytes(auth.toString());
                printout.flush ();
                printout.close ();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("classes");
                List<Guest_list_parce> milokilo = new ArrayList<>();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    //
                    Guest_list_parce catego = new Guest_list_parce();
                    catego.setSubject(finalObject.getString("subject"));
                    catego.setType(finalObject.getString("type"));
                    catego.setDescription(finalObject.getString("description"));
                    catego.setPrice(finalObject.getString("price"));
                    catego.setStudentsCount(finalObject.getString("studentsCount"));
                    try {
                        catego.setMinPrice(finalObject.getString("minPrice"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    catego.set_id(finalObject.getString("_id"));
                    catego.setCreatedBy(finalObject.getString("createdBy"));


                    catego.setCreatedByName(finalObject.getString("createdByName"));
                    JSONArray jsonArray1 = finalObject.getJSONArray("schedules");
                    ArrayList<String> starttimeschednew =new ArrayList<>();
                    ArrayList<String> endtimeschednew=new ArrayList<>();
                    ArrayList<String> groupKeynew =new ArrayList<>();
                    ArrayList<String> isAvailablenew=new ArrayList<>();
                    ArrayList<String> slotnew =new ArrayList<>();
                    ArrayList<String> availableCountnew=new ArrayList<>();
                    ArrayList<String> enrollclassid =new ArrayList<>();
                    ArrayList<String> enrollscheduleid=new ArrayList<>();
                    for (int j = 0; j < jsonArray1.length(); j++) {
                        JSONObject jsonObject = jsonArray1.getJSONObject(j);
                        catego.setStart(jsonObject.getString("start"));
                        catego.setEnd(jsonObject.getString("end"));
                        groupKeynew.add(jsonObject.getString("groupKey"));
                        isAvailablenew.add(jsonObject.getString("isAvailable"));
                        starttimeschednew.add(jsonObject.getString("start"));
                        endtimeschednew.add(jsonObject.getString("end"));
                        slotnew.add(jsonObject.getString("slotPrice"));
                        availableCountnew.add(jsonObject.getString("availableCount"));
                        enrollclassid.add(jsonObject.getString("classId"));
                        enrollscheduleid.add(jsonObject.getString("_id"));
                    }
                    catego.setGroupKey(groupKeynew);
                    catego.setIsAvailable(isAvailablenew);
                    catego.setStartli(starttimeschednew);
                    catego.setEndli(endtimeschednew);
                    catego.setSlotPrice(slotnew);
                    catego.setAvailableCount(availableCountnew);
                    catego.setSche_classId(enrollclassid);
                    catego.setSche_id(enrollscheduleid);


                                        try
                                     {
                                            JSONObject jlocati=finalObject.getJSONObject("location");
                                            catego.setLat(jlocati.getString("lat"));
                                             catego.setLng(jlocati.getString("lng"));
                                     }
                                        catch (JSONException e)

                                         {
                                            e.printStackTrace();
                                            }


                    try {
                        JSONObject profilePicture = finalObject.getJSONObject("profilePicture");
                        catego.setUrl( profilePicture.getString("url"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    milokilo.add(catego);
                }
                return milokilo;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final List<Guest_list_parce> ScheduleMode) {
            super.onPostExecute(ScheduleMode);
            if ((ScheduleMode != null) && (ScheduleMode.size()>0))
            {

                 tutorpageadapter = new Schedule_class(GuestControlBoard.this, R.layout.guest_dasboard_list, ScheduleMode);
                listViewguest.setAdapter(tutorpageadapter);
                avi.hide();
                listViewguest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                           Guest_list_parce schedule_upcoming_list = ScheduleMode.get(position);
                    Intent intent = new Intent(GuestControlBoard.this, GuestActivityDetails.class);
                            intent.putExtra("subject_name", schedule_upcoming_list.getSubject());
                            intent.putExtra("type_subject", schedule_upcoming_list.getType());
                            intent.putExtra("schedule_description", schedule_upcoming_list.getDescription());
                            intent.putExtra("latitude", schedule_upcoming_list.getLat());
                            intent.putExtra("longitude", schedule_upcoming_list.getLng());
                            intent.putExtra("schedule_price", schedule_upcoming_list.getPrice());
                            intent.putExtra("studentscount", schedule_upcoming_list.getStudentsCount());
                            intent.putExtra("minprice", schedule_upcoming_list.getMinPrice());
                            intent.putExtra("cateid",schedule_upcoming_list.get_id());
                            intent.putExtra("createdby",schedule_upcoming_list.getCreatedBy());
                            intent.putExtra("createdbyname",schedule_upcoming_list.getCreatedByName());
                            intent.putStringArrayListExtra("starttime",schedule_upcoming_list.getStartli());
                            intent.putStringArrayListExtra("endtime", schedule_upcoming_list.getEndli());
                            intent.putExtra("imgurl",schedule_upcoming_list.getUrl());
                            intent.putStringArrayListExtra("isAvailable",schedule_upcoming_list.getIsAvailable());
                            intent.putStringArrayListExtra("groupKey",schedule_upcoming_list.getGroupKey());
                            intent.putStringArrayListExtra("slotPrice",schedule_upcoming_list.getSlotPrice());
                            intent.putStringArrayListExtra("availableCount",schedule_upcoming_list.getAvailableCount());
                            intent.putStringArrayListExtra("schclassid",schedule_upcoming_list.getSche_classId());
                            intent.putStringArrayListExtra("sch_id",schedule_upcoming_list.getSche_id());
                            String Scompletestart = schedule_upcoming_list.getStart();
                            String Scompleteend = schedule_upcoming_list.getEnd();
                            String startdate = Scompletestart.substring(0, 10);
                            String starttime = Scompletestart.substring(11, 19);
                            String enddate = Scompleteend.substring(0, 10);
                            String endtime = Scompleteend.substring(11, 19);
                            String time_sched = Scompletestart.substring(11, 16);
                            String datestart = startdate + " " + starttime;
                            String dateend = enddate + " " + endtime;
                            int diff = DateTimeUtils.getDateDiff(datestart, dateend, DateTimeUnits.HOURS);
                            diff = Math.abs(diff);
                            String shours = diff + " hours - ";
                            String stimsc = time_sched + " | ";
                            String smonth = DateTimeUtils.formatWithPattern(startdate, " MMMM dd");
                            intent.putExtra("hrschmon", shours + stimsc + smonth);
                            startActivity(intent);

                        }
                    });
                tutorpageadapter.notifyDataSetChanged();



            }
            else
            {
                avi.hide();
            }

        }



    }

    @SuppressLint("StaticFieldLeak")
    public class Newpageloading extends AsyncTask<String, String, List<Guest_list_parce>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            avi.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected List<Guest_list_parce> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                DataOutputStream printout;
                DataInputStream inputStream;
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput (true);
                connection.setDoOutput (true);
                connection.setUseCaches (false);
                connection.setRequestProperty("content-type","application/json");
                connection.setRequestProperty("x-tutor-app-id","tutor-app-android");
                connection.setRequestMethod("POST");
                connection.connect();
                JSONArray array2=new JSONArray(aryGrade);
                JSONObject price=new JSONObject();
                JSONObject auth=new JSONObject();
                auth.put("grades",array2);
                auth.put("isTutorOnly","false");
                auth.put("type", typeofstu);
                price.put("min",0);
                price.put("max",amountmax);
                auth.put("price",price);
                auth.put("ratings", "0");
                auth.put("timezone", "Asia/Kuwait");


                printout = new DataOutputStream(connection.getOutputStream ());
                printout.writeBytes(auth.toString());
                printout.flush ();
                printout.close ();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("classes");
                List<Guest_list_parce> milokilo = new ArrayList<>();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    //
                    Guest_list_parce catego = new Guest_list_parce();
                    catego.setSubject(finalObject.getString("subject"));
                    catego.setType(finalObject.getString("type"));
                    catego.setDescription(finalObject.getString("description"));
                    catego.setPrice(finalObject.getString("price"));
                    catego.setStudentsCount(finalObject.getString("studentsCount"));
                    catego.setMinPrice(finalObject.getString("minPrice"));
                    catego.set_id(finalObject.getString("_id"));
                    catego.setCreatedByName(finalObject.getString("createdByName"));
                    JSONArray jsonArray1 = finalObject.getJSONArray("schedules");
                    for (int j = 0; j < jsonArray1.length(); j++) {
                        JSONObject jsonObject = jsonArray1.getJSONObject(j);
                        catego.setStart(jsonObject.getString("start"));
                        catego.setEnd(jsonObject.getString("end"));

                        //  ListSubject.add(Skind);
                    }
//                    try {
//                        JSONObject jlocati=finalObject.getJSONObject("location");
//                        catego.setLat(jlocati.getString("lat"));
//                        catego.setLng(jlocati.getString("lng"));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                    milokilo.add(catego);
                }
                return milokilo;
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final List<Guest_list_parce> ScheduleMode) {
            super.onPostExecute(ScheduleMode);
            avi.hide();
            if ((ScheduleMode != null) && (ScheduleMode.size()>0))
            {

            //    tutorpageadapter = new Schedule_class(GuestControlBoard.this, R.layout.guest_dasboard_list, ScheduleMode);
                tutorpageadapter.addAll(ScheduleMode);
                avi.hide();

                tutorpageadapter.notifyDataSetChanged();



            }
            else
            {
                listViewguest.removeFooterView(footer);
                avi.hide();
            }


        }



    }

    @Override
    public void onResult(Object result) {
        Log.d("k9res", "onResult: " + result.toString());
        if (result.toString().equalsIgnoreCase("swiped_down")) {

            //do something or nothing

        } else {
            if (result != null) {
                ArrayMap<String, List<String>> applied_filters = (ArrayMap<String, List<String>>) result;
                if (applied_filters.size() != 0) {
                  List<GuestFilters> filteredList = mData.getAllMovies();
                    //iterate over arraymap
                    for (Map.Entry<String, List<String>> entry : applied_filters.entrySet()) {
                        Log.d("k9res", "entry.key: " + entry.getKey());
                        switch (entry.getKey()) {
                            case "Type":
                                filteredList = mData.getStypeFilteredMovies(entry.getValue(), filteredList);
                                typeofstu=entry.getValue().toString();
                                typeofstu=typeofstu.replaceAll("\\[", "").replaceAll("\\]","");
                                Log.d("aac", "type: " + typeofstu);

                                break;
                            case "Rating":
                                filteredList = mData.getRatingFilteredMovies(entry.getValue(), filteredList);
                                Log.d("aac", "type: " + entry.getValue());

                                break;
                            case "Price":
                                filteredList = mData.getPriceFilteredMovies(entry.getValue(), filteredList);
                                String stringprice=entry.getValue().toString();
                                stringprice=stringprice.replaceAll("\\[", "").replaceAll("\\]","");
amountmax=Integer.valueOf(stringprice);
                                break;
                            case "Grade":
                                filteredList = mData.getGradeFilteredMovies(entry.getValue(), filteredList);
                                Log.d("aac", "type: " + entry.getValue());
                                aryGrade= entry.getValue().toArray(new String[0]);
                                break;
                        }
                        schedule_list_url = Global_url_twotr.Guest_list_api+searchque+"&page=1&size=10" ;
                        new ScheduleAsyncList().execute(schedule_list_url);
                        Log.d("pagecheck", "new size: " + filteredList.size());
                    }


//                   mList.clear();
//                    mList.addAll(filteredList);

                 //mAdapter.notifyDataSetChanged();

                }
                else {
                    Log.d("aac", "am here: " );

                    typeofstu="";
aryGrade= new String[]{};
                    amountmax=1;
                    schedule_list_url = Global_url_twotr.Guest_list_api+searchque+"&page=1&size=10" ;
                    new ScheduleAsyncList().execute(schedule_list_url);
                    //mList.addAll(mData.getAllMovies());
                    //mAdapter.notifyDataSetChanged();
                }
            }
            //handle result
        }
    }
    public ArrayMap<String, List<String>> getApplied_filters() {
        return applied_filters;
    }

    public GuestData getmData() {
        return mData;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (dialogFrag.isAdded()) {
            dialogFrag.dismiss();
            dialogFrag.show(getSupportFragmentManager(), dialogFrag.getTag());
        }


    }

    @Override
    public void onOpenAnimationStart() {
        Log.d("aah_animation", "onOpenAnimationStart: ");
    }

    @Override
    public void onOpenAnimationEnd() {
        Log.d("aah_animation", "onOpenAnimationEnd: ");

    }

    @Override
    public void onCloseAnimationStart() {
        Log.d("aah_animation", "onCloseAnimationStart: ");

    }

    @Override
    public void onCloseAnimationEnd() {
        Log.d("aah_animation", "onCloseAnimationEnd: ");

    }


    public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {

        // The minimum amount of items to have below your current scroll position
        // before loading more.
        private int visibleThreshold = 1;
        // The current offset index of data you have loaded
        private int currentPage = 0;
        // The total number of items in the dataset after the last load
        private int previousTotalItemCount = 0;
        // True if we are still waiting for the last set of data to load.
        private boolean loading = true;
        // Sets the starting page index
        private int startingPageIndex = 0;

        public EndlessScrollListener() {
        }

        public EndlessScrollListener(int visibleThreshold) {
            this.visibleThreshold = visibleThreshold;
        }

        public EndlessScrollListener(int visibleThreshold, int startPage) {
            this.visibleThreshold = visibleThreshold;
            this.startingPageIndex = startPage;
            this.currentPage = startPage;
        }

        // This happens many times a second during a scroll, so be wary of the code you place here.
        // We are given a few useful parameters to help us work out if we need to load some more data,
        // but first we check if we are waiting for the previous load to finish.
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            // If the total item count is zero and the previous isn't, assume the
            // list is invalidated and should be reset back to initial state
            if (totalItemCount < previousTotalItemCount) {
                this.currentPage = this.startingPageIndex;
                this.previousTotalItemCount = totalItemCount;
                if (totalItemCount == 0) {
                    this.loading = true;
                }
            }

            // If it’s still loading, we check to see if the dataset count has
            // changed, if so we conclude it has finished loading and update the current page
            // number and total item count.
            if (loading && (totalItemCount > previousTotalItemCount)) {
                loading = false;
                previousTotalItemCount = totalItemCount;
                currentPage++;
            }

            // If it isn’t currently loading, we check to see if we have breached
            // the visibleThreshold and need to reload more data.
            // If we do need to reload some more data, we execute onLoadMore to fetch the data.
            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                onLoadMore(currentPage + 1, totalItemCount);
                loading = true;
            }
        }

        // Defines the process for actually loading more data based on page
        public abstract void onLoadMore(int page, int totalItemsCount);

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // Don't take any action on changed

        }
    }



}
