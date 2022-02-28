package com.gb.social_notes.ui.editor;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.gb.social_notes.R;
import com.gb.social_notes.repository.CardData;
import com.gb.social_notes.ui.MainActivity;

import java.util.Calendar;


public class CardFragment extends Fragment {
    Button btnSave;
    CardData cardData;
    Calendar calendar;
    DatePicker datePicker;
    EditText inputTitle;
    EditText inputDescription;

    public static CardFragment newInstance(CardData cardData) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putParcelable("cardData", cardData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            initView(view);
            setContent(view);
            extractDate(view);
            save(view);
        }
    }

    private void initView(View view) {
        if (getArguments() != null) {
            inputTitle = (EditText) view.findViewById(R.id.inputTitle);
            inputDescription = (EditText) view.findViewById(R.id.inputDescription);
            datePicker = (DatePicker) view.findViewById(R.id.inputDate);
            btnSave = (Button) view.findViewById(R.id.btnSave);
            cardData = getArguments().getParcelable("cardData");
        }
    }

    private void setContent(View view) {
        inputTitle.setText(cardData.getTitle());
        inputDescription.setText(cardData.getDescription());
        calendar = Calendar.getInstance();
        calendar.setTime(cardData.getDate());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                    calendar.set(Calendar.YEAR, i);
                    calendar.set(Calendar.MONTH, i1);
                    calendar.set(Calendar.DAY_OF_MONTH, i2);
                }
            });
        }
    }

    private void extractDate(View view) {
        datePicker.init(calendar.get(Calendar.YEAR) - 1,
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }

    public void save(View view) {
        view.findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View it) {
                cardData.setTitle(inputTitle.getText().toString());
                cardData.setDescription(inputDescription.getText().toString());

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    calendar.set(Calendar.YEAR, datePicker.getYear());
                    calendar.set(Calendar.MONTH, datePicker.getMonth());
                    calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                }
                cardData.setDate(calendar.getTime());
                ((MainActivity) requireActivity()).getPublisher().sendMessage(cardData);
                ((MainActivity) requireActivity()).getSupportFragmentManager().popBackStack();
            }
        });
    }
}