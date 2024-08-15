package com.oioioihi.ootd.repository;

import com.oioioihi.ootd.model.dao.ProductDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductRepositoryJdbcTemplateImpl implements ProductRepositoryJdbcTemplate {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<ProductDao> getMinPriceProducts() {
        final String MIN_PRICE_PRODUCT_BY_CATEGORY =
                "SELECT DISTINCT ON (c.id) " +
                        "    b.name AS brand_name, " +
                        "    c.name AS category_name, " +
                        "    c.id AS category_id, " +
                        "    p.price " +
                        "FROM " +
                        "    product AS p " +
                        "    JOIN category AS c ON c.id = p.category_id " +
                        "    JOIN brand AS b ON b.id = p.brand_id " +
                        "ORDER BY " +
                        "    p.price," +
                        "    b.name desc";

        return jdbcTemplate.query(MIN_PRICE_PRODUCT_BY_CATEGORY, new ProductInfoRowMapper());
    }

    private static class ProductInfoRowMapper implements RowMapper<ProductDao> {
        @Override
        public ProductDao mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProductDao productInfo = new ProductDao();
            return productInfo.toBuilder()
                    .categoryId(rs.getLong("category_id"))
                    .brand(rs.getString("brand_name"))
                    .category(rs.getString("category_name"))
                    .price(rs.getLong("price"))
                    .build();

        }
    }
}
