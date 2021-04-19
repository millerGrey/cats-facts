package grey.example.catsfacts.ui.main;

import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.catsfacts.R;
import com.example.catsfacts.databinding.MainFragmentBinding;

import grey.example.catsfacts.utils.FactAdapter;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class MainFragment extends Fragment {

    MainViewModel mViewModel;
    FactAdapter mAdapter;
    Boolean mIsProgress;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MainFragmentBinding binding = MainFragmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mAdapter = new FactAdapter(mViewModel);
        binding.setViewModel(mViewModel);
        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.requireActivity()));
        binding.setLifecycleOwner(this.requireActivity());
        mViewModel.isProgress.observe(requireActivity(), isProgress -> {
            mIsProgress = isProgress;
            requireActivity().invalidateOptionsMenu();
        });
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main_fragment, menu);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.getFact).setVisible(!mIsProgress);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.getFact) {
            ConnectivityManager cm =
                    (ConnectivityManager) requireActivity().getSystemService(CONNECTIVITY_SERVICE);
            if ((cm.getActiveNetworkInfo() != null) && cm.getActiveNetworkInfo().isConnected()) {
                mViewModel.addFact();
            } else {
                Toast.makeText(requireActivity(), getResources().getString(R.string.network_not_available), Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}