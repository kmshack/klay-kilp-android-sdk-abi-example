package com.klipwallet.sample;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.klipwallet.sample.actions.KlipAction;
import com.klipwallet.sample.util.JsonHelper;
import com.klipwallet.app2app.api.Klip;
import com.klipwallet.app2app.api.KlipCallback;
import com.klipwallet.app2app.api.response.CardListResponse;
import com.klipwallet.app2app.api.response.KlipErrorResponse;
import com.klipwallet.app2app.api.response.KlipResponse;
import com.klipwallet.app2app.api.response.model.KlipResult;

public class MainActivity extends FragmentActivity {

    private Context ctx;
    private String[] list = {"prepare (Link)",
            "prepare (KLAY)",
            "prepare (Token)",
            "prepare (Card)",
            "prepare (Contract)",
            "request",
            "getResult",
            "getCardList"};

    private ListView listView;
    private TextView reqView;
    private TextView resView;

    private String requestKey;
    private String userAddress;
    private String userCardId;

    private KlipAction klipAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = this;

        reqView = findViewById(R.id.reqInfo);
        resView = findViewById(R.id.resInfo);
        listView = findViewById(R.id.listView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                list
        );

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(OnItemClickListener);

        klipAction = new KlipAction(ctx, Klip.getInstance(ctx));
    }

    private AdapterView.OnItemClickListener OnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String name = (String)parent.getItemAtPosition(position);
            reqView.setText(name);

            switch (name) {
                case "prepare (Link)":
                    klipAction.prepareLink(prepareLinkCallback);
                    break;
                case "prepare (KLAY)":
                    klipAction.prepareKlay(klipCallback);
                    break;
                case "prepare (Token)":
                    klipAction.prepareToken(prepareTokenCallback);
                    break;
                case "prepare (Card)":
                    klipAction.prepareCard(userCardId, klipCallback);
                    break;
                case "prepare (Contract)":
                    klipAction.prepareContract(prepareContractCallback);
                    break;
                case "request":
                    klipAction.request(requestKey);
                    break;
                case "getResult":
                    klipAction.getResult(requestKey, klipCallback);
                    break;
                case "getCardList":
                    klipAction.getCardList(userAddress, cardListCallback);
                    break;
                default:
                    break;
            }
        }
    };

    private KlipCallback prepareContractCallback = new KlipCallback<KlipResponse>() {
        @Override
        public void onSuccess(final KlipResponse res) {
            String out = JsonHelper.toPrettyFormat(res.toString());
            resView.setText(out);

            // save request key
            String resultKey = res.getRequestKey();
            if (resultKey != null)
                requestKey = resultKey;

            // save user address
            KlipResult result = res.getResult();
            if (result != null) {
                userAddress = result.getKlaytnAddress();
            }

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://klipwallet.com/?target=/a2a?request_key=" + requestKey) ));

        }
        @Override
        public void onFail(final KlipErrorResponse res) {
            resView.setText(res.toString());

            // reset request key
            requestKey = null;
        }
    };
    private KlipCallback prepareLinkCallback = new KlipCallback<KlipResponse>() {
        @Override
        public void onSuccess(final KlipResponse res) {
            String out = JsonHelper.toPrettyFormat(res.toString());
            resView.setText(out);

            // save request key
            String resultKey = res.getRequestKey();
            if (resultKey != null)
                requestKey = resultKey;

            // save user address
            KlipResult result = res.getResult();
            if (result != null) {
                userAddress = result.getKlaytnAddress();
            }

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://klipwallet.com/?target=/a2a?request_key=" + requestKey) ));

        }
        @Override
        public void onFail(final KlipErrorResponse res) {
            resView.setText(res.toString());

            // reset request key
            requestKey = null;
        }
    };

    private KlipCallback prepareTokenCallback = new KlipCallback<KlipResponse>() {
        @Override
        public void onSuccess(final KlipResponse res) {
            String out = JsonHelper.toPrettyFormat(res.toString());
            resView.setText(out);

            // save request key
            String resultKey = res.getRequestKey();
            if (resultKey != null)
                requestKey = resultKey;

            // save user address
            KlipResult result = res.getResult();
            if (result != null) {
                userAddress = result.getKlaytnAddress();
            }

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://klipwallet.com/?target=/a2a?request_key=" + requestKey) ));
        }
        @Override
        public void onFail(final KlipErrorResponse res) {
            resView.setText(res.toString());

            // reset request key
            requestKey = null;
        }
    };

    private KlipCallback klipCallback = new KlipCallback<KlipResponse>() {
        @Override
        public void onSuccess(final KlipResponse res) {
            String out = JsonHelper.toPrettyFormat(res.toString());
            resView.setText(out);

            // save request key
            String resultKey = res.getRequestKey();
            if (resultKey != null)
                requestKey = resultKey;

            // save user address
            KlipResult result = res.getResult();
            if (result != null) {
                userAddress = result.getKlaytnAddress();
            }
        }
        @Override
        public void onFail(final KlipErrorResponse res) {
            resView.setText(res.toString());

            // reset request key
            requestKey = null;
        }
    };

    private KlipCallback cardListCallback = new KlipCallback<CardListResponse>() {
        @Override
        public void onSuccess(final CardListResponse res) {
            String out = JsonHelper.toPrettyFormat(res.toString());
            resView.setText(out);

            // save card id
            if (res.getCards() != null) {
                userCardId = String.valueOf(res.getCards().get(0).getCardId());
            }
            else {
                Toast.makeText(ctx, "user have not any card", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onFail(final KlipErrorResponse res) {
//            String out = JsonHelper.toPrettyFormat(res.toJson());
            resView.setText(res.toString());
        }
    };
}
