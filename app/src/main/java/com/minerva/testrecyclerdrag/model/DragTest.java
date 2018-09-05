package com.minerva.testrecyclerdrag.model;

/**
 * Created by Abdulrahman on 9/3/2018.
 */

public class DragTest {

    private String Name;
    private String Image;

    public DragTest() {
    }

    public DragTest(String name, String image) {
        Name = name;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
