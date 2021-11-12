package com.electronicid.rapidapi.services;

import com.electronicid.rapidapi.dto.Caption;
import com.electronicid.rapidapi.dto.Image;
import com.electronicid.rapidapi.dto.ImageResume;
import com.electronicid.rapidapi.dto.ResultsRapidApi;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.electronicid.rapidapi.utils.Constants.*;

@Service
public class ManageComputerVisionApiImpl implements ManageComputerVisionApi {

    private static Logger logger = LoggerFactory.getLogger(ManageComputerVisionApi.class);
    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<ImageResume> processImage(final List<Image> images) {
        List<ImageResume> resumeList = new ArrayList<>();
        images.forEach(image -> {
            processImageWithFaces(resumeList, image);
        });
        int imagesProcessedOk = resumeList.stream().filter(resume -> resume.isProcessedOk()).collect(Collectors.toList()).size();
        int totalImages = resumeList.size();
        logger.info(String.format(RAPID_API_IMAGES_PROCESSED_INFO, imagesProcessedOk, totalImages));
        return resumeList;
    }

    @Override
    public String sendPostToRapidApi(final Image image, final String visualFeatures) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_VALUE);
        String responseBody = "";
        try {
        	String json = mapper.writeValueAsString(image);
            RequestBody body = RequestBody.create(mediaType, json);
            Request request = new Request.Builder()
                    .url(String.format(MICROSOFT_AZURE_API_URL, visualFeatures))
                    .post(body)
                    .addHeader(CONTENT_TYPE, org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
                    .addHeader(X_RAPIDAPI_HOST, MICROSOFT_COMPUTER_VISION_HOST)
                    .addHeader(X_RAPIDAPI_KEY, RAPIDAPI_KEY)
                    .build();
            Response response = client.newCall(request).execute();
            responseBody = response.body().string();
        } catch (IOException ex) {
            logger.error(ERROR_CALLING_TO_RAPID_API);
        }
        return responseBody;
    }

    @Override
    public void processImageWithFaces(final List<ImageResume> resumeList, final Image image) {
        ImageResume resume = new ImageResume();
        String responseBodyFaces = sendPostToRapidApi(image, FACES);
        ResultsRapidApi results = null;
        try {
            results = mapper.readValue(responseBodyFaces, ResultsRapidApi.class);
            resume.setProcessedOk(ObjectUtils.isEmpty(results.getCode()));
        } catch (JsonProcessingException e) {
            resume.setProcessedOk(Boolean.FALSE);
        } finally {
            if(resume.isProcessedOk()) {
                resume.setFaces(results.getFaces());
                if(!CollectionUtils.isEmpty(results.getFaces())) {
                    resume.setFaceDetected(Boolean.TRUE);
                    resume.setNumberOfFaces(results.getFaces().size());
                    processImageWithDescription(image, resume);
                }
            }
            resumeList.add(resume);
        }
    }

    @Override
    public void processImageWithDescription(final Image image, final ImageResume resume) {
        String responseBodyDescription = sendPostToRapidApi(image, DESCRIPTION);
        ResultsRapidApi results = null;
        try {
            results = mapper.readValue(responseBodyDescription, ResultsRapidApi.class);
        } catch (JsonProcessingException e) {
            resume.setProcessedOk(Boolean.FALSE);
        } finally {
            if (resume.isProcessedOk()) {
                List<String> description = results.getDescription().getCaptions().stream().map(Caption::getText).collect(Collectors.toList());
                resume.setDescription(description);
            }
        }
    }
}
