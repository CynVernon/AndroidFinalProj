// BearModel.java
package algonquin.cst2335.androidfinalproj.bearimages;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BearModel extends AndroidViewModel {

    private BearImage bearImageDao;
    private LiveData<List<BearImageDatabase>> allBearImages;

    public BearModel(@NonNull Application application) {
        super(application);
        BearImageDatabase db = BearImageDatabase.getInstance(application);
       //  bearImageDao = db.bearImageDao();
        allBearImages = bearImageDao.getAllBearImages();
    }

    public LiveData<List<BearImageDatabase>> getAllBearImages() {
        return allBearImages;
    }

    public void insertBearImage(BearImageDatabase bearImage) {
        BearImageDatabase.databaseWriteExecutor.execute(() -> {
            bearImageDao.insertBearImage(bearImage);
        });
    }

    public void deleteBearImage(BearImageDatabase bearImage) {
        BearImageDatabase.databaseWriteExecutor.execute(() -> {
            bearImageDao.deleteBearImage(bearImage);
        });
    }
}
