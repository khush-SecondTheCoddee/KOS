package android.app;
import android.os.Bundle; import android.content.ContentResolver;
public class Activity extends android.content.Context { public void onCreate(Bundle b) {} public void requestPermissions(String[] p,int r) {} public ContentResolver getContentResolver(){return new ContentResolver();} public Object getSystemService(String n){return null;} public void setContentView(Object v){} public void runOnUiThread(Runnable r){r.run();} protected void onDestroy(){} }
