package com.chucuaz.android.VirtualGDT;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by efren.robles on 12/20/2016.
 */

public abstract class IPFilterMinMax implements TextWatcher {

    private short min, max;
    private final EditText ev;

    public abstract void validate (EditText ev, String text);

    public IPFilterMinMax (EditText ev) {
        this.ev = ev;
    }

    @Override
    final public void afterTextChanged(Editable s) {
        String text = ev.getText().toString();
        validate(ev, text);
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) {

    }






}
