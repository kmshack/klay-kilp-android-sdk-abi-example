package com.klipwallet.sample.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.klipwallet.sample.R;
import com.klipwallet.app2app.api.Klip;
import com.klipwallet.app2app.api.KlipCallback;
import com.klipwallet.app2app.exception.KlipRequestException;

public class GetCardListDlgFragment extends DialogFragment {
    private Context ctx;
    private Klip klip;
    private KlipCallback callback;
    private String userAddress;

    public GetCardListDlgFragment(Context ctx, Klip klip, KlipCallback callback, String userAddress) {
        this.ctx = ctx;
        this.klip = klip;
        this.callback = callback;
        this.userAddress = userAddress;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dlg_card_list, null);
        final EditText sca = view.findViewById(R.id.sca);
        final EditText eoa = view.findViewById(R.id.eoa);
        final EditText cursor = view.findViewById(R.id.cursor);

        if (userAddress != null)
            eoa.setText(userAddress);

        builder.setView(view);
        builder.setTitle("Get Card List")
                .setPositiveButton("Execute", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // Card 정보
                        try {
                            klip.getCardList(sca.getText().toString(),
                                    eoa.getText().toString(),
                                    cursor.getText().toString(),
                                    callback);
                        } catch (KlipRequestException e) {
                            Toast.makeText(ctx, e.getErrorMsg(), Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }
}
