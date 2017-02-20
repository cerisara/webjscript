package fr.xtof54.webjscript;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.view.View;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;

public class WebJscript extends Activity
{
    WebView wv;

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
        initwebview();
        final Button button = (Button)findViewById(R.id.b1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv.loadUrl("javascript:document.body.innerHTML='MMMMMMMMMM';");
            }
        });
    }
    private void initwebview() {
        try {
            File ff= new File(getFilesDir()+"/tt.html");
            System.out.println("OOOOOOOOOO "+ff.getAbsolutePath());
            PrintWriter f = new PrintWriter(new FileWriter(ff));
            f.println("<html>");
            f.println("<script>");
            f.println("function t() {document.body.innerHTML='KKKKKLLLLL';}");
            f.println("</script>");
            f.println("<body onload='t();'>");
            f.println("AOKOKOK");
            f.println("</body>");
            f.println("</html>");
            f.close();
            wv.loadUrl("file:///"+ff.getAbsolutePath());
        } catch (Exception e) {e.printStackTrace();}
    }
	private class myWebViewClient extends WebViewClient {
		@Override
		public void onPageFinished(final WebView view, String url) {
		    System.out.println("page finished loading");
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
}
