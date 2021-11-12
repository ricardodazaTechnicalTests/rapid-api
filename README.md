# Processing images with Rapid Api

With this API you can process multiple images from one or more people looking at the front. 
This API returns face position, age and gender from each person on the image and an associated description.
For this application, we have used Microsoft Azure API with RapidAPI:

https://rapidapi.com/microsoft-azure-org-microsoft-cognitive-services/api/microsoft-computer-vision3

## How it works

Run the application
```
./gradlew run
```
Send post to localhost:8080/processImages with json:
```
[
  {
    "url": "my_image_url_1.jpg"
  },
  {
    "url": "my_image_url_2.jpg"
  }
]
```
Consider that it must be a valid URL.

After a few seconds of evaluation, you will receive a response like this:
```
[
    {
        "faces": [
            {
                "age": 23,
                "gender": "Female",
                "faceRectangle": {
                    "left": 649,
                    "top": 123,
                    "width": 328,
                    "height": 328
                }
            }
        ],
        "description": [
            "a woman taking a selfie in a room"
        ],
        "processedOk": true,
        "faceDetected": true,
        "numberOfFaces": 1
    },
    {
        "faces": [
            {
                "age": 30,
                "gender": "Female",
                "faceRectangle": {
                    "left": 808,
                    "top": 129,
                    "width": 213,
                    "height": 213
                }
            },
            {
                "age": 32,
                "gender": "Male",
                "faceRectangle": {
                    "left": 256,
                    "top": 131,
                    "width": 179,
                    "height": 179
                }
            }
        ],
        "description": [
            "a couple of people posing for the camera"
        ],
        "processedOk": true,
        "faceDetected": true,
        "numberOfFaces": 2
    },
    {
        "faces": [],
        "description": [
            "a close up of a person"
        ],
        "processedOk": true,
        "faceDetected": false,
        "numberOfFaces": 0
    }
]
```

You can also run the application with Docker. You just need to run in this directory the next command:
```
docker-compose up
```
<span style="color:red">Important</span>: You will need to install docker previously on your computer. See https://www.docker.com/get-started