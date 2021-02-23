package com.example.examenbiler2;

import androidx.room.Update;

public class LivrareDataSource implements LivrariDao {

    private final LivrariDao mLivrariDao;

    public LivrareDataSource(LivrariDao mLivrariDao) {
        this.mLivrariDao = mLivrariDao;
    }

    @Override
    public LivrareDao getUser(String destinatar2, String adresa2) {
        return mLivrariDao.getUser(destinatar2, adresa2);
    }

    @Override
    public void insertUser(LivrareDao livrareDao) {
        mLivrariDao.insertUser(livrareDao);
    }

    @Override
    public void deleteAllLivrari() {
        return;
    }
}
