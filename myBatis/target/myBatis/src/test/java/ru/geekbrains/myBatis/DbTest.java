package ru.geekbrains.myBatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.geekbrains.myBatis.db.dao.ProductsMapper;
import ru.geekbrains.myBatis.db.model.Products;
import ru.geekbrains.myBatis.db.model.ProductsExample;

import java.io.IOException;
import java.util.List;

public class DbTest {

    @Test
    @DisplayName("Проверка сущности из первой категории")
    void testCheckItemFirstCategory() throws IOException {
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("myBatisConfig.xml"));
        try (SqlSession session = sessionFactory.openSession()) {
            ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);
            Products product = productsMapper.selectByPrimaryKey(35L);
            Assertions.assertEquals("Cactus", product.getTitle());
            Assertions.assertEquals(600, product.getPrice());
            Assertions.assertEquals(1, product.getCategoryId());
        }
    }

    @Test
    @DisplayName("Проверка сущности из второй категории")
    void testCheckItemSecondCategory() throws IOException {
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("myBatisConfig.xml"));
        try (SqlSession session = sessionFactory.openSession()) {
            ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);
            Products product = productsMapper.selectByPrimaryKey(130L);
            Assertions.assertEquals("Vacuum cleaner Bosh ZAS8000", product.getTitle());
            Assertions.assertEquals(19300, product.getPrice());
            Assertions.assertEquals(2, product.getCategoryId());
        }
    }

    @Test
    @DisplayName("Поиск всех сущностей с ID < 100")
    void testSearchAllWithIdLessThan100() throws IOException {
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("myBatisConfig.xml"));
        try (SqlSession session = sessionFactory.openSession()) {
            ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);
            ProductsExample example = new ProductsExample();
            example.createCriteria()
                    .andIdBetween(1L, 100L);

            List<Products> products = productsMapper.selectByExample(example);
            System.out.println(products);

            example.clear();
        }
    }

    @Test
    @DisplayName("Изменение сущности")
    void testPutById() throws IOException {
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("myBatisConfig.xml"));
        try (SqlSession session = sessionFactory.openSession()) {
            ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);
            Products product = productsMapper.selectByPrimaryKey(351L);
            product.setTitle("TestFood");
            product.setPrice(99);
            Assertions.assertEquals("TestFood", product.getTitle());
            Assertions.assertEquals(99, product.getPrice());
        }
    }

    @Test
    @DisplayName("Удаление сущности")
    void testDeleteById() throws IOException {
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("myBatisConfig.xml"));
        try (SqlSession session = sessionFactory.openSession()) {
            ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);
            productsMapper.deleteByPrimaryKey(352L);
        }
    }
}