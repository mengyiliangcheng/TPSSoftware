package com.jxggdxw.www;

public class GoodTable {
}

class DatabaseTable{
	private String table_name;
	private TableElement[] element;
	
	public DatabaseTable(String name,TableElement[] elem){
		table_name = name;
		element = elem;
	}
	
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public TableElement[] getElement() {
		return element;
	}
	public void setElement(TableElement[] element) {
		this.element = element;
	}
	
	
}

class TableElement{
	
	private String name;
	private String size;
	private String type;
	
	public TableElement(String name,String size, String type){
		this.name = name;
		this.size = size;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
