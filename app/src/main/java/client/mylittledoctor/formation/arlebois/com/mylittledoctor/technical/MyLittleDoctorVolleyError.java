package client.mylittledoctor.formation.arlebois.com.mylittledoctor.technical;

import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import java.io.UnsupportedEncodingException;

public class MyLittleDoctorVolleyError implements Response.ErrorListener {

    int statusCode;
    String errorMessage;

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error instanceof TimeoutError) {
            statusCode = 0;
            errorMessage = "Timeout";
        } else {
            statusCode = error.networkResponse.statusCode;
            try {
                errorMessage = new String(error.networkResponse.data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }


    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
