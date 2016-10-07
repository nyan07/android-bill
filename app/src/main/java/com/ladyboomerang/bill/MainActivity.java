package com.ladyboomerang.bill;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.util.Locale;


public class MainActivity extends AppCompatActivity
{
    public static Locale DEFAULT_CURRENCY_LOCALE = new Locale("pt", "BR");

    private EditText amountPaid;
    private EditText bill;
    private BillService service;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        service = BillService.getInstance();

        amountPaid = (EditText) findViewById(R.id.amount_paid);
        bill = (EditText) findViewById(R.id.bill);

        amountPaid.addTextChangedListener(new MoneyTextWatcher(amountPaid));
        bill.addTextChangedListener(new MoneyTextWatcher(bill));
    }

    public void onCalculateButtonClick(View view)
    {
        try
        {
            Change result = service.calculateChange(
                    FormatterHelper.clearCurrency(amountPaid.getText().toString()),
                    FormatterHelper.clearCurrency(bill.getText().toString()));

            showAlert(result);
        }
        catch (IllegalArgumentException e)
        {
            showAlert(getResources().getString(R.string.change_error));
        }
    }

    private void showAlert(Change change)
    {
        String value = FormatterHelper.toBRL(change.value);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(getResources().getString(R.string.change_result), value));

        if (change.numBanknotes > 0)
        {
            sb.append(" ");
            sb.append(getResources().getQuantityString(R.plurals.change_result_banknotes,
                    change.numBanknotes, change.numBanknotes));
        }

        if (change.numCoins > 0)
        {
            sb.append(change.numBanknotes > 0 ? " e " : " ");
            sb.append(getResources().getQuantityString(R.plurals.change_result_coins,
                    change.numCoins, change.numCoins));
        }

        sb.append(".");
        showAlert(sb.toString());
    }

    private void showAlert(String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.change_result_title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.dismiss();
            }
        });
        AppCompatDialog dialog = builder.create();
        dialog.show();
    }



    public class MoneyTextWatcher implements TextWatcher
    {
        private final WeakReference<EditText> editTextWeakReference;

        public MoneyTextWatcher(EditText editText)
        {
            editTextWeakReference = new WeakReference<>(editText);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
        }

        @Override
        public void afterTextChanged(Editable editable)
        {
            EditText editText = editTextWeakReference.get();
            if (editText == null) return;
            String s = editable.toString();
            editText.removeTextChangedListener(this);

            String formatted = FormatterHelper.toBRL(FormatterHelper.clearCurrency(s));

            editText.setText(formatted);
            editText.setSelection(formatted.length());
            editText.addTextChangedListener(this);

        }
    }
}
