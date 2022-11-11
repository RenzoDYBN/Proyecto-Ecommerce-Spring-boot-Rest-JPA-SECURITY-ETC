package com.ecommerce.renzo.personal.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {
	
	private String folder="images//";
	
	public String saveImage(MultipartFile file) throws IOException {
		
		//Objeto multipartfile para cargar data de imagenes
		if(!file.isEmpty()) {
			//PASAR IMAGEN A BYTES PARA ENVIAR LA IMAGEN DEL CLIENTE A SERVIDOR
			//CONVERTIR A 0 Y 1 PARA ENVIARLO AL SERVIDOR
			//COMO PUEDE LANZARSE UN ERROR , AUTOMATICAMENTE ESTA SENTENCIA DE CODIGO
			// TE INDICA QUE DEBERIAS USAR UN THROWS IOXException
			byte[] bytes = file.getBytes();
			//definir la ubicacion donde se guardara la imagen (LA RUTA)
			Path path = Paths.get(folder+file.getOriginalFilename());
			//REALIZAMOS EL GUARDADO DE LA IMAGEN
			Files.write(path, bytes);
			//RETORNAMOS EL NOMBRE DE LA IMAGEN
			return file.getOriginalFilename();
			
		}
		//EN EL CASO QUE NO SUBA LA IMAGEN LA PERSONA ENTONCES , AUTOMTICAMENTE
		// DEVOLVERA UNA IMAGEN DEFAULT PARA EL PRODUCTO.
		return "default.jpg";
	}
	
	public void deleteImage(String nombre) {
		String ruta = "images//";
		File file = new File(ruta+nombre);
		file.delete();
	}
	
	
	
}
