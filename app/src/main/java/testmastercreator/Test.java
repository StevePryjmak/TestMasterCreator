package testmastercreator;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Iterator;


public class Test implements Iterable<AbstractQuestion> {
    private List<AbstractQuestion> questions = new ArrayList<>();

    public Test() {}

    public Test(List<AbstractQuestion> questions) {
        this.questions = questions;
    }
    
    public Test(AbstractQuestion[] questions) {
        for (AbstractQuestion question : questions) {
            this.questions.add(question);
        }
    }

    public List<AbstractQuestion> getQuestions() {
        return questions;
    }

    public void removeQuestion(AbstractQuestion question) {
        questions.remove(question);
    }

    public void removeQuestion(int index) {
        questions.remove(index);
    }
    
    public void addQuestoin(AbstractQuestion question) {
        questions.add(question);
    }

    // iterator
    private class QuestionIterator implements Iterator<AbstractQuestion> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < questions.size();
        }

        @Override
        public AbstractQuestion next() {
            if (!hasNext()) {
                // @TODO something or hendle it there or in app class ...
                throw new NoSuchElementException();
            }
            return questions.get(currentIndex++);
        }
    }

    @Override
    public Iterator<AbstractQuestion> iterator() {
        return new QuestionIterator();
    }

}


