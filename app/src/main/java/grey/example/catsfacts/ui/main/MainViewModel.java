package grey.example.catsfacts.ui.main;

import android.app.Application;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.catsfacts.R;

import java.util.ArrayList;
import java.util.List;

import grey.example.catsfacts.database.FactDatabase;
import grey.example.catsfacts.model.Fact;
import grey.example.catsfacts.network.Loader;
import grey.example.catsfacts.utils.ImageFileUtils;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class MainViewModel extends AndroidViewModel {

    List<Fact> mFactList = new ArrayList<>();
    MutableLiveData<Integer> size = new MutableLiveData<>(mFactList.size());
    MutableLiveData<Boolean> isProgress = new MutableLiveData<>(false);
    FactDatabase db;

    public MainViewModel(Application application) {
        super(application);

        db = Room.databaseBuilder(getApplication(), FactDatabase.class, "facts.db")
                .build();
        db.factDao().getFacts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(facts -> {
                    mFactList = facts;
                    size.setValue(mFactList.size());
                });
    }

    public LiveData<Integer> getFactListSize() {
        return size;
    }

    public LiveData<Boolean> getIsProgress() {
        return isProgress;
    }

    public List<Fact> getFactList() {
        return mFactList;
    }

    public void addFact() {
        isProgress.setValue(true);
        Single<Bitmap> getImage = Single.fromCallable(Loader::getImage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Single<String> getText = Single.fromCallable(Loader::getFact)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        Single.zip(getText, getImage,
                (text, image) -> {
                    return new Fact(text, ImageFileUtils.saveImage(getApplication(), image));
                }).subscribe(fact -> {
                    db.factDao().insertFact(fact)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(id -> {
                                fact.setId(id);
                                mFactList.add(0, fact);
                                size.setValue(mFactList.size());
                                isProgress.setValue(false);
                            });
                },
                e -> Toast.makeText(getApplication(), R.string.error_data_service, Toast.LENGTH_SHORT).show());
    }

    public void deleteItemEvent(int position) {
        Fact fact = mFactList.get(position);
        ImageFileUtils.deleteImage(getApplication(), fact.getImagePath());
        db.factDao().deleteFact(fact)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            mFactList.remove(position);
                            size.setValue(size.getValue() - 1);
                            Toast.makeText(getApplication(), R.string.fact_deleted, Toast.LENGTH_SHORT).show();
                        },
                        e -> Toast.makeText(getApplication(), R.string.error_deleting, Toast.LENGTH_SHORT).show());
    }
}