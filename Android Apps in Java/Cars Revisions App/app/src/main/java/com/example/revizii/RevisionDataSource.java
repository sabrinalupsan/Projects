package com.example.revizii;


public class RevisionDataSource implements RevisionsDao {

    private final RevisionsDao revisionsDAO;

    public RevisionDataSource(RevisionsDao mRevisionsDao) {
        this.revisionsDAO = mRevisionsDao;
    }

    @Override
    public RevisionDAO getRevision(int price) {
        return null;
    }

    @Override
    public void insertRevision(RevisionDAO revision) {
        revisionsDAO.insertRevision(revision);
    }

    @Override
    public void deleteAllRevisions() {
        return;
    }
}
