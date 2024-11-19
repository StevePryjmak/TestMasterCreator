package com.LerningBara.model;

import com.LerningBara.model.SingleChoiceQuestion;
import QuestionData.SingleChoiceQuestionData;
import QuestionData.AbstractQuestionData;

public class QuestionConventor {
    public static AbstractQuestion convertToQuestion(AbstractQuestionData data) {
        if(data instanceof SingleChoiceQuestionData) {
            try {
                return new SingleChoiceQuestion((SingleChoiceQuestionData) data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
        // else {
        //     throw new IllegalArgumentException("Unknown question type");
        //     return null;
        // }
    }
}
