package com.pansaian.volleydemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    RequestQueue mQueue;

    @Bind(R.id.stringRequest)
    Button stringRequest;
    @Bind(R.id.stringResult)
    TextView stringResult;
    @Bind(R.id.jsonObjectRequest)
    Button jsonObjectRequest;
    @Bind(R.id.jsonArrayRequest)
    Button jsonArrayRequest;
    @Bind(R.id.imageRequest)
    Button imageRequest;
    @Bind(R.id.imageResult)
    ImageView imageResult;
    @Bind(R.id.imageLoader)
    Button imageLoader;
    @Bind(R.id.stringRequestPost)
    Button stringRequestPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mQueue = Volley.newRequestQueue(this);
    }

    @OnClick({R.id.stringRequest, R.id.jsonObjectRequest, R.id.jsonArrayRequest,
            R.id.imageRequest, R.id.imageLoader,R.id.stringRequestPost})
    public void onClick(View view) {
        switch (view.getId()) {
            /*Get方式获取字符串*/
            case R.id.stringRequest:
                String url="http://192.168.1.137:8080/myweb/index.html";
                StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        imageResult.setVisibility(View.INVISIBLE);
                        stringResult.setVisibility(View.VISIBLE);
                        String result_str = response;
                        stringResult.setText(result_str);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
                mQueue.add(stringRequest);
                break;
            /*post方式获取字符串*/
            case R.id.stringRequestPost:
                String  url2="http://192.168.1.137:8080/myweb/post.html";
                StringRequest stringRequestPost=new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        imageResult.setVisibility(View.INVISIBLE);
                        stringResult.setVisibility(View.VISIBLE);
                        String result_str = response;
                        stringResult.setText(result_str);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map=new HashMap<>();
                        map.put("param","value");
                        return map;
                    }
                };
                mQueue.add(stringRequestPost);
                break;
                /*jsonObjectRequest解析json数据*/
            case R.id.jsonObjectRequest:
                String url3="http://192.168.1.137:8080/myweb/aa.html";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url3,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                imageResult.setVisibility(View.INVISIBLE);
                                stringResult.setVisibility(View.VISIBLE);
                                String str = response.toString();
                                String result_json = null;
                                try {
                                    /*设置编码*/
                                    result_json = new String(str.getBytes("ISO_8859_1"), "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                stringResult.setText(result_json);
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
                mQueue.add(jsonObjectRequest);
                break;
            /*解析JSON数组*/
            case R.id.jsonArrayRequest:
                String url4="http://192.168.1.137:8080/myweb/json.html";
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url4,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                imageResult.setVisibility(View.INVISIBLE);
                                stringResult.setVisibility(View.VISIBLE);
                                String str = response.toString();
                                String result_json = null;
                                try {
                                    result_json = new String(str.getBytes("utf-8"), "utf-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                stringResult.setText(result_json);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
                mQueue.add(jsonArrayRequest);
                break;
            /*请求图片*/
            case R.id.imageRequest:
                String url5="http://teleporter.top/img/关羽.jpg";
                ImageRequest imageRequest = new ImageRequest(url5, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        stringResult.setVisibility(View.INVISIBLE);
                        imageResult.setVisibility(View.VISIBLE);
                        imageResult.setImageBitmap(response);
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        stringResult.setVisibility(View.INVISIBLE);
                        imageResult.setVisibility(View.VISIBLE);
                        imageResult.setImageResource(R.drawable.img_err);
                    }
                });
                mQueue.add(imageRequest);
                break;
            /*用imageLoader加载图片，设置图像缓存*/
            case R.id.imageLoader:
                String url6="http://teleporter.top/img/关羽.jpg";
                stringResult.setVisibility(View.INVISIBLE);
                imageResult.setVisibility(View.VISIBLE);
                ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
                    @Override
                    public Bitmap getBitmap(String url) {
                        return null;
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                    }
                });
                ImageLoader.ImageListener listener = imageLoader.getImageListener(imageResult, R.drawable.img_err, R.drawable.img_err);
                imageLoader.get(url6, listener);
                break;
            default:
                break;
        }
    }
}
