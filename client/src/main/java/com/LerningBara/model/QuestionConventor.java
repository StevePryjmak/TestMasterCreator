package com.LerningBara.model;

import QuestionData.SingleChoiceQuestionData;
import QuestionData.MultipleChoicesQuestionData;
import QuestionData.OpenAnwserQuestionData;
import QuestionData.AbstractQuestionData;
import QuestionData.SingleChoiceQuestionWithPictureData;
import QuestionData.MultipleChoicesQuestionWithPictureData;


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
        if (data instanceof SingleChoiceQuestionWithPictureData) {
            try {
                return new SingleChoiceQuestionWithPicture((SingleChoiceQuestionWithPictureData) data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (data instanceof MultipleChoicesQuestionWithPictureData) {
            try {
                return new MultipleChoicesQuestionWithPicture((MultipleChoicesQuestionWithPictureData) data);
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
