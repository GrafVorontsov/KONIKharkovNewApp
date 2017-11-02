package ua.kharkov.koni.konikharkov;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public abstract class SearchMenuActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        final MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query){
                MenuItemCompat.collapseActionView(searchMenuItem);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText){
                //MenuItemCompat.collapseActionView(searchMenuItem);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private SearchView searchView;

    protected abstract void onQuerySubmit(String query);
}
