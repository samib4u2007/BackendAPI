package com.example.demo.service;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.example.demo.model.Model;

@Service
public class ImageService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public boolean checkImage(String id) {
		try {
			int i = jdbcTemplate.queryForObject("Select id from images where id=?", new Object[] { id }, Integer.class);
		} catch (EmptyResultDataAccessException e) {
			return false;
		}

		return true;
	}

	public List<Model> getAllImages() {
		List<Model> list;
		try {
		list =(List<Model>) jdbcTemplate.queryForObject("SELECT id FROM IMAGES", new RowMapper() {

			@Override
			public List<Model> mapRow(ResultSet rs, int rowNum) throws SQLException {
				ArrayList<Model> list = new  ArrayList<>();
				do {
					Model model = new Model();
					model.setId(rs.getString("id"));
					
					list.add(model);
					 
				}while(rs.next());
				return list;
			}
		});}catch (EmptyResultDataAccessException e) {
			// TODO: handle exception
			return new ArrayList<Model>();
		}
		
		return list;
	}

	public byte[] getImage(String id) throws SQLException {

		Blob blob = jdbcTemplate.queryForObject("SELECT image FROM IMAGES WHERE ID=?", new Object[] { id }, Blob.class);
		byte[] image = blob.getBytes(1, (int) blob.length());
		return image;
	}

	public void storeImage(String id, byte[] image) throws SerialException, SQLException {
		Blob blob = new SerialBlob(image);
		jdbcTemplate.update("Insert into images values (?,?)", new Object[] { id, blob });
	}

}
