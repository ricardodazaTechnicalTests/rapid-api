package com.electronicid.rapidapi.services;

import com.electronicid.rapidapi.dto.Image;
import com.electronicid.rapidapi.dto.ImageResume;

import java.util.List;

public interface ManageComputerVisionApi {

    List<ImageResume> processImage(final List<Image> image);

    String sendPostToRapidApi(final Image image, final String visualFeatures);

    void processImageWithFaces(final List<ImageResume> resumeList, final Image image);

    void processImageWithDescription(final Image image, final ImageResume resume);
}
