package forlife.codreg;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.*;
import java.util.List;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class DiplomatActivity extends MainActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView lv;
    ArrayAdapter<String> adapter;
    EditText inputSearch;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=forlife.codreg"));
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-4853840433535525~9597335690");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("41FE06C5095D33AA8AB2992C6DB34652").build();
        mAdView.loadAd(adRequest);

        TextView myTextView = (TextView) findViewById(R.id.textViewZ);
        assert myTextView != null;
        myTextView.setText("Дипломатические номера");

        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.dip48));


        // получаем экземпляр элемента ListView
        lv = (ListView) findViewById(R.id.listView);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        String[] diplomat = getResources().getStringArray(R.array.diplomat);

        adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.product_name, diplomat);
        lv.setAdapter(adapter);


        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // Когда, юзер изменяет текст он работает
                DiplomatActivity.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // ODO Auto-generated method stub
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                      @Override
                                      public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                                              long id) {
                                          //  TextView textView = (TextView) itemClicked;
                                          TextView textView = (TextView) lv.findViewById(R.id.product_name);

                                          Resources res = getResources();
                                          String[] reg = res.getStringArray(R.array.diplomat);
                                          for (int j=0; j<155; j++) {
                                              if (position== j) {
                                                  // Запускаем активность
                                                  Uri uri = Uri.parse("geo:0,0?q="+reg[j].substring(6));
                                                  Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                  PackageManager packageManager = getPackageManager();
                                                  List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
                                                  boolean isIntentSafe = activities.size() > 0;
                                                  if (isIntentSafe) {
                                                      startActivity(intent);
                                                  }

                                              }
                                          }
                                      }
                                  }
        );

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(DiplomatActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(DiplomatActivity.this, MilitaryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

            Intent intent = new Intent(DiplomatActivity.this, DiplomatActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

