package com.electronicid.rapidapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ImageResume {

    private List<Face> faces;
    private List<String> description;
    private Boolean processedOk = true;
    private Boolean faceDetected = false;
    private Integer numberOfFaces = 0;

    public ImageResume() {
    }

    public List<Face> getFaces() {
        return faces;
    }

    public void setFaces(List<Face> faces) {
        this.faces = faces;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public Boolean isProcessedOk() {
        return processedOk;
    }

    public void setProcessedOk(Boolean processedOk) {
        this.processedOk = processedOk;
    }

    public Boolean isFaceDetected() {
        return faceDetected;
    }

    public void setFaceDetected(Boolean faceDetected) {
        this.faceDetected = faceDetected;
    }

    public Integer getNumberOfFaces() {
        return numberOfFaces;
    }

    public void setNumberOfFaces(Integer numberOfFaces) {
        this.numberOfFaces = numberOfFaces;
    }
}
