package ch.audacus.management.table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ITable {
	
	public String getName();
	
	public String toString();
	
	public ResultSet getAll() throws SQLException;
	
	public ResultSet getByPrimary(int id) throws SQLException;
	
	public ResultSet getByField(String field, String value) throws SQLException;
	
	public ResultSet insert(HashMap<String, Object> parameters) throws SQLException;
	
	public int deleteAll()throws SQLException;
	
	public int deleteByPrimary(int id) throws SQLException;
	
	public int deleteByField(String field, String value) throws SQLException;
}
