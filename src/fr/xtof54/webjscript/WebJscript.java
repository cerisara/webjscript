package fr.xtof54.webjscript;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;

public class WebJscript extends Activity
{
    WebView wv;
    Handler handlerForJavascriptInterface = new Handler();
    String htmlcontent="rien";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		wv = (WebView)findViewById(R.id.wv);
		wv.setWebViewClient(new myWebViewClient());
		wv.getSettings().setSupportZoom(true);
		wv.getSettings().setJavaScriptEnabled(true);
        wv.addJavascriptInterface(new MyJavaScriptInterface(this), "HtmlViewer");


        final Button button = (Button)findViewById(R.id.b1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputs();
            }
        });
    }
	void showMessage(final String txt) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getBaseContext(), txt, Toast.LENGTH_LONG).show();
			}
		});
	}

    private void showInputs() {
        showMessage(htmlcontent);
    }
	private class myWebViewClient extends WebViewClient {
		@Override
		public void onPageFinished(final WebView view, String url) {
            webview.loadUrl("javascript:window.HtmlViewer.showHTML" +
                "('&lt;html&gt;'+document.getElementsByTagName('html')[0].innerHTML+'&lt;/html&gt;');");
		}
		
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			System.out.println("mywebclient detecting command from javascript: "+url);
			if (false) {
                // dont let the webview navigate
                return true;
            } else {
				// let the browser navigate normally
				return false;
			}
		}
	}
    private class MyJavaScriptInterface {
        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void showHTML(String html) {
            final String html_ = html;
            //code to use html content here
            handlerForJavascriptInterface.post(new Runnable() {
                @Override
                public void run() {
                    htmlcontent = ""+html_;
            }});
        }
    }
}
