package be.pxl.student.dao;

import be.pxl.student.entity.Label;

import java.util.List;

public interface LabelDao {
    List<Label> findAllLabels();
    Label findLabelById(long id);
    Label findLabelByName(String name);
    /*Label updateLabel(Label label);*/
    void saveLabel(Label label);
    void removeLabel(Label label);
}
