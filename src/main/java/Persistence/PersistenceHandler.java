package Persistence;

import Domain.BeerType;
import Domain.IPersistenceHandler;

import javax.xml.transform.Result;
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
}
