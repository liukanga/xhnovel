package com.ziqing.xhnovel.test;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlTest {

    public static void main(String[] args) {
        /*DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://xxxx:3306/test");
        dataSource.setUsername("hpn");
        dataSource.setPassword("hpn2017");
        //常见JdbcTemplate对象，设置数据源
        JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
        //设置sql语句
        String sql="select * from test2";
        List list=jdbcTemplate.query(sql, new RowMapper(String.class) {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                return null;
            }
        });
        System.out.println(list);*/

        /*for (int i=0;i<11;i++){
          test();
        }*/

        /*Map<String, String> map = new HashMap<>();

        map.put("1", "2");
        map.put("1", "2");

        System.out.println("map.size="+map.size());
        System.out.println("map="+map);*/

        test();

    }

    public static int test(){
        Set<String> set = new HashSet<>();
        Map<String, String> map = new HashMap<>();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 21; i++) {
                map.put(i + "", i + "");
                set.add(i+"");
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 21; i < 40; i++) {
                map.put(i + "", i + "");
                set.add(i+"");
            }
        });

        t1.start();
        t2.start();

        System.out.println("--------------------------");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("");
        }
        for (int i = 0; i < 40; i++) {
            String str = i+"";
            System.out.println("key-----------value-----------isContain");
            System.out.println(str+"-------------"+map.get(i+"")+"-------------"+map.containsKey(i+""));
        }
        System.out.println("map.size="+map.size());
//        System.out.println("map="+map);


        return 0;
    }

}
