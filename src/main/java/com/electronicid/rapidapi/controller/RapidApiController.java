package com.electronicid.rapidapi.controller;

import com.electronicid.rapidapi.dto.Image;
import com.electronicid.rapidapi.dto.ImageResume;
import com.electronicid.rapidapi.services.ManageComputerVisionApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.electronicid.rapidapi.utils.Constants.*;

@CrossOrigin(origins = "*", methods = {RequestMethod.POST})
@RestController
public class RapidApiController {

	@Autowired
	private ManageComputerVisionApi computerVisionApi;

	@RequestMapping(value= PROCESS_IMAGES, method = RequestMethod.POST, produces = APPLICATION_JSON)
	public List<ImageResume> processImages(@RequestBody List<Image> images) {
		return computerVisionApi.processImage(images);
	}
}