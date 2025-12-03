package com.upn.reclutamiento.demo.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ArchivoController {

	@GetMapping("/uploads/cv/{filename:.+}")
	public ResponseEntity<Resource> servirArchivo(@PathVariable String filename) {
	    try {
	        Path ruta = Paths.get("uploads/cv").resolve(filename).normalize();
	        Resource recurso = new UrlResource(ruta.toUri());

	        if (!recurso.exists()) {
	            return ResponseEntity.notFound().build();
	        }

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + recurso.getFilename() + "\"")
	                .contentType(MediaType.APPLICATION_PDF) 
	                .body(recurso);
	    } catch (MalformedURLException e) {
	        return ResponseEntity.badRequest().build();
	    }
	}
}

