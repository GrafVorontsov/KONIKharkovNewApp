package ua.kharkov.koni.konikharkov.activityes.ui.notifications;

import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;

import ua.kharkov.koni.konikharkov.R;
import ua.kharkov.koni.konikharkov.activityes.AboutActivity;
import ua.kharkov.koni.konikharkov.activityes.LoginActivity;
import ua.kharkov.koni.konikharkov.activityes.PhoneActivity;

public class NotificationsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(this, textView::setText);
// получение вью нижнего экрана
        LinearLayout llBottomSheet = root.findViewById(R.id.bottom_sheet);
        View fab = root.findViewById(R.id.fab);
// настройка поведения нижнего экрана
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);

// настройка состояний нижнего экрана
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

// настройка максимальной высоты
        //bottomSheetBehavior.setPeekHeight(400);

// настройка возможности скрыть элемент при свайпе вниз
        bottomSheetBehavior.setHideable(false);

// настройка колбэков при изменениях
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                // этот код скрывает кнопку сразу же
                // и отображает после того как нижний экран полностью свернется
                if (BottomSheetBehavior.STATE_DRAGGING == newState) {
                    fab.animate().scaleX(0).scaleY(0).setDuration(300).start();
                } else if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    fab.animate().scaleX(1).scaleY(1).setDuration(300).start();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        LinearLayout linearLayoutCall = root.findViewById(R.id.call);

        linearLayoutCall.setOnClickListener(v -> {
            Intent call = new Intent(getActivity(), PhoneActivity.class);
            startActivity(call);
        });

        LinearLayout linearLayoutAbout = root.findViewById(R.id.about);

        linearLayoutAbout.setOnClickListener(v -> {
            Intent about = new Intent(getActivity(), AboutActivity.class);
            startActivity(about);
        });

        LinearLayout linearLayoutLogin = root.findViewById(R.id.login);

        linearLayoutLogin.setOnClickListener(v -> {
            Intent login = new Intent(getActivity(), LoginActivity.class);
            startActivity(login);
        });

        return root;
    }
}