package com.yj.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yj.dao.ProductMapper;
import com.yj.entity.Product;
import com.yj.pojo.PageResultBean;
import com.yj.pojo.ResultBean;
import com.yj.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 29029
 * @version 1.0
 * @time 17:59
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SolrClient solrClient;


    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResultBean synAllData() {
        //1.获取数据库的数据
        List<Product> list = productMapper.getList();
        //2.将数据导入到索引库中
        for (Product product : list) {
            //3.product -> solrInputDocument
            SolrInputDocument document = new SolrInputDocument();
            document.setField("id",product.getId());
            document.setField("product_name",product.getProName());
            document.setField("product_price",product.getProPrice());
            document.setField("product_sale_price",product.getProSalePrice());
            document.setField("product_images",product.getProImages());
            document.setField("product_sale_point",product.getProSalePoint());
            try {
                solrClient.add(document);
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
                return new ResultBean("false","数据添加到索引库失败！");
            }
        }
        try {
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
            return new ResultBean("false","数据提交到索引库失败！");
        }
        return new ResultBean("true","数据同步到索引库成功！");
    }

    /**
     * 根据关键字在索引库查询
     * @param
     * @return
     */
    @Override
    public PageResultBean<Product> queryByKeyWord(PageResultBean<Product> page) {
        String keyword = page.getKeyWord();
        SolrQuery solrQuery;
        if (StringUtils.isAnyEmpty(keyword)) {
            solrQuery = new SolrQuery("*:*");
        } else {
            solrQuery = new SolrQuery("product_keywords:" + keyword );
//            //开启高亮
//            solrQuery.setHighlight(true);
//            //设置前缀
//            solrQuery.setHighlightSimplePre("<font color='red'>");
//            //设置后缀
//            solrQuery.setHighlightSimplePost("</font>");
//            solrQuery.addHighlightField("product_name");
        }

        //分页查询的开始条数
        solrQuery.setStart((page.getPageNum()-1)*page.getPageSize());
        //每页条数
        solrQuery.setRows(page.getPageSize());

        /**
         * 查询所有相关信息
         */
        PageResultBean<Product> productPageResultBean = new PageResultBean<>();
        try {
            productPageResultBean = selectPage(page);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //执行查询
        List<Product> productList = new ArrayList<>();
        try {
            QueryResponse query = solrClient.query(solrQuery);

            SolrDocumentList results = query.getResults();
            Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();

            for (SolrDocument result : results) {
                Product product = new Product();
                product.setId(Long.parseLong(result.get("id") + ""));
                product.setProName(result.get("product_name") + "");
                product.setProPrice(Long.parseLong(result.get("product_price")+""));
                product.setProImages(result.get("product_images") + "");
                product.setProSalePoint(result.get("product_sale_point") + "");
                product.setProSalePrice(Long.parseLong(result.get("product_sale_price")+""));
                //判断当前的商品是否存在高亮
//                if (keyword != null || !keyword.trim().equals("")) {
//                    System.out.println(keyword == "");
//                }else {
//                    if (highlighting.containsKey(product.getId() + "")) {
//                        //当前商品存在高亮
//                        Map<String, List<String>> stringListMap = highlighting.get(product.getId() + "");
//                        //获得高亮的字段
//                        List<String> product_name = stringListMap.get("product_name");
//                        product.setProName(product_name.get(0));
//                    }
//                }
               productList.add(product);
            }

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        productPageResultBean.setList(productList);
        return productPageResultBean;
    }


    /**
     * 查询总条数
     * @return
     * @throws IOException
     */
    public  long getSolrIndexCount() throws IOException {
        long num = 0;
        try {
            SolrQuery params = new SolrQuery();
            params.set("q", "*:*");
            QueryResponse query = solrClient.query(params);
            SolrDocumentList docs = query.getResults();
            num = docs.getNumFound();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return num;
    }

    public PageResultBean<Product> selectPage(PageResultBean<Product> pageBean) throws IOException {
        //总条数
        long count = getSolrIndexCount();
        pageBean.setTotal(getSolrIndexCount());
        //总页数
        if (count % pageBean.getPageSize() == 0){
            pageBean.setPages(Integer.parseInt(count / pageBean.getPageSize()+""));
        }else {
            pageBean.setPages(Integer.parseInt(count / pageBean.getPageSize()+1+""));
        }
        //所有导航页
        int[] num = new int[pageBean.getPages()];
        for (int i = 0; i < pageBean.getPages(); i++) {
            num[i]=i+1;
        }
        pageBean.setNavigatepageNums(num);
        //设置最后一页
        pageBean.setFinallyPage(num.length);

        return pageBean;
    }

}
