package com.example.casatorii;

import java.util.List;

public class MarriageDataSource implements MarriagesDao {

    private final MarriagesDao marriagesDao;

    public MarriageDataSource(MarriagesDao marriagesDao) {
        this.marriagesDao = marriagesDao;
    }


    @Override
    public MarriageDao getMarriage(String nume) {
        return marriagesDao.getMarriage(nume);
    }

    @Override
    public void insertMarriage(MarriageDao marriageDao) {
        marriagesDao.insertMarriage(marriageDao);
    }

    @Override
    public void deleteAllMarriages() {
        return;
    }

}
