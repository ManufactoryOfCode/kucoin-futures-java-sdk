/**
 * Copyright 2019 Mek Global Limited.
 */
package com.kumex.core.rest.adapter;

import com.kumex.core.rest.impl.retrofit.PublicRetrofitAPIImpl;
import com.kumex.core.rest.interfaces.HistoryAPI;
import com.kumex.core.rest.interfaces.retrofit.HistoryAPIRetrofit;
import com.kumex.core.rest.response.TransactionHistoryResponse;

import java.io.IOException;
import java.util.List;

/**
 * Created by chenshiwei on 2019/1/22.
 */
public class HistoryAPIAdapter extends PublicRetrofitAPIImpl<HistoryAPIRetrofit> implements HistoryAPI {

    public HistoryAPIAdapter(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public List<TransactionHistoryResponse> getTransactionHistories(String symbol) throws IOException {
        return super.executeSync(getAPIImpl().getTransactionHistories(symbol));
    }

}
