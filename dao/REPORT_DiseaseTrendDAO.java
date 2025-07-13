package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.REPORT_DiseaseTrend;

public class REPORT_DiseaseTrendDAO {

    private Connection connection;

    public REPORT_DiseaseTrendDAO(Connection connection) {
        this.connection = connection;
    }

    public List<REPORT_DiseaseTrend> getDiseaseTrendsByPeriod(String periodPattern) throws SQLException {
        String sql = "SELECT disease_name, COUNT(*) AS frequency " +
                     "FROM Diagnosis " +
                     "WHERE diagnosis_date LIKE ? " +
                     "GROUP BY disease_name";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, periodPattern + "%"); // Accepts year, month, or day

        ResultSet rs = ps.executeQuery();
        List<REPORT_DiseaseTrend> trends = new ArrayList<>();

        while (rs.next()) {
            String disease = rs.getString("disease_name");
            int count = rs.getInt("frequency");

            trends.add(new REPORT_DiseaseTrend(disease, count, periodPattern));
        }

        return trends;
    }
}
