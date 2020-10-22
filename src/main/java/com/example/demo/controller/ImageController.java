package com.example.demo.controller;

import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Model;
import com.example.demo.service.ImageService;


@RestController
public class ImageController {
	
	
	@Autowired
	private ImageService imageService;
	
	
	@RequestMapping(value = "/{id:.+}", method = RequestMethod.GET)
	
	public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) throws SerialException, SQLException {
		if(!imageService.checkImage(id)) {
			
		
		String url ="https://source.unsplash.com/random";
		RestTemplate template = new RestTemplate();
		ResponseEntity<byte[]> exchan=template.exchange(url, HttpMethod.GET, null, byte[].class);
	    byte[] image = exchan.getBody();
	    imageService.storeImage(id, image);
	    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
	    
	}
	else {
		byte[] image = imageService.getImage(id);
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
		
	}
	}
	
	@RequestMapping(value ="/images" ,method = RequestMethod.GET)
	public List<Model> allImages() {
		
		return imageService.getAllImages();
		
	}
}
