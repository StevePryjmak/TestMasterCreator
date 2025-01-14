package com.LerningBara.controller.CreateTests;

import QuestionData.AbstractQuestionData;

public abstract class CreateAbstractQestionController {
    public boolean isEdit = false;
    public abstract AbstractQuestionData getQuestionData();
}
