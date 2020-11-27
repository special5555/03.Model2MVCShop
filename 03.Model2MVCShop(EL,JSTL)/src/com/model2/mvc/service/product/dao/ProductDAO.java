package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;

public class ProductDAO {

	public ProductDAO() {
	}

	public void insertProduct(Product product) throws Exception {

		Connection con = DBUtil.getConnection();
		System.out.println("�߰�");

		String sql = "insert into PRODUCT values (seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
//		stmt.setInt(3, productVO.getProdNo());
		stmt.setString(3, product.getManuDate());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());
		int a = stmt.executeUpdate();

		if (a == 1) {
			System.out.println("�μ�Ʈ�� �Ϸ� �Ǿ����ϴ�");
		} else {
			System.out.println("����");
		}
		System.out.println();
		con.close();

	}

	public Product getProduct(int prodNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "select * from Product where PROD_NO=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		Product product = null;
		while (rs.next()) {
			product = new Product();
			product.setProdNo(rs.getInt("PROD_NO"));
			product.setProdName(rs.getString("PROD_NAME"));
			product.setProdDetail(rs.getString("PROD_DETAIL"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setRegDate(rs.getDate("REG_DATE"));
		}
		System.out.println("ProductDao getProductRegDate productVO �޾ƿ�" + product);
		con.close();

		return product;
	}

	public Map<String, Object> getProductList(Search search) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		Connection con = DBUtil.getConnection();

		// Original Query ����
		String sql = "select * from product   ";

		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0") && !search.getSearchKeyword().equals("")) {
				sql += " where PROD_NO LIKE '%" + search.getSearchKeyword() + "%'";
			} else if (search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
				sql += " where PROD_NAME LIKE'%" + search.getSearchKeyword() + "%'";
			} else if (search.getSearchCondition().equals("2") && !search.getSearchKeyword().equals("")) {
				sql += " where PRICE LIKE  '%" + search.getSearchKeyword() + "%'";
			}
		}
		sql += " order by PROD_NO";

		System.out.println("UserDAO::Original SQL :: " + sql);

		// ==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProdDAO:: totalCount ::" + totalCount);

		// ==> CurrentPage �Խù��� �޵��� Query �ٽñ���
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		System.out.println(search);

		List<Product> list = new ArrayList<Product>();

		while (rs.next()) {
			Product product = new Product();
			product.setProdNo(Integer.parseInt(rs.getString("prod_no")));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("MANUFACTURE_DAY"));
			product.setPrice(rs.getInt("PRICE"));
			product.setFileName(rs.getString("IMAGE_FILE"));
			product.setRegDate(rs.getDate("REG_DATE"));
			list.add(product);
		}

		// ==> totalCount ���� ����
		map.put("totalCount", new Integer(totalCount));
		// ==> currentPage �� �Խù� ���� ���� List ����
		map.put("list", list);

		rs.close();
		stmt.close();
		con.close();

		return map;
	}

	public void updateProduct(Product product) throws Exception {

		Connection con = DBUtil.getConnection();
		System.out.println(con + "DBUtil");
		String sql = "update PRODUCT set  prod_name=? , prod_detail=? , manufacture_day=? , price =? , image_file=? where prod_no = ? ";
		System.out.println(sql + "Dao updateProduct ������ ����");
		
		PreparedStatement stmt = con.prepareStatement(sql);

		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManuDate());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());
		System.out.println(product.getFileName());
		stmt.setInt(6, product.getProdNo());
		
		stmt.executeUpdate();
		con.close();
	}

// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception {

		sql = "SELECT COUNT(*) " + "FROM ( " + sql + ") countTable";

		Connection con = DBUtil.getConnection();
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		int totalCount = 0;
		if (rs.next()) {
			totalCount = rs.getInt(1);
		}

		stmt.close();
		con.close();
		rs.close();

		return totalCount;
	}

// �Խ��� currentPage Row ��  return 
	private String makeCurrentPageSql(String sql, Search search) {
		sql = "SELECT * " + "FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " + " 	FROM (	" + sql
				+ " ) inner_table " + "	WHERE ROWNUM <=" + search.getCurrentPage() * search.getPageSize() + " ) "
				+ "WHERE row_seq BETWEEN " + ((search.getCurrentPage() - 1) * search.getPageSize() + 1) + " AND "
				+ search.getCurrentPage() * search.getPageSize();

		System.out.println("ProductDAO :: make SQL :: " + sql);

		return sql;
	}
}
