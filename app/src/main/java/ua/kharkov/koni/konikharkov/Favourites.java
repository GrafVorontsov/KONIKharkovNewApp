package ua.kharkov.koni.konikharkov;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

public class Favourites extends AppCompatActivity {
    private List<Amortizator> amortizators;
    private AmortizatorsAdapter adapter;
    RecyclerView recyclerView;

    private AmortizatorDao mAmortizatorDao;
    private Query<Amortizator> amortizatorsQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        //добавляем кнопку назад
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.favourites);
        }

        DaoSession daoSession = DaoHelper.getInstance(this).getDaoSession();
        mAmortizatorDao = daoSession.getAmortizatorDao();

        amortizatorsQuery = mAmortizatorDao.queryBuilder().orderAsc(AmortizatorDao.Properties.Art_number).build();
        updateAmortizators();

        recyclerView = findViewById(R.id.recyclerviewfav);
        GridLayoutManager gridLayout = new GridLayoutManager(this, 1);  //(объект, количество колонок)
        recyclerView.setLayoutManager(gridLayout);

        amortizators = new ArrayList<>();

        recyclerView.setAdapter(adapter);
        //recyclerView.invalidateViews();
        //adapter.notifyItemRangeChanged(0, amortizators.size());
        //mAmortizatorDao.deleteAll(); //очистить всё

    }
    public void updateAmortizators() {
        amortizators = amortizatorsQuery.list();
        adapter = new AmortizatorsAdapter(this, amortizators);
    }

    //заставляем кнопку home работать как надо
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
