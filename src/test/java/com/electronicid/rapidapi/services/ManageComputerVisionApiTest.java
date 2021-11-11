package com.electronicid.rapidapi.services;

import com.electronicid.rapidapi.dto.Image;
import com.electronicid.rapidapi.dto.ImageResume;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ManageComputerVisionApiTest {

    private static ObjectMapper mapper = new ObjectMapper();

    @Spy
    private ManageComputerVisionApiImpl computerVisionApi;

    @Test
    public void processImage_validImageOneFace_ok() {
        List<Image> images = new ArrayList<>();
        Image image = new Image();
        image.setUrl("https://s1.eestatic.com/2020/06/21/corazon/famosos/imagenes_del_dia-carlota_corredera-carmen_lomana_499460619_154193380_1706x960.jpg");
        images.add(image);
        List<ImageResume> resume = computerVisionApi.processImage(images);
        assertNotNull(resume.get(0).getFaces());
        assertEquals(resume.get(0).getFaces().size(), 1);
    }

    @Test
    public void processImage_validImageTwoFaces_ok() {
        List<Image> images = new ArrayList<>();
        Image image = new Image();
        image.setUrl("https://imgcuore3.elperiodico.com/db/e6/64/sebastian-yatra-ester-exposito-1200x686.jpg");
        images.add(image);
        List<ImageResume> resume = computerVisionApi.processImage(images);
        assertNotNull(resume.get(0).getFaces());
        assertEquals(resume.get(0).getFaces().size(), 2);
    }

    @Test
    public void processImage_twoValidImages_ok() {
        List<Image> images = new ArrayList<>();
        Image image1 = new Image();
        image1.setUrl("https://s1.eestatic.com/2020/06/21/corazon/famosos/imagenes_del_dia-carlota_corredera-carmen_lomana_499460619_154193380_1706x960.jpg");
        Image image2 = new Image();
        image2.setUrl("https://imgcuore3.elperiodico.com/db/e6/64/sebastian-yatra-ester-exposito-1200x686.jpg");
        images.add(image1);
        images.add(image2);
        List<ImageResume> resume = computerVisionApi.processImage(images);
        assertNotNull(resume.get(0).getFaces());
        assertEquals(resume.get(0).getFaces().size(), 1);
        assertNotNull(resume.get(1).getFaces());
        assertEquals(resume.get(1).getFaces().size(), 2);
    }

    @Test
    public void sendPostToRapidApi_validImageNoFaces_ok() {
        List<Image> images = new ArrayList<>();
        Image image = new Image();
        image.setUrl("https://static.wikia.nocookie.net/minion/images/2/21/Dave2.jpg/revision/latest?cb=20200728073512&path-prefix=es");
        images.add(image);
        List<ImageResume> resume = computerVisionApi.processImage(images);
        assertEquals(resume.get(0).getFaces().size(), 0);
        assertNull(resume.get(0).getDescription());
    }

    @Test
    public void sendPostToRapidApi_invalid_nok() {
        List<Image> images = new ArrayList<>();
        Image image = new Image();
        image.setUrl("http://example.com/images/test.jpg");
        images.add(image);
        List<ImageResume> resume = computerVisionApi.processImage(images);
        assertNull(resume.get(0).getFaces());
        assertNull(resume.get(0).getDescription());
    }

}
