package com.example.talaatmagdy.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /**
     * This method is called when the client  want send the order to mail .
     */



    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity <= 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox= (CheckBox)findViewById(R.id.Whippedcreamcheckbox);
        CheckBox ChocolateCheckBox= (CheckBox)findViewById(R.id.Chocolatecheckbox);

        EditText nameEdit = (EditText)findViewById(R.id.Editname);


        boolean haswippedcream = whippedCreamCheckBox.isChecked();
        boolean haschocolate = ChocolateCheckBox.isChecked();


        int price =calculatePrice(haswippedcream , haschocolate);

        String nameClient = nameEdit.getText().toString();
        String priceMessage = CreateOrderSummary(price , haswippedcream , haschocolate ,nameClient);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + nameClient);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


        //displayMessage(priceMessage);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.number_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price value on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
    }
    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(boolean addWhippedCream , boolean addChocolate) {
        // price of 1 cup of coffe
        int basePrice = 5 ;

        // if addWhippedCream increase 1$
        if(addWhippedCream)
        {
            basePrice = basePrice +1 ;
        }

        // if addWhippedCream increase 2$

        if (addChocolate)
        {
            basePrice = basePrice +2 ;
        }

        // return all price and addition


        return   quantity * basePrice;
    }


    private String CreateOrderSummary(int price ,boolean addWhippedCream , boolean addChocolate ,String nameClient)
    {
        String priceMessage = "Name Client : "+nameClient;
        priceMessage += "\nAdd Whipped Cream ?"+addWhippedCream;
        priceMessage += "\nAdd  Chocolate ?"+addChocolate;
        priceMessage += "\nQuality"+quantity;
        priceMessage += "\nTotal: $" +price ;
        priceMessage +="\nThank You !";

        return priceMessage ;
    }

}

