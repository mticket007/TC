package com.ticket.m.qrcodescanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ViewfinderView;

import org.json.JSONObject;

public class QrcodeScannerActivity extends AppCompatActivity {
private Button scan;
private TextView source,destination,sourceName,destinationName,fare,farePrice,bookingTime,bookingTimeValue,validity,validityTime,classValue,typeValue;
private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scanner);
       /* getActionBar().setLogo(R.mipmap.ic_launcher_round);
        getActionBar().setDisplayUseLogoEnabled(true);*/
        scan=findViewById(R.id.scan);
        source=findViewById(R.id.source);
        destination=findViewById(R.id.destination);
        sourceName=findViewById(R.id.sourceName);
        destinationName=findViewById(R.id.destinationName);
        fare=findViewById(R.id.fare);
        farePrice=findViewById(R.id.farePrice);
        bookingTime=findViewById(R.id.bookingTime);
        bookingTimeValue=findViewById(R.id.bookingTimeValue);
        validity=findViewById(R.id.validity);
        validityTime=findViewById(R.id.validityTime);
        typeValue=findViewById(R.id.typeValue);
        classValue=findViewById(R.id.classValue);

        qrScan=new IntentIntegrator(this);




        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.setPrompt("Enter Qr Code into the box");
                qrScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                qrScan.setCameraId(0);
                qrScan.setOrientationLocked(false);
                qrScan.initiateScan();

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result !=null)
        {
            //if the result is empty
            if(result.getContents()==null)
            {
                Toast.makeText(this,"Result not found",Toast.LENGTH_LONG).show();
            }//if result contains data then execute this
            else
            {
                try
                {
                    JSONObject obj=new JSONObject(result.getContents());
                    sourceName.setText(obj.getString("sourceStation"));
                    destinationName.setText(obj.getString("destinationStation"));
                    farePrice.setText(obj.getString("fare"));
                    bookingTimeValue.setText(obj.getString("bookingTime"));
                    validityTime.setText(obj.getString("expiry_time"));
                    classValue.setText(obj.getString("type_of_ticket"));
                    typeValue.setText(obj.getString("type_of_journey"));

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    //if control comes here that means there is some content which is not supported
                    //by our application in this situation we will print what ever it contains
                    Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
                }
            }

        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}
