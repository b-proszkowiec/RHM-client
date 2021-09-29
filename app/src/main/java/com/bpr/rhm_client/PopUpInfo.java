package com.bpr.rhm_client;

import android.text.Spanned;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class PopUpInfo {

    public void showPopupWindow(final View view, Spanned message) {

        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, MATCH_PARENT, MATCH_PARENT, true);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        TextView popupMessage = popupView.findViewById(R.id.titleText);
        popupMessage.setText(message);

        Button editButton = popupView.findViewById(R.id.messageButton);
        editButton.setOnClickListener(v -> {
            popupWindow.dismiss();
        });

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
