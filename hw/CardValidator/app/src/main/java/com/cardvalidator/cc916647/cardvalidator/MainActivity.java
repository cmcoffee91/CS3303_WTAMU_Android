package com.cardvalidator.cc916647.cardvalidator;

/*
chris coffee
cc916647
test part 2
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button validateButton = (Button) findViewById(R.id.validateButton);
        final EditText cardNumView = (EditText) findViewById(R.id.cardNumberView);

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardNum = cardNumView.getText().toString().trim();

                if(cardNum.length() < 2)showToast("Please enter valid  card number");

                if(cardNum.isEmpty() || cardNum.length() != 15 || !cardNum.substring(0,2).equalsIgnoreCase("37")  )
                {

                        showToast("Please enter valid card number");

                }
                else
                {
                    int sum = 0;

                    List<Integer> reverseList = new ArrayList<>();
                    for(int i = cardNum.length()-2;i >= 0;i--)
                    {
                        //37 64 02 76 18 21 98 6

                        int number = Integer.parseInt(String.valueOf(cardNum.charAt(i)));
                        Log.e("Main","number is : " +  number  );

                        int realIndex = i+1;
                        Log.e("Main"," real index number is : " +  realIndex  );
                        if(i % 2 != 0)
                        {
                            Log.e("Main","index is : " +  i + "is odd" );
                            number = number*2;
                            if(number > 9) number = number - 9 ;
                        }

                        sum = sum + number;


                        reverseList.add(  number );
                    }

                    sum = sum + Integer.parseInt(String.valueOf(cardNum.charAt(cardNum.length()-1)));

                    boolean isDivisibleBy10 = sum % 10 == 0;

                    if(isDivisibleBy10)
                    {

                        showToast("This is a valid card");
                    }
                    else
                    {
                        showToast("This is not a valid card");
                    }
                }
            }
        });
    }



    private void showToast(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_LONG).show();
    }

}
