package nl.psdcompany.duonavigationdrawer.example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.views.DuoMenuView;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;
/*import com.example.lin.a312.utils.music;
import com.example.lin.a312.adapter.Myadapter;*/

import android.content.Intent;


public class MainActivity extends AppCompatActivity implements DuoMenuView.OnMenuClickListener {
    private MenuAdapter mMenuAdapter;
    private ViewHolder mViewHolder;

    private ArrayList<String> mTitles = new ArrayList<>();

     FragmentManager manager;//NEW
     FragmentTransaction transaction;//NEW

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();//NEW

        mTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.menuOptions)));

        // Initialize the views
        mViewHolder = new ViewHolder();

        // Handle toolbar actions
        handleToolbar();

        // Handle menu actions
        handleMenu();

        // Handle drawer actions
        handleDrawer();

        // Show main fragment in container
        goToFragment(new MainFragment(), false);
        mMenuAdapter.setViewSelected(0, true);
        setTitle(mTitles.get(0));
    }

    private void handleToolbar() {
        setSupportActionBar(mViewHolder.mToolbar);
    }

    private void handleDrawer() {
        DuoDrawerToggle duoDrawerToggle = new DuoDrawerToggle(this,
                mViewHolder.mDuoDrawerLayout,
                mViewHolder.mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        mViewHolder.mDuoDrawerLayout.setDrawerListener(duoDrawerToggle);
        duoDrawerToggle.syncState();

    }

    private void handleMenu() {
        mMenuAdapter = new MenuAdapter(mTitles);

        mViewHolder.mDuoMenuView.setOnMenuClickListener(this);
        mViewHolder.mDuoMenuView.setAdapter(mMenuAdapter);
    }

    @Override
    public void onFooterClicked() {
        Toast.makeText(this, "onFooterClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHeaderClicked() {
        Toast.makeText(this, "onHeaderClicked", Toast.LENGTH_SHORT).show();
    }

    private void goToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.add(R.id.container, fragment).commit();
    }



    @Override
    public void onOptionClicked(int position, Object objectClicked) {
        // Set the toolbar title
        setTitle(mTitles.get(position));

        // Set the right options selected
        mMenuAdapter.setViewSelected(position, true);


        transaction = manager.beginTransaction();

        switch (position){ //NEW
            case 0:
                MainFragment mainFragment = new MainFragment();
                transaction.replace(R.id.container,mainFragment);
                break;
            case 1:
                BlankFragment_main1 blankFragment_main1 = new BlankFragment_main1();
                transaction.replace(R.id.container,blankFragment_main1);
                break;
            case 2:
                SmartHomeFragment smartHomeFragment = new SmartHomeFragment();
                transaction.replace(R.id.container,smartHomeFragment);
                break;
            case 3:
                Intent activityIntent = new Intent();
                activityIntent.setAction("com.farwifi");//自訂的 action name
                startActivity(activityIntent);
        }



//        switch (position) {
//            case 0:
//                Intent intent = new Intent(MainActivity.this, Media.class);
//                startActivity(intent);
//                break;
//
//            //And other your activities
//        }
//         Navigate to the right fragment
//        switch (position) {
//            default:
//                goToFragment(new MainFragment(), false);
//                break;
//        }

        // Close the drawer
        mViewHolder.mDuoDrawerLayout.closeDrawer();
        transaction.commit();//NEW
    }

    private class ViewHolder {
        private DuoDrawerLayout mDuoDrawerLayout;
        private DuoMenuView mDuoMenuView;
        private Toolbar mToolbar;

        ViewHolder() {
            mDuoDrawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
            mDuoMenuView = (DuoMenuView) mDuoDrawerLayout.getMenuView();
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
        }
    }
}
