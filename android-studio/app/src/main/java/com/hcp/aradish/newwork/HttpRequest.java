package com.hcp.aradish.newwork;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Created by hcp on 15/6/16.
 */
public class HttpRequest implements Request.Method  {
    private HttpRequestListener mHttpRequestListener;
    private int mMethod = GET;
    private String mUrl;
    private Map<String, String> mParams;
    private Map<String, String> mHeaders;
    private HttpContextWrapper mHttpContextWraper;

    private StringRequest mStringRequest;

    /**
     * volley底层连接和读取超时时间是一样的
     */
    private int mConnectionTimeOut = 30 * 1000;

//    public interface HttpError {
//        int UNKNOWN = 0;
//        int CANCEl = 1;
//        int REPEAT = 2;
//        int TIMEOUT = 3;
//        int LOCALERROR = 4;
//        int SERVERERROR = 5;
//    }

    /**
     * NULL_VALUE服务器正常返回但结果为空
     */
    public enum  HttpError {
        UNKNOWN, CANCEl, REPEAT, TIMEOUT, LOCALERROR, SERVERERROR, NOCONNECTION, NETWORK_ERROR, PARSE_ERROR,NULL_VALUE
    }

    public void setConnectionTimeOut(int mConnectionTimeOut) {
        this.mConnectionTimeOut = mConnectionTimeOut;
    }

    public HttpRequest() {
        this(GET, null, null, null);
    }

    public HttpRequest(int method, String url, Map<String, String> params, Map<String, String> headers) {
        mMethod = method;
        mUrl = url;
        mParams = params;
        mHeaders = headers;
    }


    public HttpRequest(int method, String url, Map<String, String> params,
                       Map<String, String> headers, HttpContextWrapper httpContextWraper) {
        mMethod = method;
        mUrl = url;
        mParams = params;
        mHeaders = headers;
        if (httpContextWraper == null) {
            throw new IllegalArgumentException("HttpContextWrapper must not be null");
        }
        mHttpContextWraper = httpContextWraper;
    }

    public void setHttpRequestListener(HttpRequestListener httpRequestListener) {
        mHttpRequestListener = httpRequestListener;
    }

    public HttpRequestListener getHttpRequestListener() {
        return mHttpRequestListener;
    }

//    public void setRequestQueue(RequestQueue requestQueue) {
//        mRequestQueue = requestQueue;
//    }

//    public RequestQueue getRequestQueue() {
//        return mRequestQueue;
//    }

    public void setMethod(int mMethod) {
        this.mMethod = mMethod;
    }

    public int getMethod() {
        return mMethod;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setHeaders(Map<String, String> headers) {
        mHeaders = headers;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public void setParams(Map<String, String> params) {
        mParams = params;
    }

    public Map<String, String> getParams() {
        return mParams;
    }


    public void start() {
        // 处理重复请求
        if (mStringRequest != null) {
            cancelRequest(HttpError.REPEAT);
        }

        if (mUrl == null)
            return;
        if(mMethod == Request.Method.GET) {
            mUrl = handleUrl(mUrl, mParams);
        }

        mStringRequest = new StringRequest(mMethod, mUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (shouldCancel())
                    return;
                if (mHttpRequestListener != null) {
                    if(TextUtils.isEmpty(response)) {
                        HttpError error = HttpError.NULL_VALUE;
                        mHttpRequestListener.onError(error);
                    } else {
                        mHttpRequestListener.onSuccess(response);
                    }
                }
                mStringRequest = null;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (shouldCancel())
                    return;
                if (mHttpRequestListener != null) {
                    HttpError httpError = HttpError.UNKNOWN;
                    if (error != null) {
                        if(error instanceof ParseError){
                            httpError = HttpError.PARSE_ERROR;
                        }else if(error instanceof NetworkError){
                            httpError = HttpError.NETWORK_ERROR;
                        }else if(error instanceof ServerError){
                            httpError = HttpError.SERVERERROR;
                        }else if(error instanceof NoConnectionError){
                            httpError = HttpError.NOCONNECTION;
                        }else if(error instanceof TimeoutError){
                            httpError = HttpError.TIMEOUT;
                        }
                    }
                    mHttpRequestListener.onError(httpError);
                }
                mStringRequest = null;
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return mParams;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if(mHeaders == null) {
                    return super.getHeaders();
                }
                return mHeaders;
            }

            @Override
            public RetryPolicy getRetryPolicy() {
                DefaultRetryPolicy policy = new DefaultRetryPolicy(mConnectionTimeOut, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                return policy;
            }
        };
        mStringRequest.setShouldCache(false);
        HttpRequestQueue.getRequestQueue().add(mStringRequest);
    }

    public void cancel() {
        cancelRequest(HttpError.CANCEl);
    }

    private void cancelRequest(HttpError error) {
        if (mStringRequest != null) {
            mStringRequest.cancel();
            mStringRequest = null;
            if (mHttpRequestListener != null) {
                mHttpRequestListener.onError(error);
            }
        }
    }

    private boolean shouldCancel() {
        if (mHttpContextWraper == null) {
            return false;
        } else {
            Context context = mHttpContextWraper.getContext();
            Activity activity = null;
            if (context != null && context instanceof Activity) {
                activity = (Activity)context;
            }
            if (activity == null || activity.isFinishing()) {
                return true;
            }

        }
        return false;
    }

    /**
     * 拼接url和请求参数(GET请求)
     * @param url
     * @param params
     * @return
     */
    private String handleUrl(String url, Map<String, String> params){
        String param = getParam(params);
        param = !TextUtils.isEmpty(param) ? "?" + param : "";
        return url+param;
    }

    /**
     * 组织get请求参数
     *
     * @param params
     * @return
     */
    private String getParam(Map<String, String> params) {
        if (params != null) {
            LinkedList<BasicNameValuePair> paramList = new LinkedList<BasicNameValuePair>();
            Set<String> keys = params.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                paramList.add(new BasicNameValuePair(key, params.get(key)));
                Log.d("HttpRequest", "参数:" + "key ：" + key + "    value ：" + params.get(key));
            }
            return URLEncodedUtils.format(paramList, "UTF-8");
        }
        return null;
    }

    public interface HttpRequestListener<T> {
        public void onSuccess(T response);
        public void onError(HttpError error);
//        public void onReceiving();
//        public void onSending();
    }
}
