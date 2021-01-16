package com.loginius.loginiusinfotech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
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
import java.util.Date;


public class ReportActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    private static final String url = "https://maharshiinstitute.com/loginius/getProject.php";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ActivityCompat.requestPermissions(ReportActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("Project"));
        tabLayout.addTab(tabLayout.newTab().setText("Attendance "));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        MyTabAdapter myTabAdapter = new MyTabAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(myTabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void Projectpdf(View view) {

        try {
            /*File Start*/
            String x = Environment.getExternalStorageState();
            if (x.equals(Environment.MEDIA_MOUNTED)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_d_HH_mm_ss");
                String currentDateandTime = sdf.format(new Date());
                File f1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Loginius");
                if (!f1.exists()) {
                    f1.mkdir();
                }
                File f2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Loginius/Project");
                if (!f2.exists()) {
                    f2.mkdir();
                }
                File f = new File(Environment.getExternalStorageDirectory() + "/Loginius/Project", "Project" + currentDateandTime + ".pdf");
                /*File Over*/
                final Document document = new Document(PageSize.A4);
                final Font FONT = new Font(Font.FontFamily.HELVETICA, 52, Font.BOLD, new GrayColor(0.85f));
                Rectangle rect= new Rectangle(document.getPageSize());
                rect.setBorder(Rectangle.BOX);
                rect.setBorderWidth(20);
                final PdfWriter writer=PdfWriter.getInstance(document, new FileOutputStream(f));
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
                document.open();
                document.add(rect);
                Font font = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Font Nfont = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
                final PdfPTable table = new PdfPTable(7);
                PdfPCell c1 = new PdfPCell(new Phrase("Loginius Infotech Project Report\n ", font));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setBorder(0);
                c1.setColspan(7);
                table.addCell(c1);
                table.setWidths(new float[]{3f, 9f, 7f, 9f, 9f, 9f, 10f});
                c1 = new PdfPCell(new Phrase("Sr\nNo", Nfont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Project Name", Nfont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Amount", Nfont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Referance", Nfont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Start", Nfont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("End", Nfont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                c1 = new PdfPCell(new Phrase("Developer", Nfont));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(c1);
                table.setHeaderRows(2);
                final RequestQueue requestQueue = Volley.newRequestQueue(ReportActivity.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                Log.d("data", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
                            JSONArray data = jsonObject.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject myObj = data.getJSONObject(i);
                                //create istance of class to add value in layout
//                                    ProjectModelClass modalClass=new ProjectModelClass(myObj.getString("pnm"),myObj.getString("ref"),myObj.getString("start"),myObj.getString("due"),myObj.getString("amount"),myObj.getString("dev"));
                                //add data in model list
                                table.getDefaultCell().setFixedHeight(40f);
                                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                                table.addCell(String.valueOf(i + 1));
                                table.addCell(myObj.getString("pnm"));
                                table.addCell(myObj.getString("amount"));
                                table.addCell(myObj.getString("ref"));
                                table.addCell(myObj.getString("start"));
                                table.addCell(myObj.getString("due"));
                                table.addCell(myObj.getString("dev"));
                            }
                        } catch (JSONException e) {
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
                        Log.d("data", error.toString());
                    }
                });

                requestQueue.add(jsonObjectRequest);
                table.flushContent();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Project PDF Downloaded...", Toast.LENGTH_SHORT).show();
    }


}