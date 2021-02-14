package com.klipwallet.sample.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.klipwallet.app2app.api.request.model.BAppDeepLinkCB;
import com.klipwallet.app2app.exception.KlipRequestException;
import com.klipwallet.sample.R;
import com.klipwallet.app2app.api.Klip;
import com.klipwallet.app2app.api.KlipCallback;
import com.klipwallet.app2app.api.request.model.BAppInfo;
import com.klipwallet.app2app.api.request.TokenTxRequest;

public class TokenDlgFragment extends DialogFragment {
    private Klip klip;
    private KlipCallback callback;

    public TokenDlgFragment(Klip klip, KlipCallback callback) {
        this.klip = klip;
        this.callback = callback;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dlg_token, null);
        final EditText contract = view.findViewById(R.id.contract);
        final EditText to = view.findViewById(R.id.to);
        final EditText from = view.findViewById(R.id.from);
        final EditText amount = view.findViewById(R.id.amount);
        final EditText bappName = view.findViewById(R.id.bappName);
        final EditText bappSuccessURL = view.findViewById(R.id.bappSuccessURL);
        final EditText bappFailURL = view.findViewById(R.id.bappFailURL);

        builder.setView(view);
        builder.setTitle("Send Token")
                .setPositiveButton("Execute", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // BApp 정보
                        BAppInfo bAppInfo = new BAppInfo("bApp");
                        String successURL = "kakaotalk://";
                        String failURL =  "kakaotalk://";

                        if(successURL.length()!=0 || failURL.length()!=0) {
                            BAppDeepLinkCB bAppCB = new BAppDeepLinkCB(successURL, failURL);
                            bAppInfo.setCallback(bAppCB);
                        }

                        // Token 정보
                        TokenTxRequest req = new TokenTxRequest.Builder()
                                .contract("0xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx") //contract address
                                .to("0xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                                .from("0xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                                .amount("1") //amount
                                .build();

                        try {
                            klip.prepare(req, bAppInfo, callback);
                        } catch (KlipRequestException e) {
                            e.printStackTrace();
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
