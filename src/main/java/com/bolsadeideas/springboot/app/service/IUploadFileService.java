package com.bolsadeideas.springboot.app.service;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {
	
	public Resource load(String filename) throws MalformedURLException;

	public String copy(MultipartFile file) throws IOException;

	public boolean delete(String filename);

	public void deleteAll();

	public void init() throws IOException;
	
	public void saveFile(MultipartFile file) throws IOException;
	
	 public void saveMultipleFiles(List<MultipartFile> files) throws IOException;
}
