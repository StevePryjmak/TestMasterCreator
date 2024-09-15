package testmastercreator;

import java.util.*;


public class Test implements Iterable<AbstractQuestion> {
    private List<AbstractQuestion> questions = new ArrayList<>();
    private boolean shuffle = false;

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

    // iterators
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

    private class ShuffledQuestionIterator implements Iterator<AbstractQuestion> {
        private List<AbstractQuestion> shuffledQuestions;
        private int currentIndex = 0;

        public ShuffledQuestionIterator() {
            shuffledQuestions = new ArrayList<>(questions);
            Collections.shuffle(shuffledQuestions);  // Shuffle the questions list
        }

        @Override
        public boolean hasNext() {
            return currentIndex < shuffledQuestions.size();
        }

        @Override
        public AbstractQuestion next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return shuffledQuestions.get(currentIndex++);
        }
    }


    @Override
    public Iterator<AbstractQuestion> iterator() {
        if (shuffle) {
            return new ShuffledQuestionIterator();
        } else {
            return new QuestionIterator();
        }
    }

}
