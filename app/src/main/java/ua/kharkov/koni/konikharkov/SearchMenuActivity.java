package ua.kharkov.koni.konikharkov;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by Воронцовы on 31.10.2017.
 */

public abstract class SearchMenuActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        final MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_view_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query){
                //MenuItemCompat.collapseActionView(searchMenuItem);
                onQuerySubmit(query);
                return true;
            }
            @Override
            //public boolean onQueryTextChange(String newText){
                //return false;
            //}
            public boolean onQueryTextChange(String newText){
                //MenuItemCompat.collapseActionView(searchMenuItem);
                onQuerySubmit(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    protected abstract void onQuerySubmit(String query);
}
