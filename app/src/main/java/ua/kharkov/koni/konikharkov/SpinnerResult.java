package ua.kharkov.koni.konikharkov;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SpinnerResult extends SearchActivity {

    private List<Amortizator> amortizators;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayout;
    private AmortizatorsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_spinner_result);
        setContentView(R.layout.activity_spinner_result);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        //amortizators = new ArrayList<>();
        amortizators = (ArrayList<Amortizator>)getIntent().getSerializableExtra("AMORTIZATORS_LIST");
        gridLayout = new GridLayoutManager(this, 1);  //(объект, количество колонок)
        recyclerView.setLayoutManager(gridLayout);

        adapter = new AmortizatorsAdapter(this, amortizators);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
