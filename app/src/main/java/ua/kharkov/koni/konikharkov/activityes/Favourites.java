package ua.kharkov.koni.konikharkov.activityes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import ua.kharkov.koni.konikharkov.entity.Amortizator;
import ua.kharkov.koni.konikharkov.adapter.AmortizatorsAdapter;
import ua.kharkov.koni.konikharkov.R;
import ua.kharkov.koni.konikharkov.greenDAO.AmortizatorDao;
import ua.kharkov.koni.konikharkov.greenDAO.DaoSession;
import ua.kharkov.koni.konikharkov.helper.DaoHelper;

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

        amortizatorsQuery = mAmortizatorDao.queryBuilder().orderAsc(AmortizatorDao.Properties.Marka_name, AmortizatorDao.Properties.Model_name, AmortizatorDao.Properties.Car_name, AmortizatorDao.Properties.Art_number).build();
        updateAmortizators();

        recyclerView = findViewById(R.id.recyclerviewfav);
        GridLayoutManager gridLayout = new GridLayoutManager(this, 1);  //(объект, количество колонок)
        //LinearLayoutManager gridLayout = new LinearLayoutManager(this); //(объект, количество колонок)
        recyclerView.setLayoutManager(gridLayout);

        amortizators = new ArrayList<>();

        recyclerView.setAdapter(adapter);
    }
    public void updateAmortizators() {
        amortizators = amortizatorsQuery.list();
        adapter = new AmortizatorsAdapter(this, amortizators);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //заставляем кнопку home работать как надо
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == R.id.menu_delete) {
            //Диалог подтвердения удаения всех данных из избранного
            new AlertDialog.Builder(this)
                    .setTitle("Очистить избранное?")
                    .setMessage("Выдействительно хотите очистить избранное?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            mAmortizatorDao.deleteAll(); //очистить всё избранное
                            recreate(); //пересоздаём активити
                        }
                    }).create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
