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

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.klipwallet.app2app.api.request.model.BAppDeepLinkCB;
import com.klipwallet.app2app.exception.KlipRequestException;
import com.klipwallet.sample.R;
import com.klipwallet.app2app.api.Klip;
import com.klipwallet.app2app.api.KlipCallback;
import com.klipwallet.app2app.api.request.model.BAppInfo;
import com.klipwallet.app2app.api.request.ContractTxRequest;

public class ContractDlgFragment extends DialogFragment {
    private Klip klip;
    private KlipCallback callback;

    public ContractDlgFragment(Klip klip, KlipCallback callback) {
        this.klip = klip;
        this.callback = callback;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dlg_contract, null);
        final EditText to = view.findViewById(R.id.to);
        final EditText from = view.findViewById(R.id.from);
        final EditText value = view.findViewById(R.id.value);
        final EditText abi = view.findViewById(R.id.abi);
        final EditText params = view.findViewById(R.id.params);
        final EditText bappName = view.findViewById(R.id.bappName);
        final EditText bappSuccessURL = view.findViewById(R.id.bappSuccessURL);
        final EditText bappFailURL = view.findViewById(R.id.bappFailURL);

        builder.setView(view);
        builder.setTitle("Execute Contract")
                .setPositiveButton("Execute", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // BApp 정보
                        BAppInfo bAppInfo = new BAppInfo("bApp");
                        String successURL = bappSuccessURL.getText().toString();
                        String failURL = bappFailURL.getText().toString();
                        if(successURL.length()!=0 || failURL.length()!=0) {
                            BAppDeepLinkCB bAppCB = new BAppDeepLinkCB(successURL, failURL);
                            bAppInfo.setCallback(bAppCB);
                        }

                        // Contract 정보
                        ContractTxRequest req = new ContractTxRequest.Builder()
                                .to("0xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")//contract address
                                .value("0")
                                .abi("{" +
                                        "        \"constant\": false,\n" +
                                        "        \"inputs\": [\n" +
                                        "            {\n" +
                                        "                \"name\": \"_to\",\n" +
                                        "                \"type\": \"address\"\n" +
                                        "            },\n" +
                                        "            {\n" +
                                        "                \"name\": \"_value\",\n" +
                                        "                \"type\": \"uint256\"\n" +
                                        "            }\n" +
                                        "        ],\n" +
                                        "        \"name\": \"transfer\",\n" +
                                        "        \"outputs\": [\n" +
                                        "            {\n" +
                                        "                \"name\": \"\",\n" +
                                        "                \"type\": \"bool\"\n" +
                                        "            }\n" +
                                        "        ],\n" +
                                        "        \"payable\": false,\n" +
                                        "        \"stateMutability\": \"nonpayable\",\n" +
                                        "        \"type\": \"function\"\n" +
                                        "    }")

                                /**
                                 * key = To Address
                                 * value = amount //1000000000000000000 = 1
                                 */
                                .params(new ArrayList<Object>(Arrays.asList("0xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", "100000000000000000000"))) //0.01
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

    private ArrayList<Object> getStringToList(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ArrayList.class);
    }
}
