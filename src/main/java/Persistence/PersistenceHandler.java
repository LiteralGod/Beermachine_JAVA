package Persistence;

import Domain.Batch;
import Domain.BeerType;
import Domain.DefaultProduct;
import Domain.IPersistenceHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistenceHandler implements IPersistenceHandler {
    private static ConnectionHandler connectionHandler;
    private static PersistenceHandler instance;

    public PersistenceHandler() {
        connectionHandler = ConnectionHandler.getInstance();
    }

    public static PersistenceHandler getInstance() {
        if (instance == null) {
            instance = new PersistenceHandler();
        }
        return instance;
    }

    @Override
    public List<BeerType> queryAllBeerTypes() {
        try {
            PreparedStatement stmt = connectionHandler.getConnection().prepareStatement(
                    "SELECT * FROM beer_type"
            );
            ResultSet sqlReturnValues = stmt.executeQuery();

            List<BeerType> returnValue = new ArrayList<>();

            while (sqlReturnValues.next()){
                returnValue.add(new BeerType(
                        sqlReturnValues.getString(2)));
            }
            return returnValue;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Batch> queryAllBatches() {
        try {
            PreparedStatement stmt = connectionHandler.getConnection().prepareStatement(
                    "SELECT * FROM batches"
            );
            ResultSet sqlReturnValues = stmt.executeQuery();

            List<Batch> returnValue = new ArrayList<>();

            while (sqlReturnValues.next()){
                returnValue.add(new Batch(
                        sqlReturnValues.getInt(1),
                        sqlReturnValues.getString(2),
                        sqlReturnValues.getInt(3),
                        sqlReturnValues.getInt(4),
                        sqlReturnValues.getInt(5)));
            }
            return returnValue;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public void insertBatch(int currentBatchID, String productName, int totalAmount, int totalGood, int totalBad) {
        try {
            PreparedStatement stmt = connectionHandler.getConnection().prepareStatement(
                    "INSERT INTO batches(batchID, productName, totalAmount, goodAmount, badAmount)" +
                            "VALUES (?, ?, ?, ?, ?)");

            stmt.setInt(1, currentBatchID);
            stmt.setString(2, productName);
            stmt.setInt(3, totalAmount);
            stmt.setInt(4, totalGood);
            stmt.setInt(5, totalBad);

            stmt.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void deleteBatch(int batchID) {
        try {
            PreparedStatement stmt = connectionHandler.getConnection().prepareStatement(
                    "DELETE FROM batches WHERE ID = ?");

            stmt.setInt(1, batchID);

            stmt.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public BeerType getBeerType(int id){
        try {
            PreparedStatement stmt = connectionHandler.getConnection().prepareStatement(
                    "SELECT name FROM beer_type WHERE id = ?"
            );
            stmt.setInt(1, id);

            ResultSet sqlReturnValues = stmt.executeQuery();

            if (!sqlReturnValues.next()) {
                return null;
            }

            return new BeerType(
                    sqlReturnValues.getString(1)
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


    @Override
    public List<DefaultProduct> queryAllDefaultProducts() {
        try {
            PreparedStatement stmt = connectionHandler.getConnection().prepareStatement(
                    "SELECT * FROM default_product"
            );
            ResultSet sqlReturnValues = stmt.executeQuery();

            List<DefaultProduct> returnValue = new ArrayList<>();

            while (sqlReturnValues.next()){
                returnValue.add(new DefaultProduct(
                        getBeerType(sqlReturnValues.getInt(1)),
                        sqlReturnValues.getInt(3),
                        sqlReturnValues.getInt(4)));
            }
            return returnValue;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}
