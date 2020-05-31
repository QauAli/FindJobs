
package Fragments;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ammara.FindJobs.Constants;
import com.example.ammara.FindJobs.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ModelClasses.Validation;
import RestfullServices.Client;
import RestfullServices.FindJobService;
import ModelClasses.Advertisement;
import ModelClasses.Skill;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AdvertisementF extends Fragment{
    private LinearLayout skillsLayout;
    private List<Skill> skills;
    private LinkedList<String> selectedSkills = new LinkedList<>();
    private EditText lastDate,jobTitle;
    private Spinner province, district, education;
    private Button btn;
    private Advertisement advertisement;
    private FindJobService service = Client.getClient().create(FindJobService.class);


    public AdvertisementF() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_advertisement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getReferences();
        Call<List<Skill>> call = service.getAllSkills();
        call.enqueue(new Callback<List<Skill>>() {
            @Override
            public void onResponse(Call<List<Skill>> call, Response<List<Skill>> response) {
                skills = response.body();
                if (response.isSuccessful()) {
                    for (int i = 0; i < skills.size(); i++) {
                        //TableRow row = new TableRow(getContext());
                        //row.setId(i);
                        //row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                        CheckBox checkBox = new CheckBox(getContext());
                        checkBox.setId(skills.get(i).getId());
                        checkBox.setText(skills.get(i).getName());
                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    //Toast.makeText(getContext(),buttonView.getText()+" checked", Toast.LENGTH_SHORT).show();
                                    selectedSkills.add(buttonView.getText().toString());
                                } else {
                                    //Toast.makeText(getContext(),buttonView.getText()+" Unchecked",Toast.LENGTH_SHORT).show();
                                    if (selectedSkills.size() == 1) {
                                        selectedSkills.clear();
                                    }
                                    selectedSkills.remove(new String(buttonView.getText().toString()));
                                }
                            }
                        });
                        skillsLayout.addView(checkBox);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Skill>> call, Throwable t) {

            }
        });

        final ArrayAdapter<CharSequence> Kpk = ArrayAdapter.createFromResource(getActivity(),
                R.array.Kpk, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> Punjab = ArrayAdapter.createFromResource(getActivity(),
                R.array.Punjab, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> Sindh = ArrayAdapter.createFromResource(getActivity(),
                R.array.Sindh, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> Balochistan = ArrayAdapter.createFromResource(getActivity(),
                R.array.Balochistan, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> Islamabad = ArrayAdapter.createFromResource(getActivity(),
                R.array.Islamabad, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> Gilgit = ArrayAdapter.createFromResource(getActivity(),
                R.array.Gilgit, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> Kashmir = ArrayAdapter.createFromResource(getActivity(),
                R.array.Kashmir, android.R.layout.simple_spinner_item);

        province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProvince = province.getSelectedItem().toString();

                switch (selectedProvince) {
                    case "Kpk":
                        district.setAdapter(Kpk);
                        break;
                    case "Punjab":
                        district.setAdapter(Punjab);
                        break;
                    case "Sindh":
                        district.setAdapter(Sindh);
                        break;
                    case "Balochistan":
                        district.setAdapter(Balochistan);
                        break;
                    case "Islamabad(Captial Area)":
                        district.setAdapter(Islamabad);
                        break;
                    case "GilgitBaltistan":
                        district.setAdapter(Gilgit);
                        break;
                    case "Kashmir":
                        district.setAdapter(Kashmir);
                        break;
                    default:
                        district.setAdapter(Kpk);
                }

                //Toast.makeText(getContext(),province.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lastDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validation.isEmpty(jobTitle)){
                    jobTitle.setError("Cannot Empty!");
                    return;
                }
                if(Validation.isEmpty(lastDate)){
                    Toast.makeText(getContext(),"Select Last Date!",Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date lDate = null;
                try {
                    lDate = sdf.parse(lastDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (new Date().after(lDate)) {
                    Toast.makeText(getContext(),"Invalid Last Date!",Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences prefs = getActivity().getSharedPreferences(Constants.file, MODE_PRIVATE);
                int advertiserId = prefs.getInt("AdvertiserId", 0);

                advertisement = new Advertisement(advertiserId,jobTitle.getText().toString(),
                        province.getSelectedItem().toString(),
                        district.getSelectedItem().toString(),
                        education.getSelectedItem().toString(),
                        lastDate.getText().toString(),
                        selectedSkills);
                Call<Advertisement> call = service.createAdvertisement(advertisement);
                call.enqueue(new Callback<Advertisement>() {
                    @Override
                    public void onResponse(Call<Advertisement> call, Response<Advertisement> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getContext(),"Successfull",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Advertisement> call, Throwable t) {

                    }
                });

            }
        });

    }
    void getReferences(){

        skillsLayout = getView().findViewById(R.id.skills_layout);
        lastDate = getView().findViewById(R.id.last_date);
        province = getView().findViewById(R.id.province_spinner_id);
        district = getView().findViewById(R.id.district_spinner_id);
        jobTitle = getView().findViewById(R.id.job_title);
        education = getView().findViewById(R.id.education_spinner_id);
        btn = getView().findViewById(R.id.btn);

    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            lastDate.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear+1)
                    + "-" + String.valueOf(year));
        }
    };
    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }
}
