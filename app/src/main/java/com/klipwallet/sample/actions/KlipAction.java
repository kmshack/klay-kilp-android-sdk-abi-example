package com.klipwallet.sample.actions;

import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.klipwallet.app2app.exception.KlipRequestException;
import com.klipwallet.sample.dialog.CardDlgFragment;
import com.klipwallet.sample.dialog.ContractDlgFragment;
import com.klipwallet.sample.dialog.GetCardListDlgFragment;
import com.klipwallet.sample.dialog.KlayDlgFragment;
import com.klipwallet.sample.dialog.LinkDlgFragment;
import com.klipwallet.sample.dialog.TokenDlgFragment;
import com.klipwallet.app2app.api.Klip;
import com.klipwallet.app2app.api.KlipCallback;
import com.klipwallet.app2app.api.response.CardListResponse;
import com.klipwallet.app2app.api.response.KlipResponse;

public class KlipAction {
    private Context ctx;
    private Klip klip;

    public KlipAction(Context ctx, Klip klip) {
        this.ctx = ctx;
        this.klip = klip;
    }

    public void prepareLink(KlipCallback<KlipResponse> callback) {
        new LinkDlgFragment(klip, callback).show(((FragmentActivity)ctx).getSupportFragmentManager(), null);
    }

    public void prepareKlay(KlipCallback<KlipResponse> callback) {
        new KlayDlgFragment(klip, callback).show(((FragmentActivity)ctx).getSupportFragmentManager(), null);
    }

    public void prepareToken(KlipCallback<KlipResponse> callback) {
        new TokenDlgFragment(klip, callback).show(((FragmentActivity)ctx).getSupportFragmentManager(), null);
    }

    public void prepareCard(String userCardId, KlipCallback<KlipResponse> callback) {
        new CardDlgFragment(klip, userCardId, callback).show(((FragmentActivity)ctx).getSupportFragmentManager(), null);
    }

    public void prepareContract(KlipCallback<KlipResponse> callback) {
        new ContractDlgFragment(klip, callback).show(((FragmentActivity)ctx).getSupportFragmentManager(), null);
    }

    public void request(String requestKey) {
        try {
            klip.request(requestKey);
        } catch (KlipRequestException e) {
            Toast.makeText(ctx, "it's need to call request api first", Toast.LENGTH_LONG).show();
        }
    }

    public void getResult(String requestKey, KlipCallback<KlipResponse> callback) {
        try {
            klip.getResult(requestKey, callback);
        } catch (KlipRequestException e) {
            Toast.makeText(ctx, "it's need to call request api first", Toast.LENGTH_LONG).show();
        }
    }

    public void getCardList(String userAddress, KlipCallback<CardListResponse> callback) {
        new GetCardListDlgFragment(ctx, klip, callback, userAddress).show(((FragmentActivity)ctx).getSupportFragmentManager(), null);
    }
}
