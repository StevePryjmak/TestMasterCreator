package com.LerningBara.model;

import com.LerningBara.model.SingleChoiceQuestion;
import com.LerningBara.model.MultipleChoicesQuestion;
import com.LerningBara.model.OpenAnwserQuestion;
import QuestionData.SingleChoiceQuestionData;
import QuestionData.MultipleChoicesQuestionData;
import QuestionData.OpenAnwserQuestionData;
import QuestionData.AbstractQuestionData;

public class QuestionConventor {
    public static AbstractQuestion convertToQuestion(AbstractQuestionData data) {
        if (data instanceof SingleChoiceQuestionData) {
            try {
                return new SingleChoiceQuestion((SingleChoiceQuestionData) data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (data instanceof MultipleChoicesQuestionData) {
            try {
                return new MultipleChoicesQuestion((MultipleChoicesQuestionData) data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (data instanceof OpenAnwserQuestionData) {
            try {
                return new OpenAnwserQuestion((OpenAnwserQuestionData) data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
        // else {
        // throw new IllegalArgumentException("Unknown question type");
        // return null;
        // }
    }
}
