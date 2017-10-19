package server.models;

public class Result {
    int result;
    int nrQuestions;

    public Result(int result, int nrQuestions) {
        this.result = result;
        this.nrQuestions = nrQuestions;
    }

    public Result() {

    }

    public int getNrQuestions() {
        return nrQuestions;
    }

    public void setNrQuestions(int nrQuestions) {
        this.nrQuestions = nrQuestions;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
