package com.example.global.selectlocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.global.selectlocation.ui.widget.SelectMapLocationView;

public class MainActivity extends AppCompatActivity {

    private SelectMapLocationView mSelectLocationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initializeWidgets();
    }

    private void initializeWidgets() {
        LinearLayout mLinearLayout = findViewById(R.id.content);

        int id = 2;
        mSelectLocationView = new SelectMapLocationView(
                this,getString(R.string.selccione_una_direccion));
        mSelectLocationView.setId(id);

        mLinearLayout.addView(mSelectLocationView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SelectMapLocationView.REQUEST_SELECT_LOCATION){
            if (mSelectLocationView!=null){
                mSelectLocationView.setInformationMap(data);
            }
        }
    }
}
