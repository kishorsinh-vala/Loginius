package com.loginius.loginiusinfotech;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AttendanceFragment extends Fragment {
    TextView txtData;
    DatePickerDialog datepicker;
    Button btn, btn_mn;
    RecyclerView recyclerView;
    Context context;
    int month1, day1, year1;
    List<DispAttdModel> mList;
    DispAttdAdapter adapterClass;
    RelativeLayout relLay;
    LazyLoader lazyLoader;
    ConstraintLayout cns;
    ImageButton attd;

    public AttendanceFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_attendance, container, false);

        try {
            recyclerView = view.findViewById(R.id.recyclerviewAttendance);
            btn = view.findViewById(R.id.btn_report_attd);
            btn_mn = view.findViewById(R.id.btn_report_min_attd);
            txtData = view.findViewById(R.id.txt_attd);
            cns = view.findViewById(R.id.main_cns);
            relLay = view.findViewById(R.id.main_rel);
            lazyLoader = view.findViewById(R.id.my_progress);
            attd = view.findViewById(R.id.img_btn_attd);
            Date date = new Date();
            String currday = (String) DateFormat.format("dd", date); // 20
            String currmonth = (String) DateFormat.format("MM", date); // 06
            String currYear = (String) DateFormat.format("yyyy", date); // 2020
            if(currday.equals("01")||currday.equals("02")||currday.equals("03")||currday.equals("04")||currday.equals("05")
                    ||currday.equals("06")||currday.equals("07")||currday.equals("08")||currday.equals("09"))
            {
                day1 = Integer.parseInt(currday.replace("0", ""));
            }
            else
            {
                day1 = Integer.parseInt(currday);
            }
            if(currmonth.equals("01")||currmonth.equals("02")||currmonth.equals("03")|| currmonth.equals("04")||currmonth.equals("05")
                    ||currmonth.equals("06")||currmonth.equals("07")||currmonth.equals("08")||currmonth.equals("09"))
            {
                month1 = Integer.parseInt(currmonth.replace("0", ""));
            }
            else
            {
                month1 = Integer.parseInt(currmonth);
            }
            year1 = Integer.parseInt(currYear);
            txtData.setText(day1 + "-" + (month1) + "-" + year1);
            getData(day1, month1);
            relLay.setVisibility(View.INVISIBLE);


            txtData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    final int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);
                    datepicker = new DatePickerDialog(context,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    day1 = dayOfMonth;
                                    month1 = monthOfYear + 1;
                                    year1 = year;
                                    txtData.setText(day1 + "-" + (month1) + "-" + year1);
                                    chk();
                                }
                            }, year, month, day);
                    datepicker.show();

                }
            });
            /*btn + Start*/
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String d = txtData.getText().toString();
                    Date date = new Date();
                    day1 = day1 + 1;
                    String currday = (String) DateFormat.format("dd", date); // 20
                    String currmonth = (String) DateFormat.format("MM", date); // 06
                    int chkd = Integer.parseInt(currday);
                    int chkM = Integer.parseInt(currmonth);
                    if (day1 > chkd || month1 > chkM || d.equals("Select Date") || day1 <= 0 || month1 <= 0) {
                        relLay.setVisibility(View.INVISIBLE);
                        Toast.makeText(context, "Please First Select Date...", Toast.LENGTH_SHORT).show();
                        mList.clear();
                        adapterClass = new DispAttdAdapter(context, mList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(adapterClass);
                    } else {
                        if (day1 > 31) {
                            day1 = day1 - 1;
                            txtData.setText(day1 + "-" + (month1) + "-" + year1);
                        } else {
                            txtData.setText(day1 + "-" + (month1) + "-" + year1);
//                        Log.d("date",txtData.getText().toString());
//                        Toast.makeText(context, "OK"+txtData.getText(), Toast.LENGTH_SHORT).show();
                            AlphaAnimation alpha = new AlphaAnimation(0.2F, 0.2F);
                            alpha.setDuration(0);
                            alpha.setFillAfter(true);
                            cns.startAnimation(alpha);
                            txtData.setEnabled(false);
                            btn.setEnabled(false);
                            btn_mn.setEnabled(false);
                            attd.setEnabled(false);
                            getData(day1, month1);
                        }
                    }

                }
            });
            /*btn + Over*/

            /*btn - Start*/
            btn_mn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (day1 <= 0 || month1 <= 0) {
                        Toast.makeText(context, "Please First Select Date...", Toast.LENGTH_SHORT).show();
                    } else {
                        day1 = day1 - 1;
                        if (day1 == 0) {


                            txtData.setText(day1 + 1 + "-" + (month1) + "-" + year1);
                        } else {
                            txtData.setText(day1 + "-" + (month1) + "-" + year1);
                            AlphaAnimation alpha = new AlphaAnimation(0.2F, 0.2F);
                            alpha.setDuration(0);
                            alpha.setFillAfter(true);
                            cns.startAnimation(alpha);
                            txtData.setEnabled(false);
                            btn.setEnabled(false);
                            getData(day1, month1);
                        }
                    }
                }
            });
            /*btn - over*/
            attd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int rcData = recyclerView.getAdapter().getItemCount();
                    if (rcData > 0) {
                        try {
                            String x = Environment.getExternalStorageState();
                            if (x.equals(Environment.MEDIA_MOUNTED)) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_d_HH_mm_ss");
                                String currentDateandTime = sdf.format(new Date());
                                File f1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Loginius");
                                if (!f1.exists()) {
                                    f1.mkdir();
                                }
                                File f2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Loginius/Attendance");
                                if (!f2.exists()) {
                                    f2.mkdir();
                                }
                                File f = new File(Environment.getExternalStorageDirectory() + "/Loginius/Attendance", "Attendance" + currentDateandTime + ".pdf");
                                /*File Over*/
                                final Document document = new Document(PageSize.A4);
                                /*WATER MARK START*/
                                final Font FONT = new Font(Font.FontFamily.HELVETICA, 52, Font.BOLD, new GrayColor(0.85f));
                                final PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(f));
                                writer.setPageEvent(new PdfPageEvent() {
                                    @Override
                                    public void onOpenDocument(PdfWriter pdfWriter, Document document) {

                                    }

                                    @Override
                                    public void onStartPage(PdfWriter pdfWriter, Document document) {

                                    }

                                    @Override
                                    public void onEndPage(PdfWriter pdfWriter, Document document) {
                                        ColumnText.showTextAligned(pdfWriter.getDirectContentUnder(),
                                                Element.ALIGN_CENTER, new Phrase("LOGINIUS INFOTECH", FONT),
                                                297.5f, 421, pdfWriter.getPageNumber() % 2 == 1 ? 45 : -45);
                                    }

                                    @Override
                                    public void onCloseDocument(PdfWriter pdfWriter, Document document) {

                                    }

                                    @Override
                                    public void onParagraph(PdfWriter pdfWriter, Document document, float v) {

                                    }

                                    @Override
                                    public void onParagraphEnd(PdfWriter pdfWriter, Document document, float v) {

                                    }

                                    @Override
                                    public void onChapter(PdfWriter pdfWriter, Document document, float v, Paragraph paragraph) {

                                    }

                                    @Override
                                    public void onChapterEnd(PdfWriter pdfWriter, Document document, float v) {

                                    }

                                    @Override
                                    public void onSection(PdfWriter pdfWriter, Document document, float v, int i, Paragraph paragraph) {

                                    }

                                    @Override
                                    public void onSectionEnd(PdfWriter pdfWriter, Document document, float v) {

                                    }

                                    @Override
                                    public void onGenericTag(PdfWriter pdfWriter, Document document, Rectangle rectangle, String s) {

                                    }
                                });
                                /*WATERMARK SET OVER*/
                                document.open();
                                /*RECTANGLE START*/
                                Rectangle rect = new Rectangle(document.getPageSize());
                                rect.setBorder(Rectangle.BOX);
                                rect.setBorderWidth(20);
                                /*RECTANGLE OVER*/
                                document.add(rect);
                                Font font = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                                Font Nfont = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
                                final PdfPTable table = new PdfPTable(3);
                                PdfPCell c1 = new PdfPCell(new Phrase("Attendance Report For Date:" + txtData.getText().toString() + "\n ", font));
                                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                                c1.setBorder(0);
                                c1.setColspan(3);
                                table.addCell(c1);
                                table.setWidths(new float[]{3f, 7f, 5f});
                                c1 = new PdfPCell(new Phrase("Sr No.", Nfont));
                                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(c1);
                                c1 = new PdfPCell(new Phrase("Student Name", Nfont));
                                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(c1);
                                c1 = new PdfPCell(new Phrase("Attendance", Nfont));
                                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(c1);
                                table.setHeaderRows(2);
                                /*DATA START*/
                                final String url_attd = "https://maharshiinstitute.com/loginius/getAtd.php";
                                String par = "?day=" + day1 + "&month=" + month1;
                                String final_URL = url_attd + par.replace(" ", "%20");
                                final RequestQueue mQueue = Volley.newRequestQueue(context);
                                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, final_URL, null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        try {
                                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
                                            JSONArray data = jsonObject.getJSONArray("data");

                                            for (int i = 0; i < data.length(); i++) {
                                                JSONObject myObj = data.getJSONObject(i);
                                                //create istance of class to add value in layout
                                                DispAttdModel modalClass = new DispAttdModel(myObj.getString("username"), myObj.getString("attd"));
                                                //add data in model list
                                                table.getDefaultCell().setFixedHeight(20f);
                                                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                                                table.addCell(String.valueOf(i + 1));
                                                table.addCell(myObj.getString("username"));
                                                table.addCell(myObj.getString("attd"));

                                            }
                                        } catch (JSONException e) {
                                            Log.d("error", response.toString());
                                            e.printStackTrace();
                                        }
                                        try {
                                            document.add(table);
                                        } catch (DocumentException e) {
                                            e.printStackTrace();
                                        }

                                        document.close();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });
                                mQueue.add(request);
                                table.flushContent();
                                Toast.makeText(context, "Attendance PDF Downloaded...", Toast.LENGTH_SHORT).show();
                                /*DATA OVER*/

                            }
                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "No Data Available", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    private void chk() {
        relLay.setVisibility(View.VISIBLE);
        LazyLoader loader = new LazyLoader(context, 30, 20,
                ContextCompat.getColor(context, R.color.loader_selected),
                ContextCompat.getColor(context, R.color.loader_selected),
                ContextCompat.getColor(context, R.color.loader_selected));
        loader.setAnimDuration(500);
        loader.setFirstDelayDuration(100);
        loader.setSecondDelayDuration(200);
        loader.setInterpolator(new LinearInterpolator());

        lazyLoader.addView(loader);
        String d = txtData.getText().toString();
        Date date = new Date();
        String currday = (String) DateFormat.format("dd", date); // 20
        String currmonth = (String) DateFormat.format("MM", date); // 06
        int chkd = Integer.parseInt(currday);
        int chkM = Integer.parseInt(currmonth);
        if (day1 > chkd || month1 > chkM || d.equals("Select Date")) {
            relLay.setVisibility(View.INVISIBLE);
            Toast.makeText(context, "Please Select Valid  Date...", Toast.LENGTH_SHORT).show();
        } else {
//                        Log.d("date",txtData.getText().toString());
//                        Toast.makeText(context, "OK"+txtData.getText(), Toast.LENGTH_SHORT).show();
            AlphaAnimation alpha = new AlphaAnimation(0.2F, 0.2F);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            cns.startAnimation(alpha);
            txtData.setEnabled(false);
            btn.setEnabled(false);
            btn_mn.setEnabled(false);
            attd.setEnabled(false);
            getData(day1, month1);
        }

    }

    private void getData(int day1, int month1) {
        try {
            final String url_attd = "https://maharshiinstitute.com/loginius/getAtd.php";
            String par = "?day=" + day1 + "&month=" + month1;
            String final_URL = url_attd + par.replace(" ", "%20");
            final RequestQueue mQueue = Volley.newRequestQueue(context);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, final_URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(response));
                        JSONArray data = jsonObject.getJSONArray("data");
                        mList.clear();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject myObj = data.getJSONObject(i);
                            //create istance of class to add value in layout
                            DispAttdModel modalClass = new DispAttdModel(myObj.getString("username"), myObj.getString("attd"));
                            //add data in model list
                            mList.add(modalClass);
                        }
                    } catch (JSONException e) {
                        Log.d("error", response.toString());
                        e.printStackTrace();
                    }
                    adapterClass = new DispAttdAdapter(context, mList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(adapterClass);
                    relLay.setVisibility(View.INVISIBLE);
                    cns.startAnimation(new AlphaAnimation(0, 0));
                    txtData.setEnabled(true);
                    btn.setEnabled(true);
                    btn_mn.setEnabled(true);
                    attd.setEnabled(true);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            mQueue.add(request);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}
