package grey.example.catsfacts.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.catsfacts.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setSupportActionBar(findViewById(R.id.app_bar));
        ProgressBar mProgress = findViewById(R.id.progressBar);
        MainViewModel mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.getIsProgress().observe(this, progress -> {
            if(progress)
                mProgress.setVisibility(View.VISIBLE);
            else
                mProgress.setVisibility(View.GONE);
        });
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commit();
        }
    }
}