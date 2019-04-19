package com.youngth.zhou.is;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YoungTH
 * @date 2019/4/18
 */
public class MyBatisPlusGenerator {
    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] childrens = dir.list();
            //递归删除目录中的子目录下
            if (childrens != null) {
                for (String children : childrens) {
                    boolean success = deleteDir(new File(dir, children));
                    if (!success) {
                        return false;
                    }
                }
            }
        }

        return dir.delete();
    }

    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();

        //   String outputDir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java";
//        String outputDir = System.getProperty("user.dir") + File.separator + "test" ;
        String outputDir = "C:\\forRest" + File.separator + "test" ;
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(outputDir);
        //是否覆盖
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(false);
        gc.setAuthor("YoungTH");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");

        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.ORACLE);
        dsc.setTypeConvert(new OracleTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public DbColumnType processTypeConvert(String fieldType) {
                return super.processTypeConvert(fieldType);
            }
        });
        dsc.setDriverName("oracle.jdbc.driver.OracleDriver");
        dsc.setUsername("youngth");
        dsc.setPassword("zys123");
        dsc.setUrl("jdbc:oracle:thin:@//localhost:1521/XE");
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 此处可以修改为您的表前缀
        strategy.setTablePrefix("");
        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude("FORREST_USER");
        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(null);
        pc.setEntity("com.youngth.zhou.entity");
        pc.setMapper("com.youngth.zhou.is.mapper");
        pc.setXml("com.youngth.zhou.is.mapper");
        pc.setService("com.youngth.zhou.is.service");
        pc.setServiceImpl("com.youngth.zhou.is.service.impl");
        //本项目没用，生成之后删掉
        String controllerFolderName = "TTT";
        pc.setController(controllerFolderName);
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };
        mpg.setCfg(cfg);

        // 执行生成
        mpg.execute();

        //删除多余的文件
        deleteDir(new File(outputDir + File.separator + controllerFolderName));

        // 打印注入设置
        System.err.println(mpg.getCfg().getMap().get("abc"));
    }
}
